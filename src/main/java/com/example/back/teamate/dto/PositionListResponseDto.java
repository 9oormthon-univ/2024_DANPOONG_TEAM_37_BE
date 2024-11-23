package com.example.back.teamate.dto;

import com.example.back.teamate.entity.Position;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class PositionListResponseDto {

	private int positionId;
	private String positionName;

	// Position 엔티티를 기반으로 하는 생성자 추가
	public PositionListResponseDto(Position position) {
		this.positionId = position.getPositionId();
		this.positionName = position.getPositionName().getPositionDisplayName(); // Enum 값을 String으로 변환
	}
}