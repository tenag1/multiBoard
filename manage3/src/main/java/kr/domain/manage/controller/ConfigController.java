package kr.domain.manage.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.domain.manage.service.BoardService;
import kr.domain.manage.service.CategoryService;
import kr.domain.manage.service.ConfigService;
import kr.domain.manage.service.MemberService;
import kr.domain.manage.vo.CommVO;
import kr.domain.manage.vo.ConfigVO;
import kr.domain.manage.vo.MemberVO;
import kr.domain.manage.vo.PagingVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class ConfigController {

	@Autowired
	private ConfigService configService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private BoardService boardService;
	
	private static final Logger logger = LoggerFactory.getLogger(ConfigController.class);

	@RequestMapping(value = "/")
	public String index(Principal principal, HttpServletRequest request, Model model) {
		//로그인 성공시 돌아오는 default-target-url로 인증자가 있으면 사용자 정보를 session에 저장
		if(principal != null) {
			logger.info("index-principal: "+principal);
			MemberVO vo = memberService.selectById(principal.getName());
			vo.setPassword("");
			request.getSession().setAttribute("vo", vo);
			memberService.updateNoLeave(vo.getIdx());
		}
		//selectNew 
		//model.addAttribute("newBoard", boardService.selectNew(4));
		return "index";
	}
	//접근 거부 
	@RequestMapping(value = "/denied")
	public String denied() {
		return "denied";
	}
	
	@RequestMapping(value = { "/admin/configList", "/admin" })
	public String list(@ModelAttribute CommVO commVO, Model model) {

		PagingVO<ConfigVO> pagingVO = configService.selectList(commVO.getCurrentPage(),commVO.getPageSize(),commVO.getBlockSize());
		model.addAttribute("pagingVO", pagingVO);
		model.addAttribute("commVO", commVO);
		logger.info(" home - pagingVO" + pagingVO);

		return "config/list";
	}

	@RequestMapping(value = "/admin/configForm")
	public String insertBoard(@RequestParam(value = "no", defaultValue = "-1", required = false) int no, Model model) {
		model.addAttribute("configVO", new ConfigVO()); // 폼에 VO 보내기(검사용)
		if (no > 0) {
			ConfigVO vo = configService.selectByIdx(no);
			model.addAttribute("configVO", vo);
		}
		return "config/configForm";
	}
	
	@RequestMapping("/admin/nameCheck")
	@ResponseBody
	public int nameCheck(@RequestParam("tableName") String tableName) throws UnsupportedEncodingException {
		tableName = URLDecoder.decode(tableName, "UTF-8");
		return configService.nameCheck(tableName);
	}
	
	@RequestMapping(value = "/admin/configFormOk", method = RequestMethod.POST)
	public String formOk(@Valid @ModelAttribute ConfigVO configVO, BindingResult result,
			HttpServletRequest request, Model model) {

		if (result.hasErrors()) {
			return "config/configForm";
		}
		
		
		if (configVO.getIdx() > 0) {
			configService.update(configVO);
		} else {
			configService.insertConfig(configVO);
		}
		request.getSession().setAttribute("grpList", categoryService.selectGrp());
		request.getSession().setAttribute("seqList", categoryService.selectSeq());
    
		return "redirect:/admin/configList";
	}

	@RequestMapping(value = "/admin/configDelete", method = RequestMethod.POST)
	public String boardDelete(@RequestParam(value = "idx") int idx) {

		configService.delete(idx);

		return "redirect:/admin/configList";
	}
	
	@RequestMapping(value = "/admin/userList")
	public String userConfig(@ModelAttribute CommVO commvo, Model model) {
		
		PagingVO<MemberVO> pagingVO = memberService.selectList(commvo.getCurrentPage(), commvo.getPageSize(), commvo.getBlockSize());
		model.addAttribute("pagingVO", pagingVO);
		model.addAttribute("userCount", memberService.selectCount());
		return "config/configUser";
	}
	
	// 나중에 폼 select로 권한 1~몇까지 있는지 값 등록해줘야 할 듯
	@ModelAttribute("level")
	public List<Integer> level() {
		List<Integer> level = new ArrayList<Integer>();
		level.add(-1); // 사용 안 함
		level.add(0); // 모두 가능
		level.add(1); // 회원만 가능
		level.add(2); // 관리자만 가능
		return level;
	}
	

	@ModelAttribute("category")
	private List<String> categoryList(){
		List<String> list = categoryService.selectAllName();
		list.add("대항목추가");
		return list;
	}
	
}
