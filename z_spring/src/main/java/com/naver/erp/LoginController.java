package com.naver.erp;

import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class LoginController {
	
	// <중요>@Autowired가 붙은 속성변수에는 인터페이스 자료형을 쓰고 이 인터페이스를 구현한 클래스를 객체화하여 저장한다
	@Autowired
	private LoginService loginService;
	
	//******************************************************************************
	// 가상주소 /erp/loginForm.do 로 접속하면 호출되는 메소드 선언
	//******************************************************************************
	@RequestMapping( value="/loginForm.do")
	public ModelAndView loginForm( HttpSession session ) {
		
		// HttpSession 객체의 removeAttribute() 메소드를 호출하여 admin_id 라는 키값으로 저장된 것 지우기
		// 예전에 로그인한 아이디를 지우기 위해서
		session.removeAttribute("admin_id");
		/*// <참고>HttpSession 객체에 저장된 모든 데이터를 제거할 때는 아래를 사용
		session.invalidate();*/
		
		// ModelAndView 객체 생성해서 호출할 JSP 페이지명을 저장하기
		ModelAndView mav = new ModelAndView();
		mav.setViewName("loginForm.jsp");
		return mav;
	}
	//******************************************************************************
	// 가상주소  /erp/loginProc.do 로 접속하면 호출되는 메소드 선언
	//******************************************************************************
	/*@RequestMapping( value="/loginProc.do", method=RequestMethod.POST)
	public ModelAndView loginProc(
			// admin_id 라는 파라미터값이 들어올때 String 형 매개변수 선언하기
			// @RequestParam( value="파라미터명", required=true | false, defaultValue="디폴트값"
			// defaultValue="디폴트값"은  파라미터 값이 없을경우 디폴트 값을 매개변수에 저장. required=false가 있어야 한다.
			@RequestParam( value="admin_id" ) String admin_id
			, @RequestParam( value="pwd" ) String pwd
			// @RequestParam Map<String, String> admin_id_pwd 이렇게 써도 됨
	) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("loginProc.jsp");
		int admin_idCnt = 0;
		try {
			// HashMap 객체에 로그인 아이디, 암호 저장하기
			Map<String, String> admin_id_pwd = new HashMap<String, String> ();
			admin_id_pwd.put("admin_id", admin_id);
			admin_id_pwd.put("pwd", pwd);
			
			// loginServiceImpl 객체(서비스클래스)의 getAdminCnt(admin_id_pwd) 메소드 호출로
			// 로그인 아이디의 존재개수를 얻기
			admin_idCnt = this.loginService.getAdminCnt(admin_id_pwd);
			//System.out.println(admin_idCnt );
			
			//------------------------------------------------------------
			// ModelAndView 객체에 아이디 존재 개수 저장하기
			//------------------------------------------------------------
			mav.addObject( "admin_idCnt", admin_idCnt );
			
		} catch(Exception ex) {
			System.out.println( "LoginController.loginProc 에서 에러발생" );
			mav.addObject( "admin_idCnt", -1 );
		}
		return mav;
	}*/
	// 위 loginProc 메소드는 아래처럼 @ResponseBody 를 사용하면 JSP 페이지 호출없이
	// 리턴하는 로그인 정보 존재개수를 직접 클라이언트에게 전송할 수도 있다.
	// 단 서버가 보내는 데이터를 클라이언트가 받을 수 있도록 클라이언트가 코딩되어 있어야 한다.
	@RequestMapping(
	value="/loginProc.do"
	, method=RequestMethod.POST
	, produces="application/json;chrset=utf-8"
	)
	@ResponseBody
	public int loginProc (
			HttpSession session	// HttpSession  객체를 받아오는 매개변수 선언
			
			// HttpServletResponse 객체가 들어올 매개변수 선언
			, HttpServletResponse response 
			
			// 파라미터명과 파라미터값이 저장된 HashMap 객체를 받아오는 매개변수 선언
			// 파라미터명은 키값으로 마라미터 값은 키값에 대응하는 저장 문자열로 HashMap 객체에 저장된다
			// 예) 아이디: abc, 암호:123, 체크박스 한개의 밸류값  paramsMap에 넣는다.
			, @RequestParam Map<String, String> paramsMap	
			
	) {
		/*-------------------------------------------------------------
		paramsMap에 아이디 암호가 잘 들어왔는지 콘솔에 찍어보자
		System.out.println(paramsMap.get("admin_id"));
		System.out.println(paramsMap.get("pwd"));
		System.out.println(paramsMap.get("is_login"));
		-------------------------------------------------------------*/
		// 파라미터값 꺼내기
		String admin_id = paramsMap.get("admin_id");
		String pwd = paramsMap.get("pwd");
		String is_login = paramsMap.get("is_login");
		// 로그인 아이디와 암호의 존재 개수를 저장하는 변수 선언
		int admin_idCnt= 0;
		try {
			// LoginServiceImpl 객체의 getAdminCnt 메소드 호출하여 admin 테이블에 존재하는 로그인 아이디의 존재 개수를 얻어온다
			// 만약 존재 개수가 1이면 (로그인이 성공하면) HttpSession 객체에 로그인 아이디를 저장한다
			admin_idCnt = this.loginService.getAdminCnt( paramsMap );
			if( admin_idCnt == 1) {
				//-----------------------------------------------------------
				// HttpSession 객체에 [아이디] 저장하기
				// HttpSession 객체에 로그인에 성공한 아이디를 저장하면 
				// 연결상태가 유지되는 한 모든 JSP페이지에서 꺼내서 확인해 볼수 있다 
				session.setAttribute("admin_id", admin_id);
				//--------------------------------------------------------------------------------------------------------------
				// [아이디 암호 저장의사가 없을경우]
				// Cookies 객체 생성하고 쿠키값을 를 null로 하고 수명 0으로 하기 
				// 그리고  이 쿠키를 HttpServletResponse 객체에 저장하기
				// HttpServletResponse 객체에 저장된 쿠키는 클라이언트에게 전송된다 
				if(is_login == null) {
					Cookie cookie1 = new Cookie("admin_id", null);
					cookie1.setMaxAge(0);
					response.addCookie(cookie1);
					Cookie cookie2 = new Cookie("pwd", null);
					cookie2.setMaxAge(0);
					response.addCookie(cookie2);
				}
				//--------------------------------------------------------------------------------------------------------------
				// [아이디 암호 저장 의사가 있을 경우] 
				// 쿠키 생성하고 쿠키명-쿠키값을 [admin_id - 입력아이디] , [pwd-입력암호]로 하고 수명 정하기
				// 그리고  이 쿠키를 HttpServletResponse 객체에 저장하기
				// HttpServletResponse 객체에 저장된 쿠키는 클라이언트에게 전송된다 
				else {
					Cookie cookie1 = new Cookie("admin_id", admin_id);
					cookie1.setMaxAge(60*60*24);
					response.addCookie(cookie1);
					Cookie cookie2 = new Cookie("pwd", pwd );
					cookie2.setMaxAge(60*60*24);
					response.addCookie(cookie2);
				}
			}
			
		} catch(Exception ex) {
			System.out.println("LoginController.loginProc 에서 에러발생");
			admin_idCnt = -1;
		}
		// 로그인 존재 개수 리턴하기
		return admin_idCnt;
	}
	
}