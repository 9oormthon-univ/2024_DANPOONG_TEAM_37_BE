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
	private TeamRole teamRole; // team_leader or team_member

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;

	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true) // Role과 관련된 Post
	private List<Post> posts;
}
