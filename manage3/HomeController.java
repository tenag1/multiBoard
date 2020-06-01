package kr.domain.board;

import java.nio.file.FileVisitOption;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;

import kr.domain.board.service.BoardService;
import kr.domain.board.vo.CommVO;
import kr.domain.board.vo.CommentVO;
import kr.domain.board.vo.FreeBoardVO;
import kr.domain.board.vo.PagingVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	private BoardService boardService;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/")
	public String home(HttpServletRequest request, @ModelAttribute CommVO commVO, Model model) {
		
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if(flashMap != null) {
			Integer p = (Integer) flashMap.get("p");
			if(p != null) commVO.setP(p);
			Integer s = (Integer) flashMap.get("s");
			if(s != null) commVO.setS(s);
			Integer b = (Integer) flashMap.get("b");
			if(b != null) commVO.setB(b);
		}
		
		PagingVO<FreeBoardVO> pagingVO = boardService.selectList(commVO.getCurrentPage(), commVO.getPageSize(), commVO.getBlockSize());
		model.addAttribute("pagingVO", pagingVO);
		model.addAttribute("cvo", commVO);
		logger.info("HomeController.index- pVO: "+pagingVO);
		logger.info("HomeController.index- cVO: "+commVO);
		return "index";
	}
	

	@RequestMapping(value = "/insert")
	public String insert(@ModelAttribute CommVO commVO, Model model) {
		logger.info("HomeController.insert- cVO: "+commVO);
		
		model.addAttribute("cvo", commVO);
		return "insert";
	}
	
	@RequestMapping(value = "/insertOk", method = RequestMethod.GET)
	public String insertOkGet() {
		return "redirect:/";
	}
	
	@RequestMapping(value = "/insertOk", method = RequestMethod.POST)
	public String insertOkPost(HttpServletRequest request, @ModelAttribute CommVO commVO,
							 @ModelAttribute FreeBoardVO vo, Model model) {

		logger.info("HomeController.insertOkPost- vo: "+vo);
		logger.info("HomeController.insertOkPost- commVO: "+commVO);
		
		FlashMap map = RequestContextUtils.getOutputFlashMap(request);
		map.put("p", 1);
		map.put("s", commVO.getPageSize());
		map.put("b", commVO.getBlockSize());
		
		boardService.insert(vo);
		
		return "redirect:/";
	}
//		return "redirect:"+request.getContextPath(); --> board/board로 감...
	
	@RequestMapping(value = "/view")
	public String view(@ModelAttribute CommVO commVO,HttpServletRequest request, Model model) {
		
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if(flashMap != null) {
			Integer p = (Integer) flashMap.get("p");
			if(p != null) commVO.setP(p);
			Integer s = (Integer) flashMap.get("s");
			if(s != null) commVO.setS(s);
			Integer b = (Integer) flashMap.get("b");
			if(b != null) commVO.setB(b);
			Integer idx = (Integer) flashMap.get("idx");
			if(idx != null) commVO.setIdx(idx);
			Integer m = (Integer) flashMap.get("m");
			if(m != null) commVO.setM(m);
		}
		
		FreeBoardVO vo = boardService.selectByIdx(commVO.getIdx(), commVO.getMode());
		model.addAttribute("vo", vo);
		model.addAttribute("newLine", "\n");
		model.addAttribute("br", "<br/>");
		model.addAttribute("cvo", commVO);
		return "view";
	}
	
	//댓글 저장
	@RequestMapping(value = "/commentInsertOk")
	public String commentInsertOk(@ModelAttribute CommVO commVO, @ModelAttribute CommentVO commentVO,
								  HttpServletRequest request, Model model) {
		commentVO.setIp(request.getRemoteAddr());
		boardService.commentInsert(commentVO);
		
		FlashMap map = RequestContextUtils.getOutputFlashMap(request);
		map.put("p", commVO.getCurrentPage());
		map.put("s", commVO.getPageSize());
		map.put("b", commVO.getBlockSize());
		map.put("idx", commentVO.getRef());
		map.put("m", 0);
		
		return "redirect:/view";
	}
	//댓글 수정
	@RequestMapping(value = "/commentUpdateOk")
	public String commentUpdateOk(@ModelAttribute CommVO commVO, @ModelAttribute CommentVO commentVO,
								  HttpServletRequest request, Model model) {
		commentVO.setIp(request.getRemoteAddr());
		boardService.commentUpdate(commentVO);
		
		FlashMap map = RequestContextUtils.getOutputFlashMap(request);
		map.put("p", commVO.getCurrentPage());
		map.put("s", commVO.getPageSize());
		map.put("b", commVO.getBlockSize());
		map.put("idx", commentVO.getRef());
		map.put("m", 0);
		
		return "redirect:/view";
	}
	//댓글 수정
	@RequestMapping(value = "/commentDeleteOk")
	public String commentDeleteOk(@ModelAttribute CommVO commVO, @ModelAttribute CommentVO commentVO,
								  HttpServletRequest request, Model model) {
		
		boardService.commentDelete(commentVO);
		
		FlashMap map = RequestContextUtils.getOutputFlashMap(request);
		map.put("p", commVO.getCurrentPage());
		map.put("s", commVO.getPageSize());
		map.put("b", commVO.getBlockSize());
		map.put("idx", commentVO.getRef());
		map.put("m", 0);
		
		return "redirect:/view";
	}
	
	@RequestMapping(value = "/update")
	public String update(@ModelAttribute CommVO commVO, Model model) {
		FreeBoardVO vo = boardService.selectByIdx(commVO.getIdx(), commVO.getMode());
		model.addAttribute("vo", vo);
		model.addAttribute("cvo", commVO);
		return "update";
	}
	
	@RequestMapping(value = "/updateOk", method = RequestMethod.GET)
	public String updateOkGet() {
		return "redirect:/";
	}
	
	@RequestMapping(value = "/updateOk", method = RequestMethod.POST)
	public String updateOkPost(HttpServletRequest request, @ModelAttribute CommVO commVO,
							 @ModelAttribute FreeBoardVO vo, Model model) {

		boardService.update(vo);
		
		FlashMap map = RequestContextUtils.getOutputFlashMap(request);
		map.put("p", commVO.getCurrentPage());
		map.put("s", commVO.getPageSize());
		map.put("b", commVO.getBlockSize());
		map.put("idx", vo.getIdx());
		map.put("m", 0);
		
		return "redirect:/view";
	}
	
	@RequestMapping(value = "/delete")
	public String delete(@ModelAttribute CommVO commVO, Model model) {
		FreeBoardVO vo = boardService.selectByIdx(commVO.getIdx(), commVO.getMode());
		model.addAttribute("vo", vo);
		model.addAttribute("cvo", commVO);
		return "delete";
	}
	
	@RequestMapping(value = "/deleteOk", method = RequestMethod.GET)
	public String deleteOkGet() {
		return "redirect:/";
	}
	
	@RequestMapping(value = "/deleteOk", method = RequestMethod.POST)
	public String deleteOkPost(HttpServletRequest request, @ModelAttribute CommVO commVO,
							 @ModelAttribute FreeBoardVO vo, Model model) {

		boardService.delete(vo);
		
		FlashMap map = RequestContextUtils.getOutputFlashMap(request);
		map.put("p", commVO.getCurrentPage());
		map.put("s", commVO.getPageSize());
		map.put("b", commVO.getBlockSize());
		
		return "redirect:/";
	}
}
