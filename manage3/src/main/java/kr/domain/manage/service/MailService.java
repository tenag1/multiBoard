package kr.domain.manage.service;

public interface MailService {
	public void sendMail(String email, String subject, String content);
}
