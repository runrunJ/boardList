<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);
	//response.addHeader("Cache-Control", "no-store");
%>


<!-- jQuery 라이브러리 수입 -->
<script src="/erp/resources/jquery-1.11.0.min.js"></script>

<!--common.js 라이브러리 수입 -->
<script src="/erp/resources/common.js?ver=<%=Math.random()%>"></script>

<!-- JSP 페이지에서 사용할 [커스텀 태그]인 [JSTL의 C코어 태그]를 사용할수 있도록 선언 --> 
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %> 

<!-- JSP 페이지에서 사용할 [css 파일] import -->
<link href="/erp/resources/common.css?ver=<%=Math.random()%>" rel="stylesheet" type="text/css">

<!-- JSP 페이지에서 사용할 [커스텀 태그]인 [JSTL의 C코어 태그] 선언 -->
<!-- C코어 태그를 사용하여 선언된 변수는 자바영역의 변수와 동일하다-->
<!-- C코어 태그로 선언된 변수안의 데이터를 꺼낼 때는 EL 문법으로 달러표기{변수명} 으로꺼낸다 -->
<c:set var="headerColor"	value="#FAFAAA"/>
<c:set var="evenColor"	value="#F7F8E0"/>
<c:set var="oddTrColor"	value="white"/>
<c:set var="mouseOverColor"	value="#ACFA58"/>

