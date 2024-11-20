package com.example.back.teamate.kakao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoService {

	private String clientId;
	private final String KAUTH_TOKEN_URL_HOST;
	private final String KAUTH_USER_URL_HOST;



	@Autowired
	public KakaoService(@Value("${kakao.client_id}") String clientId) {
		this.clientId = clientId;
		KAUTH_TOKEN_URL_HOST ="https://kauth.kakao.com";
		KAUTH_USER_URL_HOST = "https://kapi.kakao.com";
	}

	public KakaoTokenResponseDto getTokensFromKakao(String code) {
		KakaoTokenResponseDto kakaoTokenResponseDto = WebClient.create(KAUTH_TOKEN_URL_HOST).post()
			.uri(uriBuilder -> uriBuilder
				.scheme("https")
				.path("/oauth/token")
				.queryParam("grant_type", "authorization_code")
				.queryParam("client_id", clientId)
				.queryParam("code", code)
				.build(true))
			.header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
			.retrieve()
			.onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
				return clientResponse.bodyToMono(String.class).flatMap(body -> {
					log.error("4xx Error Response: {}", body);
					return Mono.error(new RuntimeException("Invalid Parameter: " + body));
				});
			})
			.bodyToMono(KakaoTokenResponseDto.class)
			.block();

		log.info(" [Kakao Service] Access Token ------> {}", kakaoTokenResponseDto.getAccessToken());
		log.info(" [Kakao Service] Refresh Token ------> {}", kakaoTokenResponseDto.getRefreshToken());
		log.info(" [Kakao Service] Scope ------> {}", kakaoTokenResponseDto.getScope());

		return kakaoTokenResponseDto;
	}



	public KakaoUserInfoResponseDto getUserInfo(String accessToken) {
		KakaoUserInfoResponseDto userInfo = WebClient.create(KAUTH_USER_URL_HOST)
			.get()
			.uri(uriBuilder -> uriBuilder
				.scheme("https")
				.path("/v2/user/me")
				.build())
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
			.retrieve()
			.onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
				Mono.error(new RuntimeException("Invalid Parameter: " + clientResponse.statusCode())))
			.onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
				Mono.error(new RuntimeException("Internal Server Error: " + clientResponse.statusCode())))
			.bodyToMono(KakaoUserInfoResponseDto.class)
			.block();

		return userInfo;
	}

	public String sendMessageToMe(String accessToken, String messageText) {
		String uniqueMessageText = messageText + " (전송 시간: " + System.currentTimeMillis() + ")";
		String templateObject = buildTemplateObject(uniqueMessageText);

		return WebClient.create()
			.post()
			.uri("https://kapi.kakao.com/v2/api/talk/memo/default/send")
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
			.bodyValue("template_object=" + templateObject)
			.retrieve()
			.onStatus(status -> status.isError(), clientResponse -> {
				// 클라이언트 에러 또는 서버 에러 발생 시 상세 메시지 로깅
				return clientResponse.bodyToMono(String.class)
					.flatMap(body -> {
						log.error("Error response body: {}", body);
						return Mono.error(new RuntimeException("HTTP Error: " + clientResponse.statusCode() + ", Body: " + body));
					});
			})
			.bodyToMono(String.class)
			.block();
	}



	public String buildTemplateObject(String messageText) {
		return "{"
			+ "\"object_type\": \"text\","
			+ "\"text\": \"" + messageText + "\","
			+ "\"link\": {"
			+ "    \"web_url\": \"https://teamate.duckdns.org\","
			+ "    \"mobile_web_url\": \"https://teamate.duckdns.org\""
			+ "},"
			+ "\"button_title\": \"자세히 보기\""
			+ "}";
	}
}