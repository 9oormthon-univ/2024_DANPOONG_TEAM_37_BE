package com.example.back.teamate.kakao;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class KakaoLoginController {

	private final KakaoService kakaoService;

	@GetMapping("/auth/callback")
	public ResponseEntity<?> getTokens(@RequestParam("code") String code) {
		try {
			// 1. 토큰 발급
			KakaoTokenResponseDto tokens = kakaoService.getTokensFromKakao(code);

			// 2. 토큰 저장 (Redis나 DB)
			// 예: redisService.saveTokens(userId, tokens);

			return ResponseEntity.ok(tokens.getAccessToken());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("오류 발생: " + e.getMessage());
		}
	}

	@PostMapping("/message/send")
	public ResponseEntity<?> sendMessage(@RequestParam("accessToken") String accessToken,
		@RequestParam("messageText") String messageText) {
		try {
			// 메시지 전송
			String response = kakaoService.sendMessageToMe(accessToken, messageText);
			return ResponseEntity.ok("메시지가 전송되었습니다: " + response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("오류 발생: " + e.getMessage());
		}
	}

}