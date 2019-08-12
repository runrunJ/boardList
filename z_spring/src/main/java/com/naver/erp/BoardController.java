package com.naver.erp;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// 가상 URL 주소로 접속하며 호출되는 메소드를 소유한 [컨트롤러 클래스] 선언
// @Controller 를 붙임으로써 [컨트롤러 클래스]임을 지정한다.
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
@Controller
public class BoardController {

	//===============================================================
	// 속성변수 boardService 선언하고 [boardService 인터페이스]를 구현받은 객체를 생성해 저장.
	// 관용적으로 [boardService 인터페이스]를 구현받은 객체명은 boardServiceImpl 이다.
	//===============================================================
		// @Autowired 역할 -> 속성변수에 붙은 자료형인 [인터페이스]를 구현한 [클래스]를 객체화하여 저장한다.
		// [인터페이스]를 구현한 [클래스]가 1개가 아니면 에러가 발생한다.
		// 단 @Autowired( required=false ) 로 선언하면 0개 이어도 에러없이 null 이 저장된다.
		// Spring 에서만 지원하는 어노테이션이다.
	@Autowired
	private BoardService boardService;
	
	//-----------------------------------------------------------------------------------------------------------------------------------------
	// [BoardController 클래스] 내부의 @RequestMapping이 붙은 모든 메소드가 호출되기 전에 자동으로 호출되는 메소드 선언
	// 반드시 @ModelAttribute("키값명") 이 붙어야 한다
	//-----------------------------------------------------------------------------------------------------------------------------------------
		// 	@ModelAttribute("키값명")이 붙은 메소드가 리턴하는 데이터는
		// @RequestMapping 이 붙은 메소드 호출후에 이동하는 JSP페이지에서 ${키값명}으로 꺼낼 수 있다
	@ModelAttribute("warning")
	public String gogo() {
		return "♥수료가 한달 남았어.싹 다 자퇴해 스벌♥";
	}
	
