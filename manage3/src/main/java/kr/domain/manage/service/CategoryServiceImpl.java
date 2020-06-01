package kr.domain.manage.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.domain.manage.dao.CategoryDAO;
import kr.domain.manage.vo.CategoryVO;

@Transactional
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDAO categoryDAO;
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public List<CategoryVO> selectAll() {
		return categoryDAO.selectAll();
	}

	@Override
	public List<CategoryVO> selectGrp() {
		logger.info("grp: "+categoryDAO.selectGrp());
		return categoryDAO.selectGrp();
	}

	@Override
	public List<CategoryVO> selectSeq() {
		logger.info("grp: "+categoryDAO.selectSeq());
		return categoryDAO.selectSeq();
	}

	@Override
	public void insert(CategoryVO vo) {
		if(vo!=null) {
			//저장 후 저장된 idx를 구하고
			categoryDAO.insert(vo);
			int grp = categoryDAO.selectMaxIdx();
			//그룹에 원본 글 번호 넣기
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			map.put("grp", grp);
			map.put("idx", grp);
			categoryDAO.updateGrp(map);
		}
	}

	@Override
	public void insertReply(CategoryVO vo, String parent) {
		CategoryVO parentVO = categoryDAO.selectByContent(parent);
		logger.info("categoryService insertReply - parentVO: "+ parentVO);
		if(parentVO!=null) {
			//원본 글의 레벨+1, seq +1
			vo.setGrp(parentVO.getGrp());
			vo.setLvl(parentVO.getLvl()+1);
			vo.setSeq(parentVO.getSeq()+1);
			logger.info("categoryService insertReply - vo: "+ vo);
			
			//나보다 크거나 같은 seq값을 모두 +1
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			map.put("grp", vo.getGrp());
			map.put("seq", vo.getSeq());
			
			categoryDAO.updateSeq(map);
			//글 추가
			categoryDAO.insertReply(vo);
		}
	}

	@Override
	public void delete(int grp, int seq, int lvl) {
		//같은 grp 내 seq가 큰 글 모두 가져옴
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("grp", grp);
		map.put("seq", seq);
		List<CategoryVO> list = categoryDAO.selectDeleteCategory(map);
		// 글 중 같은 레벨이 나타나기 전까지 삭제
		if(list != null) {
			categoryDAO.delete(list.get(0).getIdx());
			for(int i = 1; i<list.size(); i++) {
				//레벨이 적다면 종료
				if(lvl>=list.get(i).getLvl()) break;
				categoryDAO.delete(list.get(i).getIdx());
			}
		}
		
	}

	@Override
	public List<String> selectAllName() {
		return categoryDAO.selectAllName();
	}

}
