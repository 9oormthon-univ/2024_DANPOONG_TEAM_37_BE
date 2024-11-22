package com.example.back.teamate.repository;

import com.example.back.teamate.entity.PostSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostSkillRepository extends JpaRepository<PostSkill, Long> {
}
