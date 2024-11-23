package com.example.back.teamate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.example.back.teamate.entity.Mode;
import com.example.back.teamate.enums.ModeName;
import com.example.back.teamate.repository.ModeRepository;

@SpringBootTest
public class ModeRepositoryTest {

	@Autowired
	private ModeRepository modeRepository;

	@Test
	@Rollback(value = false)
	public void createAndFindModes() {
		Mode onlineMode = Mode.builder()
			.modeId(ModeName.ONLINE.getModeId())
			.modeName(ModeName.ONLINE)
			.build();

		Mode offlineMode = Mode.builder()
			.modeId(ModeName.OFFLINE.getModeId())
			.modeName(ModeName.OFFLINE)
			.build();

		modeRepository.save(onlineMode);
		modeRepository.save(offlineMode);

	}
}