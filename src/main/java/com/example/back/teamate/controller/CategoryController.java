package com.example.back.teamate.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.back.teamate.dto.ApiResponse;
import com.example.back.teamate.dto.PositionListResponseDto;
import com.example.back.teamate.dto.SkillListResponseDto;
import com.example.back.teamate.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor()
@RequestMapping("/api/v1")
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping("/positions")
	public ResponseEntity<?> findAll() {
		List<PositionListResponseDto> positions = categoryService.findAllPositions();
		return ResponseEntity.ok(ApiResponse.createSuccess(positions));
	}

	@GetMapping("/skills")
	public ResponseEntity<?> findAllSkills() {
		List<SkillListResponseDto> positions = categoryService.findAllSkills();
		return ResponseEntity.ok(ApiResponse.createSuccess(positions));
	}
}
