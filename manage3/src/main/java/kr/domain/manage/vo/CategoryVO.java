package kr.domain.manage.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryVO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int idx;
	private int ref;
	private int grp;
	private int seq;
	private int lvl;
	private String content;
	
	public CategoryVO(int ref, String content) {
		this.ref = ref;
		this.content = content;
	}
}
