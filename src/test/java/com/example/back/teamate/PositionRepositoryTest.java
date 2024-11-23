package com.example.back.teamate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.example.back.teamate.entity.Position;
import com.example.back.teamate.enums.PositionName;
import com.example.back.teamate.repository.PositionRepository;

@SpringBootTest
public class PositionRepositoryTest {

	@Autowired
	private PositionRepository positionRepository;

	@Test
	@Rollback(value = false)
	public void saveSkills() {

		// Skill 데이터 저장
		for (PositionName positionName : PositionName.values()) {
			Position position = Position.builder()
				.positionName(positionName)
				.build();
			positionRepository.save(position);
			System.out.println("Saved Skill: " + positionName.getPositionDisplayName());
		}
	}
}
