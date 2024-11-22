package com.example.back.teamate;

import com.example.back.teamate.entity.Users;
import com.example.back.teamate.repository.UsersRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional // 테스트 후 롤백
public class UsersRepositoryTest {

	@Autowired
	private UsersRepository userRepository;

	@Test
	@Rollback(false)
	public void saveUsersTest() {
		// 1. 엔티티 생성
		Users user = Users.builder()
			.kakaoId(3799309839L)
			.email("menten4859@naver.com")
			.nickname("전창하")
			.profileImage("http://img1.kakaocdn.net/thumb/R640x640.q70/?fname=http://t1.kakaocdn.net/account_images/default_profile.jpeg")
			.age(0)
			.gender(null) // null
			.job(null) // null
			.mbti(null) // null
			.position(null) // null
			.mode(null) // null
			.region(null) // null
			.city(null) // null
			.district(null) // null
			.isActiveProject(false)
			.isActiveStudy(false)
			.build();

		// 2. 저장
		Users savedUser = userRepository.save(user);

		// 3. 저장된 값 검증
		assertThat(savedUser.getId()).isNotNull(); // ID가 생성되었는지 확인
		assertThat(savedUser.getEmail()).isEqualTo("menten4859@naver.com");
		assertThat(savedUser.getNickname()).isEqualTo("전창하");
		assertThat(savedUser.getProfileImage()).isEqualTo("http://img1.kakaocdn.net/thumb/R640x640.q70/?fname=http://t1.kakaocdn.net/account_images/default_profile.jpeg");
		assertThat(savedUser.isActiveProject()).isFalse();
		assertThat(savedUser.isActiveStudy()).isFalse();
	}
}
