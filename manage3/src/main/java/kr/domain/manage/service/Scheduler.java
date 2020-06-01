package kr.domain.manage.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class Scheduler {
	
	@Autowired
	private MemberService memberService;
	
	private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);
			
	@Scheduled(cron = "0 0 0 1/1 * ?")
	public void leaveUser() {
		logger.info("스케쥴러 작동");
		memberService.delete();
	}
}
