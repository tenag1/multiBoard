package kr.domain.manage.controller;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.domain.manage.vo.CommVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MailController {
	
	private static final Logger logger = LoggerFactory.getLogger(MailController.class);
	
	@RequestMapping(value = "/admin/mailSender")
	public String mailSender(@ModelAttribute CommVO commVO, Model model, HttpServletRequest request) throws AddressException, MessagingException {
		
		
		return "board/boardList";
	}
}
	
