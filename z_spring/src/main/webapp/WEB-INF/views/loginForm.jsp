<!-- JSP 기술의 한종류인 [Page Directive]를 이용해서 현 JSP 페이지 처리 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!--JSP 기술의 한 종류인 [Include Directive] 를 이용하여 common.jsp파일의 코드를 삽입-->
<!-- 다른 JSP 페이지의 코드를 똑같이 가져오는 것이다. -->
<!--  여러 JSP 페이지에 공통으로 들어갈 코드가 있다면 이 방법을 사용한다 --> 
<%@include file="common.jsp" %>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<html>
<head>
<script>
	// body 태그 안의 소스를 모두 실행한 후 진행 될 자스 코드
	$(document).ready(function() {
		//$(".admin_id").val("abc");
		//$(".pwd").val("abc123");
		
		// 클라이언트가 보낸 쿠키값을 value 값에 삽입
		inputData("admin_id", '${cookie.admin_id.value}');
		inputData("pwd", '${cookie.pwd.value}');
		//alert("쿠키확인용2");	
			// 쿠키중 admin_id 라는 쿠키값이 존재하면 name=is_login 태그를 체크하기
			<c:if test="${!empty cookie.admin_id.value}">
				//inputData("is_login", 'y');
				$('[name=is_login]').prop("checked", true);
			</c:if>
			
		 // name=loginForm 을 가진 태그안의 class= .login 가진 태그에 click 이벤트 발생시 실행할 코드 설정
		$("[name=loginForm] .login").click(function() {			// $( '[name="loginForm"] ').find('.login').click(function( ){ } ) 도 가능
			
			// 아이디와 암호의 유효성 체크할 함수 호출(아래에 함수가 바로 존재한다) 
			checkLoginForm();
		})
	});	
	
	// 로그인 정보 유효성 체크하고 비동기 방식으로 서버와 통신하여 로그인 아이디, 암호 존재여부 확인
	function checkLoginForm() {
		// 입력된 아이디 가져와서 변수에 저장
		var admin_id = $("[name=admin_id]").val();
		// 또는 var admin_id = $(".admin_id").val();
		//-------------------------------------------------------
		// 아이디를 입력 안했거나 공백이 있으면 아이디 입력란 비우고  함수 중단
		if( admin_id.split(" ").join("") == "" ) {
			alert("관리자 아이디 입력하시죠");
			$(".admin_id").val("");
			return;
		}
		//------------------------------------------------------
		// 입력한 [암호]를 가져와 변수에 저장
		var pwd = $("[name=pwd]").val();
		
		if(pwd.split(" ").join("") == "" ) {
			$("[name = pwd]").val("");
			alert("압호를 입력 하시죠");			
			return;
		}
		//******************************************************************************************************
		// 현재 화면에서 페이지 이동 없이 (비동기 방식으로) 서버쪽 loginProc.jsp 을 호출하여 HTML 소스를 응답받기
		//******************************************************************************************************
		$.ajax({
			//--------------------------------------------------------------
			// 서버쪽 호출 url 주소 지정
			url : "/erp/loginProc.do"
			//--------------------------------------------------------------
			// form 태그 안의 데이터 즉, 파라미터 값을 보내는 방법 지정
			, type : "post"
			//--------------------------------------------------------------
			// 서버에 보낼 파라미터명과 파라미터값을 설정
			, data :  $("[name=loginForm]").serialize()
			// {'admin_id' : admin_id, 'pwd' : pwd}도 가능. 제이슨 방식
			//--------------------------------------------------------------
			// 서버가 응답할 페이지 종류 설정. html일 경우 생략 가능
			, datatype : "html"
			//--------------------------------------------------------------
			// 서버의 응답을 성공적으로 받았을 경우 실행할 익명함수 설정
			// 익명함수의 매개변수 html 에는 서버가 응답한 [html 소스 문자열]이 들어온다
			// 이 안에 loginProc.jsp 가 html 소스로 변한 소스가 들어온다.(0 또는 1 또는 -1이다)
			/* , success : function(html) {
				
				// 서버가 응답한 [html 소스 문자열] 중에 body 태그안의 숫자를 읽어오기
				var idCnt = $(html).text();
				idCnt = idCnt.split(" ").join("");
				// 아이디 존재개수가 1개면 메세지 띄우고 /erp/boardList.do 로 이동
				if (idCnt == 1) {		// 정확히는 문자 "1"이다. 자스는 숫자와 숫자문자를 편의상 자동으로 비교해준다. 자바는 안된다
					alert("로그인 성공!");
					//location.replace("/erp/boardListForm.do");
				}
				else {
					alert("로그인 실패. 아이디와 암호를 재입력 하세요");
				}
			} */
			
			, success : function(data) {
				// 아이디 존재 개수가 1개면 메시지 띄우고 /erp/boardList.do로 이동
				if(data == 1) {
					//alert("로그인 성공");
					//--------------------------------------------------------------------------------
					// location.replace 는 get 방식으로 BoardController클래스 => getBoardList 메소드에 접근하는 것이다
					//  post 방식으로 보내고 싶다면 <body> 하단에 비어있는 form 태그를 만들고(최하단 참조)
					// 아래코드 대신 document.boardListForm.submit(); 을 써야한다.
					location.replace("/erp/boardListForm.do"); 	
				}
				else {
					 alert("로그인 실패!")
				 }
			} 
			//--------------------------------------------------------------
			// 서버의 응답을 못 받았을 경우 실행할 익명함수 설정
			, error : function() {
				alert("서버 접속 실패");
			}
		});
	}
</script> 
</head>
<title>게시판</title>
<body><center><br>
	<!-- [로그인 정보 입력 양식] 내포한 form 태그 선언-->
	<form name="loginForm" method="post" action="/erp/loginProc.do">
		<div style="height:6"></div>
		<table class="tbcss1"	border=1 cellpadding=20 cellspacing=20	bordercolor="#A9BCF5"><tr><th>
			<b ><font color="grey">[게시판 이용 전 로그인이 필요합니다]</font></b>
			
			<table class="tbcss1"	border=1 cellpadding=5 cellspacing=0 bordercolor="#A9BCF5">
				<tr>
					<td bgcolor="#FAFAD2" align=center> 아이디
					<td><input type="text" name="admin_id" class="admin_id" size="20">
				<tr>
					<td bgcolor="#FAFAD2" align=center> 암호
					<td><input type="password" name="pwd" class="pwd" size="20">
			</table><br>
			<div style="height:6"></div>
			<input type="button" value="로그인" class="login">
			<input type="checkbox" name="is_login" value="y">아이디.암호 기억
		</table>
	</form>
	
	<!-- post 방식으로 BoardController에 접근하려면 아래의 form태그를 써야한다  -->
	 <!--  <form name="boardListForm" method="post" action="/erp/boardListForm.do"></form>-->
</body>
</html>