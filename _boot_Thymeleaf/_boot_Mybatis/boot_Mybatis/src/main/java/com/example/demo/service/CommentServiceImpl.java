package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.CommentVO;
import com.example.demo.domain.PagingVO;
import com.example.demo.handler.PagingHandler;
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
	
	@Transactional
	@Override
	public PagingHandler getList(long bno, PagingVO pgvo) {
		// TODO Auto-generated method stub
		//Controller에서 처리해도 되지만, 처리 속도가 더 빨라짐.
		//totalCount
		int totalCount = mapper.selectOneBnoTotalCount(bno);
		//List
		List<CommentVO> list = mapper.getList(bno, pgvo);
		//PagingHandler
		PagingHandler ph = new PagingHandler(pgvo, totalCount, list);
		
		return ph;
	}

	@Override
	public int edit(CommentVO cvo) {
		// TODO Auto-generated method stub
		return mapper.edit(cvo);
	}

	@Override
	public int delete(int cno) {
		// TODO Auto-generated method stub
		return mapper.delete(cno);
	}

}
