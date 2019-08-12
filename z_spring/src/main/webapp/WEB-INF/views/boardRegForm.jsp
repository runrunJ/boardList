<!-- JSP 기술의 한종류인 [Page Directive]를 이용해서 현 JSP 페이지 처리 -->
<!-- 현재 JSP 페이지의 처리 방식 설정 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!--JSP 기술의 한 종류인 [Include Directive] 를 이용하여 common.jsp파일의 코드를 삽입-->
<%@include file="common.jsp" %>
<%@include file="menubar.jsp" %>
<%@include file="checkLogin.jsp" %>


<html>
<head>

<title>게시판 새글쓰기</title>
<script>
	
	// 게시판 등록 화면에 입력된 데이터의 유효성 체크 함수 선언
	function checkBoardRegForm() {
		//------------------------------------------------------------
		/* var writer = $("[name=writer]").val();
		if(writer.split(" ").join("") == "" ) {
			alert("이름을 입력하세요");
			$("[name=writer]").focus();
			return;
		}
		var subject = $("[name=subject]").val();
		if(subject.split(" ").join("") == "" ) {
			alert("제목을 입력하세요");
			$("[name=subject]").focus();
			return;
		}
		var email = $("[name=email]").val();
		if(email.split(" ").join("") == "" ) {
			alert("이메일을 입력하세요");
			$("[name=email]").focus();
			return;
		}
		var content = $("[name=content]").val();
		if(content.split(" ").join("") == "" ) {
			alert("내용을 입력하세요");
			$("[name=content]").focus();
			return;
		}
		var pwd = $("[name=pwd]").val();
		if(pwd.split(" ").join("") == "" ) {
			alert("암호를 입력하세요");
			$("[name=pwd]").focus();
			return;
		} */
		
		if(is_empty("writer")) {
			alert("이름을 입력하세요");
			$("[name=writer]").focus();
			return;
		}
		if(is_empty("subject")) {
			alert("제목을 입력하세요");
			$("[name=subject]").focus();
			return;
		}
		if(is_empty("email")) {
			alert("이메일을 입력하세요");
			$("[name=email]").focus();
			return;
		}
		if(is_empty("content")) {
			alert("내용을 입력하세요");
			$("[name=content]").focus();
			return;
		}
		if(is_empty("pwd")) {
			alert("암호를 입력하세요");
			$("[name=pwd]").focus();
			return;
		}
		//-----------------------------------------------------------------------
		/* if(
			new RegExp(/^[a-zA-Z]{3,10}$/).test( $("[name=writer]").val() ) == false
			&&
			new RegExp(/^[가-힣]{2,10}$/).test( $("[name=writer]").val() ) == false
		){
			alert("영어 또는 한글만 입력 가능");
			$("[name=writer]").val("");
			return;
		} */
		//-----------------------------------------------------------------------
		if (is_pattern( "writer", /^[a-zA-Z]{3,10}$/ ) == false	&&
			is_pattern( "writer", /^[가-힣]{2,10}$/ ) == false) {
				alert("영어 또는 한글만 입력 가능");
				$("[name=writer]").val("");
				return;
			} 
		//-----------------------------------------------------------------------
		var content = $("[name=content]").val();
		if (content.length > 1000) {
				alert("내용은 1000자를 넘을 수 없습니다");
				return;
			} 
		
		if(confirm("정말 저장하시겠습니까?") == false) {return;}
		
		//alert($("[name=boardRegForm]").serialize());
		//return;
		//----------------------------------------------------------------------
		// 현재 화면에서 페이지 이동 없이 서버쪽 "erp/boardRegProc.do" 를 호출
		// 게시판 입력 행 적용 개수 가 있는 html 소스를 문자열로 받기
		$.ajax({
			// 호출할 서버쪽 URL 주소 설정
			url : "/erp/boardRegProc.do"
			
			// 전송방법 설정
			, type : "post"
			
			// 서버에 보낼 파라미터값 설정
			, data : $("[name=boardRegForm]").serialize()
			ㄴ
			, success : function( boardRegCnt ) {
				if(boardRegCnt == 1) {
					alert("게시판 새글 등록 성공!");
					location.replace("/erp/boardListForm.do");
				}
				// [게시판 새글 입력 행 적용 개수] 가 1개가 아니면 경고하기
				else {
					alert("게시판 새글 등록 실패. 관리자에게 문의바람")
				}
			}
			
			// 서버의 응답을 못받았을 경우 실행할 익명함수
			, error : function() {
				alert("서버와 비동기 방식 통신 실패");
			}
			
		});
		//onClick="checkBoardRegForm()">
	}
</script>
</head>
<body><center><br>
	<!-- [게시판 등록] 화면을 출력하는 form 태그 선언-->
	<form  method="post" name="boardRegForm" action="/erp/boardRegProc.do">
		<!-- 커스텀태그의 일종인 JSTL C코어 태그를 사용하여 
		파라미터명이 b_no 인 파라미터 값이 비어 있으면 새글쓰기 출력-->
		<c:if test="${ empty param.b_no}">
			<b>[새글쓰기]</b>
		</c:if>
		<!-- 파라미터명이 b_no인 파라미터 값이 있으면 댓글쓰기 출력한다 -->
		<c:if test="${!empty param.b_no}">	
			<b>[댓글쓰기]</b>
			</c:if>
		
		<table class="tbcss1"	border="1"	bordercolor=gray	cellspacing="0"		cellpadding="5"	align="center">
			<tr>
				<th bgcolor="${headerColor}"> 이 름
				<td><input type="text" 	size="10"		maxlength="10" name="writer">
			</tr>
			<tr>
				<th bgcolor="${headerColor}">  제 목
				<td><input type="text" 	size="40"		maxlength="50" name="subject">
			</tr>
			<tr>
				<th bgcolor="${headerColor}">  이메일
				<td><input type="text" 	size="40"		maxlength="30" name="email">
			</tr>
			<tr>
				<th bgcolor="${headerColor}"> 내 용
				<td><textarea name="content" 	rows="13"	cols="40"></textarea>
			</tr>
			<tr>
				<th bgcolor="${headerColor}"> 비밀번호
				<td><input type="password" 	size="8"		maxlength="12" name="pwd">
			</tr>		
		</table>
		
		<table	border="0"><tr height=4><td></table> <!-- 여백을 위한 -->
		
		<input type="hidden"		name="b_no"	value="${param.b_no }">
		
		<input type="button"	value="저장"		onClick="checkBoardRegForm()">
		<input type="reset"	value="다시작성">
		<input type="button"	value="목록보기"		onClick="document.boardListForm.submit()">
	</form>
	
	<form name="boardListForm"	method="post"	action="/erp/boardListForm.do">
	</form>
	
	<input type="button" 	value="정보보기" 	onclick="print_html_info();">
</body>
</html>