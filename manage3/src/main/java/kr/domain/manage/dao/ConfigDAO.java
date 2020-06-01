package kr.domain.manage.dao;

import java.util.HashMap;
import java.util.List;

import kr.domain.manage.vo.ConfigVO;

public interface ConfigDAO {
	public int selectCount();
	public int selectMaxIdx();
	public String selectByName(String tableName);
	public void insertConfig(ConfigVO configVO);
	public List<ConfigVO> selectList(HashMap<String, Integer> map);
	public ConfigVO selectByIdx(int idx);
	public void update(ConfigVO configVO);
	public void delete(int idx);
}
