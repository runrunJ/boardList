package com.naver.erp;
import java.util.*;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//**************************************************************************************
// DAO 클래스인 [LoginDAOImpl 클래스] 선언
// @Repository 를 붙임으로서 [DAO 클래스] 임을 지정하게 되고, bean 태그로 자동 등록된다
//*****************************************************************
@Repository
public class LoginDAOImpl implements LoginDAO {
	//*****************************************************************
	// SqlSessionTemplate 를 생성해 속성변수 sqlSession 에 저장한다
	//*****************************************************************
	@Autowired
	private SqlSessionTemplate sqlSession;
		//***********************************************
		// 로그인 정보의 개수를 리턴하는 메소드 선언
		//***********************************************
	public int getAdminCnt( Map<String, String> admin_id_pwd ) {
		//--------------------------------------------------------------------------------------------------------------
		// SqlSessionTemplate 객체의 selectOne 메소드 호출로 로그인 아이디, 암호의 존재개수를 리턴
		//--------------------------------------------------------------------------------------------------------------
		// 아래 selectOne( "com.naver.erp.LoginDAO.getAdminCnt" , admin_id_pwd)의 의미:
			// 마이바티스 SQL 구문 설정 XML 파일(mapper_login.xml)에서
			// <mapper namespace="com.naver.erp.LoginDAO"> 태그 내부의
			// <slect id="getAdminCnt"> 태그 내부의
			// [1행 리턴 select 쿼리문]을 실행하고 얻은 데이터를 int로 리턴한다
			// 두 번째 인자는 [1행 리턴 select 쿼리문]에 삽입될 데이터이다. 리턴자료형은 무조건 int이다
		//--------------------------------------------------------------------------------------------------------------
		int adminCnt = this.sqlSession.selectOne(
				"com.naver.erp.LoginDAO.getAdminCnt"
				, admin_id_pwd
		);
		return adminCnt;
	}
}
