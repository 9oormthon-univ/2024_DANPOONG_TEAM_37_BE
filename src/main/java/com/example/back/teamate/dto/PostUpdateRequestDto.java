package com.example.back.teamate.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostUpdateRequestDto {
	private String title; // 제목 (null 가능)
	private String content; // 내용 (null 가능)
	private Integer totalMembers; // 모집 인원
	private Integer expectedPeriod; // 예상 기간
	private LocalDate startDate; // 시작 날짜 (null 가능)
	private LocalDate deadline; // 마감 날짜 (null 가능)
	private String mode;
	private String field; // 모집 분야 (null 가능)
	private String googleFormUrl; // 구글 폼 URL (null 가능)
	private String kakaoChatUrl; // 카카오 채팅 URL (null 가능)
	private String portfolioUrl; // 포트폴리오 URL (null 가능)
	private List<PositionRequestDto> positionList; // 포지션과 기술 스택 리스트 (null 가능)

	@Getter
	@Setter
	public static class PositionRequestDto implements PositionRequest {
		private String position;
		private List<String> skills;
	}
}
