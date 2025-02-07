package com.asics.demo.api;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("demo")
@AllArgsConstructor
public class ApiController {

    private ApiComponent apiComponent;

    @GetMapping("services/execute-all")
    public List<DetailedApiResponse> executeAll() {
        return apiComponent.executeAll();
    }

    @GetMapping("services/execute/{serviceId}")
    public ResponseEntity<ApiResponse> execute(@PathVariable String serviceId) {
        return apiComponent.execute(serviceId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
