<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/includePageTag.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login form</title>
<jsp:include page="/WEB-INF/views/include/includeHead.jsp"/>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/login.css" />
<style>
	.wrapper {
		padding: 60px 30px;
	}
</style>
<script>
function loginChk(){
	var id = $("#id").val();
	var pwd = $("#pwd").val();
	$.ajax({
		url:'${pageContext.request.contextPath}/user/loginCheck?id='+id+'&password='+pwd,
		success: function(data){
			if(data == false){
				alert("회원 정보를 확인해주세요.");
				return false;
			}
		}
	});
}
</script>
</head>
<body>
<div class="wrapper fadeInDown">
	<div id="formContent">
	  <!-- Tabs Titles -->
	  <h1 class="hidden">로그인 입력 페이지</h1>
	  <!-- Icon -->
	  <div class="fadeIn first">
	    <img src="${pageContext.request.contextPath }/resources/image/user.png" id="icon" alt="User Icon" />
	  </div>
	  <!-- Login Form -->
	  <form action="${pageContext.request.contextPath }/user/loginOk" method="post" onsubmit="return loginChk();">
			<input type="text" name="id" id="id" class="fadeIn second" placeholder="id">
			<br><input type="password" name="password" id="pwd" class="fadeIn third" placeholder="password" autocomplete>
			<br> 
			<input type="submit" class="fadeIn fourth" value="로그인"/>
	  </form>
	  <!-- Remind Passowrd -->
	  	<div id="formFooter">
	   		 <a class="underlineHover" href="${pageContext.request.contextPath}/findInfo">forgot your info?</a>
		</div>
	</div>
</div>
</body>
</html>