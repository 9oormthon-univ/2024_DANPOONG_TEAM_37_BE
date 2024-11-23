package com.example.back.teamate.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "post_position")
public class PostPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postPositionId; // Primary Key

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    @OneToMany(mappedBy = "postPosition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostSkill> postSkills; // 추가: PostSkill과 1:N 관계 매핑
}
