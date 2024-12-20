package com.example.back.teamate.entity;

import com.example.back.teamate.enums.PositionName;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "position")
@Builder
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int positionId;

    @Enumerated(EnumType.STRING)
    @Column(name="name", nullable = false, unique = true)
    private PositionName positionName;
}
