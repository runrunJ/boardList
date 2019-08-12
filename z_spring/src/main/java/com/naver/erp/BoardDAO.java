package com.naver.erp;

import java.util.List;
import java.util.Map;

public interface BoardDAO {
	// 한개 게시판 글 출력번호 수정하고 수정 행의 개수 리턴하는 메소드
	int updatePrint_no( BoardDTO boardDTO ); // 인터페이스는 무조건 메소드에 public이 있어야한다. 코딩하지 않으면 자동으로 컴파일할때 넣어준다.없어도 있는것이다
	
	// 게시판 글 입력 후 입력 적용 행의 개수 리턴하는 메소드
	int insertBoard( BoardDTO boardDTO);

	// 검색한 게시판 목록 리턴하는 메소드 
	List<Map<String,String>> getBoardList( BoardSearchDTO boardSearchDTO );
	
	// 검색한 게시판 목록 개수 리턴하는 메소드  
	int getBoardListAllCnt( BoardSearchDTO boardSearchDTO );
	
	// 1개의 게시판 정보를 리턴하는 메소드 
	BoardDTO getBoardDTO( int b_no );
	
	// 조회수 증가하는 메소드
	int updateReadcount(int b_no);
	
	// 삭제할 게시판의 존재 개수를 리턴하는 메소드 
	int getBoardCnt(BoardDTO boardDTO);
	
	// 삭제할 게시판의 비밀번호 개수를 리턴하는 메소드 
	int getPwdCnt(BoardDTO boardDTO);

	// // 수정할 게시판의 존재 개수를 리턴하는 메소드
	int updateBoard(BoardDTO boardDTO);
	
	// 삭제할 게시판의 자식글 존재 개수를 리턴하는 메소드
	int getSonCnt(BoardDTO boardDTO);
	
	// 삭제 될 게시판 이후 글의 출력 순서번호를 1씩 감소 시킨 후 수정적용행의 개수 리턴하는 메소드
	int downPrintNo(BoardDTO boardDTO);

	// 게시판 삭제 명령한후 삭제 적용행의 개수를 리턴하는 메소드
	int deleteBoard(BoardDTO boardDTO);
	
}
