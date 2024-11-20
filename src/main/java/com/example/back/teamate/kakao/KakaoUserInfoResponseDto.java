package com.example.back.teamate.kakao;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserInfoResponseDto {

	@JsonProperty("id")
	public Long id;

	@JsonProperty("has_signed_up")
	public Boolean hasSignedUp;

	@JsonProperty("properties")
	public HashMap<String, String> properties;

	@JsonProperty("kakao_account")
	public KakaoAccount kakaoAccount;

	@JsonProperty("for_partner")
	public Partner partner;

	// 동의한 권한 범위 (scope)
	@JsonProperty("scope")
	public String scope;

	@Getter
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public class KakaoAccount {
		@JsonProperty("profile_needs_agreement")
		public Boolean isProfileAgree;

		@JsonProperty("profile_nickname_needs_agreement")
		public Boolean isNickNameAgree;

		@JsonProperty("profile_image_needs_agreement")
		public Boolean isProfileImageAgree;

		@JsonProperty("profile")
		public Profile profile;

		@JsonProperty("email_needs_agreement")
		public Boolean isEmailAgree;

		@JsonProperty("is_email_valid")
		public Boolean isEmailValid;

		@JsonProperty("is_email_verified")
		public Boolean isEmailVerified;

		@Getter
		@NoArgsConstructor
		@JsonIgnoreProperties(ignoreUnknown = true)
		public class Profile {
			@JsonProperty("nickname")
			public String nickName;

			@JsonProperty("thumbnail_image_url")
			public String thumbnailImageUrl;

			@JsonProperty("profile_image_url")
			public String profileImageUrl;

			@JsonProperty("is_default_image")
			public String isDefaultImage;

			@JsonProperty("is_default_nickname")
			public Boolean isDefaultNickName;
		}
	}

	@Getter
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public class Partner {
		@JsonProperty("uuid")
		public String uuid;
	}
}
