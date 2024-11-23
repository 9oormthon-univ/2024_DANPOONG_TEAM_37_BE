package com.example.back.teamate.controller;

import com.example.back.teamate.dto.ApiResponse;
import com.example.back.teamate.dto.PostDetailResponseDto;
import com.example.back.teamate.dto.PostListResponseDto;
import com.example.back.teamate.dto.PostRequestDto;
import com.example.back.teamate.dto.RedisUserInfoDto;
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

    @PostMapping
    public ResponseEntity<?> createPost(@RequestHeader("Authorization") String authHeader, @RequestBody @Valid PostRequestDto postRequestDto) {
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
    public ResponseEntity<ApiResponse<PostDetailResponseDto>> getPost(@PathVariable ("postId") Long postId) {
        PostDetailResponseDto post = postService.getPost(postId);
        return ResponseEntity.ok(ApiResponse.createSuccess(post));
    }
}
