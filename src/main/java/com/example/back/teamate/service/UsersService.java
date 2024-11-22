package com.example.back.teamate.service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.back.teamate.dto.PropositionSettingsDto;
import com.example.back.teamate.dto.RedisUserInfoDto;
import com.example.back.teamate.dto.UserDetailsDto;
import com.example.back.teamate.entity.Users;
import com.example.back.teamate.dto.KakaoUserInfoResponseDto;
import com.example.back.teamate.repository.UsersRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService {
	private final RedisTemplate<String, Object> redisTemplate;
	private final UsersRepository usersRepository;

	public RedisUserInfoDto registerOrLogin(KakaoUserInfoResponseDto userInfo) {

		// 사용자 정보 조회 또는 생성
		Users user = usersRepository.findByKakaoId(userInfo.getId()).orElseGet(() -> {
			// 카카오 계정 정보 가져오기
			KakaoUserInfoResponseDto.KakaoAccount kakaoAccount = userInfo.getKakaoAccount();
			// 이메일 유효성 확인
			String email = kakaoAccount.getEmail();
			if (email == null || !kakaoAccount.getIsEmailVerified()) {
				throw new RuntimeException("이메일 정보가 없거나 인증되지 않았습니다.");
			}
			// 프로필 정보 가져오기
			KakaoUserInfoResponseDto.KakaoAccount.Profile profile = kakaoAccount.getProfile();
			// Users 객체 생성 및 저장
			Users newUser = Users.builder()
				.kakaoId(userInfo.getId())
				.email(email)
				.nickname(profile.getNickname())
				.profileImage(profile.getProfileImageUrl())
				.build();
			return usersRepository.save(newUser); // 저장 후 반환
		});

		// RedisUserInfoDto로 사용자 정보 매핑 및 반환
		return RedisUserInfoDto.builder()
			.id(user.getId())
			.kakaoId(user.getKakaoId())
			.nickname(user.getNickname())
			.build();
	}

	public void saveToken(String accessToken, RedisUserInfoDto userData) {
		String key = "kakao:token:" + accessToken;
		redisTemplate.opsForValue().set(key, userData, 21600, TimeUnit.SECONDS); // 6시간 TTL
	}

	public RedisUserInfoDto validateToken(String accessToken) {
		String key = "kakao:token:" + accessToken;
		RedisUserInfoDto userData = (RedisUserInfoDto) redisTemplate.opsForValue().get(key);

		if (userData == null) {
			throw new RuntimeException("유효하지 않은 토큰입니다.");
		}

		return userData; // 인증된 사용자 데이터 반환
	}

	public void logout(String accessToken) {
		String key = "kakao:token:" + accessToken;
		redisTemplate.delete(key);
	}

	// 제안 설정 업데이트
	public void updatePropositionSettings(Long userId, PropositionSettingsDto settingsDto) {
		Users user = usersRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("User not found"));

		// 기존 값 유지 또는 새 값으로 업데이트
		boolean isActiveProject = settingsDto.getIsActiveProject() != null
			? settingsDto.getIsActiveProject()
			: user.isActiveProject();
		boolean isActiveStudy = settingsDto.getIsActiveStudy() != null
			? settingsDto.getIsActiveStudy()
			: user.isActiveStudy();

		user.updatePropositionSettings(isActiveProject, isActiveStudy);
		usersRepository.save(user);
	}

	public UserDetailsDto getUserDetails(Long userId) {
		Users user = usersRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("User not found"));

		return UserDetailsDto.builder()
			.age(user.getAge())
			.job(user.getJob())
			.gender(user.getGender())
			.mbti(user.getMbti())
			.position(user.getPosition())
			.mode(user.getMode())
			.region(user.getRegion())
			.city(user.getCity())
			.district(user.getDistrict())
			.kakaoTalkId(user.getKakaoTalkId())
			.build();
	}

	@Transactional
	public void updateUserDetails(Long userId, UserDetailsDto updateDto) {
		Users user = usersRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("User not found"));

		// 엔티티 메서드를 통해 업데이트
		user.updateDetails(
			updateDto.getAge(),
			updateDto.getJob(),
			updateDto.getGender(),
			updateDto.getMbti(),
			updateDto.getPosition(),
			updateDto.getMode(),
			updateDto.getRegion(),
			updateDto.getCity(),
			updateDto.getDistrict(),
			updateDto.getKakaoTalkId()
		);
		// 변경된 엔티티 저장
		usersRepository.save(user);
	}

}
