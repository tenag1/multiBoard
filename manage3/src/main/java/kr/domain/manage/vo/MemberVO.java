package kr.domain.manage.vo;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
@Data
public class MemberVO {
	private int idx;
	@Size(min = 4, max = 30)
	@Pattern(regexp = "^[A-Za-z0-9]+$")
	private String id;
	@Size(min = 8, max = 50)
	private String password;
	@NotEmpty
	private String name;
	private String nick;
	@Email
	@NotEmpty
	private String email;
	private int sex;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private Date birth;
	private String hp;	
	private char certify;	
	private String zipCode;
	private String addr;
	private String addr2;
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date joinDate;
	private char mailing;	
	private char sms;	
	private Date leaveDate;
	private int enabled;
	
	private MemberRoleVO memberLoleVO;
	
	public MemberVO() {
		certify = 'N';
		mailing = 'N';
		sms = 'N';
	}
}
