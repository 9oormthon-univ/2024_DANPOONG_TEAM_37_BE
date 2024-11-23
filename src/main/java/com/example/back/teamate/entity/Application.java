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

	@JoinColumn(name = "field_id", nullable = false)
	private int fieldId; // 분야 (Field 테이블 참조)

	@JoinColumn(name = "position_id", nullable = false)
	private int positionId; // 포지션 (Position 테이블 참조)

	@Column(length = 1000)
	private String introduction; // 자기소개

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role; // 유저 역할 아이디

}
