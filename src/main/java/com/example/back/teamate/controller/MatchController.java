package com.example.back.teamate.controller;

import com.example.back.teamate.dto.ApiResponse;
import com.example.back.teamate.dto.MatchStatusRequestDto;
import com.example.back.teamate.dto.RedisUserInfoDto;
import com.example.back.teamate.service.MatchService;
import com.example.back.teamate.service.PostService;
import com.example.back.teamate.service.TokenAuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;
    private final TokenAuthenticationService tokenAuthenticationService;
    private final PostService postService;

    @PatchMapping("/{postId}/applicants/{applicantId}")
    public ResponseEntity<ApiResponse<Void>> updateMatchStatus(
        @RequestHeader("Authorization") String authHeader,
        @PathVariable Long postId,
        @PathVariable Long applicantId,
        @RequestBody @Valid MatchStatusRequestDto matchStatusRequestDto) {
        // 사용자 인증
        RedisUserInfoDto userInfo = tokenAuthenticationService.authenticateUser(authHeader);
        Long userId = userInfo.getId();

        // 팀장 권한 확인
        postService.validateTeamLeader(userId, postId);

        // Match 상태 업데이트
        matchService.updateMatchStatus(postId, applicantId, matchStatusRequestDto);
        return ResponseEntity.ok(ApiResponse.createSuccessWithContent("지원 상태가 성공적으로 업데이트되었습니다."));
    }
}