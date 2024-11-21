package com.example.back.teamate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.back.teamate.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
	Optional<Users> findByKakaoId(Long kakaoId);
}
