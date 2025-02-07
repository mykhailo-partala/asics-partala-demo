package com.asics.demo.api;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ThirdApiService implements ApiService {

    private static final long WORK_SIMULATION_MIN_DURATION_MILLIS = 1500;
    private static final long WORK_SIMULATION_MAX_DURATION_MILLIS = 2500;

    @Override
    public String getId() {
        return "3";
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public ApiResponse execute() {
        long randomMillis = ThreadLocalRandom.current()
                .nextLong(WORK_SIMULATION_MIN_DURATION_MILLIS, WORK_SIMULATION_MAX_DURATION_MILLIS);
        simulateWork(Duration.ofMillis(randomMillis));
        return ApiResponse.builder()
                .serviceId(getId())
                .data(UUID.randomUUID().toString())
                .build();
    }

}
