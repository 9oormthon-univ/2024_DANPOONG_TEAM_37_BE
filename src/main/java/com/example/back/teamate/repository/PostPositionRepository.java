package com.example.back.teamate.repository;

import com.example.back.teamate.entity.PostPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostPositionRepository extends JpaRepository<PostPosition, Long> {
}
