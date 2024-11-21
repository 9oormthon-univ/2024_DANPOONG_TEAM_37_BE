package com.example.back.teamate.entity;

import com.example.back.teamate.enums.FieldName;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "field")
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fieldId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private FieldName fieldName;
}