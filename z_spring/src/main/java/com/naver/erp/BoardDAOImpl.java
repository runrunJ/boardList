package com.naver.erp;

import java.util.*;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

// @Repository 를 붙입으로서 DAO클래스 임을 지정하고 xml파일에 bean 태그로 등록된다.
@Repository
public class BoardDAOImpl implements BoardDAO{
	
	//*****************************************************************
	// 속성변수 sqlSession 선언하고, [SqlSessionTemplate] 객체 생성
	//*****************************************************************
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	//*****************************************************************
	// 한 개 게시판 글 출력번호 수정하고 수정 행의 개수 리턴하는 메소드 선언(댓글이 작성됐을 때 )
	//*****************************************************************
	public int updatePrint_no( BoardDTO boardDTO ) {
		//-----------------------------------------------------------------------------------------------------------------
		// SqlSessionTemplate 객체의 update 메소드를 호출하여 게시판 글 출력번호를 1증가 하고 수정 행의 개수 얻기
		//-----------------------------------------------------------------------------------------------------------------
		// 아래 매개변수 코드의 의미설명
		// int update( "쿼리문설정XML 파일안에 mapper태그의 namespace 속성값.update 태그id속성값"
		// ,update 쿼리에 삽입되는 외부 데이터 자료형
		// )
		//-----------------------------------------------------------------------------------------------------------------
			// 쿼리문 설정 XML 파일 안에 namespace 속성값을 가진 mapper 태그안에 update 태그 증에 id 속성값을 가진 태그내부의
			// update 쿼리에 삽입되는 외부 데이터 자료형을 삽입하여 쿼리를 실행한후 리턴되는 정수 데이터를 int로 리턴한다
			// 리턴 자료형은 무조건 int 형이고 , resultType 속성은 없다
			// 외부 데이터 자료형이 DTO일 경우 DTO 내부의 속성변수는 #{속성변수명} 또는 ${속성변수명}으로 삽입된다
			// #{속성변수명} 일 경우 속성변수의 자료형이  String 형이면 싱글쿼트가 붙어 삽입된다
			// ${속성변수명} 일 경우는 속성변수의 자료형이 무엇이든 싱클쿼트 없이 삽입된다
		
		int updatePrint_noCnt = sqlSession.update(
				"com.naver.erp.BoardDAO.updatePrint_no"  // 실행할 SQL 구문의 위치 지정
				, boardDTO														// 실행할 SQL 구문에서 사용할 데이터 설정 
		);
		
		
		return updatePrint_noCnt;
	}
	
	//*****************************************************************
	// 게시판 글 입력 후 입력 적용 행의 개수 리턴하는 메소드 선언
	//*****************************************************************
	public int insertBoard( BoardDTO boardDTO ) {
		//-----------------------------------------------------------------------------------------------------------------
		// SqlSessionTemplate 객체의 insert 메소드를 호출하여 입력 실행 후 입력 행의 개수 얻기
		//-----------------------------------------------------------------------------------------------------------------
		// int insert( "쿼리문설정XML 파일안에 mapper태그의 namespace 속성값.insert 태그id속성값"
		// ,insert 쿼리에 삽입되는 외부 데이터 자료형
		// )
		//-----------------------------------------------------------------------------------------------------------------
		int boardRegCnt = sqlSession.insert(
				"com.naver.erp.BoardDAO.insertBoard" 
				, boardDTO			
		);
		return boardRegCnt;
	}
	
	//*****************************************************************
	// [검색한 게시판 목록] 리턴하는 메소드 선언
	//*****************************************************************
	public List<Map<String,String>> getBoardList( BoardSearchDTO boardSearchDTO ) {
		
		// SqlSessionTemplate 객체의 selectList  메소드를 호출하여 [검색한 게시판] 목록 얻기
		// List<Map<String,String>>  selectList("쿼리문설정 XML 파일안에 mapper 태그의 name 속성값.select태그id속성값"
		// 	, select 쿼리에 삽입되는 외부데이터)
		List<Map<String,String>>  boardList = sqlSession.selectList(
				"com.naver.erp.BoardDAO.getBoardList"  // 실행할 SQL 구문의 위치 지정
				, boardSearchDTO
		);
		//System.out.println(1);
		return boardList;
	}
	
