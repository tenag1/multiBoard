package kr.domain.manage.vo;

import java.util.List;

public class PagingVO<T> {
	private List<T> list;
	
	private int no;
	private int totalCount;
	private int currentPage;
	private int pageSize;
	private int blockSize;
	
	private int totalPage;
	private int startNo;
	private int endNo;
	private int startPage;
	private int endPage;
	
	public PagingVO(int no, int totalCount, int currentPage, int pageSize, int blockSize) {
		super();
		this.no = no;
		this.totalCount = totalCount;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.blockSize = blockSize;
		calc();
	}
	public PagingVO(int totalCount, int currentPage, int pageSize, int blockSize) {
		super();
		this.totalCount = totalCount;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.blockSize = blockSize;
		calc();
	}
	
	private void calc() {
		if(currentPage <= 0) currentPage = 1;
		if(pageSize <= 1) pageSize = 10;
		if(blockSize <= 1) blockSize = 10; 
		
		totalPage = (totalCount-1)/pageSize+1;
		
		if(currentPage > totalPage) currentPage = 1;
		
		startNo = (currentPage-1) *pageSize+1;
		endNo = startNo+pageSize-1;
		if(endNo>=totalCount) endNo = totalCount;
		
		startPage = (currentPage-1)/blockSize*blockSize +1;
		endPage = startPage+blockSize-1;
		if(endPage>totalPage) endPage = totalPage;
	}
	
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public int getBlockSize() {
		return blockSize;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public int getStartNo() {
		return startNo;
	}
	public int getEndNo() {
		return endNo;
	}
	public int getStartPage() {
		return startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	
	public String getPageInfo() {
		String info;
		if(totalCount > 0) 
			info = "전체 "+totalCount+"개("+currentPage+"/"+totalPage+"page)";
		else 
			info = "전체 "+totalCount+"개";
		
		return info;
	}
	public String getPageListPost() {
		StringBuffer sb = new StringBuffer();
		if (totalPage > 0) {
			sb.append("<ul class='pagination pagination-sm justify-content-center'>");
			// 시작페이지번호가 1보다 크다면 "이전"이 있다.
			if (startPage > 1) {
				sb.append("<li class='page-item'>");
				sb.append("<a class='page-link'  href='#' onclick='post_to_url(\"?\",{\"p\": \"" + (1) + "\",\"s\": \""
						+ pageSize + "\",\"b\": \"" + blockSize + "\",\"no\": \""+no+"\"})'>");
				sb.append("처음</a></li><li class='page-item'>");
				sb.append("<a class='page-link'  href='#' onclick='post_to_url(\"?\",{\"p\": \"" + (startPage - 1)
						+ "\",\"s\": \"" + pageSize + "\",\"b\": \"" + blockSize + "\",\"no\": \""+no+"\"})'>");
				sb.append("이전</a></li>");
			}
			// 페이지 번호들 출력
			for (int i = startPage; i <= endPage; i++) {
				if (i == currentPage) {
					sb.append("<li class='page-item active'>");
					sb.append("<a class='page-link'>"+i+"</a></li>");
				} else {
					sb.append("<li class='page-item'>");
					sb.append("<a class='page-link' href='#' onclick='post_to_url(\"?\",{\"p\": \"" + (i)
							+ "\",\"s\": \"" + pageSize + "\",\"b\": \"" + blockSize + "\",\"no\": \""+no+"\"})'>");
					sb.append(i+"</a></li>");
				}
			}
			// 마지막페이지번호가 전체페이지수 보다 적다면 "다음"이 있다.
			if (endPage < totalPage) {
				sb.append("<li class='page-item'>");
				sb.append("<a class='page-link'  href='#' onclick='post_to_url(\"?\",{\"p\": \"" + (endPage + 1)
						+ "\",\"s\": \"" + pageSize + "\",\"b\": \"" + blockSize + "\",\"no\": \""+no+"\"})'>");
				sb.append("다음</a></li><li class='page-item'>");
				sb.append("<a class='page-link'  href='#' onclick='post_to_url(\"?\",{\"p\": \"" + (totalPage)
						+ "\",\"s\": \"" + pageSize + "\",\"b\": \"" + blockSize + "\",\"no\":\""+no+"\"})'>");
				sb.append(" 끝 </a></li>");
			}
			sb.append("</ul>");
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		return "list:  "+list+", totalCount:  "+totalCount+", currentPage: "+currentPage+", pageSize: "+pageSize
				+", blockSize: "+blockSize+", totalPage: "+totalPage+", startNo: "+startNo+
				", endNo: "+endNo+", startPage: "+startPage+", endPage: "+endPage;
	}
	
}
