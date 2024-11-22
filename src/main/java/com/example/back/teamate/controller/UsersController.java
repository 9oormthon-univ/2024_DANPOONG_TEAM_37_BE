package com.example.back.teamate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.back.teamate.dto.ApiResponse;
import com.example.back.teamate.dto.PropositionSettingsDto;
import com.example.back.teamate.dto.RedisUserInfoDto;
import com.example.back.teamate.dto.UserDetailsDto;
import com.example.back.teamate.service.TokenAuthenticationService;
import com.example.back.teamate.service.UsersService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UsersController {

	private final UsersService usersService;
	private final TokenAuthenticationService tokenAuthService;

	@GetMapping("/secure-endpoint")
	public ResponseEntity<ApiResponse<String>> secureEndpoint(@RequestHeader("Authorization") String authHeader) {
		RedisUserInfoDto userData = tokenAuthService.authenticateUser(authHeader);
		String greeting = "Hello, " + userData.getNickname();
		return ResponseEntity.ok(ApiResponse.createSuccess(greeting));
	}

	@PatchMapping("/proposition-settings")
	public ResponseEntity<ApiResponse<Void>> updatePropositionSettings(
		@RequestHeader("Authorization") String authHeader,
		@RequestBody PropositionSettingsDto propositionSettingsDto) {
		RedisUserInfoDto userData = tokenAuthService.authenticateUser(authHeader);
		usersService.updatePropositionSettings(userData.getId(), propositionSettingsDto);
		return ResponseEntity.ok(ApiResponse.createSuccessWithNoContent());
	}

	@GetMapping("/info")
	public ResponseEntity<ApiResponse<UserDetailsDto>> getUserDetails(
		@RequestHeader("Authorization") String authHeader) {
		RedisUserInfoDto userData = tokenAuthService.authenticateUser(authHeader);
		UserDetailsDto userDetails = usersService.getUserDetails(userData.getId());
		return ResponseEntity.ok(ApiResponse.createSuccess(userDetails));
	}

	@PatchMapping("/info")
	public ResponseEntity<ApiResponse<Void>> updateUserInfo(
		@RequestHeader("Authorization") String authHeader,
		@RequestBody UserDetailsDto updateDto) {
		RedisUserInfoDto userData = tokenAuthService.authenticateUser(authHeader);
		usersService.updateUserDetails(userData.getId(), updateDto);
		return ResponseEntity.ok(ApiResponse.createSuccessWithContent("개인정보 수정을 완료하였습니다."));
	}


}
