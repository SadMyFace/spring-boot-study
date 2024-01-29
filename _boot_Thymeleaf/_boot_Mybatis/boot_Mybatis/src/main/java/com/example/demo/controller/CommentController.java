package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.CommentVO;
import com.example.demo.domain.PagingVO;
import com.example.demo.handler.PagingHandler;
import com.example.demo.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/comment/*")
@RestController
public class CommentController {
	
	private final CommentService csv;
	
	@PostMapping("/post")
	@ResponseBody
	public String post(@RequestBody CommentVO cvo){
		log.info(">>> cvo >>> {}", cvo);
		
		int isOk = csv.postCommet(cvo);
		
		return isOk > 0 ? "1" : "0";
	}
	
	@GetMapping(value="/{bno}/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagingHandler> list(@PathVariable("bno") long bno, @PathVariable("page") int page){
		log.info(">>> bno >>> " + bno);
		log.info(">>> page >>> " + page);
		PagingVO pgvo = new PagingVO(page, 5);
		PagingHandler ph = csv.getList(bno, pgvo);
		
		return new ResponseEntity<PagingHandler>(ph, HttpStatus.OK);
		
	}
	
	@PutMapping(value="/edit", consumes = "application/json", produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String edit(@RequestBody CommentVO cvo){
		log.info(">>> cvo >>> {}", cvo);
		
		int isOk = csv.edit(cvo);
		
		return isOk > 0 ? "1" : "0";
	}
	
	@DeleteMapping(value="/delete/{cno}", produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String delete(@PathVariable("cno") int cno) {
		log.info(">>> cno >>> " + cno);
		
		int isOk = csv.delete(cno);
		
		return isOk > 0 ? "1" : "0"; 
	}
	
}
