package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.BoardVO;
import com.example.demo.domain.PagingVO;

@Mapper
public interface BoardMapper {

	int register(BoardVO bvo);

	List<BoardVO> getList(PagingVO pgvo);

	BoardVO getDetail(long bno);

	int modify(BoardVO bvo);

	int delete(long bno);

	int getTotalCount(PagingVO pgvo);

	long getBno();

}
