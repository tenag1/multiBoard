package kr.domain.manage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FilesVO {
	private int idx;
	private int ref;
	private String origName;
	private String saveName;
}
