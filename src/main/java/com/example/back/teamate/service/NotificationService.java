package com.example.back.teamate.service;


import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.example.back.teamate.enums.EventType;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

	EventType eventType = EventType.MATCH_STATUS;

	private final JavaMailSender mailSender;

	private final TemplateEngine templateEngine;

	public void sendEmail(String to, String subject, String templateName, Context context) throws MessagingException {
		// HTML 템플릿 렌더링
		String htmlContent = templateEngine.process(templateName, context);

		// 이메일 작성
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(htmlContent, true); // HTML로 설정

		// 이메일 발송
		mailSender.send(message);
	}
}
