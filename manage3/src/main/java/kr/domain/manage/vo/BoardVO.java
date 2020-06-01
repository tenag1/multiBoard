package kr.domain.manage.vo;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@XmlRootElement
public class BoardVO {
	private int idx;		// 키필드
	private int cf_idx;   // 그룹 번호
	@Size(min = 1)
	private String subject;
	@Size(min = 1)
	private String content;
	private String writer;
	private String writer_nick;
	private String password;
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date   createDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date   updateDate;
	private int hit;
	// 댓글 
	private int commentCount;
	private List<CommentsVO> commentList; 
	// 파일
	private int fileCount;
	private List<FilesVO> fileList;
}
