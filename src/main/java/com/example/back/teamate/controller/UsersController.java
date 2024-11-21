package com.example.back.teamate.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.back.teamate.dto.RedisUserInfoDto;
import com.example.back.teamate.service.UsersService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UsersController {

	private final UsersService usersService;

	@GetMapping("/secure-endpoint")
	public ResponseEntity<?> secureEndpoint(@RequestHeader("Authorization") String authHeader) {
		try {
			String accessToken = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
			RedisUserInfoDto userData = usersService.validateToken(accessToken);
			return ResponseEntity.ok("Hello, " + userData.getNickname());
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}


}
