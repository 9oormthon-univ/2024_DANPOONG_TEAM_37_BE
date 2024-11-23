package com.example.back.teamate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.example.back.teamate.entity.Skill;
import com.example.back.teamate.enums.SkillName;
import com.example.back.teamate.repository.SkillRepository;

@SpringBootTest
public class SkillRepositoryTest {

	@Autowired
	private SkillRepository skillRepository;

	@Test
	@Rollback(value = false)
	public void saveSkills() {

		// Skill 데이터 저장
		for (SkillName skillName : SkillName.values()) {
			Skill skill = Skill.builder()
				.skillName(skillName)
				.build();
			skillRepository.save(skill);
			System.out.println("Saved Skill: " + skillName.getSkillDisplayName());
		}
	}
}
