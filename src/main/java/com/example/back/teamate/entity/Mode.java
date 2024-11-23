package com.example.back.teamate.entity;

import com.example.back.teamate.enums.ModeName;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "mode")
public class Mode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int modeId;

    @Enumerated(EnumType.STRING)
    @Column(name="name", nullable = false, unique = true)
    private ModeName modeName;
}
