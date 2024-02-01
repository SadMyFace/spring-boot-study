package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.security.MemberVO;
import com.example.demo.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member/*")
@Controller
public class MemberController {
	
	private final MemberService msv;
	private final PasswordEncoder passwordEncoder;
	
	@GetMapping("/register")
	public String join() {
		return "/member/join";
	}
	
	@GetMapping("/login")
	public String login() {
		return "/member/login";
	}
	
	@PostMapping("/register")
	public String register(MemberVO mvo) {
		log.info(">>> mvo >>> {}", mvo);
		mvo.setPwd(passwordEncoder.encode(mvo.getPwd()));
		int isOk = msv.insert(mvo);
		
		return isOk > 0 ? "/index" : "/member/register";
	}
	
	private void logout(HttpServletRequest req, HttpServletResponse res) {
		Authentication authentication = 
				SecurityContextHolder.getContext().getAuthentication();
		new SecurityContextLogoutHandler().logout(req, res, authentication);
	}
}
