package com.example.back.teamate.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Application extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 지원서 인덱스

	@Column(nullable = false)
	private String name; // 지원서 이름

	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "fieldId", nullable = false)
	// private Field field; // 분야 (Field 테이블 참조)
	//
	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "positionId", nullable = false)
	// private Position position; // 포지션 (Position 테이블 참조)
	//
	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "skillId")
	// private Skill skill; // 기술 스택 (Skill 테이블 참조)

	private String topic; // 관심 토픽

	@Column(length = 1000)
	private String introduction; // 자기소개

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role; // 유저 역할 아이디

}
