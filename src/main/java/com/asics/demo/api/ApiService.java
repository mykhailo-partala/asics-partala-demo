package com.asics.demo.api;

import java.time.Duration;

public interface ApiService {

    boolean isActive();

    String getId();

    ApiResponse execute();

    default void simulateWork(Duration duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted", e);
        }
    }
}