	//========================================
	// 가상주소 /boardListForm.do 로 접근하면 호출되는 메소드 선언
	//========================================
	// method=RequestMethod.POST 쓰면 안된다. loginForm.jsp에서 get 방식으로 들어오기 때문이다
	// method=RequestMethod.POST 가 없으면 get 또는 post 방식 둘다 허용한다
	@RequestMapping( value="/boardListForm.do" )  
	public ModelAndView getBoardList (
			//---------------------------------------------------------------------------
			// 파라미터 값을 저장할 BoardSearchDTO 객체를 매개변수로 선언
			// 매개변수로 DTO 객체를 넣으면 파라미터명과 일치하는 속성변수에 파라미터값이 자동저장 된다
			// 매개변수로 선언된 DTO는 HttpServletRequest 객체에 setAttribute 로 자동저장되므로 
			// 추후 이동하는 JSP 페이지에 ${requestScope.키명}으로 꺼낼 수 있다
			// 이때 키값은 객체명의 맨 앞 영문을 소문자로 고친 문자열이 키값이 된다.
			// 즉 현재 매개변수 안의 BoardSearchDTO 객체는 setAttribute("boardSearchDTO ", boardSearchDTO)로 저장된다 
			//---------------------------------------------------------------------------
			// 만약 키값명을 "xxx" 로 사용자가 직접 정의하고 싶으면 
			// @ModelAttribute("xxx") BoardSearchDTO boardSearchDTO로 한다
		BoardSearchDTO boardSearchDTO
		//---------------------------------------------------------------------------
		// HttpSession 객체가 저장되어 들어오는 매개변수 선언
		// 매개변수에 HttpSession객체가 저장시키는 일은  스프링이 알아서 한다. 개발자는 선언만 하면 된다
		, HttpSession session
	) {
		//---------------------------------------------------------------------------
		// <1>HttpSession 객체에 uri 라는 키값으로 저장된 문자열 꺼내기
		// <2>만약 꺼낸 문자열이 null 이거나  "boardListForm" 이라면 
		// 		매개변수로 받은 boardSearchDTO를 HttpSession 객체에 uri 란 키값으로 저장하기
		// <3>만약 꺼낸 문자열이 null 또는  "boardListForm" 아니라면
		// 		HttpSession객체에 uri 라는 키값으로 저장된 것을 꺼내 매개변수에 저장하기
		// <4>HttpSession 객체에 uri 라는 키값으로 "boardListForm" 을 저장하기
		
		String uri = (String)session.getAttribute( "uri" );	//<1>
		if( uri == null || uri.equals("boardListForm") ) {		//<2>
			session.setAttribute( "boardSearchDTO", boardSearchDTO);
		} else {																								
			boardSearchDTO = (BoardSearchDTO)session.getAttribute( "boardSearchDTO" );	//<3>
		}
		session.setAttribute( "uri", "boardListForm");		//<4>		
		/*------------------------------------------------------------------------------------------
		boardSearchDTO에서 SelectPageNo, RowCntPerPage 속성변수에 
		기본값을 설정하지 않았다면 여기에서 기본값을 설정해야 한다
		if( boardSearchDTO.getSelectPageNo() == 0 ) {
			boardSearchDTO.setSelectPageNo(1);
		}
		if ( boardSearchDTO.getRowCntPerPage() == 0 ) {
			boardSearchDTO.setRowCntPerPage(10);
		}*/
		//---------------------------------------------------------------
		// [ModelAndView 객체] 생성하기
		// [ModelAndView 객체]에 [호출 JSP 페이지명]을 저장하기
		ModelAndView mav = new ModelAndView();
		mav.setViewName("boardListForm.jsp");
		
		try {
			//---------------------------------------------------------------
			// 게시판 검색 개수 얻기, 게시판 검색 목록 얻기
			// BoardService 인터페이스를 구현한 객체 소유의 getBoarListAllCnt 메소드 호출로 게시판 검색 개수 얻기
			//---------------------------------------------------------------
			// this 용도
			// 1) this.속성변수 =>매개변수명 또는 부모 속성변수명과 구별할 때 붙인다
			// 2) this.메소드 =>부모메소드와 구별할 때 붙인다
			// this 를 붙이면 '내꺼'라는 의미가 있다
			int boardListAllCnt = this.boardService.getBoardListAllCnt(boardSearchDTO);
			
			//---------------------------------------------------------------
			// [선택된 페이지 번호]에서 [시작 행 번호] 구하기
			//---------------------------------------------------------------
			int beginRowNo = 
					boardSearchDTO.getSelectPageNo() * boardSearchDTO.getRowCntPerPage()
					- boardSearchDTO.getRowCntPerPage() + 1;
			
			//-----------------------------------------------------------------------------------
			// [선택된 페이지 번호]와 [총 검색 개수] 의 관계가 맞지 않으면
			// [선택된 페이지 번호]를 1로 하고 검색 행의 [시작행 번호]를 1페이지로 하기
			//-----------------------------------------------------------------------------------
			if( boardListAllCnt < beginRowNo ) {
				boardSearchDTO.setSelectPageNo( 1 );
			}
			
			//------------------------------------------------------------------
			// 게시판 검색 목록얻기
			// BoardService 인터페이스를 구현한 객체 소유의 getBoarList 메소드 호출로 게시판 검색 목록 얻기
			//------------------------------------------------------------------
			// 자바에서 DB연동하여 얻은 데이터가 n행 n열일 경우 보관하는 방법 
				// List<Map<String,String>> 장점: DTO 객체 안만들어도 된다 / 단점: DB에서 자료형을 살릴 수 없다 
				// List<DTO객체> DB에서 자료형을 살릴 수 없다
			List<Map<String,String>> boardList = this.boardService.getBoardList(boardSearchDTO);
			//------------------------------------------------------------------
			// ModelAndView 객체에  검색 개수, 게시판 검색 목록 저장하기
			// ModelAndView 객체에 addObject 메소드로 저장된 것은
			// 추후 HttpServletRequest 객체에 setAttribute 메소드 호출로 다시 재저장 된다
			mav.addObject("boardList", boardList);
			mav.addObject("boardListAllCnt", boardListAllCnt);
		}catch(Exception e) {
			System.out.println("BoardController.getBoardList(~) 메소드 호출 시 에러발생!");
			System.out.println( e.toString() );
		}
		//---------------------
		// [ModelAndView 객체] 리턴하기
		//---------------------
		return mav;
	}
	