	//*****************************************************************
	// 검색한 게시판 목록 개수를 리턴하는 메소드 선언
	//*****************************************************************
	public int getBoardListAllCnt( BoardSearchDTO boardSearchDTO ) {
		
		// <주의> selectOne 사용 시 리턴되는 데이터는 꼭 1행 n열이어야 한다. 2행이상이면 에러발생
		int boardListAllCnt= sqlSession.selectOne(
				"com.naver.erp.BoardDAO.getBoardListAllCnt"  // 실행할 SQL 구문의 위치 지정
				, boardSearchDTO
		);
		
		return boardListAllCnt;
	}
	
	//*****************************************************************
	// 한개 게시판의 글 정보를 리턴하는 메소드 선언
	//*****************************************************************
	public 	BoardDTO getBoardDTO( int b_no) {
		
		BoardDTO boardDTO  = sqlSession.selectOne(
				"com.naver.erp.BoardDAO.getBoardDTO"  
				, b_no															// 실행할 SQL 구문에 삽입되는 데이터 설정
		);
		
		return boardDTO;
	}

	//*****************************************************************
	// 게시판 글 조회수 증가하고 수정행의 개수 리턴하는 메소드 선언
	//*****************************************************************
	public int updateReadcount( int b_no ) {
		
		int readConut = sqlSession.update(
				"com.naver.erp.BoardDAO.updateReadcount"  
				, b_no
		);
		
		return readConut;
	}
	
	//*****************************************************************
	// 삭제할 게시판의 존재 개수를 리턴하는 메소드 선언
	public int getBoardCnt(BoardDTO boardDTO) {
		
		int boardCnt = sqlSession.selectOne(
				"com.naver.erp.BoardDAO.getBoardCnt"  
				, boardDTO
		);
		
		return boardCnt;
	}
	//*****************************************************************
	// 삭제할 게시판의 비밀번호 개수를 리턴하는 메소드 선언
	public int getPwdCnt(BoardDTO boardDTO) {
		
		int pwdCnt = sqlSession.selectOne(
				"com.naver.erp.BoardDAO.getPwdCnt"  
				, boardDTO
		);
		return pwdCnt;
	}
	//*****************************************************************
	// 수정할 게시판의 존재 개수를 리턴하는 메소드 선언
	public int updateBoard(BoardDTO boardDTO) {
		int updateCnt = sqlSession.update(
				"com.naver.erp.BoardDAO.updateBoard"  
				, boardDTO
		);
	
		return updateCnt;
	}
	//*****************************************************************
	// 삭제할 게시판의 자식글 존재 개수를 리턴하는 메소드 선언
	public int getSonCnt(BoardDTO boardDTO) {
		int sonCnt = sqlSession.selectOne(
				"com.naver.erp.BoardDAO.getSonCnt"  
				, boardDTO
		);
		return sonCnt;
	}
	//*****************************************************************
	// 삭제 될 게시판 이후 글의 출력 순서번호를 1씩 감소 시킨 후 수정된 행의 개수 리턴하는 메소드 선언
	public int downPrintNo(BoardDTO boardDTO) {
		int downPrintNoCnt = sqlSession.update(
				"com.naver.erp.BoardDAO.downPrintNo"  
				, boardDTO
		);

		return downPrintNoCnt;
	}
	//*****************************************************************
	// 게시판 삭제 명령한후 삭제 적용행의 개수를 리턴하는 메소드 선언
	public int deleteBoard(BoardDTO boardDTO) {
		int deleteBoardCnt = sqlSession.delete(
				"com.naver.erp.BoardDAO.deleteBoard"  
				, boardDTO
		);
		return deleteBoardCnt;
	}
}
