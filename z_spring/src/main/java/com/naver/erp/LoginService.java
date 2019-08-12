package com.naver.erp;

import java.util.Map;

public interface LoginService {
	// 로그인 아이디와 암호 존재개수를 검색하는 메소드 선언
	int getAdminCnt( Map<String, String> admin_id_pwd );
}
