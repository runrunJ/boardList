<!-- JSP 기술의 한종류인 [Page Directive]를 이용해서 현 JSP 페이지 처리 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!--HttpServletRequest 객체에 boardRegCnt 라는 키값으로 저장된 숫자를  -->
<!-- [게시판 글 입력  행의 개수를 뜻 하는 숫자 1을 EL로 body 태그안에 표현하기] -->
<html><body>${requestScope.boardRegCnt}</body></html>
