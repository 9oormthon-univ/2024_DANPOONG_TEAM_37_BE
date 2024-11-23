package com.example.back.teamate.repository;

import com.example.back.teamate.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
}

