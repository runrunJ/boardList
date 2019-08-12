package com.naver.erp;
import java.util.*;

public interface BoardService {
	// [게시판 글 입력 후 입력 적용 행의 개수] 리턴하는 메소드 선언
	int insertBoard(BoardDTO boardDTO);
	
	// [검색한 게시판 목록 개수] 리턴하는 메소드 선언
	int getBoardListAllCnt( BoardSearchDTO boardSearchDTO );
	
	// [검색한 게시판 목록] 리턴하는 메소드 선언
	List<Map<String,String>> getBoardList( BoardSearchDTO boardSearchDTO );
	
	// [한 개 게시판 글] 리턴하는 메소드 선언
	BoardDTO getBoardDTO(int b_no);
	
	// 조회수 증가하는 메소드 선언
	BoardDTO getBoardDTO_without_upReadcount( int b_no );
	
	// 한개 게시판 수정 후 수정 적용행의 개수를 리턴하는 메소드 선언
	int updateBoard(BoardDTO boardDTO);
	
	// 한개 게시판 삭제 후 수정 적용행의 개수를 리턴하는 메소드 선언
	int deleteBoard(BoardDTO boardDTO);
}
