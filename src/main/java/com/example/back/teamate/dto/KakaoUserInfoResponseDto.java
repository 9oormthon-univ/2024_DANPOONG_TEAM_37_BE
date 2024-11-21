package com.example.back.teamate.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserInfoResponseDto {

	@JsonProperty("id")
	private Long id;

	@JsonProperty("properties")
	private Map<String, String> properties;

	@JsonProperty("kakao_account")
	private KakaoAccount kakaoAccount;

	@Getter
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class KakaoAccount {

		@JsonProperty("profile_needs_agreement")
		private Boolean profileNeedsAgreement;

		@JsonProperty("profile_nickname_needs_agreement")
		private Boolean profileNicknameNeedsAgreement;

		@JsonProperty("profile_image_needs_agreement")
		private Boolean profileImageNeedsAgreement;

		@JsonProperty("profile")
		private Profile profile;

		@JsonProperty("email_needs_agreement")
		private Boolean emailNeedsAgreement;

		@JsonProperty("is_email_valid")
		private Boolean isEmailValid;

		@JsonProperty("is_email_verified")
		private Boolean isEmailVerified;

		@JsonProperty("email")
		private String email;

		@Getter
		@NoArgsConstructor
		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class Profile {
			@JsonProperty("nickname")
			private String nickname;

			@JsonProperty("thumbnail_image_url")
			private String thumbnailImageUrl;

			@JsonProperty("profile_image_url")
			private String profileImageUrl;

			@JsonProperty("is_default_image")
			private Boolean isDefaultImage;

			@JsonProperty("is_default_nickname")
			private Boolean isDefaultNickname;
		}
	}
}
