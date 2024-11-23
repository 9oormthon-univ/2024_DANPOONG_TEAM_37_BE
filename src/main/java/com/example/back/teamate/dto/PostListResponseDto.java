package com.example.back.teamate.dto;

import java.util.List;

import com.example.back.teamate.enums.FieldName;
import com.example.back.teamate.enums.PositionName;
import com.example.back.teamate.enums.SkillName;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PostListResponseDto {
	private Long postId;
	private String title;
	private FieldName field;
	private List<SkillName> skill;
	private List<PositionName> position;
	private String deadline;
	private AuthorInfo author;        // 작성자 정보

	@Builder
	@Data
	public static class AuthorInfo {
		private String nickname;       // 작성자 닉네임
		private String profileImage;   // 작성자 프로필 이미지
	}
}


