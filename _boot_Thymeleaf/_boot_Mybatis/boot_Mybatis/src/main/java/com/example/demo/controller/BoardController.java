package com.example.demo.controller;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.BoardVO;
import com.example.demo.domain.PagingVO;
import com.example.demo.handler.PagingHandler;
import com.example.demo.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/board/*")
@RequiredArgsConstructor
@Controller
public class BoardController {
	
	private final BoardService bsv;
//	private final Logger log = LoggerFactory.getLogger(BoardController.class);
	
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/register")
	public String register(BoardVO bvo) {
		log.info(">>> bvo >>> {}", bvo);
		
		int isOk = bsv.register(bvo);
		
		return "redirect:/board/list";
	}
	
	@GetMapping("/list")
	public void list(Model m, PagingVO pgvo) {
		log.info(">>> pgvo >>> {}", pgvo);
		List<BoardVO> list = bsv.getList(pgvo);
		
		//totalCount
		int totalCount = bsv.getTotalCount(pgvo);
		//PagingHanler 객체 생성
		PagingHandler ph = new PagingHandler(pgvo, totalCount);
		
		m.addAttribute("list", list);
		//PagingHandler 객체 보내기
		m.addAttribute("ph", ph);
	}
	
	@GetMapping({"/detail", "/modify"})
	public void detail(Model m, @RequestParam("bno") long bno){
		log.info(">>> bno >>> " + bno);
		
		m.addAttribute("bvo", bsv.getDetail(bno));
	}
	
	@PostMapping("/modify")
	public String modify(BoardVO bvo) {
		log.info(">>> bvo >>> {}", bvo);
		
		int isOk = bsv.modify(bvo);
		
		return "redirect:/board/detail?bno=" + bvo.getBno();
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("bno") long bno, RedirectAttributes re) {
		log.info(">>> bno >>> " + bno);
		
		int isOk = bsv.delete(bno);
		
		re.addFlashAttribute("isDel", isOk);
		
		return "redirect:/board/list";
	}
}
