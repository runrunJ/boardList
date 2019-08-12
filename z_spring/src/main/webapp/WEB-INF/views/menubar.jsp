<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!--********************************************* -->
<!-- [메뉴바] 를 body 태그 바로 밑에 삽입하기 -->
<!--********************************************* -->
<script>
$(document).ready(function(){
    //printMenubar(~) 함수 호출로 출력하기
    
    printMenubar(
           "#F3F781"   	//menubarBgColor
          , "white" 		//mouseoverBgColor
          , "#8A4B08" 	//mouseoverFontColor
          , "#F3F781" 	//mouseoutBgColor
          , "#8A4B08" 	//mouseoutFontColor
          , [
                ['/erp/boardListForm.do', '게시판']
                ,['/erp/loginForm.do', '[로그아웃]']
          ]
    );
 })
</script>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>재영게시판</title>
</head>
<body>
</body>
</html>