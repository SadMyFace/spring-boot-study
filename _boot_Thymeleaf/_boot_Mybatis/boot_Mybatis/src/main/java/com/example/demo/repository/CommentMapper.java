package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.CommentVO;

@Mapper
public interface CommentMapper {

	int postComment(CommentVO cvo);

	List<CommentVO> getList(long bno);

}
