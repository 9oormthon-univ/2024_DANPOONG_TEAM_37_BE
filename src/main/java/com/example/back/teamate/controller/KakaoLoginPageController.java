package com.example.back.teamate.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/login")
public class KakaoLoginPageController {

	@Value("${kakao.client_id}")
	private String client_id;

	@Value("${kakao.redirect_uri}")
	private String redirect_uri;

	@GetMapping("/page")
	public String loginPage(Model model) {
		String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+client_id+"&redirect_uri=http://localhost:5173/api/v1/kakao";
		model.addAttribute("location", location);

		return "login";
	}
}