package kr.domain.manage.dao;

import java.util.HashMap;
import java.util.List;

import kr.domain.manage.vo.MemberRoleVO;
import kr.domain.manage.vo.MemberVO;

public interface MemberDAO {
	public int selectCount();
	public String selectByIdx_Id(int idx);
	public MemberVO selectByEmail(String email);
	public MemberVO selectByIdx(int idx);
	public MemberVO selectById(String id);
	public List<MemberVO> selectList(HashMap<String, Integer> map);
	public void insert(MemberVO memberVO);
	public void update(MemberVO memberVO);
	public void updatePwd(MemberVO memberVO);
	public void updateCertify(int idx);
	public void updateLeaveDate(int idx);
	public void delete();
	public void updateNoLeave(int idx);
	public void insertRole(MemberRoleVO memberRoleVO);
	public MemberRoleVO selectRoleByIdx(int mb_idx);
	public List<String> selectRoles();
}
