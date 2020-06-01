package kr.domain.manage.dao;

import java.util.HashMap;
import java.util.List;

import kr.domain.manage.vo.BoardVO;

public interface BoardDAO {
	public int selectCount();
	public int selectCountByCf_idx(int cf_idx);
	public BoardVO selectByIdx(int idx);
	public int selectByWriter(String writer);
	public List<BoardVO> selectList(HashMap<String, Integer> map);
	public void insert(BoardVO boardVO);
	public void update(BoardVO boardVO);
	public void deleteByIdx(int idx);
	public void updateHit(int idx);
	public List<BoardVO> selectNew(int cf_idx);
}
