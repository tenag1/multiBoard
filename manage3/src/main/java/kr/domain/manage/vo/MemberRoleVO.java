package kr.domain.manage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class MemberRoleVO {
	private int idx;
	private int mb_idx;
	private String role;
}
