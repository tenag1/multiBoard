package kr.domain.manage.vo;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@XmlRootElement
public class BannerVO {
	private int idx;
	private Date startDate;
	private Date endDate;
	private Date uploadDate;
	private String title;
	private String url;
	private String image;
}
