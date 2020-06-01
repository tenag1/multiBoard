package kr.domain.manage.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.javassist.expr.NewArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.google.gson.JsonObject;

import kr.domain.manage.dao.CommentDAO;
import kr.domain.manage.service.BoardService;
import kr.domain.manage.service.CategoryService;
import kr.domain.manage.service.ConfigService;
import kr.domain.manage.util.UploadFileUtils;
import kr.domain.manage.vo.BoardVO;
import kr.domain.manage.vo.CategoryVO;
import kr.domain.manage.vo.CommVO;
import kr.domain.manage.vo.CommentsVO;
import kr.domain.manage.vo.ConfigVO;
import kr.domain.manage.vo.FilesVO;
import kr.domain.manage.vo.PagingVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	@Autowired
	private ConfigService configService;
	@Autowired
	private CommentDAO commentDAO;
	@Resource(name = "uploadPath")
	private String uploadPath;

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@RequestMapping(value = "/board/List")
	public String boardList(@ModelAttribute CommVO commVO, Model model, HttpServletRequest request) {
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if (flashMap != null) {
			Integer p = (Integer) flashMap.get("p");
			if (p != null)
				commVO.setP(p);
			Integer s = (Integer) flashMap.get("s");
			if (s != null)
				commVO.setS(s);
			Integer idx = (Integer) flashMap.get("idx");
			if (idx != null)
				commVO.setIdx(idx);
			Integer no = (Integer) flashMap.get("no");
			if (no != null)
				commVO.setNo(no);
			Integer m = (Integer) flashMap.get("m");
			if (m != null)
				commVO.setM(m);
		}
		PagingVO<BoardVO> pagingVO = boardService.selectList(commVO.getNo(), commVO.getCurrentPage(),
				commVO.getPageSize(), commVO.getBlockSize());
		ConfigVO configVO = configService.selectByIdx(commVO.getNo());
		model.addAttribute("configVO", configVO);
		model.addAttribute("pagingVO", pagingVO);
		model.addAttribute("commVO", commVO);

		model.addAttribute("list_level", getLevel(configVO.getList_level()));
		model.addAttribute("read_level", getLevel(configVO.getRead_level()));
		model.addAttribute("writer_level", getLevel(configVO.getWriter_level()));
		model.addAttribute("comment_level", getLevel(configVO.getComment_level()));
		model.addAttribute("use_secret", configVO.getUse_secret());

		model.addAttribute("newIcon", configVO.getNewIcon());
		model.addAttribute("newIconDate", newIconDate(configVO.getNewIcon()));
		model.addAttribute("hotIcon", configVO.getHotIcon());
		model.addAttribute("gallery_cols", configVO.getGallery_cols());

		logger.info("boardList- " + configVO);
		logger.info("boardList- " + pagingVO);
		logger.info("boardList- " + commVO);

		return "board/boardList";
	}
	
	@RequestMapping(value = "/board/form")
	public String boardForm(@ModelAttribute CommVO commVO, Model model) {
		model.addAttribute("commVO", commVO);
		model.addAttribute("boardVO", new BoardVO()); // validation 유효성 검사를 위한 vo주입

		ConfigVO configVO = configService.selectByIdx(commVO.getNo());
		model.addAttribute("read_level", getLevel(configVO.getRead_level()));
		model.addAttribute("use_secret", configVO.getUse_secret());

		if (commVO.getIdx() != -1) {
			BoardVO boardVO = boardService.selectByIdx(commVO.getIdx(), -1);
			model.addAttribute("boardVO", boardVO);
		}
		// 나중에 유저 식별 코드와 같으면 수정버튼 넣으면서 수정으로 들어오면 boardVO주입하기

		return "board/boardForm";
	}

	@RequestMapping(value = "/board/FormOk", method = RequestMethod.POST)
	public String boardOk(@Valid @ModelAttribute BoardVO boardVO, BindingResult result, @ModelAttribute CommVO commVO,
			MultipartHttpServletRequest request) throws IOException {

		if (result.hasErrors()) {
			return "board/boardForm";
		}
		if (boardVO.getPassword() == null) {
			boardVO.setPassword("");
		}
		
		String realPath = request.getRealPath(uploadPath);
		logger.info("보드 폼 realPath : "+realPath);
		List<MultipartFile> files = request.getFiles("files");
		logger.info("보드 폼 files : "+files);
		//첨부 파일이 있으면
		if (files != null) {
			List<FilesVO> list = new ArrayList<FilesVO>();
			for (MultipartFile file : files) {
				//첨부 파일 중 비어있지 않으면 업로드
				if (file != null && file.getSize() > 0 && file.getSize() < 10485760) {
					//파일 저장
					String savedName = UploadFileUtils.uploadFile(realPath, 
							file.getOriginalFilename(), file.getBytes());
					logger.info("보드 폼 저장된 이름: "+savedName);
					//list에 추가할 FilesVO 생성/세팅 
					FilesVO fvo = new FilesVO();
					fvo.setOrigName(file.getOriginalFilename());
					fvo.setSaveName(savedName);
					list.add(fvo);
				}
			}
			boardVO.setFileList(list);
			boardVO.setFileCount(list.size());
		}
		
		if (boardVO.getIdx() > 0) {
			logger.info("보드 업데이트");
			String deleteFiles = request.getParameter("deleteFiles");
			
			//파일 수정
			boardService.update(boardVO, deleteFiles, realPath);
			
			//파일 view로 이동
			FlashMap map = RequestContextUtils.getOutputFlashMap(request);
			
			map.put("p", commVO.getCurrentPage());
			map.put("s", commVO.getPageSize());
			map.put("idx", commVO.getIdx());
			map.put("no", commVO.getNo());
			map.put("m", 0);
			logger.info("*** board/formOk - map - : "+map);
			return "redirect:/board/view";
		} else {
			logger.info("보드 추가");
			logger.info(boardVO + "");
			boardService.insert(boardVO);
		}
		return "redirect:/board/List?no=" + boardVO.getCf_idx(); // 여기에 받은 그룹 번호를 추가
	}

	@RequestMapping(value = "/board/view")
	public String boardView(@ModelAttribute CommVO commVO, Model model, HttpServletRequest request) {
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if (flashMap != null) {
			Integer p = (Integer) flashMap.get("p");
			if (p != null && p != 1)
				commVO.setCurrentPage(p);
			Integer s = (Integer) flashMap.get("s");
			if (s != null && s != 10)
				commVO.setPageSize(s);
			Integer m = (Integer) flashMap.get("m");
			if (m != null)
				commVO.setMode(m);
			Integer idx = (Integer) flashMap.get("idx");
			if (idx != null && idx != -1)
				commVO.setIdx(idx);
			Integer no = (Integer) flashMap.get("no");
			if (no != null && no != -1)
				commVO.setNo(no);
		}
		BoardVO boardVO = boardService.selectByIdx(commVO.getIdx(), commVO.getMode());
		ConfigVO configVO = configService.selectByIdx(commVO.getNo());
		logger.info("boardView - commVO: " + commVO);
		logger.info("boardView - boardVO: " + boardVO);
		logger.info("boardView - configVO:  " + configVO);

		model.addAttribute("read_level", getLevel(configVO.getRead_level()));
		model.addAttribute("comment_level", getLevel(configVO.getComment_level()));
		model.addAttribute("use_secret", configVO.getUse_secret());
		// 댓글 엔터
		model.addAttribute("newLine", "\n");
		model.addAttribute("br", "<br/>");

		model.addAttribute("boardVO", boardVO);
		model.addAttribute("commVO", commVO);

		return "board/boardView";
	}

	//json으로 보내보기
	@RequestMapping(value = "/board/summernoteUpload", produces = "application/json")
	@ResponseBody
	public JsonObject summernoteUpload(MultipartHttpServletRequest request) {
		JsonObject jsonObject = new JsonObject();
		
		MultipartFile file = request.getFile("file");
		String path = request.getRealPath("upload/summernote");
		logger.info("path: "+path);
		String origName = file.getOriginalFilename();
		String saveName = null;
		
		try {
			saveName = UploadFileUtils.uploadFile(path, origName, file.getBytes());
			logger.info("path+saveName: "+path+saveName);
			jsonObject.addProperty("url", "upload/summernote"+saveName);
	        jsonObject.addProperty("responseCode", "success");
		} catch (IOException e) {
			File targetFile = new File(path+saveName); 
			File targetFileS = new File(path+"s_"+saveName); 
			FileUtils.deleteQuietly(targetFile);
			FileUtils.deleteQuietly(targetFileS);
	        jsonObject.addProperty("responseCode", "error");
	        e.printStackTrace();
		}	
		return jsonObject;
	} 

	@RequestMapping("/board/download")
	public void fileDownload(@RequestParam("of") String origName,
			@RequestParam("sf") String saveName, HttpServletRequest request
			, HttpServletResponse response) {
		String path = request.getRealPath(uploadPath);
		logger.info("fileDownload - path: "+ path);
		logger.info("fileDownload - of: "+ origName);
		logger.info("fileDownload - sf: "+ saveName);
		InputStream in = null;
		OutputStream os = null;
		File file = null;
		boolean skip = false;
		String client = "";
		try {
			// 파일을 읽어 스트림에 담기
			try {
				file = new File(path, saveName);
				in = new FileInputStream(file);
			} catch (FileNotFoundException fe) {
				logger.info("fileDownload - fileNotFoundeE: "+ fe.getMessage());
				skip = true;
			}
			// 브라우져 종류
			client = request.getHeader("User-Agent");
			// 파일 다운로드 헤더 지정
			response.reset();
			response.setContentType("application/octet-stream"); // 현재 데이터가 스트림이다라고 알려준다.
			if (!skip) {
				// 한글 파일명 처리
				if (client.indexOf("Trident") == -1 && client.indexOf("Edge") == -1) {
					origName = new String(origName.getBytes("utf-8"), "iso-8859-1");
				} else {
					// IE라면 : 공백이 +로 바뀌어서 다운된다.replaceAll는 +를 공백으로 바꿔준다.
					origName = URLEncoder.encode(origName, "UTF-8").replaceAll("\\+", "%20");
				}
				
				response.setHeader("Content-Disposition", "attachment; filename=\"" + origName + "\"");
				response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
				response.setHeader("Content-Length", "" + file.length());

				//출력
				os = response.getOutputStream();
				// 복사
				byte[] buffer = new byte[(int) file.length()]; // 파일 크기만큼 배열선언
				int leng = 0;
				while ((leng = in.read(buffer)) != -1) {
					logger.info("fileDownload - in: "+in.toString());
					os.write(buffer, 0, leng); // 쓰기
					os.flush();
					logger.info("fileDownload - flush()");
				}
			}
		} catch (Exception e) {
			logger.info("download exception : "+e.getMessage());
		}finally {
			try {
				if(in != null) {
					in.close();
				}
				if(os != null) {
					os.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@RequestMapping(value = "/board/delete")
	public String boardDelete(@ModelAttribute CommVO commVO, HttpServletRequest request,
			@RequestParam(value = "writer") String writer) {
		logger.info("boardView - " + commVO);

		boardService.deleteByIdx(commVO.getIdx(), writer, null);

		FlashMap map = RequestContextUtils.getOutputFlashMap(request);
		map.put("p", commVO.getCurrentPage());
		map.put("s", commVO.getPageSize());
		map.put("idx", commVO.getIdx());
		map.put("no", commVO.getNo());
		map.put("m", 0);

		return "redirect:/board/List";
	}

	@RequestMapping(value = "/board/commentForm", method = RequestMethod.POST)
	public String boardCommentInsert(@ModelAttribute CommentsVO commentsVO, @ModelAttribute CommVO commVO, Model model,
			HttpServletRequest request) {
		logger.info("comment insert - commentsVO: "+commentsVO);
		if(commentsVO.getMb_id() == null || commentsVO.getMb_id().equals("")) {
			commentsVO.setMb_id("익명님");
			commentsVO.setMb_nick("익명");
		}
		if (commentsVO.getIdx() > 0) {
			commentDAO.update(commentsVO);
		} else {
			commentDAO.insert(commentsVO);
		}
		FlashMap map = RequestContextUtils.getOutputFlashMap(request);
		map.put("p", commVO.getCurrentPage());
		map.put("s", commVO.getPageSize());
		map.put("b", commVO.getBlockSize());
		map.put("idx", commentsVO.getRef());
		map.put("m", 0);
		map.put("no", commVO.getNo());
		return "redirect:/board/view";
	}

	@RequestMapping(value = "/board/commentDelete", method = RequestMethod.POST)
	public String boardCommentDelete(@ModelAttribute CommentsVO commentsVO, @ModelAttribute CommVO commVO,
			HttpServletRequest request) {
		CommentsVO vo = commentDAO.selectByIdx(commentsVO.getIdx());
		if (vo.getMb_id().equals(commentsVO.getMb_id())) {
			logger.info("boardCommentDelete 삭제!");
			commentDAO.delete(commentsVO.getIdx());
		}
		FlashMap map = RequestContextUtils.getOutputFlashMap(request);
		map.put("p", commVO.getCurrentPage());
		map.put("s", commVO.getPageSize());
		map.put("b", commVO.getBlockSize());
		map.put("idx", commentsVO.getRef());
		map.put("m", 0);
		map.put("no", commVO.getNo());
		logger.info("boardCommentDelete - commVO: " + commVO);
		return "redirect:/board/view";
	}

	private String getLevel(int level) {
		// -1은 사용 안함, 0은 모두 사용 가능, 1은 user/admin, 2는 admin만 사용가능
		String lev = "";
		switch (level) {
		case -1:
			lev = "denyAll()";
			break;
		case 0:
			lev = "permitAll()";
			break;
		case 1:
			lev = "hasAnyRole('USER', 'ADMIN')";
			break;
		case 2:
			lev = "hasRole('ADMIN')";
			break;
		}
		return lev;
	}

	private int newIconDate(int newIcon) {
		// newIcon = 1일 경우 20200428을 기준으로 20200427까지만 new 아이콘이 나온다.
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return Integer.parseInt(sdf.format(new Date())) - newIcon;
	}
}