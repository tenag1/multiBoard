package kr.domain.manage.service;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.domain.manage.dao.BannerDAO;
import kr.domain.manage.vo.BannerVO;
import kr.domain.manage.vo.PagingVO;

@Transactional
@Service("bannerService")
public class BannerServiceImpl implements BannerService{

	@Autowired
	private BannerDAO bannerDAO;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public BannerVO selectByIdx(int idx) {
		return bannerDAO.selectByIdx(idx);
	}

	@Override
	public void insert(BannerVO bannerVO, String path, MultipartFile file) {
		//파일 업로드
		
		bannerDAO.insert(bannerVO);
	}

	@Override
	public void update(BannerVO bannerVO) {
		bannerDAO.update(bannerVO);
	}

	@Override
	public void delete(int idx) {
		//업로드된 파일도 같이 삭제
		bannerDAO.delete(idx);
	}

	@Override
	public PagingVO<BannerVO> selectList(int currentPage, int pageSize, int blockSize) {
		int totalCount = bannerDAO.selectCount();
		PagingVO<BannerVO> pagingVO = new PagingVO<BannerVO>(totalCount, currentPage, pageSize, blockSize);
		
		//PagingVO의 계산된 startNo, endNO로 배너 리스트를 가져와 주입 
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("startNo", pagingVO.getStartNo());
		map.put("endNo", pagingVO.getEndNo());
		
		pagingVO.setList(bannerDAO.selectList(map));
		return pagingVO;
	}
	
}
