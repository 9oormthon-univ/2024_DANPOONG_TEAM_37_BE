package com.example.back.teamate.repository;

import com.example.back.teamate.entity.Application;
import com.example.back.teamate.entity.ApplicationSkill;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationSkillRepository extends JpaRepository<ApplicationSkill, Long> {
    List<ApplicationSkill> findByApplication(Application application);
}
