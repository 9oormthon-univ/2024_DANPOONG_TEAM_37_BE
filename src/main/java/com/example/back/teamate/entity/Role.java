package com.example.back.teamate.entity;

import java.util.List;

import com.example.back.teamate.enums.TeamRole;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING) // Enum 값을 문자열로 저장
	private TeamRole teamRole; // TEAM_LEADER or TEAM_MEMBER

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private Users user;

	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true) // Role과 관련된 Post
	private List<Post> posts;

	// 필요한 필드만 초기화하는 생성자 추가
	public Role(Users user, TeamRole teamRole) {
		this.user = user;
		this.teamRole = teamRole;
	}
}
