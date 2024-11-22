package com.example.back.teamate.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiResponse<T> {

	private String status;
	private T data;
	private String message;

	private static final String SUCCESS_STATUS = "success";
	private static final String FAIL_STATUS = "fail";
	private static final String ERROR_STATUS = "error";

	private ApiResponse(String status, T data, String message) {
		this.status = status;
		this.data = data;
		this.message = message;
	}

	public static <T> ApiResponse<T> createSuccess(T data) {
		return new ApiResponse<>(SUCCESS_STATUS, data, null);
	}

	public static <T> ApiResponse<T> createSuccessWithContent(String message) {
		return new ApiResponse<>(SUCCESS_STATUS, null, message);
	}

	public static ApiResponse<Void> createSuccessWithNoContent() {
		return new ApiResponse<>(SUCCESS_STATUS, null, null);
	}

	public static ApiResponse<Void> createFail(String message) {
		return new ApiResponse<>(FAIL_STATUS, null, message);
	}

	public static ApiResponse<Void> createError(String message) {
		return new ApiResponse<>(ERROR_STATUS, null, message);
	}
}
