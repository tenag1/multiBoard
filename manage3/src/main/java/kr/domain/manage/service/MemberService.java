package kr.domain.manage.service;

import java.util.List;

import kr.domain.manage.vo.MemberVO;
import kr.domain.manage.vo.PagingVO;

public interface MemberService{
	public void insert(MemberVO memberVO);
	public int idCheck(String id);
	public int emailCheck(String email);
	public String idFind(String email, String birth);
	public int pwdFind(String email, String id);
	public int selectCount();
	public MemberVO selectByIdx(int idx);
	public MemberVO selectById(String id);
	public PagingVO<MemberVO> selectList(int currentPage, int pageSize, int blockSize);
	public void update(MemberVO memberVO);
	public void updateCertify(int idx);
	public void updateLeaveDate(int idx);
	public void delete();
	public void updateNoLeave(int idx);
	
	public List<String> selectRoles();
}
