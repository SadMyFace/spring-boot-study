package com.example.demo.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

//Auditing : 감시하다
//AuditingEntityListener.class : 반드시 지정. 데이터가 추가되거나 변경될 때 자동으로 감시하여 시간 값을 지정할 수 있음.

@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class}) //반드시 지정
@Getter
abstract class TimeBased {
	
	//날짜 시간만 따로 빼서 관리하는 테이블
	
	@CreatedDate
	@Column(name="reg_at", updatable = false)
	private LocalDateTime regAt;
	
	@LastModifiedDate
	@Column(name="mod_at")
	private LocalDateTime modAt;
	

}
