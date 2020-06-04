package kr.domain.manage.dao;

import java.util.HashMap;
import java.util.List;

import kr.domain.manage.vo.BannerVO;

public interface BannerDAO {
	public int selectCount();
	public BannerVO selectByIdx(int idx);
	public void insert(BannerVO bannerVO);
	public void update(BannerVO bannerVO);
	public void delete(int idx);
	public List<BannerVO> selectList(HashMap<String, Integer> map);
}
