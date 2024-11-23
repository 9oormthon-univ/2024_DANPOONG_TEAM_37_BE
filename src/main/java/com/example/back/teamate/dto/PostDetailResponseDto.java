package com.example.back.teamate.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.example.back.teamate.enums.FieldName;
import com.example.back.teamate.enums.ModeName;
import com.example.back.teamate.enums.PositionName;
import com.example.back.teamate.enums.SkillName;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PostDetailResponseDto {

	private Long postId;                            // 게시글 ID
	private HeaderInfo header;                      // 헤더 정보
	private AuthorInfo author;                      // 작성자 정보
	private CategoryInfo category;                  // 카테고리 정보
	private String content;                         // 모집글 내용

	@Builder
	@Data
	public static class HeaderInfo {
		private String title;                       // 제목
		private String deadline;                    // 모집 마감일 ("D-10" 형태)
		private String createdAt;                  // 작성일
	}

	@Builder
	@Data
	public static class AuthorInfo {
		private String nickname;                   // 작성자 닉네임
		private String profileImage;              // 작성자 프로필 이미지
	}

	@Builder
	@Data
	public static class CategoryInfo {
		private int totalMembers;                 // 총 모집 인원
		private int currentMembers;               // 현재 인원
		private ModeName mode;                    // 진행 방식 (온라인/오프라인)
		private FieldName field;                  // 모집 분야 (프로젝트/스터디)
		private String startDate;                   // 시작 예정 날짜
		private Map<PositionName, List<SkillName>> positionSkills; // 포지션별 스킬 매핑
		private String googleFormUrl;             // 구글 폼 URL
		private String kakaoChatUrl;              // 오픈 톡 URL
		private String portfolioUrl;              // 포트폴리오 URL
	}
}