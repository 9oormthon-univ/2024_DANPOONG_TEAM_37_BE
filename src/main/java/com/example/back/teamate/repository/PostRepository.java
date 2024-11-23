package com.example.back.teamate.repository;

import com.example.back.teamate.entity.Post;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
	Page<Post> findAll(Pageable pageable);
	@Query("SELECT p FROM Post p WHERE p.id = :postId AND p.role.id = :roleId")
	Optional<Post> findByPostIdAndRoleId(@Param("postId") Long postId, @Param("roleId") Long roleId);
}

