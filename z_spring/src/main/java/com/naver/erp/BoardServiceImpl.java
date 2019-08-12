package com.naver.erp;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//==========================================
// 스프링에서 서비스 클래스 역할은 무엇인가?
//	[컨트롤러 클래스]에서 받은 DB연동 지시를 세분화해서 [DAO 클래스]에게 지시한다. 이때 트랜잭션이 걸린다

// 자바에서 throws 와 throw 의 차이는?
// throws : 예외 발생 구문을 호출한 곳으로 예외를 던질 때
// throw : 코딩으로 직접 예외를 발생 시킬 때 사용(조건식을 만든 후 쓴다). 인위적으로 예외를 발생시킨다. 웹개발시 거의 안쓴다
//==========================================
@Service // 서비스 클래스 임을 지정하고 bean 태그로 자동 등록된다
@Transactional	// 메소드 내부에서 일어나는 모든 작업에 트랜잭션이 걸린다
public class BoardServiceImpl implements BoardService{
	@Autowired
	private BoardDAO boardDAO;
	
	//*********************************************************
	// [게시판 글 입력 후 적용된 행의 개수] 리턴하는 메소드 선언
	//*********************************************************
	public int insertBoard( BoardDTO boardDTO ) {
		// 댓글일 경우에 게시판의 출력순서 번호를 1씩 증가하기 
		String b_no = boardDTO.getB_no();
		if( b_no != null && b_no.length() > 0 ) {
			
			// BoardDAO 인터페이스를 구현한 객체의 updatePrint_no 메소드를 호출하여
			// 출력 순서 번호를 1증가 시키고 수정행의 적용 개수를 리턴받는다
			// 새롭게 게시판 글이 입력된 후 이후의 글들은 출력 순서 번호를 1씩 증가해야 한다.
			int updatePrint_noCnt = this.boardDAO.updatePrint_no(boardDTO);
			
		}
		
		// 한개 게시판 글 입력 후 입력 적용 행의 개수 리턴하기
		int boardRegtCnt = this.boardDAO.insertBoard(boardDTO);
		return boardRegtCnt;
	}
	//******************************************************************************
	// [검색한 게시판 목록] 리턴하는 메소드 선언
	//******************************************************************************
	public List<Map<String,String>> getBoardList( BoardSearchDTO boardSearchDTO ) {
		
		// 매개변수로 검색조건이 내장된 boardSearchDTO 객체가 넘어간다
		// <주의> 서비스 클래스 메소드에서 DAO클래스의 메소드 호출시 매개변수의 개수는 대부분 1개이다.
		//		이유는 DAO클래스의 메소드에서 sqlSessionTemplate 객체의 메소드 호출 시
		//		SQL 구문에 참여할 데이터의 개수가 1개여야 하기 때문이다
		List<Map<String,String>> boardList= this.boardDAO.getBoardList( boardSearchDTO );
		return boardList;
	}
	
	//******************************************************************************
	// [검색한 게시판 목록의 개수] 리턴하는 메소드 선언
	//******************************************************************************
	public int getBoardListAllCnt(BoardSearchDTO boardSearchDTO) {
		
		// BoardDAO 인터페이스를 구현한 객체의 getBoardListAllCnt 메소드를 호출하여
		// 검색한 게시판 목록의 개수를 얻는다
		int  boardListAllCnt = this.boardDAO.getBoardListAllCnt( boardSearchDTO );
		return boardListAllCnt;
	}
	
