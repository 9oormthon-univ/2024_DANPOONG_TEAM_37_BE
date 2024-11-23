package com.example.back.teamate.controller;

import com.example.back.teamate.dto.ApiResponse;
import com.example.back.teamate.dto.ApplicationResponseDto;
import com.example.back.teamate.dto.PostDetailResponseDto;
import com.example.back.teamate.dto.PostFilterRequestDto;
import com.example.back.teamate.dto.PostListResponseDto;
import com.example.back.teamate.dto.PostRequestDto;
import com.example.back.teamate.dto.RedisUserInfoDto;
import com.example.back.teamate.enums.TeamRole;
import com.example.back.teamate.service.ApplicationService;
import com.example.back.teamate.service.PostService;
import com.example.back.teamate.service.TokenAuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final TokenAuthenticationService tokenAuthenticationService;
    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestHeader("Authorization") String authHeader,
        @RequestBody @Valid PostRequestDto postRequestDto) {
        RedisUserInfoDto redisUserInfoDto = tokenAuthenticationService.authenticateUser(authHeader);

        Long postId = postService.createPost(redisUserInfoDto.getId(), postRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(Map.of("message", "Post created successfully. postId"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PostListResponseDto>>> getPostList(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size) {
        List<PostListResponseDto> posts = postService.getPostListAsList(page, size);
        return ResponseEntity.ok(ApiResponse.createSuccess(posts));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostDetailResponseDto>> getPost(
        @PathVariable("postId") Long postId) {
        PostDetailResponseDto post = postService.getPost(postId);
        return ResponseEntity.ok(ApiResponse.createSuccess(post));
    }

    @GetMapping("/{postId}/applications")
    public ResponseEntity<?> getApplicationsByPostId(
        @RequestHeader("Authorization") String authHeader,
        @PathVariable Long postId) throws IllegalAccessException {
        RedisUserInfoDto userInfo = tokenAuthenticationService.authenticateUser(authHeader);
        applicationService.checkIfUserIsTeamLeader(userInfo.getId(), postId);

        //팀장인지 권한 확인
        List<ApplicationResponseDto> applications = applicationService.getApplicationsByPostId(userInfo.getId(),postId);
        return ResponseEntity.ok(ApiResponse.createSuccess(applications));
    }

    @GetMapping("/posts")
    public ResponseEntity<ApiResponse<Map<String, List<PostListResponseDto>>>> getUserPosts(
        @RequestHeader("Authorization") String authHeader,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size) {

        // 사용자 인증 및 userId 가져오기
        RedisUserInfoDto userInfo = tokenAuthenticationService.authenticateUser(authHeader);
        Long userId = userInfo.getId();

        // 팀장/팀원의 게시물 조회
        List<PostListResponseDto> leaderPosts = postService.getPostsByRole(userId, TeamRole.TEAM_LEADER, page, size);
        List<PostListResponseDto> memberPosts = postService.getPostsByRole(userId, TeamRole.TEAM_MEMBER, page, size);

        // 결과 합치기
        Map<String, List<PostListResponseDto>> result = Map.of(
            "leaderPosts", leaderPosts,
            "memberPosts", memberPosts
        );

        return ResponseEntity.ok(ApiResponse.createSuccess(result));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(@RequestHeader("Authorization") String authHeader, @PathVariable("postId") Long postId, @RequestBody @Valid PostRequestDto postRequestDto) {
        RedisUserInfoDto redisUserInfoDto = tokenAuthenticationService.authenticateUser(authHeader);

        postService.updatePost(postId, redisUserInfoDto.getId(),postRequestDto);
        return ResponseEntity.ok(Map.of("message", "Post updated successfully."));
    }

    @PostMapping("/filter")
    public ResponseEntity<List<PostListResponseDto>> getPostsByFilter(@RequestBody PostFilterRequestDto postFilterRequestDto) {
        List<PostListResponseDto> posts = postService.getPostListByFilter(postFilterRequestDto);
        return ResponseEntity.ok(posts);
    }
}
