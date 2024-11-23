package com.example.back.teamate.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "post_skill")
@Builder
public class PostSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postSkillId; // Primary Key

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post; // Post 엔티티와 연관

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_position_id", nullable = false)
    private PostPosition postPosition;

}
