package com.nevin.sunny.controller;

import com.nevin.sunny.pojo.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nevinsunny
 * date 02/04/24
 * time 5:00 pm
 */

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class HealthCheckController {

    @GetMapping("/health-check")
    public ResponseEntity<BaseResponse> getMyResponse() {
        return ResponseEntity.ok(BaseResponse.builder()
                .data("Success")
                .code(HttpStatus.OK)
                .build());
    }
}
