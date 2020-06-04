package kr.domain.manage.service;

import java.util.List;
import java.util.Map;

import kr.domain.manage.vo.BoardVO;
import kr.domain.manage.vo.ConfigVO;
import kr.domain.manage.vo.PagingVO;

public interface ConfigService {
	public int selectCount();
	public int nameCheck(String tableName);
	public void insertConfig(ConfigVO configVO);
	public PagingVO<ConfigVO> selectList(int currentPage, int pageSize, int blockSize);
	public ConfigVO selectByIdx(int idx);
	public void update(ConfigVO configVO);
	public void delete(int idx);
	public Map<String, List<BoardVO>> selectMain();
	public void updateMainSelect(int idx, int mainSelect);
}
