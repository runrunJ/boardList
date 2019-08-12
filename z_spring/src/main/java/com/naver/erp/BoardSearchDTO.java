package com.naver.erp;

//==========================================================
// 게시판 검색 조건들을 저장하는 BoardSearchDTO 클래스 선언
//		검색조건들을 BoardSearchDTO에 저장하는 이유는?
//			다량의 데이터를 하나로 단위화 시키면 DB연동할 때 편리하기 때문이다.
//			특히 스프링은 다량의 파라미터 값이 자동으로 DTO에 저장되기 때문에 많이 사용된다.
//==========================================================
public class BoardSearchDTO {
	// 속성변수 선언
	private String keyword1;
	private String keyword2;
	private String or_and;		// 2개의 검색 키워드 사이 관계를 저장하는 속성변수
	private String[] date;			// 어제 또는 오늘을 저장하는 속성변수
	private int selectPageNo=1;			// 현재 선택된 페이지 번호. 기본값을 설정하지 않으면 0이 들어간다 
	private int rowCntPerPage=10;	// 한 화면에 보여줄 행의 개수.  0이 들어가면 게시판 글목록이 웹브라우저 화면에 나오지 않는다
	
	public String getKeyword1() {
		return keyword1;
	}
	public void setKeyword1(String keyword1) {
		this.keyword1 = keyword1;
	}
	public String getKeyword2() {
		return keyword2;
	}
	public void setKeyword2(String keyword2) {
		this.keyword2 = keyword2;
	}
	public String getOr_and() {
		return or_and;
	}
	public void setOr_and(String or_and) {
		this.or_and = or_and;
	}
	public String[] getDate() {
		return date;
	}
	public void setDate(String[] date) {
		this.date = date;
	}
	public int getSelectPageNo() {
		return selectPageNo;
	}
	public void setSelectPageNo(int selectPageNo) {
		this.selectPageNo = selectPageNo;
	}
	public int getRowCntPerPage() {
		return rowCntPerPage;
	}
	public void setRowCntPerPage(int rowCntPerPage) {
		this.rowCntPerPage = rowCntPerPage;
	}

	
}
	
	