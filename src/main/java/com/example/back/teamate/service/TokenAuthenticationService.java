package com.example.back.teamate.service;

import org.springframework.stereotype.Service;

import com.example.back.teamate.dto.RedisUserInfoDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenAuthenticationService {

	private final UsersService usersService;

	public RedisUserInfoDto authenticateUser(String authHeader) {
		// Authorization 헤더에서 "Bearer " 제거
		String accessToken = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;

		// 토큰 검증 및 사용자 정보 반환
		return usersService.validateToken(accessToken);
	}
}