package kr.domain.board.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import kr.domain.board.service.TestService;
import kr.domain.board.vo.TestVO;

@Controller
public class TestController {

	@Autowired
	private TestService testService;
	
	@RequestMapping(value="/testVO")
	@ResponseBody
	public TestVO testObject() {
		return new TestVO(1,"한사람",22);
	}
	
	@RequestMapping(value="/testText" , produces="text/plain;charset=utf-8")
	@ResponseBody
	public String testText() {
		return "한글qwerty`12345!@#$%";
	}

	@RequestMapping(value="/testDB")
	public String testDB(Model model) {
		model.addAttribute("today", testService.today());
		model.addAttribute("mul", testService.mul(12,34));
		model.addAttribute("sum", testService.sum(1,2,3));
		return "testDB";
	}
	
	// redirect:/ 시 post방식으로 파라미터들을 전송하기
	// 보내기
	@RequestMapping(value = "/redirect1")
	public String redirect1(RedirectAttributes redirectAttributes) {
//		Map<String, Object> map = new HashMap<String, Object>();
	    redirectAttributes.addFlashAttribute("name", "잠자리");
	    redirectAttributes.addFlashAttribute("age", "3");
	    return "redirect:/herepage1";
	}
	// redirect:/ 는 get방식이다. 그래도 ? 파라미터는 노출되지 않는다.
	// 받기
	@RequestMapping(value = "/herepage1") 
	public String herepagePost1(HttpServletRequest request, Model model){
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if(flashMap != null) {
			model.addAttribute("name", flashMap.get("name"));
			model.addAttribute("age", flashMap.get("age"));
		}
	    return "herepage";
	}

	// 보내기 2
		@RequestMapping(value = "/redirect2")
		public String redirect2(HttpServletRequest request) {
			FlashMap map = RequestContextUtils.getOutputFlashMap(request);
			map.put("name", "부라타치즈");
			map.put("age", "1");
		    return "redirect:/herepage2";
		}
		// redirect:/ 는 get방식이다. 그래도 ? 파라미터는 노출되지 않는다.
	// 받기 2
		@RequestMapping(value = "/herepage2") 
		public String herepagePost2(HttpServletRequest request, Model model){
			Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
			if(flashMap != null) {
				model.addAttribute("name", flashMap.get("name"));
				model.addAttribute("age", flashMap.get("age"));
			}
		    return "herepage2";
		}
}
