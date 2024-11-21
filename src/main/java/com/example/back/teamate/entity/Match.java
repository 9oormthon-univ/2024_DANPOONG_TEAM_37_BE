package com.example.back.teamate.entity;

import com.example.back.teamate.enums.MatchStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "match")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MatchStatus matchStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", nullable = false)
    private Post post;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "applicationId", nullable = false)
//    private Application application;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
