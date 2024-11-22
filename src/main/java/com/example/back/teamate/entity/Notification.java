package com.example.back.teamate.entity;

import com.example.back.teamate.enums.EventType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Notification extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long userId; // 유저 id

	@Enumerated(EnumType.STRING)
	private EventType eventType; // Match or Proposition 구별

	private Long eventReferenceId; // Match or Proposition id

	private String message; // 내용

}
