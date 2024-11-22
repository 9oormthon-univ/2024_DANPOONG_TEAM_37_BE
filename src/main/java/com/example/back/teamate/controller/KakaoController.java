package com.example.back.teamate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.back.teamate.dto.AccessTokenResponseDto;
import com.example.back.teamate.dto.ApiResponse;
import com.example.back.teamate.dto.KakaoTokenResponseDto;
import com.example.back.teamate.dto.KakaoUserInfoResponseDto;
import com.example.back.teamate.dto.RedisUserInfoDto;
import com.example.back.teamate.service.KakaoService;
import com.example.back.teamate.service.TokenAuthenticationService;
import com.example.back.teamate.service.UsersService;

import lombok.RequiredArgsConstructor;
import static com.example.back.teamate.dto.ApiResponse.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class KakaoController {

	private final KakaoService kakaoService;
	private final UsersService usersService;
	private final TokenAuthenticationService tokenAuthService;

	@GetMapping("/auth/callback")
	public ResponseEntity<ApiResponse<?>> getTokens(@RequestParam("code") String code) {
		try {
			KakaoTokenResponseDto tokens = kakaoService.getTokensFromKakao(code);
			return ResponseEntity.ok(createSuccess(tokens.getAccessToken()));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(createError(e.getMessage()));
		}
	}

	@GetMapping("/login")
	public ResponseEntity<ApiResponse<AccessTokenResponseDto>> loginWithKakao(@RequestParam String code) {
		KakaoTokenResponseDto tokenResponse = kakaoService.getTokensFromKakao(code);
		KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(tokenResponse.getAccessToken());
		RedisUserInfoDto userData = usersService.registerOrLogin(userInfo);
		usersService.saveToken(tokenResponse.getAccessToken(), userData);

		AccessTokenResponseDto response = AccessTokenResponseDto.builder()
			.accessToken("Bearer " + tokenResponse.getAccessToken())
			.userData(userData)
			.build();

		return ResponseEntity.ok(ApiResponse.createSuccess(response));
	}


	@PostMapping("/logout")
	public ResponseEntity<ApiResponse<Void>> logout(@RequestHeader("Authorization") String authHeader) {
		try {
		usersService.logout(authHeader);
			return ResponseEntity.ok(createSuccessWithNoContent());
		} catch (Exception e) {
			return ResponseEntity.status(500).body(createError("Logout failed: " + e.getMessage()));
		}
	}

	@GetMapping("/unlink")
	public ResponseEntity<ApiResponse<Void>> unlinkKakao(@RequestHeader("Authorization") String authHeader) {
		try {
			kakaoService.unlinkUser(authHeader);
			return ResponseEntity.ok(createSuccessWithNoContent());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(createError("Unlink failed: " + e.getMessage()));
		}
	}
}
