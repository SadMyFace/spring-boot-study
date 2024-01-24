package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.BoardVO;

@Mapper
public interface BoardDAO {

	int register(BoardVO bvo);

	List<BoardVO> getList();

	BoardVO getDetail(long bno);

	int modify(BoardVO bvo);

	int delete(long bno);

}
