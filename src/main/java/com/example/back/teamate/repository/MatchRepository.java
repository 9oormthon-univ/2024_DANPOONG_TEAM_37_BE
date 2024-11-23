package com.example.back.teamate.repository;

import com.example.back.teamate.entity.Application;
import com.example.back.teamate.entity.Match;
import com.example.back.teamate.enums.MatchStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    @Query("SELECT m.application FROM Match m WHERE m.post = :postId")
    List<Application> findApplicationsByPostId(@Param("postId") Long postId);

    @Query("SELECT COUNT(m) FROM Match m WHERE m.post = :postId AND m.matchStatus = :status")
    int countByPostIdAndMatchStatus(@Param("postId") Long postId, @Param("status") MatchStatus status);

    @Query("SELECT m FROM Match m WHERE m.post = :postId AND m.application.id = :applicantId")
    Optional<Match> findByPostIdAndApplicantId(@Param("postId") Long postId, @Param("applicantId") Long applicantId);
}