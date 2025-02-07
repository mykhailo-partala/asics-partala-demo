package com.asics.demo.api;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@Slf4j
public class ApiComponent {

    private final List<ApiService> services;
    private final ExecutorService executorService;

    private static final int THREAD_POOL_SIZE = Math.max(1, Runtime.getRuntime().availableProcessors() / 2);
    private static final int THREADS_MAX_EXECUTION_TIME = 2;

    public ApiComponent(List<ApiService> services) {
        this.services = services;
        this.executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    }

    public List<DetailedApiResponse> executeAll() {
        List<Callable<DetailedApiResponse>> tasks = services.stream()
                .filter(ApiService::isActive)
                .map(ApiComponent::getExecutionTask)
                .toList();
        return invokeTasks(tasks);
    }

    public Optional<ApiResponse> execute(String serviceId) {
        return services.stream()
                .filter(ApiService::isActive)
                .filter(service -> service.getId().equals(serviceId))
                .findFirst()
                .map(ApiService::execute);
    }

    private List<DetailedApiResponse> invokeTasks(List<Callable<DetailedApiResponse>> tasks) {

        List<Future<DetailedApiResponse>> results;
        try {
            results = executorService.invokeAll(tasks, THREADS_MAX_EXECUTION_TIME, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Can't execute tasks! Execution time > " + THREADS_MAX_EXECUTION_TIME + " seconds!", e);
            return List.of();
        }

        long failedTasks = results.stream()
                .filter(Future::isCancelled)
                .count();

        if (failedTasks > 0) {
            log.debug("{} tasks were cancelled or timed out.", failedTasks);
        }

        return results.stream()
                .map(ApiComponent::getFutureResult)
                .filter(Objects::nonNull)
                .flatMap(Optional::stream)
                .toList();
    }

    private static Callable<DetailedApiResponse> getExecutionTask(ApiService apiService) {
        return () -> {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            ApiResponse apiResponse = apiService.execute();
            stopWatch.stop();
            return DetailedApiResponse.builder(apiResponse)
                    .executionTime(stopWatch.getTotalTimeMillis())
                    .build();
        };
    }

    private static Optional<DetailedApiResponse> getFutureResult(Future<DetailedApiResponse> future) {
        try {
            return Optional.of(future.get(THREADS_MAX_EXECUTION_TIME, TimeUnit.SECONDS));
        } catch (TimeoutException e) {
            log.warn("Future result timed out after {} seconds", THREADS_MAX_EXECUTION_TIME);
            future.cancel(true);
            return Optional.empty();
        } catch (CancellationException | ExecutionException e) {
            log.debug("Can't get future result: {}", e.getMessage(), e);
            return Optional.empty();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.debug("Thread was interrupted", e);
            return Optional.empty();
        }
    }

    @PreDestroy
    public void shutdownExecutorService() {
        if (!executorService.isShutdown()) {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}
