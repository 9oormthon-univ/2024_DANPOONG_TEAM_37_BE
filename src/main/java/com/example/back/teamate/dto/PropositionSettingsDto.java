package com.example.back.teamate.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class PropositionSettingsDto {
	private Boolean isActiveProject;
	private Boolean isActiveStudy;

	public PropositionSettingsDto() {} // 기본 생성자

	public PropositionSettingsDto(Boolean isActiveProject, Boolean isActiveStudy) {
		this.isActiveProject = isActiveProject;
		this.isActiveStudy = isActiveStudy;
	}
}