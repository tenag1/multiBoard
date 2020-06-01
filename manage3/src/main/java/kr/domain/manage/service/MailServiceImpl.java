package kr.domain.manage.service;

import javax.annotation.Resource;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service("mailService")
public class MailServiceImpl implements MailService {

	@Autowired
	JavaMailSender javaMailSender;
	
	@Autowired
	String address;
	
	@Override
	public void sendMail(String addresses, String subject, String content) {
		try {
			MimeMessagePreparator preparator = new MimeMessagePreparator() {

				@Override
				public void prepare(MimeMessage mimeMessage) throws Exception {
					// Helper객체 생성
					MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
					helper.setFrom(address);
					helper.setTo(getAddresses(addresses));
					
					helper.setSubject(subject);
					helper.setText("<html><body>" + content + "</body></html>", true);
					// 파일 첨부
					//helper.addInline("dog", new ClassPathResource("dog.jpg"));
				}
			};
			javaMailSender.send(preparator);
		}catch (Exception e) {
			e.getStackTrace();
			e.getMessage();
		}finally {
			System.out.println("발송 완료");
		}
	}

	private InternetAddress[] getAddresses(String addresses) throws AddressException {
		String[] array = addresses.split(",");
		InternetAddress[] addArr = new InternetAddress[array.length];
		for (int i = 0; i < addArr.length; i++) {
			addArr[i] = new InternetAddress(array[i]);
		}
		return addArr;
	}
}
