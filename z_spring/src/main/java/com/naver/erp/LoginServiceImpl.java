package com.naver.erp;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//************************************************
// [서비스클래스]  LoginServiceImpl 클래스를 선언
//************************************************************************************************
	// @Service => 서비스 클래스 임을 지정하고 bean 태그로 자동 등록된다
	// @Transactional =>  메소드 내부에서 일어나는 모든 작업에는 트랜잭션이 걸린다
//************************************************************************************************
@Service
public class LoginServiceImpl implements LoginService{
	//************************************************************************************************
	// 속성변수 loginDAO 선언하고 LoginDAO라는 인터페이스를 구현한 클래스를 객체화해서 저장
	// @Autowired 가 붙은 속성변수에는 인터페이스 자료형을 쓰고 이 인터페이스를 구현한 클래스를 객체화하여 저장한다.
	// LoginService 라는 인터페이스를 구현한 클래스의 이름을 몰라도 관계없다. 한개 존재하기만 하면 된다.
	//************************************************************************************************
	@Autowired
	private LoginDAO loginDAO;
	//***********************************************
	// 로그인 정보의 개수를 리턴하는 메소드 선언
	//***********************************************
	public int getAdminCnt( Map<String, String> admin_id_pwd ) {
		int adminCnt = this.loginDAO.getAdminCnt( admin_id_pwd );
		return adminCnt;
	}
}
