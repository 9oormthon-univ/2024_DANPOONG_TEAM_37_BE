package com.example.back.teamate.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccessTokenResponseDto {
	private String accessToken;
	private RedisUserInfoDto userData; // 사용자 정보
}