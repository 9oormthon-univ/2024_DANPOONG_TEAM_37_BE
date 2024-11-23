package com.example.back.teamate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.back.teamate.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByPosts_PostId(Long postId);
}
