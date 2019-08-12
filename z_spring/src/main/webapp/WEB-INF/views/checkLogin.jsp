<!-- JSP 기술의 한종류인 [Page Directive]를 이용해서 현 JSP 페이지 처리 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!--JSP 기술의 한 종류인 [Include Directive] 를 이용하여 common.jsp파일의 코드를 삽입-->
<!-- 다른 JSP 페이지의 코드를 똑같이 가져오는 것이다. -->
<!--  여러 JSP 페이지에 공통으로 들어갈 코드가 있다면 이 방법을 사용한다 --> 
<%@include file="common.jsp" %>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- [JSTL 커스텀 태그] 와 EL 을 사용하여 -->
<!-- HttpSession 객체에 로그인 아이디가 없으면 경고하고 로그인 화면으로 이동 시키기 -->
<!-- 만약 HttpSession 객체에 admin_id 라는 키값으로 저장된 것이 없으면 자스코딩으로 경고하고
		로그인 화면으로 이동시키기 -->
<!-- ------------------------------------------------------------------------------------------------->		
<!--sessionScope은 HttpSession 객체에 저장된 admin_id가 있는지 true | false 로 출력  -->
<c:if test="${empty sessionScope.admin_id}">
	<script>
		alert('로그인 필요!');
		location.replace('/erp/loginForm.do');
	</script>	
</c:if>	

<br>
<center>${warning}</center>