	//==============================
	// 가상주소 /boardRegForm.do 로 접근하면 호출되는 메소드 선언
	//==============================
	@RequestMapping( value="/boardRegForm.do" )
	public ModelAndView goBoardRegForm(
			// 파라미터명이 b_no 인 파라미터값을 받아오는 매개변수 b_no 선언하기
			// 만약 파라미터명이 없으면 null 값이 들어오므로
			// 매개변수의 자료형은 String 으로 하던가
			// 아니면 defaultValue 를 사용하여 원하는 기본값을 받아오도록 한다.
			@RequestParam(value="b_no", defaultValue="0") int b_no
	){
		//---------------------
		// [ModelAndView 객체] 생성하기
		// [ModelAndView 객체]에 [호출 JSP 페이지명]을 저장하기
		// [ModelAndView 객체] 리턴하기
		//---------------------
		ModelAndView mav = new ModelAndView();
		mav.setViewName("boardRegForm.jsp");
		return mav;
	}
	
	//======================================
	// /erp/boardRegProc.do 로 접근하면 호출되는 메소드 선언
	//======================================
	@RequestMapping(
			value="/boardRegProc.do"
			,method=RequestMethod.POST
			,produces="application/json;charset=UTF-8"
	)
	@ResponseBody
	public int insertBoard(
			//--------------------------------------------
			// 파라미터값을 저장할 [boardDTO 객체]를 매개변수로 선언
			//--------------------------------------------
				// [파라미터명]과 [BoardDTO 객체]의 [속성변수명]이 같으면
				// setter 메소드가 작동되어 [파라미터값]이 [속성변수]에 저장된다.
				// [속성변수명]에 대응하는 [파라미터명]이 없으면 setter 메소드가 작동되지 않는다.
				// [속성변수명]에 대응하는 [파라미터명]이 있는데 [파라미터값]이 없으면
				// [속성변수]의 자료형에 관계없이 무조건 null 값이 저장된다.
				// 이때 [속성변수]의 자료형이 기본형일 경우 null 값이 저장될 수 없어 에러가 발생한다.
				// 이런 에러를 피하려면 파라미터값이 기본형이거나 속성변수의 자료형을 String으로 해야한다.
				// 이런 에러가 발생하면 메소드안의 실행구문은 하나도 실행되지 않음에 주의한다.
				// 매개변수로 들어온 [DTO 객체]는 이 메소드가 끝난 후 호출되는 JSP 페이지로 그대로 이동한다.
				// 즉, HttpServletRequest 객체에 boardDTO 라는 키값명으로 저장된다.
				// 매개변수 앞의 @ModelAttribute("xxx") 가 없으면 [DTO 객체]의 시작문자를 소문자로 고친 단어가 키값명이다.
			BoardDTO boardDTO
	) {
		// 메소드 첫 줄에 도스창 찍는 명령어 안되면 매개변수 쪽으로 들어오다가 오류 발생 한 것
		System.out.println("insertBoard 메소드 시작.boardDTO 이상없음");

		//--------------------------------------
		// 게시판 글 입력하고 [게시판 입력 적용행의 개수] 저장할 변수 선언
		//--------------------------------------
		int boardRegCnt = 0;
		try {
			//--------------------------------------
			// [BoardServiceImpl 객체]의 insertBoard 메소드 호출로 게시판 입력하고 [게시판 입력 적용행의 개수] 얻기
			//--------------------------------------
			boardRegCnt = this.boardService.insertBoard(boardDTO);
			
			
		}catch(Exception e) {
			System.out.println("BoardController.insertBoard(~)에서 에러발생!");
			boardRegCnt = -1;
		}
		//
		return boardRegCnt;
	}
	
