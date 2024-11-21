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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="roleId", nullable = false)
//    private Role roleId;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modeId", nullable = false)
    private Mode modeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fieldId", nullable = false)
    private Field fieldId;

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
