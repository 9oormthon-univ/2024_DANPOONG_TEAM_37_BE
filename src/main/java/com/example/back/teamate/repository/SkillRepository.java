package com.example.back.teamate.repository;

import com.example.back.teamate.entity.Skill;
import com.example.back.teamate.enums.SkillName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    Optional<Skill> findBySkillName(SkillName skillName);
}
