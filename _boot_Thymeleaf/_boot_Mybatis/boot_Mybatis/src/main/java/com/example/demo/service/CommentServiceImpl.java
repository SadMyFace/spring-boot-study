package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.CommentVO;
import com.example.demo.repository.CommentMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
	
	private final CommentMapper mapper;

	@Override
	public int postCommet(CommentVO cvo) {
		// TODO Auto-generated method stub
		return mapper.postComment(cvo);
	}

	@Override
	public List<CommentVO> getList(long bno) {
		// TODO Auto-generated method stub
		return mapper.getList(bno);
	}
	
}
