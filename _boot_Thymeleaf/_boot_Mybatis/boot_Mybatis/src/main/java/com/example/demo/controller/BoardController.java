package com.example.demo.controller;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.BoardDTO;
import com.example.demo.domain.BoardVO;
import com.example.demo.domain.FileVO;
import com.example.demo.domain.PagingVO;
import com.example.demo.handler.FileHandler;
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
	private final FileHandler fh;
	
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/register")
	public String register(BoardVO bvo, @RequestParam(name="files", required = false) MultipartFile[] files) {
		log.info(">>> bvo >>> {}", bvo);
		List<FileVO> flist = null;
		if(files[0].getSize() > 0 || files != null) {
			//파일 핸들러 작업
			flist = fh.uploadFiles(files);
		}
		
		int isOk = bsv.register(new BoardDTO(bvo, flist));
		//파일 업로드 부분 추가
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
	
	@GetMapping("/detail")
	public void detail(Model m, @RequestParam("bno") long bno){
		log.info(">>> bno >>> " + bno);
		
		m.addAttribute("bdto", bsv.getDetail(bno));
	}
	
	@PostMapping("/modify")
	public String modify(BoardVO bvo, @RequestParam(name="files", required = false) MultipartFile[] files) {
		log.info(">>> bvo >>> {}", bvo);
		List<FileVO> flist = null;
		if(files[0].getSize() > 0 || files != null) {
			flist = fh.uploadFiles(files);
		}
		
		int isOk = bsv.modify(new BoardDTO(bvo, flist));
		
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
