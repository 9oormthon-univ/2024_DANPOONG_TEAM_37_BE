package com.example.back.teamate.entity;

import com.example.back.teamate.enums.ModeName;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mode")
public class Mode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long modeId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private ModeName modeName;
}
