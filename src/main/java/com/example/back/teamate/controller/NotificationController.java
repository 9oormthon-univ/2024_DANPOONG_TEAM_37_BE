package com.example.back.teamate.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

import com.example.back.teamate.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class NotificationController {

	private final NotificationService emailService;

	/**
	 * 이메일 발송 API
	 *
	 * @param to       수신자 이메일
	 * @param subject  이메일 제목
	 * @param message  이메일 본문 메시지
	 * @return 성공 또는 실패 메시지
	 */
	@PostMapping("/send")
	public String sendEmail(
		@RequestParam String to,
		@RequestParam String subject,
		@RequestParam String message) {
		try {
			// Thymeleaf Context 설정
			Context context = new Context();
			context.setVariable("title", subject);
			context.setVariable("message", message);

			// 이메일 발송
			emailService.sendEmail(to, subject, "email-template.html", context);
			return "Email sent successfully to: " + to;
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed to send email: " + e.getMessage();
		}
	}
}