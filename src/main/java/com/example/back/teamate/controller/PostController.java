package com.example.back.teamate.controller;

import com.example.back.teamate.dto.PostRequestDto;
import com.example.back.teamate.service.PostService;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody @Valid PostRequestDto postRequestDto) {
        Long postId = postService.createPost(postRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(Map.of("message", "Post created successfully."));
    }
}
