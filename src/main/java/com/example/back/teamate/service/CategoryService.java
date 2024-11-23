package com.example.back.teamate.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.back.teamate.dto.PositionListResponseDto;
import com.example.back.teamate.dto.SkillListResponseDto;
import com.example.back.teamate.entity.Position;
import com.example.back.teamate.entity.Skill;
import com.example.back.teamate.repository.PositionRepository;
import com.example.back.teamate.repository.SkillRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final PositionRepository positionRepository;
	private final SkillRepository skillRepository;

	public List<PositionListResponseDto> findAllPositions() {
		List<Position> positionList = positionRepository.findAll();

		List<PositionListResponseDto> positions = new ArrayList<>();
		for(Position position : positionList) {
			//PositionListResponseDto에 담기
			PositionListResponseDto positionListResponseDto = new PositionListResponseDto(position);
			positions.add(positionListResponseDto);
		}

		return positions;
	}

	public List<SkillListResponseDto> findAllSkills() {
		List<Skill> skillList = skillRepository.findAll();

		List<SkillListResponseDto> positions = new ArrayList<>();
		for(Skill skill : skillList) {
			//PositionListResponseDto에 담기
			SkillListResponseDto skillListResponseDto = new SkillListResponseDto(skill);
			positions.add(skillListResponseDto);
		}

		return positions;
	}
}
