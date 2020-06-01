package kr.domain.manage.vo;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@XmlRootElement
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConfigVO implements Serializable{
	
	private static final long serialVersionUID = -1531636716559104778L;
	
	private int idx;
	@Size(min = 3, max = 20)
	private String tableName;
	@Size(min = 1, max = 80)
	private String subject; 
	@NotEmpty
	private String admin;
	private String Category;
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date createDate;
	private int list_level; // level들 -1일 경우 사용x
	private int read_level;
	private int writer_level;
	private int comment_level;
	private char use_secret;
	@NotNull
	private int newIcon;      // 0 일 경우 사용x
	@NotNull
	private int hotIcon;      // 0 일 경우 사용x
	@NotNull
	private int gallery_cols; // 0일 경우 사용x
}
