package com.example.back.teamate.service;


import com.example.back.teamate.dto.MatchStatusRequestDto;
import com.example.back.teamate.entity.Match;
import com.example.back.teamate.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;

    @Transactional
    public void updateMatchStatus(Long postId, Long applicantId, MatchStatusRequestDto matchStatusRequestDto) {
        // Match 엔티티 조회
        Match match = matchRepository.findByPostIdAndApplicantId(postId, applicantId)
            .orElseThrow(() -> new IllegalArgumentException("해당 지원자를 찾을 수 없습니다."));

        // MatchStatus 업데이트
        match.setMatchStatus(matchStatusRequestDto.getStatus());
        matchRepository.save(match); // 상태를 DB에 저장
    }

}