	//=========================================
	@RequestMapping( value="/boardContentForm.do" , method=RequestMethod.POST)
	public ModelAndView goBoardContentForm(
			@RequestParam( value="b_no" ) int b_no
			, HttpSession session
	){
		session.setAttribute( "uri", "");
		//---------------------------------------------------------------
		// [ModelAndView 객체] 생성하기
		// [ModelAndView 객체]에 [호출 JSP 페이지명]을 저장하기
		//---------------------------------------------------------------
		ModelAndView mav = new ModelAndView();
		mav.setViewName("boardContentForm.jsp");
		
		try {
			//------------------------------------------------------------------------------------
			// BoardServiceImpl 객체의 getBoard 메소드 호출로 [1개의 게시판 글] 정보 얻기
			// ModelAndView 객체에 호출 JSP페이지에 반영할 [1개의 게시판 글] 정보 저장
			//------------------------------------------------------------------------------------
			BoardDTO boardDTO = this.boardService.getBoardDTO(  b_no );
			mav.addObject("board", boardDTO);
		} catch(Exception e) {
			System.out.println(e);
			System.out.println("BoardController.goBoardContentForm(~) 메소드 호출 시 에러발생");
		}
		//---------------------
		// [ModelAndView 객체] 리턴하기
		//---------------------
		return mav;
	}
	
	//******************************************************************************************
	// 가상주소 /boardUpDelForm.do 로 접근하면 호출되는 메소드 선언
	//******************************************************************************************
	@RequestMapping( value="/boardUpDelForm.do" , method=RequestMethod.POST)
	public ModelAndView goBoardUpDelForm(
			@RequestParam( value="b_no" ) int b_no 
			, HttpSession session
			){
				session.setAttribute( "uri", "" );
		//---------------------------------------------------------------
		// [ModelAndView 객체] 생성하기
		// [ModelAndView 객체]에 [호출 JSP 페이지명]을 저장하기
		//---------------------------------------------------------------
		ModelAndView mav = new ModelAndView();
		mav.setViewName("boardUpDelForm.jsp");
		
		try {
			//------------------------------------------------------------------------------------
			// BoardServiceImpl 객체의 getBoard 메소드 호출로 [1개의 게시판 글] 정보 얻기
			// ModelAndView 객체에 호출 JSP페이지에 반영할 [1개의 게시판 글] 정보 저장
			//------------------------------------------------------------------------------------
			BoardDTO boardDTO = this.boardService.getBoardDTO_without_upReadcount(  b_no );
			mav.addObject("boardDTO", boardDTO);
		} catch(Exception e) {
			System.out.println(e);
			System.out.println("BoardController.goBoardUpDelForm(~) 메소드 예외발생");
		}
		//---------------------
		// [ModelAndView 객체] 리턴하기
		//---------------------
		return mav;
	}
	
	//******************************************************************************************
	// erp/boardUpDelProc.do 로 접근하면 호출되는 메소드
	//******************************************************************************************
	@RequestMapping(
			value="/boardUpDelProc.do"
			,method=RequestMethod.POST
			,produces="application/json;charset=UTF-8"
	)
	@ResponseBody
	public int boardUpDelProc(
		BoardDTO boardDTO
		, @RequestParam( value="upDel" ) String upDel
	) {
		int boardUpDelCnt = 0;
		
		try {
			// 만약 수정 모드이면 수정 실행하고 수정 적용행의 개수를 저장
			if( upDel.equals("up") ) {
				boardUpDelCnt= this.boardService.updateBoard(  boardDTO );
			}
			// 만약 삭제 모드이면 삭제 실행 후 삭제 적용행의 개수를 저장
			else {
				boardUpDelCnt = this.boardService.deleteBoard(  boardDTO );
				
			}
		} catch(Exception e) {
			boardUpDelCnt = -10;
			System.out.println("BoardController.boardUpDelProc(~) 메소드 예외발생");
		}
		return boardUpDelCnt;
	}
	
	//******************************************************************************************
	// 이 [컨트롤러 클래스] 내의 @RequestMapping 이 붙은 메소드 호출 시 예외발생하면 호출되는 메소드를 선언한다
	//******************************************************************************************
		//@ExceptionHandler(Exception.class) 를 곡 붙여야 한다. 리턴되는 문자열은 호출 JSP 페이지 이름이다.
	@ExceptionHandler(Exception.class)
	public String handleExeception(
			HttpServletRequest request
	) {
		// HttpServletRequest 객체에 클라이언트의 URL 주소를 담기. 호출할 error.jsp 페이지를 문자열 리턴
		request.setAttribute("msg", request.getRequestURL()+ "접속 시 에러발생");
		return "error.jsp";
	}
}
