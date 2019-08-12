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
public class DevSearchController {
	
	
	//******************************************************************************
	// 가상주소 /erp/loginForm.do 로 접속하면 호출되는 메소드 선언
	//******************************************************************************
	@RequestMapping( value="/devSearchForm.do")
	public ModelAndView loginForm( HttpSession session ) {
		
		
		// ModelAndView 객체 생성해서 호출할 JSP 페이지명을 저장하기
		ModelAndView mav = new ModelAndView();
		mav.setViewName("devSearchForm.jsp");
		return mav;
	}

}