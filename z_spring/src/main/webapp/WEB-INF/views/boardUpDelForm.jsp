<!-- JSP 기술의 한종류인 [Page Directive]를 이용해서 현 JSP 페이지 처리 -->
<!-- 현재 JSP 페이지의 처리 방식 설정 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!--JSP 기술의 한 종류인 [Include Directive] 를 이용하여 common.jsp파일의 코드를 삽입-->
<!-- 절대경로로 common.jsp파일의 코드를 삽입-->
<%@include file="/WEB-INF/views/common.jsp" %>
<%@include file="menubar.jsp" %>
<%@include file="checkLogin.jsp" %>

<html>
<head>
	<title>게시판</title>
	<script>
		// 게시판 수정 삭제 화면에 입력된 데이터의 유효성 체크 함수 선언
		function checkBoardUpDelForm(upDel) {
			//------------------------------------------------------------------------------------------------------------------
			// 매개변수로 들어온 upDel에 del이 저장되었으면(삭제 버튼을 눌렀다면)암호 확인하고 삭제여부 묻기
			if(upDel == "del") {
				var pwd = $("[name=pwd]").val();
				if( pwd.split(" ").join("")=="" ) {
					alert("암호를 입력해 주세요");
					$("[name=pwd]").focus();
					return;
				}
				//<주의>------------------------------------------------------------------------------------------------
				// 아래코드를 생략하면 수정버튼 누르고 다시 취소를 누르면 [name=upDel] 에 up 이 들어간다.
				// 그리고 삭제 버튼 누르면 여전히 up 상태이기 때문에 삭제가 되지 않는다
				document.boardUpDelForm.upDel.value="del";
				//--------------------------------------------------------------------------------------------------------
				if( confirm("정말 삭제 하시겠습니까?")==false) { return; }
			}
			//------------------------------------------------------------------------------------------------------------------
			// 매개변수로 들어온 upDel에 up이 저장되었으면(수정 버튼을 눌렀다면)입력양식의 유효성 체크하고 수정여부 묻기
			else if(upDel=="up") {
				
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
				
				//----------------------------------------------------------------------
				if (
						is_pattern( "writer", /^[a-zA-Z]{3,10}$/ ) == false	
						&&
						is_pattern( "writer", /^[가-힣]{2,10}$/ ) == false	
					) {
						alert("작성자는 영어 또는 한글만 입력 가능");
						$("[name=writer]").val("");
						return;
					} 
				//----------------------------------------------------------------------
				if(confirm("정말 수정하시겠습니까?") == false) {return;}
			}
			//----------------------------------------------------------------------
			// 현재 화면에서 페이지 이동 없이 서버쪽 "erp/boardUpDelProc.do" 를 호출
			// 게시판 수정 또는 삭제 적용 개수 가 있는 html 소스를  받기
			$.ajax({
				// 호출할 서버쪽 URL 주소 설정
				url : "/erp/boardUpDelProc.do"
				
				// 전송방법 설정
				, type : "post"
				
				// 서버에 보낼 파라미터값 설정
				, data : $("[name=boardUpDelForm]").serialize()
				
				// 서버의 응답을 성공적으로 받았을 경우 실행할 익명함수 설정
				// 익명함수의 매개변수 html 에는 boardUpDelProc.jsp 의 실행 결과물인 html 소스가 문자열로 들어옴
				/* , success : function(html) {
					// 매개변수로 들어온 html 문자열을 실행하고 출력 문자열을 변수에 저장	
					var upDelCnt = $(html).text();
					
					// 게시판 새글 입력 행 적용개수 가 한개면 메세지 띄우고 /erp/boardListForm.do 로 이동
					if(upDel == "up") {
						if(upDelCnt == 1) {
						alert("수정성공!");
						location.replace("/erp/boardListForm.do");
						} else if (upDelCnt == 0) {
							alert("비밀번호가 잘못 입력되었습니다.")
						} else if (upDelCnt == -1) {
							alert("삭제되어 수정이 불가능합니다")
							location.replace("/erp/boardListForm.do");
						} else {
							alert("서버 DB 연동 실패!")
						}
					}	
					  else if(upDel=="del") {
						if(upDelCnt == 1) {
							alert("삭제 성공!");
							location.replace("/erp/boardListForm.do");
						} else if(upDelCnt == -1) {
							alert("이미 삭제된 글입니다");
							location.replace("/erp/boardListForm.do");
						} else if(upDelCnt == -2) {
							alert("비밀번호가 틀립니다");
							location.replace("/erp/boardListForm.do");
						} else if(upDelCnt == -3) {
							alert("자식글이 있어 삭제 불가능합니다");
							location.replace("/erp/boardListForm.do");
						} else {
							alert("서버연동 실패");
						}
					  }	
				} */
				, success : function( upDelCnt ) {	
				
					// 게시판 새글 입력 행 적용개수 가 한개면 메세지 띄우고 /erp/boardListForm.do 로 이동
				if(upDel == "up") {
					if(upDelCnt == 1) {
					alert("수정성공!");
					location.replace("/erp/boardListForm.do");
					} else if (upDelCnt == -2) {
						alert("비밀번호가 잘못 입력되었습니다.")
					} else if (upDelCnt == -1) {
						alert("삭제되어 수정이 불가능합니다")
						location.replace("/erp/boardListForm.do");
					} else {
						alert("서버 DB 연동 실패!")
					}
				}	
				  else if(upDel=="del") {
					if(upDelCnt == 1) {
						alert("삭제 성공!");
						location.replace("/erp/boardListForm.do");
					} else if(upDelCnt == -1) {
						alert("이미 삭제된 글입니다");
						location.replace("/erp/boardListForm.do");
					} else if(upDelCnt == -2) {
						alert("비밀번호가 틀립니다");
						location.replace("/erp/boardListForm.do");
					} else if(upDelCnt == -3) {
						alert("자식글이 있어 삭제 불가능합니다");
						location.replace("/erp/boardListForm.do");
					} else {
						alert("서버연동 실패");
					}
				  }	
			} 
				// 서버의 응답을 못받았을 경우 실행할 익명함수
				, error : function() {
					alert("서버와 통신 실패");
				}
					
			});	
	}
		
	</script>