	//******************************************************************************
	// [1 개 게시판 글] 리턴하는 메소드 선언
	//******************************************************************************
	public BoardDTO getBoardDTO(int b_no) {
		
		// BoardDAO 인터페이스를 구현한 객체의 getBoardDTO 메소드를 호출하여
		// 1개 게시판 글을 얻는다
		BoardDTO boardDTO = this.boardDAO.getBoardDTO(b_no);
		
		// 만약 1개 게시판 글이 있으면 조회수를 증가시키고 수정한 행의 개수를 얻는다
		// 그리고 BoardDTO 객체의 조회수를 저장하는 속성변수 readcount에 1증가 시킨다.
		if(boardDTO != null) {
			int readCount = this.boardDAO.updateReadcount(b_no);
			// boardDTO = this.boardDAO.getBoardDTO(b_no)  => 아래 코드도 가능
			boardDTO.setReadcount( boardDTO.getReadcount()+1 );
		}
		return boardDTO;
	}
	
	//******************************************************************************
	// 조회수 증가 없이 [1 개 게시판 글] 리턴하는 메소드 선언
	//******************************************************************************
	public BoardDTO getBoardDTO_without_upReadcount( int b_no ) {
		
		// BoardDAO 인터페이스를 구현한 객체의 getBoardDTO 메소드를 호출하여
		// 조회수 증가없이 1개 게시판 글을 얻는다
		BoardDTO boardDTO = this.boardDAO.getBoardDTO(  b_no );
		return boardDTO;
	}
	
	//******************************************************************************
	// 한개 게시판을 수정 후 적용 행의 개수를 리턴하는 메소드 선언
	//******************************************************************************
	public int updateBoard( BoardDTO boardDTO) {
		
		// BoardDAO 인터페이스를 구현한 객체의 getBoardDTO 메소드를 호출하여
		// 수정할 [게시판]의 존재 개수를 얻는다
		int boardCnt = this.boardDAO.getBoardCnt(boardDTO);
		if(boardCnt == 0) { return -1;}
		
		// 수정할 게시판의 [비밀번호] 존재개수를 얻는다
		int pwdCnt = this.boardDAO.getPwdCnt(boardDTO);
		if(pwdCnt == 0) { return 0;}
		
		// 게시판 수정명령 후 적용행의 개수를 얻는다
		int updateCnt = this.boardDAO.updateBoard(boardDTO);
		
		// System.out.println(updateCnt); => 오류 확인했던 코드
		
		// 게시판 수정 명령 후 수정한 적용 행의 개수 리턴하기
		return updateCnt;
	}
	
	//******************************************************************************
	// 한개 게시판 삭제 후 수정 적용행의 개수를 리턴하는 메소드 선언
	//******************************************************************************
	public int deleteBoard(BoardDTO boardDTO) {
		
		// BoardDAO 인터페이스를 구현한 객체의 getBoardCnt 메소드를 호출하여
		// 삭제할 게시판의 존재 개수를 얻는다
		int boardCnt = this.boardDAO.getBoardCnt(boardDTO);
		if(boardCnt == 0) { return -1;}
		
		// BoardDAO 인터페이스를 구현한 객체의 getPwdCnt 메소드를 호출하여
		// 삭제할 게시판의 비밀번호 존재 개수를 얻는다 
		int pwdCnt = this.boardDAO.getPwdCnt(boardDTO);
		if(pwdCnt == 0) { return -2;}
		
		// BoardDAO 인터페이스를 구현한 객체의 getSonCnt 메소드를 호출하여
		// 삭제할 게시판의 자식글 존재 개수 알아내기
		int sonCnt = this.boardDAO.getSonCnt(boardDTO);
		if(sonCnt>0) { return -3;}
		
		// BoardDAO 인터페이스를 구현한 객체의 upPrintNo 메소드를 호출하여
		// 삭제 될 게시판 이후 글의 출력 순서번호를 1씩 감소 시킨 후 수정 적용 행의 개수 알아내기
		int downPrintNo = this.boardDAO.downPrintNo(boardDTO);
		
		// BoardDAO 인터페이스를 구현한 객체의 deleteBoard 메소드를 호출하여
		// 게시판 삭제 명령한 후 삭제 적용행의 개수 알아내기
		int deleteCnt = this.boardDAO.deleteBoard(boardDTO);
		return deleteCnt;
	}
}
