<!-- JSP 기술의 한종류인 [Page Directive]를 이용해서 현 JSP 페이지 처리 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- HttpServletRequest 객체에  admin_id 라는 키값으로 저장된 데이터를 꺼내서 body 안에 표현하기-->
<!-- 이 jsp 파일이 html로 바뀐다. 그후 클라이언트로 던진다 -->
<html>
<body>
	${requestScope.admin_idCnt} 
</body>
</html>