package kr.domain.manage.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentsVO {
	private int idx;
	private int ref;
	private String mb_id;
	private String mb_nick;
	private Date createDate;
	private String content;
	private Date updateDate;
}
