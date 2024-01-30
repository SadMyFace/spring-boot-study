package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.repository.MemberMapper;
import com.example.demo.security.MemberVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
	
	private final MemberMapper memberMapper;

	@Override
	public int insert(MemberVO mvo) {
		// TODO Auto-generated method stub
		int isOk = memberMapper.insert(mvo);
		return memberMapper.insertAuthinit(mvo.getEmail());
	}
}
