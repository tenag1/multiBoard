<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/includePageTag.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>index page</title>
<jsp:include page="/WEB-INF/views/include/includeHead.jsp" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/login.css" />
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<style>
	input {padding-left: 5px;}
	
	.datepicker{
		z-index: 2;
	}
	#ui-datepicker-div{
		background-color: #fff; 
	}
	#ui-datepicker-div{
		padding: 10px;
		text-align: center;}
	.ui-datepicker-month{
		margin-right: 15px;	
	}
	.ui-datepicker-prev{
		margin-right:130px;
	}
	.ui-datepicker-prev,.ui-datepicker-next{
		background-color: skyblue;
		border-radius: 5px;
		cursor: pointer;
	}
	.ui-datepicker-header,.ui-datepicker-calendar{
		width: 200px; 
	}
</style>
<script>
$(function(){
   $('.datepicker').datepicker	({
		dateFormat: 'yy-mm-dd',
		changeYear:true,
		changeMonth:true,
		yearRange: '1900:2020',
		monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		dayNamesMin:['일','월','화','수','목','금','토']
	});
	$( "#datepicker" ).datepicker();
	$("#birth").focus(function(){
		$("#ui-datepicker-div").css("border", "1px solid black");
	});
	$("#birth").focusout(function(){	
		$("#ui-datepicker-div").css("border", "none");			
	});
	
	$("#idEnter").click(function(){
		var email = $("#email").val();
		var birth = $("#birth").val();
		if(email.trim() == "" || birth.trim() == ""){
			alert("이메일과 생일을 입력해주세요.");
		}else{
			$.ajax({
				url: '${pageContext.request.contextPath}/user/idForgot?email='+email+'&birth='+birth,
				type:'GET',
				success: function(data){
					if(data != null && data != ""){
						alert("당신의 아이디는 "+data+"입니다.");
					}else{
						alert("일치하는 회원 정보가 없습니다.");						
					}
				},
				error:function(request,status,error){
					console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
			});
		}
	});
	$("#pwdEnter").click(function(){
		var id = $("#id").val();
		var email = $("#pEmail").val();
		if(id.trim() == "" || email.trim() == ""){
			alert("아이디와 이메일을 입력해주세요.");
		}else{
			$.ajax({
				url: '${pageContext.request.contextPath}/user/pwdForgot?id='+id+'&email='+email,
				type:'GET',
				success: function(data){
					//data가 1이면 비밀번호 임시 발급 0이면 해당 없음
					if(data == 1){
						alert("이메일로 임시 비밀번호를 발급해드렸습니다.");
					}else{
						alert("일치하는 회원 정보가 없습니다.");
					}
				},
				error:function(request,status,error){
			        console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				}
			});
		}
	});
});
</script>
</head>
<body>
	<div class="wrapper">
		<div id="formContent">
			<h1 class="hidden">회원 정보 찾기</h1>
			<h2>Find your ID / Password</h2>
			
			<div class="findDiv">
				<h3>아이디 찾기</h3>
				<p class="anno">회원가입시 입력하신 이메일과 생일로 아이디를 찾으실 수 있습니다.</p>
				<table>
					<tbody>
						<tr>	
							<td width="50%"><label for="email">email</label></td>
							<td><input type="email" id="email" placeholder="abc@aaa.aaa"></td>
						</tr>
						<tr>
							<td width="50%"><label for="birth">birth</label></td>
							<td><input type="text" id="birth" class="datepicker" autocomplete="off" placeholder="2020-01-01"></td>
						</tr>
						<tr>
							<td colspan="2"><input type="button" class="fourth" id="idEnter" value="아이디 찾기"/></td>
						</tr>
					</tbody>
				</table>
				<form>
		              
		               
		        </form>
			</div>
			<hr />
			<div class="findDiv">
				<h3>임시 비밀번호 받기</h3>
				<p class="anno">아이디와 이메일을 입력하시면 이메일로 임시 비밀번호를 보내드립니다.</p>
				<table>
					<tbody>
						<tr>
							<td width="50%"><label for="id">id</label></td>
							<td><input type="text" id="id" ><br/></td>
						</tr>
						<tr>
							<td width="50%"><label for="pEmail">email</label></td>
							<td><input type="email" id="pEmail" placeholder="abc@aaa.aaa"><br/></td>
						</tr>
						<tr>
							<td colspan="2"><input type="button" class="btn btn-info" id="pwdEnter" value="비밀번호 찾기"/></td>
						</tr>
					</tbody>
				</table>
				<form>
		        </form>
			</div>
		</div>
	</div>
</body>
</html>