<!-- JSP 기술의 한종류인 [Page Directive]를 이용해서 현 JSP 페이지 처리 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- common.jsp 소스 파일 가져오기 [Include Directive] 기술-->
<!--  여러 JSP 페이지에 공통으로 들어갈 코드가 있다면 이 방법을 사용한다--> 
<%@include file="common.jsp" %>

<%@include file="menubar.jsp" %>

<%@include file="checkLogin.jsp" %>
<!-- ------------------------------------------------------------------------------------------------->
	

<!-- 게시판 화면을 구성하는 태그 -->
<html>
<head>
	<title>게시판</title>
	<script>
		// body 태그를 읽어들인 후 실행할 자바스크립트 코딩설정
		$().ready(function() {
			
			// name=boardRegForm, name=boardContentForm 두 form 태그를 안보이게 하기
			$("[name=boardRegForm], [name=boardContentForm]").hide();
			
			// 입력양식에 이벤트가 발생하면 실행할 코드 설정하기
			$("[name=rowCntPerPage]").change(function(){
				$(".contactSearch").click();
				//goSearch( );  // 이것도 가능
			});
			
			
			// 게시판 목록 table 홀,짝수 행 배경색 설정하기
			// 제이쿼리 객체의 css 메소드 호출로 색 지정하기(EL 문법으로 표기한다)
			//--------같은 변수를 두번이상 설정하면 아래를 인식한다--------------
			var headerColor = "${headerColor}";
			var evenColor = "${evenColor}";
			var oddTrColor = "${oddTrColor}";
			var mouseOverColor = "${mouseOverColor}";
			
			 setTableTrBgColor(
				"boardList"
				, headerColor				// 헤더 배경색
				, oddTrColor		// 홀수 tr 배경색
				, evenColor				// 짝수 tr 배경색
				, mouseOverColor
			);
			//-------------------------------------------------------------------------------
			// 페이징 처리 관련 소스를 class=pagingNumber 태그 안에 삽입하기
			//-------------------------------------------------------------------------------
			$(".pagingNumber").html(
					getPagingNumber(
						"${requestScope.boardListAllCnt}"	// 검색 결과 총 행의 개수_totRowCnt
						, "${sessionScope.boardSearchDTO.selectPageNo}"				// 선택된 현재 페이지 번호
						, "${sessionScope.boardSearchDTO.rowCntPerPage}"			// 페이지당 출력되는 행의 개수
						, "10"													// 한 화면당 보여줄 페이지번호 개수_pageNoCntPerPage_str
						, "goSearch();"										// 페이지 번호 클릭 후 실행할 자스 코드_jsCodeAfterClick
					)
			);
			 
			//--------------------------------------------------------------------------------------
			// 검색조건에 흔적 남기기2
			//--------------------------------------------------------------------------------------
			inputData("keyword1", "${sessionScope.boardSearchDTO.keyword1}");
			inputData("keyword2", "${sessionScope.boardSearchDTO.keyword2}");
			inputData("or_and", "${sessionScope.boardSearchDTO.or_and}");
			<c:forEach items="${sessionScope.boardSearchDTO.date}" var="date">
				inputData("date", "${date}");
			</c:forEach>
			inputData("selectPageNo", "${sessionScope.boardSearchDTO.selectPageNo}");
			inputData("rowCntPerPage", "${sessionScope.boardSearchDTO.rowCntPerPage}");
		})	
	
		// [게시판 입력 화면] 으로 이동하는 함수 선언
		function goBoardRegForm() { 
			// name=boardRegForm 을 가진 form 태그 안의 action 에 설정된 URL로 이동하기
			// 이동시 form 태그안의 모든 입력 양식이 파라미터 값으로 전송
			document.boardRegForm.submit(); 
		}
		//------------------------------------------------------------------------------------
		// [한 개의 게시판 내용물] 을 보여주는 [게시판 상세 보기 화면] 으로 이동하는 함수 선언
		function goBoardContentForm(b_no) {
			
			// 클릭한 게시판 글의 PK값을 name=	boardContentForm 을 가진
			// form 태그안의 name=b_no 을 가진 입력 양식에 삽입하기
			$("[name=boardContentForm] [name=b_no]").val(b_no);
			
			// name=boardContentForm 을 가진 form 태그안의 action에 설정된 URL로 이동하기
			// 이동 시 form 태그안의 모든 입력 양식이 파라미터 값으로 전송된다.
			document.boardContentForm.submit();
		}
		//********************************************************************************************
		// [게시판 목록 화면]으로 이동하는 함수
		//********************************************************************************************
		function goSearch( ) {
			//alert($("[name=boardListForm]").serialize()); // 파라미터 값은 내가 키보드로 입력한 값이다.
			if( is_special_char("keyword1") ||  is_special_char("keyword2") ) {
				alert( "검색은 한글, 영문, 숫자, 언더바(_)만 입력가능합니다" );
				$(".keyword1,.keyword2").val("");
				return;
			}
			
			if( is_empty("keyword1") && is_empty("keyword2") && is_empty("date" ) ) {
				
				$(".keyword1,.keyword2").val("");
			}
			document.boardListForm.submit();
		}

		//--------------------------------------------------------------
		// goSearch2는 현재 쓰임새 없음. 참고용
		function goSearch2( ) {
			// 입력된 키워드 가져오기. 앞뒤 공백 제거
			var keyword1 = $(".keyword1").val();
			var keyword2 = $(".keyword2").val();
			var dateCnt = $("[name=date]").filter(":checked").length;
			
			keyword1 = $.trim(keyword1);
			keyword2 = $.trim(keyword2);
			$(".keyword1").val(keyword1);
			$(".keyword2").val(keyword2);
			
			// 입력된 키워드 공백 제거하기. // 키워드가 없으면 경고하고 함수 중단
			if(keyword1.split(" ").join("") == "" && keyword2.split(" ").join("") == "" && dateCnt == 0) {
				alert("입력된 키워드가 없습니다");
				$(".keyword1").val("");
				$(".keyword2").val("");
				return;
			}
			
			// name="boardListForm" 를 가진 form 태그안의 action에 설정 된 url 로 이동하기
			// 이동 시 form 태그안의 모든 입력 양식이 파라미터 값으로 전송된다.
			document.boardListForm.submit();
			
		}
		//------------------------------------------------------------------------------------
		
		// 키워드 없이 게시판 목록 화면으로 이동하는 모두검색 함수 만들기 
		function goSearchAll() {
			setEmpty2( "[name=keyword1], [name=keyword2], [name=date]" );
			inputData( "selectPageNo", "1" );
			/* setEmpty("keyword1");
			setEmpty("keyword2");
			setEmpty("date"); */
			/* $(".keyword1").val("");
			$(".keyword2").val("");
			$("[name=date]").prop("checked", false);
			//return; */
			document.boardListForm.submit();
		}
	</script>
