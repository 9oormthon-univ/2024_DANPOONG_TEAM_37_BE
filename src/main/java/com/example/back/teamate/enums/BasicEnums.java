package com.example.back.teamate.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

public class BasicEnums {

	@RequiredArgsConstructor
	public enum Job {
		STUDENT("학생"),
		JOB_SEEKER("취준생"),
		OFFICE_WORKER("직장인"),
		POSTGRADUATE_STUDENT("대학원생");

		private final String koreanName;

		@JsonValue
		public String getKoreanName() {
			return koreanName;
		}

		@JsonCreator
		public static Job fromKoreanName(String koreanName) {
			return Arrays.stream(values())
				.filter(job -> job.koreanName.equals(koreanName))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 직업: " + koreanName));
		}
	}

	@RequiredArgsConstructor
	public enum Gender {
		MALE("남성"),
		FEMALE("여성");

		private final String koreanName;

		@JsonValue
		public String getKoreanName() {
			return koreanName;
		}

		@JsonCreator
		public static Gender fromKoreanName(String koreanName) {
			return Arrays.stream(values())
				.filter(gender -> gender.koreanName.equals(koreanName))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 성별: " + koreanName));
		}
	}

	public enum MBTI {
		INTJ, INTP, ENTJ, ENTP, INFJ, INFP, ENFJ, ENFP, ISTJ, ISFJ, ESTJ, ESFJ, ISTP, ISFP, ESTP, ESFP;
	}

	@RequiredArgsConstructor
	public enum Position {
		FRONTEND("프론트엔드"),
		BACKEND("백엔드"),
		FULLSTACK("풀스택"),
		DESIGNER("디자이너"),
		PM("프로젝트 매니저"),
		OTHER("기타");

		private final String koreanName;

		@JsonValue
		public String getKoreanName() {
			return koreanName;
		}

		@JsonCreator
		public static Position fromKoreanName(String koreanName) {
			return Arrays.stream(values())
				.filter(position -> position.koreanName.equals(koreanName))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 포지션: " + koreanName));
		}
	}

	@RequiredArgsConstructor
	public enum Mode {
		ONLINE("온라인"),
		OFFLINE("오프라인");

		private final String koreanName;

		@JsonValue
		public String getKoreanName() {
			return koreanName;
		}

		@JsonCreator
		public static Mode fromKoreanName(String koreanName) {
			return Arrays.stream(values())
				.filter(mode -> mode.koreanName.equals(koreanName))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 모드: " + koreanName));
		}
	}

	@RequiredArgsConstructor
	public enum Region {
		SEOUL("서울"),
		GYEONGGI("경기"),
		INCHEON("인천");

		private final String koreanName;

		@JsonValue
		public String getKoreanName() {
			return koreanName;
		}

		@JsonCreator
		public static Region fromKoreanName(String koreanName) {
			return Arrays.stream(values())
				.filter(region -> region.koreanName.equals(koreanName))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 지역: " + koreanName));
		}
	}
}
