package kr.domain.manage.vo;

public class CommVO {
	private int p=1;  //현재 페이지
	private int s=10;  //페이지 사이즈
	private int b=10;  //블록 사이즈
	private int m=-1;  //모드 
	private int idx=-1;//글 번호
	private int no=-1; //그룹 번호
	
	private int currentPage=1;
	private int pageSize=10;
	private int blockSize=10;
	private int mode=10;
	
	public int getP() {
		return p;
	}
	public void setP(int p) {
		this.p = p;
		currentPage = this.p;
	}
	public int getS() {
		return s;
	}
	public void setS(int s) {
		this.s = s;
		pageSize = this.s;
	}
	public int getB() {
		return b;
	}
	public void setB(int b) {
		this.b = b;
		blockSize = this.b;
	}
	public int getM() {
		return m;
	}
	public void setM(int m) {
		this.m = m;
		mode = this.m;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getBlockSize() {
		return blockSize;
	}
	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	@Override
	public String toString() {
		return "p: "+p+", s: "+s+", b: "+b+", m: "+m+", idx: "+idx+", no: "+no
				+", currentPage: "+currentPage+", pageSize: "+pageSize+", blockSize: "+blockSize+", mode:"+mode;
	}
	
	
	
}
