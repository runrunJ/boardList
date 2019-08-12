<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>수정/삭제 화면</title>
</head>
<body>



<form name="SearchForm" method="post" action="/erp/SearchProc.do">
	<table border="1" cellspacing="0" cellpadding="5">
		<tr align="center">
					<td colspan="6"	bgcolor="#C0C0C0">사원 정보 수정</td>
		<tr align="center">
					<td bgcolor="#C0C0C0">이름</td>
					<td><input type="text" name="dev_name" size=20 maxlength=10></td>
					<td bgcolor="#C0C0C0">주민번호</td>
	                <td>
                    <input type="text" name="jumin_num1" maxlength="6">  -  
                    <input type="text" name="jumin_num2" maxlength="7">
					<td bgcolor="#C0C0C0">종교</td>
					<td> 
						<select name="religion">
								<option value=""></option>
								<option value="무교">무교</option>
								<option value="기독교">기독교</option>
								<option value="불교">불교</option>
								<option value="이슬람교">이슬람교</option>
								<option value="천주교">천주교</option>
						</select>
					</td>
			</tr>
			
			<tr align="center">
				<td bgcolor="#C0C0C0">학력</td>
	            <td>
		            <input type="radio" name="school" value="고졸">고졸
		            <input type="radio" name="school" value="전문대졸">전문대졸
		            <input type="radio" name="school" value="일반대졸">일반대졸
	       
		       <td bgcolor="#C0C0C0">기술</td>
		       <td colspan="3">
		       		<input type="checkbox" name="skill" value="Java">Java
		           <input type="checkbox" name="skill" value="JSP">JSP
		           <input type="checkbox" name="skill" value="ASP">ASP
		           <input type="checkbox" name="skill" value="PHP">PHP
		           <input type="checkbox" name="skill" value="Delphi">Delphi
	       </tr>
	       
	       <tr align="center">
	       <td bgcolor="#C0C0C0">졸업일</td>
		      <td colspan="5">
		       <select name=" graduate_year" onchange="checkBirthDay();">
		           <option value="">
		           <script>
		               for(var i = 1990; i <= new Date().getFullYear() + 1; i++) {
		                   document.write("<option value=" + i + ">" + i);
		               }
		           </script>
		       </select>년
	                    <select name="graduate_month">
	                            <option value="">
	                            <script>
	                                for(var i = 1; i <= 12; i++){
	                                    var m = (i < 10)? "0" + i : i;
	                                    document.write("<option value=" + m + ">" + m);
	                                }
	                            </script>
	                        </select>월 &nbsp;&nbsp; ~
		       <select name=" graduate_year" onchange="checkBirthDay();">
		           <option value="">
		           <script>
		               for(var i = 1990; i <= new Date().getFullYear() + 1; i++) {
		                   document.write("<option value=" + i + ">" + i);
		               }
		           </script>
		       </select>년
	                    <select name="graduate_month">
	                            <option value="">
	                            <script>
	                                for(var i = 1; i <= 12; i++){
	                                    var m = (i < 10)? "0" + i : i;
	                                    document.write("<option value=" + m + ">" + m);
	                                }
	                            </script>
	                        </select>월 
	          </tr>
	          
	</table><p></p>		
					
					<input type="button" value="  수정  ">&nbsp;&nbsp;&nbsp;
					<input type="button" value="  삭제  ">
	
	</form>




</body>
</html>