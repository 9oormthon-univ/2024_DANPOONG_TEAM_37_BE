package com.example.back.teamate.controller;

import com.example.back.teamate.dto.ApiResponse;
import com.example.back.teamate.dto.ApplicationRequestDto;
import com.example.back.teamate.dto.ApplicationListResponseDto;
import com.example.back.teamate.dto.RedisUserInfoDto;
import com.example.back.teamate.service.ApplicationService;
import com.example.back.teamate.service.TokenAuthenticationService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final TokenAuthenticationService tokenAuthenticationService;
    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<?> createApplication(@RequestHeader("Authorization") String authHeader, @RequestBody @Valid ApplicationRequestDto applicationRequestDto) {
            RedisUserInfoDto redisUserInfoDto = tokenAuthenticationService.authenticateUser(authHeader);

        Long applicationId = applicationService.createApplication(redisUserInfoDto.getId(), applicationRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(Map.of("message", "Application created successfully."));
    }

    @GetMapping("/applications")
    public ResponseEntity<ApiResponse<List<ApplicationListResponseDto>>> getApplicationsByUserId(@RequestHeader("Authorization") String authHeader,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size) {
        // 사용자 인증 및 userId 검증
        RedisUserInfoDto authenticatedUser = tokenAuthenticationService.authenticateUser(authHeader);
        // 지원서 목록 조회
        List<ApplicationListResponseDto> applications = applicationService.getApplicationsByAuthenticatedUser(authHeader, page, size);
        return ResponseEntity.ok(ApiResponse.createSuccess(applications));
    }
}
