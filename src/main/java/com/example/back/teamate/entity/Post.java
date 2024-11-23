package com.example.back.teamate.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 관계 설정
    @JoinColumn(name = "role_id", nullable = false) // 외래 키 이름 명시
    private Role role; // Role과의 매핑

    @Column(nullable = false)
    private int totalMembers;

    @Column(nullable = false)
    private int expectedPeriod;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate deadline;

    @Column
    private String googleFormUrl;

    @Column
    private String kakaoChatUrl;

    @Column
    private String portfolioUrl;

    @JoinColumn(name = "mode_id", nullable = false)
    private int modeId;

    @JoinColumn(name = "field_id", nullable = false)
    private int fieldId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate createdAt;

    @UpdateTimestamp
    private LocalDate updatedAt;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
}
