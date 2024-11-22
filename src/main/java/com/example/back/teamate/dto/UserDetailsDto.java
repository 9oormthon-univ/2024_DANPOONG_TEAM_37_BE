package com.example.back.teamate.dto;

import com.example.back.teamate.enums.BasicEnums;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserDetailsDto {
	private int age; // 나이
	private BasicEnums.Job job; // 직업
	private BasicEnums.Gender gender; // 성별
	private BasicEnums.MBTI mbti; // MBTI
	private BasicEnums.Position position; // 포지션
	private BasicEnums.Mode mode; // 선호방식
	private BasicEnums.Region region; // 대권역
	private String city; // 도시
	private String district; // 구
}