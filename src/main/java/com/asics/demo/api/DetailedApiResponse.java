package com.asics.demo.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class DetailedApiResponse extends ApiResponse {
    private long executionTime;

    public DetailedApiResponse(ApiResponse instance) {
        super(instance.toBuilder());
    }

    public static DetailedApiResponse.DetailedApiResponseBuilder<?, ?> builder(ApiResponse instance) {
        return new DetailedApiResponse(instance).toBuilder();
    }
}
