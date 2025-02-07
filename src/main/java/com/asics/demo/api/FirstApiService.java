package com.asics.demo.api;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
public class FirstApiService implements ApiService {

    @Override
    public String getId() {
        return "1";
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public ApiResponse execute() {
        simulateWork(Duration.ofMillis(50));
        return ApiResponse.builder()
                .serviceId(getId())
                .data(UUID.randomUUID().toString())
                .build();
    }

}
