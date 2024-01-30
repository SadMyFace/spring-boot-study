package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.BoardDTO;
import com.example.demo.domain.BoardVO;
import com.example.demo.domain.FileVO;
import com.example.demo.domain.PagingVO;
import com.example.demo.repository.BoardMapper;
import com.example.demo.repository.FileMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
		
	private final BoardMapper mapper;
	private final FileMapper fileMapper;
	
	@Transactional
	@Override
	public int register(BoardDTO bdto) {
		// TODO Auto-generated method stub
		int isOk = mapper.register(bdto.getBvo());
		if(isOk > 0 && bdto.getFlist().size() > 0) {
			long bno = mapper.getBno();
			for(FileVO fvo : bdto.getFlist()) {
				fvo.setBno(bno);
				isOk *= fileMapper.insertFile(fvo);
			}
		}
		return isOk;
	}

	@Override
	public List<BoardVO> getList(PagingVO pgvo) {
		// TODO Auto-generated method stub
		return mapper.getList(pgvo);
	}

	@Override
	public BoardDTO getDetail(long bno) {
		// TODO Auto-generated method stub
		BoardDTO bdto = new BoardDTO();
		bdto.setBvo(mapper.getDetail(bno));
		bdto.setFlist(fileMapper.getFileList(bno));
		return bdto;
	}
	
	@Transactional
	@Override
	public int modify(BoardDTO bdto) {
		// TODO Auto-generated method stub
		int isOk = mapper.modify(bdto.getBvo());
		if(isOk > 0 && bdto.getFlist().size() > 0) {
			long bno = bdto.getBvo().getBno();
			for(FileVO fvo : bdto.getFlist()) {
				fvo.setBno(bno);
				isOk *= fileMapper.insertFile(fvo);
			}
		}
		return isOk;
	}

	@Override
	public int delete(long bno) {
		// TODO Auto-generated method stub
		return mapper.delete(bno);
	}

	@Override
	public int getTotalCount(PagingVO pgvo) {
		// TODO Auto-generated method stub
		return mapper.getTotalCount(pgvo);
	}
	
}
