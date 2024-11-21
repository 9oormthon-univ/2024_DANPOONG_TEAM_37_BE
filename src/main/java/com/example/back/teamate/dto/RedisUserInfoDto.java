package com.example.back.teamate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RedisUserInfoDto {

	private Long id;
	private Long kakaoId;
	private String nickname;
}
