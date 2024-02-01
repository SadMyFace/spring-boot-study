package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.security.CustomUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	// springSecurity5(이후부터 변경사항)
	
	// passwordEncoder => springSecurity5(이후부터 변경사항)
	// createDelegatingPasswordEncoder
	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	// SecurityFilterChain 객체로 설정
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authorize -> authorize
					.requestMatchers("/index", "/", "/js/**", "/dist/**", "/board/register", "/board/list", "/member/login", 
						"/member/register", "/comment/**", "/upload/**").permitAll()
					.requestMatchers("/member/list").hasAnyRole("ADMIN")
					.anyRequest().authenticated())
				.formLogin(login -> login
						.usernameParameter("email")
						.passwordParameter("pwd")
						.loginPage("/member/login")
						.defaultSuccessUrl("/board/list").permitAll())
				.logout(logout -> logout
						.logoutUrl("/member/logout")
						.deleteCookies("JSESSIONID")
						.logoutSuccessUrl("/")).build();
	}
	
	// AuthenticationManager 객체로 설정
	@Bean
	AuthenticationManager authenticationManager(
			AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	// UserDetailService : 기존 Spring과 같은 클래스
	@Bean
	UserDetailsService userDetailService() {
		return new CustomUserService();
	}
	
}
