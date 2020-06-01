package kr.domain.manage.service;

import java.util.List;

import kr.domain.manage.vo.BoardVO;
import kr.domain.manage.vo.PagingVO;

public interface BoardService {
	public int selectCount();
	public int selectCountByNo(int no);
	public BoardVO selectByIdx(int idx, int mode);
	public PagingVO<BoardVO> selectList(int no, int currentPage, int pageSize, int blockSize);
	public void insert(BoardVO boardVO);
	public void update(BoardVO boardVO, String deleteFiles, String path);
	public void deleteByIdx(int idx, String writer, String path);
	public List<BoardVO> selectNew(int cf_idx);
}
