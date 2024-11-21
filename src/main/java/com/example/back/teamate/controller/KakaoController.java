package com.example.back.teamate.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.back.teamate.dto.AccessTokenResponseDto;
import com.example.back.teamate.dto.KakaoTokenResponseDto;
import com.example.back.teamate.dto.KakaoUserInfoResponseDto;
import com.example.back.teamate.dto.RedisUserInfoDto;
import com.example.back.teamate.service.KakaoService;
import com.example.back.teamate.service.UsersService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class KakaoController {

	private final KakaoService kakaoService;
	private final UsersService usersService;

	@GetMapping("/auth/callback")
	public ResponseEntity<?> getTokens(@RequestParam("code") String code) {
		try {
			// 1. 토큰 발급
			KakaoTokenResponseDto tokens = kakaoService.getTokensFromKakao(code);


			return ResponseEntity.ok(tokens.getAccessToken());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("오류 발생: " + e.getMessage());
		}
	}

	@GetMapping("/login")
	public ResponseEntity<?> loginWithKakao(@RequestParam String code) {
		// 1. 인증 코드로 Kakao Access Token 가져오기
		KakaoTokenResponseDto tokenResponse = kakaoService.getTokensFromKakao(code);

		// 2. Access Token으로 사용자 정보 가져오기
		KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(tokenResponse.getAccessToken());

		// 3. 사용자 정보로 회원가입 또는 로그인 처리
		RedisUserInfoDto userData = usersService.registerOrLogin(userInfo);

		// 4. Redis에 사용자 정보 저장
		usersService.saveToken(tokenResponse.getAccessToken(), userData);

		// 5. 사용자 정보 반환 (Bearer 포함)
		return ResponseEntity.ok(AccessTokenResponseDto.builder()
			.accessToken("Bearer " + tokenResponse.getAccessToken())
			.userData(userData) // 사용자 정보를 함께 반환
			.build());
	}



	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
		try {
			String accessToken = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
			usersService.logout(accessToken);
			return ResponseEntity.ok("로그아웃 되었습니다.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그아웃 실패");
		}
	}


	@GetMapping("/unlink")
	public ResponseEntity<?> unlinkKakao(@RequestHeader("Authorization") String authHeader) {
		try {
			String accessToken = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
			kakaoService.unlinkUser(accessToken);
			return ResponseEntity.ok("연결이 해제되었습니다.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("오류 발생: " + e.getMessage());
		}
	}

}