package kr.domain.manage.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.domain.manage.controller.ConfigController;
import kr.domain.manage.dao.ConfigDAO;
import kr.domain.manage.vo.BoardVO;
import kr.domain.manage.vo.CategoryVO;
import kr.domain.manage.vo.ConfigVO;
import kr.domain.manage.vo.PagingVO;

@Transactional
@Service("configService")
public class ConfigServiceImpl implements ConfigService {

	@Autowired
	private ConfigDAO configDAO;
	@Autowired
	private CategoryService categoryService;
	
	private static final Logger logger = LoggerFactory.getLogger(ConfigController.class);
	
	@Override
	public void insertConfig(ConfigVO configVO) {
		configDAO.insertConfig(configVO);
		//저장 후 idx를 가져옴
		int idx = configDAO.selectMaxIdx();
		CategoryVO categoryVO = new CategoryVO(idx, configVO.getSubject());
		
		if(configVO.getCategory().equals("대항목추가")) {
			categoryService.insert(categoryVO);
		}else {
			categoryService.insertReply(categoryVO, configVO.getCategory());
		}
		
	}

	@Override
	public PagingVO<ConfigVO> selectList(int currentPage, int pageSize, int blockSize) {
		
		int totalCount =configDAO.selectCount();
		PagingVO<ConfigVO> pagingVO = new PagingVO<ConfigVO>(totalCount, currentPage, pageSize, blockSize);
		
		//페이징 VO 안에 넣을 List<BoardVO> 가져오기
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("startNo", pagingVO.getStartNo());
		map.put("endNo", pagingVO.getEndNo());
		
		List<ConfigVO> list = configDAO.selectList(map);
		
		pagingVO.setList(list);
		
		logger.info("selectList 리턴 값: "+pagingVO);
		
		return pagingVO;
	}

	@Override
	public ConfigVO selectByIdx(int idx) {
		return configDAO.selectByIdx(idx);
	}

	@Override
	public int nameCheck(String tableName) {

		int flag = 0;
		
		flag = configDAO.selectByName(tableName) == null? 0: 1;
		
		logger.info("flag: "+flag);
		if(flag != 1) {
			tableName = tableName.toUpperCase();
			for(char c: tableName.toCharArray()) {
				if(c<'A' || c>'Z') {
					flag = 1;
				}
			}
		}
		logger.info("flag: "+flag);
		return flag;
	}


	@Override
	public void delete(int idx) {
		ConfigVO vo = configDAO.selectByIdx(idx);
		if(vo != null ) {
			configDAO.delete(idx);
		}
	}

	@Override
	public void update(ConfigVO configVO) {
		if(configVO != null ) {
			configDAO.update(configVO);
		}
	}

	@Override
	public int selectCount() {
		// TODO Auto-generated method stub
		return 0;
	}
}
