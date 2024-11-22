package com.example.back.teamate.entity;

import com.example.back.teamate.enums.BasicEnums;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Users extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 사용자 ID

	@Column(nullable = false, unique = true)
	private Long kakaoId; // 카카오 사용자 ID

	@Column(nullable = false)
	private String email; // 이메일

	private String kakaoTalkId; // 카카오톡 ID

	private String nickname; // 닉네임

	private String profileImage; // 프로필 이미지

	private int age; // 나이

	@Enumerated(EnumType.STRING)
	private BasicEnums.Job job; // 직업

	@Enumerated(EnumType.STRING)
	private BasicEnums.Gender gender; // 성별

	@Enumerated(EnumType.STRING)
	private BasicEnums.MBTI mbti; // MBTI

	@Enumerated(EnumType.STRING)
	private BasicEnums.Position position; // 포지션

	@Enumerated(EnumType.STRING)
	private BasicEnums.Mode mode; // 선호방식

	@Enumerated(EnumType.STRING)
	private BasicEnums.Region region; // 대권역

	private String city; // 도시
	private String district; // 구

	private boolean isActiveProject; // 프로젝트 활성화 여부
	private boolean isActiveStudy; // 스터디 활성화 여부

	// 기타 필드와 Getter/Setter
	public void updatePropositionSettings(boolean isActiveProject, boolean isActiveStudy) {
		this.isActiveProject = isActiveProject;
		this.isActiveStudy = isActiveStudy;
	}

	public void updateDetails(
		Integer age,
		BasicEnums.Job job,
		BasicEnums.Gender gender,
		BasicEnums.MBTI mbti,
		BasicEnums.Position position,
		BasicEnums.Mode mode,
		BasicEnums.Region region,
		String city,
		String district, String kakaoTalkId) {
		if (age != null) this.age = age;
		if (job != null) this.job = job;
		if (gender != null) this.gender = gender;
		if (mbti != null) this.mbti = mbti;
		if (position != null) this.position = position;
		if (mode != null) this.mode = mode;
		if (region != null) this.region = region;
		if (city != null) this.city = city;
		if (district != null) this.district = district;
		if (kakaoTalkId != null) this.kakaoTalkId = kakaoTalkId;
	}



	public void changeEmail(String newEmail) {
		this.email = newEmail;
	}
}
