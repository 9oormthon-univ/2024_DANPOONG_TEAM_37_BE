package com.example.back.teamate.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "post_skill")
public class PostSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postSkillId; // Primary Key

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", nullable = false)
    private Post post; // Post 엔티티와 연관

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skillId", nullable = false)
    private Skill skill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postPositionId", nullable = false)
    private PostPosition postPosition;

}
