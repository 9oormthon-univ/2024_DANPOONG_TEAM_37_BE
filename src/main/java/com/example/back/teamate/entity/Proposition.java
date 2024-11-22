package com.example.back.teamate.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Proposition extends BaseEntity{


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private boolean isSuggestedByLeader; // 팀장이 추천한 제안인 지 여부

	// @OneToOne
	// @JoinColumn(name = "post_id")
	// private Post post;

	@ManyToOne
	@JoinColumn(name = "team_member_id")
	private Users teamMember;

	@ManyToOne
	@JoinColumn(name = "team_leader_id")
	private Users teamLeader;



}
