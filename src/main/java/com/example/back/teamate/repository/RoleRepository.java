package com.example.back.teamate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.back.teamate.entity.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByPosts_PostId(Long postId);
	List<Role> findByUser_Id(Long userId);
}
