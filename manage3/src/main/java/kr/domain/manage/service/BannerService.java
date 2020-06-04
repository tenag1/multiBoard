package kr.domain.manage.service;

import org.springframework.web.multipart.MultipartFile;

import kr.domain.manage.vo.BannerVO;
import kr.domain.manage.vo.PagingVO;

public interface BannerService {
	public BannerVO selectByIdx(int idx);
	public void insert(BannerVO bannerVO, String path, MultipartFile file);
	public void update(BannerVO bannerVO);
	public void delete(int idx);
	public PagingVO<BannerVO> selectList(int currentPage, int pageSize, int blockSize);
}
