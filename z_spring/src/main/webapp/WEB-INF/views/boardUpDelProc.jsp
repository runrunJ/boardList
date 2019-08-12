<!-- JSP 기술의 한종류인 [Page Directive]를 이용해서 현 JSP 페이지 처리 -->
<!-- 현재 JSP 페이지의 처리 방식 설정 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!--JSP 기술의 한 종류인 [Include Directive] 를 이용하여 common.jsp파일의 코드를 삽입-->
<!-- 절대경로로 common.jsp파일의 코드를 삽입-->
<%@include file="/WEB-INF/views/common.jsp" %>

<!-- 게시판 글의 수정/삭제 적용할 행의 개수 (한 개)를 EL로 표현하기 -->
<html><body>${requestScope.boardUpDelCnt}</body></html>