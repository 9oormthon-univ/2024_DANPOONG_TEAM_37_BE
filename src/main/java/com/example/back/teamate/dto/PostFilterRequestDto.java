package com.example.back.teamate.dto;

import java.util.List;

import lombok.Data;

@Data
public class PostFilterRequestDto {

	private int field;
	private List<Integer> positions;
	private List<Integer> skills;
	private int mode;
	private int page;
	private int size;
}
