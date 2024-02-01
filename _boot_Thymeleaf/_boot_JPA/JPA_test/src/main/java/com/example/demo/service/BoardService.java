package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.demo.dto.BoardDTO;
import com.example.demo.entity.Board;

public interface BoardService {

	Long register(BoardDTO bdto);
	
	//BoardDTO 객체를 DB(Entity객체 = Board)에 저장
	//Board => BoardDTO로 변환
	default Board convertDtoToEntity(BoardDTO bdto) {
		return Board.builder()
				.bno(bdto.getBno())
				.title(bdto.getTitle())
				.writer(bdto.getWriter())
				.content(bdto.getContent())
				.build();
	}
	
	//DB(Entity객체 = Board)에서 가져온 데이터를 BoardDTO 객체로 변환
	default BoardDTO convertEntityToDto(Board board) {
		return BoardDTO.builder()
				.bno(board.getBno())
				.title(board.getTitle())
				.writer(board.getWriter())
				.content(board.getContent())
				.regAt(board.getRegAt())
				.modAt(board.getModAt())
				.build();
	}

	//List<BoardDTO> getList();
	Page<BoardDTO> getList(int page);

	BoardDTO getDetail(Long bno);

	Long modify(BoardDTO bdto);

	void delete(Long bno);
	
}
