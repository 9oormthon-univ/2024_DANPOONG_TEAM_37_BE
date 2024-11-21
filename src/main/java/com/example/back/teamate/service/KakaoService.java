package com.example.back.teamate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.example.back.teamate.dto.KakaoTokenResponseDto;
import com.example.back.teamate.dto.KakaoUserInfoResponseDto;

import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class KakaoService {

	@Value("${kakao.client_id}")
	private String clientId;

	@Value("${kakao.redirect_uri}")
	private String redirectUri;

	private String KAUTH_TOKEN_URL_HOST = "https://kauth.kakao.com";

	private String KAUTH_USER_URL_HOST = "https://kapi.kakao.com";

	private final WebClient webClient = WebClient.builder()
		.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
		.build();

	public KakaoTokenResponseDto getTokensFromKakao(String code) {
		try {
			return webClient.post()
				.uri(KAUTH_TOKEN_URL_HOST + "/oauth/token")
				.body(BodyInserters.fromFormData("grant_type", "authorization_code")
					.with("client_id", clientId)
					.with("redirect_uri", redirectUri)
					.with("code", code))
				.retrieve()
				.bodyToMono(KakaoTokenResponseDto.class)
				.block();
		} catch (WebClientResponseException e) {
			log.error("Failed to retrieve tokens for code {}: {}", code, e.getResponseBodyAsString());
			throw new RuntimeException("Failed to retrieve tokens for code: " + code, e);
		}
	}

	public KakaoUserInfoResponseDto getUserInfo(String accessToken) {
		return webClient.get()
			.uri(KAUTH_USER_URL_HOST + "/v2/user/me")
			.header(HttpHeaders.AUTHORIZATION, accessToken.startsWith("Bearer ") ? accessToken : "Bearer " + accessToken)
			.retrieve()
			.bodyToMono(KakaoUserInfoResponseDto.class)
			.block();
	}

	public void unlinkUser(String accessToken) {
		webClient.post()
			.uri(KAUTH_USER_URL_HOST + "/v1/user/unlink")
			.header(HttpHeaders.AUTHORIZATION, accessToken.startsWith("Bearer ") ? accessToken : "Bearer " + accessToken)
			.retrieve()
			.bodyToMono(Void.class)
			.block();
	}

}