</head>
<body		onKeyup="if(even.keyCode==13) { goSearch(); }"><center><br>
	<!-- ******************************************************************************** -->
	<form	name="boardListForm"	method=post 	action="/erp/boardListForm.do">
		<input type="text"	name="keyword1"		class="keyword1">
		<select name="or_and">
			<option value="or">or
			<option value="and">and
		</select>
		<input type="text"	name="keyword2"		class="keyword2">&nbsp;
		<!-- ******************************************************************************** -->
		<input type="checkbox"		name="date"		value="오늘">오늘 &nbsp;
		<input type="checkbox"		name="date"		value="어제">어제 &nbsp;
		<!-- ******************************************************************************** -->
		<input type="button"	value="검색"		class="contactSearch"	onClick="goSearch();">&nbsp;
		<input type="button"	value="모두검색"		onClick="goSearchAll();">&nbsp;
		<!-- ******************************************************************************** -->
		<input type="hidden" 	name="selectPageNo">
		<select name="rowCntPerPage">
			<option value="10">10
			<option value="15">15
			<option value="20">20
			<option value="25">25
			<option value="30">30
		</select>행보기<br><br>
	</form>
	<!-- ******************************************************************************** -->

	<table border=0>
		<tr>
			<td align=right>
				<!-- EL문법으로 키값을 써서 개수 꺼내기 -->
				[검색 글 개수] : ${requestScope.boardListAllCnt} &nbsp;&nbsp;&nbsp;&nbsp;
				
				<a href="javascript:goBoardRegForm()"><b>[새 글쓰기]</b></a>
		<!--------------------------------------------------------------------------------------------------->
		<!-- 페이징 처리 -->
		<!--------------------------------------------------------------------------------------------------->
		<tr>
			<th><span class="pagingNumber"></span>
		
		<tr><td>
				<!--------------------------------------------------------------------------------------------------->
				<table border=0		class="boardList tbcss2"		cellpadding=5		cellspacing=0> 
				<tr><th>번호<th>제목<th>글쓴이<th>등록일<th>조회수
				<!--------------------------------------------------------------------------------------------------->
				<!--BoardDAO.java 파일의 getBoardList() 메서드-while반복문 내부 ->
				<!-- HttpServletRequest 객체에 boardList 라는 키값으로 저장된 -->
				<!-- ArrayLIst<HashMap<String, String>> 객체를 꺼내고  HashMap 의 키값에 대응하는 문자열을 출력 -->
				<!--------------------------------------------------------------------------------------------------->
				<!-- 자바 지역변수 var = "board" 에 한 행씩 저장하다. 
				반복문 돌때마다 지역변수 board에는 n번째 HashMap<String, String>  객체가 저장된다-->
				<!-- 반복문 돌 때마다 LoopTagStatus 객체의 index라는 속성변수안 데이터를 꺼내어 출력한다. 
				출력 시 형식은 EL이고 달러표시{loopTagStatus.index} 로 한다. 
				반복문 돌 때마다 loopTagStatus 객체의 index 변수는 0부터 시작해서 증가한다.
				현재 LoopTagStatus 객체의 메위주는 varStatus="loopTagStatus에 선언된 지역변수이다.-->
				<c:forEach items = "${requestScope.boardList}" 	var = "board" 	varStatus="loopTagStatus">
					<tr style="cursor:pointer"	onClick="goBoardContentForm(${board.b_no})">
							<td>
								 <!-- 역순으로 행 번호생성(두가지 방법)-->	
								 <!-- ${requestScope.boardListAllCnt - ((sessionScope.selectPageNo-1) * sessionScope.rowCntPerPage) - loopTagStatus.index} -->
								 ${boardListAllCnt - (boardSearchDTO.selectPageNo * boardSearchDTO.rowCntPerPage 
								 - boardSearchDTO.rowCntPerPage +1 + loopTagStatus.index) + 1}
									<!--------------------------------------------------------------------------------------------------->
								 <!-- 순차적으로 행 번호생성-->	
								  <!-- ${sessionScope.selectPageNo*sessionScope.rowCntPerPage-sessionScope.rowCntPerPage+1+loopTagStatus.index} -->
							<td>	
									<c:if test="${board.print_level > 0 }">
										<c:forEach begin="0" end="${board.print_level }">
											&nbsp;&nbsp;&nbsp;
										</c:forEach>
										ㄴ
									</c:if>
									${board.subject }		
							<td>${board.writer }	
							<td>${board.reg_date }		
							<td>${board.readcount } 
							<!-- <td>${board.pwd } -->	<br> 
				</c:forEach> 
				</table>
	</table>
	
	${requestScope.boardListAllCnt==0?'검색된 글이 없습니다' : ''}
	<!-- [게시판 등록 화면] 으로 이동하는 주소를 가진 form 태그 선언하기 -->
	<!-- 이동시 form 태그안의 모든 입력 양식이 파라미터 값으로 전송된다. -->
	<form name="boardRegForm" method="post" action="/erp/boardRegForm.do">
	</form>		
	 
	 <!-- 선택한 게시판 상세보기 화면으로 이동하는 주소를 가진 form 태그 선언하기 -->
	 <!-- 이 form 태그 의 입력양식은 파라미터값으로 페이지 이동 시 전달된다 -->
	 <form name="boardContentForm" 	method="post" 	action="/erp/boardContentForm.do">
	 	<input type="hidden" 	name="b_no">
	 </form>
</body>
</html>