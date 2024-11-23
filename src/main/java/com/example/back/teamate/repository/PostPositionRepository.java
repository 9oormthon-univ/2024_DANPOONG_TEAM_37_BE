package com.example.back.teamate.repository;

import java.util.List;

import com.example.back.teamate.entity.PostPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostPositionRepository extends JpaRepository<PostPosition, Long> {
	List<PostPosition> findByPost_PostId(Long postId); // PostPosition의 post 필드에서 postId 탐색
}
