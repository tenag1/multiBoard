package kr.domain.manage.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.domain.manage.controller.ConfigController;
import kr.domain.manage.dao.BoardDAO;
import kr.domain.manage.dao.CommentDAO;
import kr.domain.manage.dao.FilesDAO;
import kr.domain.manage.vo.BoardVO;
import kr.domain.manage.vo.FilesVO;
import kr.domain.manage.vo.PagingVO;
@Transactional
@Service("boardService")
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardDAO boardDAO;
	@Autowired
	private CommentDAO commentDAO;
	@Autowired
	private FilesDAO filesDAO;
	
	private static final Logger logger = LoggerFactory.getLogger(ConfigController.class);
	
	//사이트의 글 전체 개수 가져오기
	@Override
	public int selectCount() {
		int count = boardDAO.selectCount();
		logger.info("selectCount-count: "+count);
		return count;
	}
	//해당 게시판의 글 전체 개수 가져오기
	@Override
	public int selectCountByNo(int no) {
		int count = boardDAO.selectCountByCf_idx(no);
		logger.info("selectCountByno-count: "+count);
		return count;
	}
	//idx로 글 하나 가져오기
	@Override
	public BoardVO selectByIdx(int idx, int mode) {
		BoardVO vo = boardDAO.selectByIdx(idx);
		logger.info("selectByIdx-mode전 vo: "+vo);
		//1일 경우 조회수 증가(list에서 선택시)
		if(mode == 1) { 
			vo.setHit(vo.getHit()+1);
			boardDAO.updateHit(idx);
		}
		//댓글, 파일 
		if(vo != null) {
			vo.setCommentList(commentDAO.selectList(vo.getIdx()));
			vo.setFileList(filesDAO.selectList(vo.getIdx()));

			if(vo.getFileList() != null) {
				vo.setFileCount(vo.getFileList().size());
			}else {
				vo.setFileCount(0);
			}
		}
		logger.info("selectByIdx-mode후 vo: "+vo);
		return vo;
	}

	@Override
	public PagingVO<BoardVO> selectList(int no, int currentPage, int pageSize, int blockSize) {
		logger.info("selectList 인수: "+no+", "+currentPage+", "+pageSize+", "+blockSize);
		
		//페이징 VO 생성
		int totalCount =boardDAO.selectCountByCf_idx(no);
		PagingVO<BoardVO> pagingVO = new PagingVO<BoardVO>(no, totalCount, currentPage, pageSize, blockSize);
		
		//페이징 VO 안에 넣을 List<BoardVO> 가져오기
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		logger.info("스타트 넘버 엔드넘버 확인: "+ pagingVO.getStartNo()+", "+pagingVO.getEndNo());
		map.put("startNo", pagingVO.getStartNo());
		map.put("endNo", pagingVO.getEndNo());
		map.put("cf_idx", no);
		
		List<BoardVO> list = boardDAO.selectList(map);
		//BoardVO 각각에 댓글(제목 옆 표시용), 파일 넣기 (표시+이름)
		if(list != null) {
			for(BoardVO vo : list) {
				vo.setCommentCount(commentDAO.selectCount(vo.getIdx()));
				vo.setFileList(filesDAO.selectList(vo.getIdx()));
			}
		}
		pagingVO.setList(list);
		
		logger.info("selectList 리턴 값: "+pagingVO);
		return pagingVO;
	}

	@Override
	public void insert(BoardVO boardVO) {
		if(boardVO != null) {
			//저장 전 익명도 쓸 수 있는 게시판을 위한 작성자 주입
			if(boardVO.getWriter() == null || boardVO.getWriter().trim().length()<1) {
				boardVO.setWriter("익명님");
				boardVO.setWriter_nick("익명님");
			}
			//저장
			boardDAO.insert(boardVO);
			//저장 후 글의 idx를 가져오기
			int ref = boardDAO.selectByWriter(boardVO.getWriter());
			logger.info("board insert-  writer: "+boardVO.getWriter());
			if(boardVO.getFileList() != null) {
				for(FilesVO fvo: boardVO.getFileList()) {
					logger.info("board insert- file: "+fvo);
					fvo.setRef(ref);
					filesDAO.insert(fvo);
				}
			}
		}
	}

	@Override
	public void update(BoardVO boardVO, String deleteFiles, String path) {
		if(boardVO != null) {
			BoardVO dbVO = boardDAO.selectByIdx(boardVO.getIdx());
			// 전에 작성한 글쓴이의 id와 현재의 id가 같으면 (id는 변경/중복 불가) 업데이트
			if(dbVO.getWriter().equals(boardVO.getWriter())) {
				boardDAO.update(boardVO);
				if(boardVO.getFileList() != null) {
					//첨부된 파일 저장
					for(FilesVO fvo : boardVO.getFileList()) {
						fvo.setRef(boardVO.getIdx());
						filesDAO.insert(fvo);
					}
				}
				// 삭제할 파일들은 삭제해 준다.
				if (deleteFiles != null && deleteFiles.trim().length() > 0) {
					String files[] = deleteFiles.trim().split(" ");
					if (files != null && files.length > 0) {
						for (String file : files) {
							// FileBoardFilesVO fvo = fileBoardFilesDAO.selectByIdx(Integer.parseInt(file));
							FilesVO fvo = filesDAO.selectByIdx(Integer.parseInt(file));
							filesDAO.deleteByIdx(Integer.parseInt(file)); // DB 내용 삭제
							File file2 = new File(path + fvo.getSaveName());
							file2.delete(); // 저장된 실제 파일 삭제
						}
					}
				}
			}
		}
	}

	@Override
	public void deleteByIdx(int idx, String writer, String path) {
		BoardVO vo = boardDAO.selectByIdx(idx);
		// 해당 idx의 vo가 있으면
		if(vo!=null) {
			BoardVO dbVO = boardDAO.selectByIdx(idx);
			// 전에 작성한 글쓴이의 id와 현재의 id가 같으면 (id는 변경/중복 불가) 삭제, 익명일 경우 그냥 삭제
			if(dbVO.getWriter().equals(writer) || dbVO.getWriter().equals("익명님")) {
				//글 삭제, 글 삭제시 db의 파일/댓글 자동 삭제 foregin key
				boardDAO.deleteByIdx(idx);
				//경로의 파일 삭제
				List<FilesVO> list = filesDAO.selectList(idx);
				for(FilesVO fvo : list) {
					File file = new File(path + fvo.getSaveName());
					file.delete();
				}
			}
		}
	}
	@Override
	public List<BoardVO> selectNew(int cf_idx) {
		return boardDAO.selectNew(cf_idx);
	}
}
