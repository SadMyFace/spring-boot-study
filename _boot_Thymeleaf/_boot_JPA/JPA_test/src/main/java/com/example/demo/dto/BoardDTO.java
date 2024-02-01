package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Builder : new 처럼 객체를 생성해주는 역할을 해주는 어노테이션

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDTO {
	
	//Entity : DB의 테이블 클래스
	//DTO : 객체를 생성하는 클래스 (=VO)
	
	private Long bno;
	private String title;
	private String writer;
	private String content;
	private LocalDateTime regAt, modAt;
	
	
}
