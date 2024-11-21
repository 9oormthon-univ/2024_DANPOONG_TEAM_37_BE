package com.example.back.teamate.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "post_position")
public class PostPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postPositionId; // Primary Key

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "positionId", nullable = false)
    private Position position;
}
