package com.asics.demo.api;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
public class SecondApiService implements ApiService {

    @Override
    public String getId() {
        return "2";
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public ApiResponse execute() {
        simulateWork(Duration.ofMillis(2500));
        return ApiResponse.builder()
                .serviceId(getId())
                .data(UUID.randomUUID().toString())
                .build();
    }

}
