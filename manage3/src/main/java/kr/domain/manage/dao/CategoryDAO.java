package kr.domain.manage.dao;

import java.util.HashMap;
import java.util.List;

import kr.domain.manage.vo.CategoryVO;

public interface CategoryDAO {
	public List<CategoryVO> selectAll();
	public List<CategoryVO> selectGrp();
	public List<CategoryVO> selectSeq();
	public List<String> selectAllName();
	public CategoryVO selectByContent(String content);
	public int selectMaxIdx();
	public void insert(CategoryVO categoryVO);
	public void updateGrp(HashMap<String, Integer> map);
	public void insertReply(CategoryVO categoryVO);
	public void updateSeq(HashMap<String, Integer> map);
	public List<CategoryVO> selectDeleteCategory(HashMap<String, Integer> map);
	public void delete(int idx);
}