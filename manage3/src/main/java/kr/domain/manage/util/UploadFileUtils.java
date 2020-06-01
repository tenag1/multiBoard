package kr.domain.manage.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

public class UploadFileUtils {
	private static final Logger logger = LoggerFactory.getLogger(UploadFileUtils.class);
	//파일 이름 생성
	private static String makeIcon(String uploadPath, String Path, String fileName) {
		String iconName = uploadPath + Path + File.separator + fileName;
		return iconName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}
	//썸네일 이미지 생성
	private static String makeThumbnail(String uploadPath, String path, String fileName) throws IOException {
		//원본 이미지 읽기
		BufferedImage sourceImg = ImageIO.read(new File(uploadPath + path, fileName));
		//썸네일 이미지 생성 
		BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);
		String thumbnailName = uploadPath + path + File.separator;
		//파일 생성
		File newFile = new File(thumbnailName+"s_"+fileName);
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
		
		ImageIO.write(destImg, formatName.toUpperCase(), newFile);
		return (thumbnailName+fileName).substring(uploadPath.length()).replace(File.separatorChar, '/');
	}
	
	//폴더 만들기
	private static void makeDir(String uploadPath, String ...paths) {
		// 폴더가 존재한다면 리턴
		if(new File(uploadPath + paths[paths.length-1]).exists()) {
			return;
		}
		for(String path: paths) {
			File dirPath = new File(uploadPath + path);
			if(!dirPath.exists()) {
				dirPath.mkdir();
			}
		}
	}
	// yyyy/MM/dd 형식으로 폴더 만들기
	private static String calcPath(String uploadPath) {
		Calendar calendar = Calendar.getInstance();
		String yearPath = File.separator + calendar.get(Calendar.YEAR);
		String monthPath = yearPath + File.separator + String.format("%02d", calendar.get(Calendar.MONTH)+1);
		String datePath = monthPath + File.separator + String.format("%02d", calendar.get(Calendar.DATE));
		makeDir(uploadPath, yearPath, monthPath, datePath);
		logger.info("만들어진 폴더 확인: "+datePath);
		return datePath;
	}
	
	public static String uploadFile(String uploadPath, String originalName, byte[] fileData) throws IOException {
		UUID uuid = UUID.randomUUID();
		String saveName = uuid.toString() + "_" + originalName; // 겹치지 않는 유일한 이름
		String savePath = calcPath(uploadPath);					// 폴더 생성
		File   target = new File(uploadPath + File.separator + savePath, saveName); // 만든 폴더에 파일 생성
		FileCopyUtils.copy(fileData, target);
		String formatName = originalName.substring(originalName.lastIndexOf(".")+1); // 확장자 추출
		String uploadedFileName = null;
		if(MediaUtils.getMediaType(formatName)!= null) {							 // 이미지면 썸네일 생성
			uploadedFileName = makeThumbnail(uploadPath, savePath, saveName);
		}else {																		 // 아니면 아이콘 생성
			uploadedFileName = makeIcon(uploadPath, savePath, saveName);
		}
		logger.info("uploadFileName: "+uploadedFileName);
		return uploadedFileName;
		
	}
}
