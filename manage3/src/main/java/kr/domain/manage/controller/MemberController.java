package kr.domain.manage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.domain.manage.service.MemberService;
import kr.domain.manage.service.MemberServiceImpl;
import kr.domain.manage.vo.MemberVO;

@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;

	Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);

	// ajax 중복 id처리
	@RequestMapping(value = "/user/idCheck", method = RequestMethod.GET)
	@ResponseBody
	public int idCheck(@RequestParam("id") String id) {
		return memberService.idCheck(id);
	}

	// ajax 중복 email처리
	@RequestMapping(value = "/user/emailCheck", method = RequestMethod.GET)
	@ResponseBody
	public int emailCheck(@RequestParam("email") String email) {
		return memberService.emailCheck(email);
	}

	// ajax id 분실 처리
	@RequestMapping(value = "/user/idForgot", method = RequestMethod.GET)
	@ResponseBody
	public String idForgot(@RequestParam("email") String email, @RequestParam("birth") String birth) {
		return memberService.idFind(email, birth);
	}

	// ajax pwd 분실 처리
	@RequestMapping(value = "user/pwdForgot", method = RequestMethod.GET)
	@ResponseBody
	public int pwdForgot(@RequestParam("email") String email, @RequestParam("id") String id) {

		int no = memberService.pwdFind(email, id);
		return no;
	}

	// 회원 정보 찾기 페이지
	@RequestMapping(value = "/findInfo")
	public String findInfo() {
		return "findInfo";
	}

	// 회원가입 페이지
	@RequestMapping(value = "/user/form", method = RequestMethod.GET)
	public String UserForm(Model model, HttpServletRequest request) {
		// validation 사용할 객체를 주입
		// 세션이 있다면 기본 정보를 주입
		if (request.getSession().getAttribute("vo") != null) {
			MemberVO vo = (MemberVO) request.getSession().getAttribute("vo");
			model.addAttribute("memberVO", vo);
		} else {
			// 없다면 새 객체를 주입
			model.addAttribute("memberVO", new MemberVO());
			logger.info("회원가입시 새로운 memberVO " + new MemberVO());
		}
		return "user/form";
	}

	// 회원가입 처리
	@RequestMapping(value = "/user/formOk", method = RequestMethod.POST)
	public String UserFormOk(@Valid @ModelAttribute MemberVO memberVO, BindingResult result
			, HttpServletRequest request, Model model) {

		if (result.hasErrors()) {
			return "user/form";
		}
		// 패스워드 암호화
		memberVO.setPassword(bcryptPasswordEncoder.encode(memberVO.getPassword()));

		// 업데이트 후 세션에 업데이트 된 정보를 세팅함
		if (memberVO.getIdx() > 0) {
			memberService.update(memberVO);
			MemberVO dbVO = memberService.selectById(memberVO.getId());
			dbVO.setPassword("");

			request.getSession().setAttribute("vo", dbVO);

			return "redirect:/user/info";
		}
		memberService.insert(memberVO);
		return "redirect:/user/login";

	}

	// 로그인 페이지
	@RequestMapping(value = "/user/login")
	public String disLogin() {
		return "user/login";
	}

	// 로그인 확인
	@RequestMapping(value = "/user/loginCheck")
	@ResponseBody
	public boolean loginCheck(@ModelAttribute MemberVO memberVO, HttpServletRequest request) {
		boolean flag = true;
		MemberVO dbVO = memberService.selectById(memberVO.getId());
		if (dbVO == null || !bcryptPasswordEncoder.matches(memberVO.getPassword(), dbVO.getPassword())) {
			flag = false;
		}
		return flag;
	}

	// 유저 내 정보 페이지
	@RequestMapping(value = "/user/info")
	public String dpInfo(Model model, HttpServletRequest request) {
		return "user/myInfo";
	}

	// 유저 탈퇴
	@RequestMapping(value = "/user/leave")
	public String leave(@RequestParam("idx") int userIdx,
			HttpServletRequest request) {
		memberService.updateLeaveDate(userIdx);
		logger.info("leave - idx: " + userIdx);
		request.getSession().removeAttribute("vo");
		return "redirect:/logout";
	}
}
