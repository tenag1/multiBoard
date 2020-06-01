package kr.domain.manage.service;

import java.util.List;

import kr.domain.manage.vo.CategoryVO;

public interface CategoryService {
	public List<CategoryVO> selectAll();
	public List<CategoryVO> selectGrp();
	public List<CategoryVO> selectSeq();
	public List<String> selectAllName();
	public void insert(CategoryVO vo);
	public void insertReply(CategoryVO vo, String parent);
	public void delete(int grp, int seq, int lvl);
}
