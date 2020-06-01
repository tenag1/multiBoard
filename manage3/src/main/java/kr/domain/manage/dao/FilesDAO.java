package kr.domain.manage.dao;

import java.util.List;

import kr.domain.manage.vo.FilesVO;

public interface FilesDAO {
	public int selectCount(int ref);
	public List<FilesVO> selectList(int ref);
	public FilesVO selectByIdx(int idx);
	public void insert(FilesVO filesVO);
	public void deleteByIdx(int idx);
}