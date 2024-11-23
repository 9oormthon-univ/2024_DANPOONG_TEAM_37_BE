package com.example.back.teamate.dto;

import com.example.back.teamate.entity.Skill;

import lombok.Data;

@Data
public class SkillListResponseDto {
	private int skillId;
	private String skillName;

	public SkillListResponseDto(Skill skill) {
		this.skillId = skill.getSkillId();
		this.skillName = skill.getSkillName().getSkillDisplayName();
	}
}