</head>

<body><center><br>
	<!-- [게시판 등록] 화면을 출력하는 form 태그 선언-->
	<form  method="post" name="boardUpDelForm" action="/erp/boardUpDelProc.do">
		<b>[글 수정/삭제]</b>
		<table class="tbcss1"	border="1"	bordercolor=gray	cellspacing="0"		cellpadding="5"	align="center">	
			<tr>
				<th bgcolor="${headerColor}"> 작성자
				<td><input type="text" 	size="10"		maxlength="10" name="writer"	value="${requestScope.boardDTO.writer}"></td>
			</tr>
			<tr>
				<th bgcolor="${headerColor}"> 제목
				<td><input type="text" 	size="40"		maxlength="50" name="subject"	value="${requestScope.boardDTO.subject}"></td>
			</tr>
			<tr>
				<th bgcolor="${headerColor}"> 이메일
				<td><input type="text" 	size="40"		maxlength="30" name="email"	value="${requestScope.boardDTO.email}"></td>
			</tr>
			<tr>
				<th bgcolor="${headerColor}"> 내 용
				<td>
				<textarea name="content" 	rows="13"	cols="40"> ${requestScope.boardDTO.content}</textarea>
			</tr>
			<tr>
				<th bgcolor="${headerColor}"> 비밀번호
				<td><input type="password" 	size="8"		maxlength="12" name="pwd">
			</tr>
		</table>
		
		<input type="hidden"		name="upDel"		value="up">
		<input type="hidden"		name="b_no"		value="${requestScope.boardDTO.b_no}">
		
		<input type="button"	value="수정"	onClick="checkBoardUpDelForm('up');">
		<input type="button"	value="삭제"	onClick="checkBoardUpDelForm('del');">
		<input type="button"	value="목록보기"	onClick="document.boardListForm.submit();"> 
	</form>
	
	<!-- [선택한 페이지번호] 저장한 hidden 태그 선언하고 [게시판 목록 화면] 으로 이동하는 form 태그 선언 -->
	<form name="boardListForm"	method="post"	action="/erp/boardListForm.do">
	</form>
	
	<input type="button" 	value="정보보기" 	onclick="print_html_info();">
</body>
</html>