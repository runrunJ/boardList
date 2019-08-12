<!-- JSP 기술의 한종류인 [Page Directive]를 이용해서 현 JSP 페이지 처리 -->
<!-- 현재 JSP 페이지의 처리 방식 설정 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- common.jsp 파일 수입하기. [Include Directive] 기술-->
<%@include file="common.jsp" %>

<%@include file="checkLogin.jsp" %>

<%@include file="menubar.jsp" %>
<!-- 게시판에 보이는 글이 내가 클릭하기 직전에 삭제되었으면 경고를 띄우고  게시판 목록보기 화면으로 이동하기-->
<c:if test="${empty board}">
	<script>
		alert("게시판 글이 삭제되었습니다");
		location.replace("/erp/boardListForm.do")
	</script>
</c:if> 

<html>
<head>
	<title>게시판</title>
	<script>
		// 게시판 댓글 화면으로 이동하는 함수 선언
		function goBoardRegForm() {
			document.boardRegForm.submit();
		} 
		
		// 게시판 수정화면으로 이동하는 함수 선언
		function goBoardUpDelForm() {
			
			// name=boardUpDelForm 을 가진 form 태그의 action 값 url로 서버에 접속
			document.boardUpDelForm.submit();
			
		}
	</script>
</head>

<body><center><br>
	<!-- [게시판 등록] 화면을 출력하는 form 태그 선언-->
	<form  class="boardContentForm"	method="post" 	name="boardContentForm" action="/erp/boardRegForm.do">
		<b>[글 상세 보기]</b>
		<table  class="tbcss1"	width="500"		border="1"	bordercolor="#dddddd"	cellpadding="5"	align="center">
			<tr align="center">
				<th bgcolor="${headerColor}" width=60> 글번호
				<td	width=150>${board.b_no}
				<th bgcolor="${headerColor}" width=60> 조회수
				<td	width=150>${board.readcount}
			<tr align="center">
				<th bgcolor="${headerColor}" width=60> 작성자
				<td>${board.writer}
				<th bgcolor="${headerColor}" width=60> 작성일
				<td>${board.reg_date}
			<tr>
				<th bgcolor="${headerColor}"> 글제목
				<td colspan="3">${board.subject}
			<tr>
				<th bgcolor="${headerColor}"> 글내용
				<td colspan="3"><pre>${board.content}</pre> 					
		</table>
		
		<table><tr height=3><td></table>
		
		<input type="hidden"		name="b_no"	value="${board.b_no}">
		
		<input type="button"	value="댓글쓰기"	onClick="goBoardRegForm();">
		<input type="button"	value="수정/삭제"	onClick="goBoardUpDelForm();">
		<input type="button"	value="글목록보기"	onClick="document.boardListForm.submit();">
		
	</form>
	<!-- 이전 페이지에서 온 게시판 선택 페이지 번호를 저장한 hidden 태그 출력하고 [게시판 목록]  화면으로 이동하는 form태그 선언-->
	<form name="boardListForm"	method="post"	action="/erp/boardListForm.do">
	</form>
	
	<form name="boardRegForm"	method="post"	action="/erp/boardRegForm.do">
		<!-- 게시판 상세보기 화면을  구성하는 글의 고유번호를 hidden 태그에 저장한다-->
		<!-- 댓글을 달기위해서는 주인글의 고유번호를 알아야 하기 때문이다-->
		<input type="hidden"		name="b_no"	value="${board.b_no}">
	</form>
	
	<!--************************************************************************ -->
	<!-- 수정/삭제 화면으로 이동하기 위한 form 태그 선언-->
	<!--************************************************************************ -->
	<form name="boardUpDelForm"	method="post"	action="/erp/boardUpDelForm.do">
		<!-- 게시판 상세보기 화면을  구성하는 글의 고유번호를 hidden 태그에 저장한다-->
		<!-- 수정/삭제를 하려면 현재 글의 고유번호를 알아야 하기 때문이다-->
		<input type="hidden"		name="b_no"	value="${board.b_no}">	
	</form>
</body>
</html>