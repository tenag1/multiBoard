<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/includePageTag.jsp" %>
<!doctype html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<title>user form</title>
<jsp:include page="/WEB-INF/views/include/includeHead.jsp"/>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<style>
	h1{
		text-align: left;
		margin: 20px 0;
	}
	
	.table{
		width: 800px; margin: auto;
	}
	.table td, .table th{
		padding: 0.5rem;
	}
	tr>td:nth-of-type(1){width: 15%;}
	tr>td:nth-of-type(2){width: 35%;}
	tr>td:nth-of-type(3){width: 50%; text-align: left;}
	.notInput{
		text-align: left;
	}
	/*datepicker*/
	.datepicker{
		display: block;
	    width: :100%;
	    height: calc(1.5em + .75rem + 2px);
	    padding: .375rem .75rem;
	    font-size: 1rem;
	    font-weight: 400;
	    line-height: 1.5;
	    color: #495057;
	    background-color: #fff;
	    background-clip: padding-box;
	    border: 1px solid #ced4da;
	    border-radius: .25rem;
	    transition: border-color .15s ease-in-out,box-shadow .15s ease-in-out;
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
		var idx = $("#idx").val();
		if(idx>0){
			$("#id").attr("readonly",true);
			$("#submitBtn").val("정보 수정");
		}
		
		$('#addr').attr("readonly",'readonly');
		
		$('.datepicker').datepicker	({
			dateFormat: 'yy-mm-dd',
			changeYear:true,
			changeMonth:true,
			yearRange: '1900:2020',
			monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			dayNamesMin:['일','월','화','수','목','금','토']
		});
		$( "#datepicker" ).datepicker();

		$("#hp").keyup(function(){
			var str = $("#hp").val();
			str = str.replace(/[^0-9]/g, '');
		      var tmp = '';
		      if( str.length < 4){
		    	  document.getElementById("hp").value = str;
		      }else if(str.length < 7){
		          tmp += str.substr(0, 3);
		          tmp += '-';
		          tmp += str.substr(3);
		          document.getElementById("hp").value = tmp;
		      }else if(str.length < 11){
		          tmp += str.substr(0, 3);
		          tmp += '-';
		          tmp += str.substr(3, 3);
		          tmp += '-';
		          tmp += str.substr(6);
		          document.getElementById("hp").value = tmp;
		      }else{              
		          tmp += str.substr(0, 3);
		          tmp += '-';
		          tmp += str.substr(3, 4);
		          tmp += '-';
		          tmp += str.substr(7);
		          document.getElementById("hp").value = tmp;
		      }
		});
		
		$("#id").change(function(){
			var id = $("#id").val();
			//id의 길이가 4이상, idx가 0 이하(신규가입) 일 때
			if(id.length>3 && idx<=0){
				$.ajax({
					url: '${pageContext.request.contextPath}/user/idCheck?id='+id,
					type:'get',
					success: function(data){
						//"1은 중복 0은 중복이 아닙니다."
						if(data==1){
							//중복이라면
							$("#id_check").text("사용중인 아이디입니다.");
							$("#submitBtn").attr("disabled",true);
						}else{
							//중복이 아니라면
							$("#id_check").text("");
							$("#submitBtn").attr("disabled",false);
							
						}
					}
				});
			}
		});
		$("#email").change(function(){
			var email = $("#email").val();
			//email의 길이가 4이상, idx가 0 이하(신규가입) 일 때
			if(email.length>3 && idx <= 0){
				$.ajax({
					url: '${pageContext.request.contextPath}/user/emailCheck?email='+email,
					type:'get',
					success: function(data){
						//"1은 중복 0은 중복이 아닙니다."
						if(data==1){
							//중복이라면
							$("#email_check").text("사용중인 이메일입니다.");
							$("#submitBtn").attr("disabled",true);
						}else{
							//중복이 아니라면
							$("#email_check").text("");
							$("#submitBtn").attr("disabled",false);
							
						}
					}
				});
			}
		});
		$('#zipCode').click(function(){
			 new daum.Postcode({
		        oncomplete: function(data) {
		        	// 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
	                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
	                var roadAddr = data.roadAddress; // 도로명 주소 변수

	                // 우편번호와 주소 정보를 해당 필드에 넣는다.
	                document.getElementById('zipCode').value = data.zonecode;
	                document.getElementById("addr").value = roadAddr;
	         
	                var guideTextBox = document.getElementById("guide");
	                // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
	                if(data.autoRoadAddress) {
	                    var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
	                    guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
	                    guideTextBox.style.display = 'block';

	                }else {
	                    guideTextBox.innerHTML = '';
	                    guideTextBox.style.display = 'none';
	                }
		        }
		    }).open();
		});
	});
</script>
</head>
<body>
	<h1>
		<c:if test="${memberVO.idx gt 0}">회원 정보 수정 페이지</c:if>
		<c:if test="${memberVO.idx le 0}">회원 가입 페이지</c:if>
	</h1>
	<hr />
	<form:form id="formOk" action="${pageContext.request.contextPath }/user/formOk" method="POST" modelAttribute="memberVO">
	<form:hidden path="idx" id="idx"/>
		<table class="table table-borderless">
			<tbody>
				<tr>
					<td><span class="notNull">*</span><label for="id">아이디: </label></td>
					<td><form:input type="text" path="id" id="id" name="id" class="form-control"/></td>
					<td><span id="id_check"></span><form:errors path="id" class="error"/></td>
				</tr>
				<tr>
					<td><span class="notNull">*</span><label for="password">비밀번호: </label></td>
					<td><form:input type="password" path="password" id="password" name="password" class="form-control"/></td>
					<td><form:errors path="password" class="error" /></td>
				</tr>
				<tr>
					<td><span class="notNull">*</span><label for="name">이름: </label></td>
					<td><form:input type="text" path="name" id="name" name="name" class="form-control"/></td>
					<td><form:errors path="name" class="error" /></td>
				</tr>
				<tr>
					<td><label for="nick">닉네임: </label></td>
					<td><form:input type="text" path="nick" id="nick" name="nick" class="form-control"/></td>
					<td><form:errors path="nick" class="error" /></td>
				</tr>
				<tr>
					<td><span class="notNull">*</span><label for="email">이메일: </label></td>
					<td><form:input type="text" path="email" id="email" name="email" class="form-control"/></td>
					<td><span id="email_check"></span><form:errors path="email" class="error" /></td>
				</tr>
				<tr>
					<td><label for="sex">성별: </label></td>
					<td class="notInput">
						<form:radiobutton path="sex" value="1" />여성 | 
						<form:radiobutton path="sex" value="2" />남성 |
						<c:if test="${memberVO.sex ne 0}">
							<form:radiobutton path="sex" value="0" />선택 안 함</c:if>
						<c:if test="${memberVO.sex eq 0}">
							<form:radiobutton path="sex" value="0" checked='checked' />선택 안 함</c:if>
					</td>
					<td><form:errors path="sex" class="error" /></td>
				</tr>
				<tr>
					<td><span class="notNull">*</span><label for="birth">생일: </label></td>
					<td><form:input type="text" path="birth" class="datepicker" name="birth"  autocomplete="off"/></td>
					<td><form:errors path="birth" class="error" /></td>
				</tr>
				<tr>
					<td><label for="hp">핸드폰 번호: </label></td>
					<td><form:input type="text" path="hp" id="hp" name="hp" class="form-control"/></td>
					<td><form:errors path="hp" class="error" /></td>
				</tr>
				<tr>
					<td><label for="zipCode">우편 번호: </label></td>
					<td><form:input type="text" path="zipCode" id="zipCode" name="zipCode" readonly="readonly" class="form-control"/></td>
					<td><form:errors path="zipCode" class="error" /></td>
				</tr>
				<tr>
					<td><label for="addr">주소: </label></td>
					<td><form:input type="text" path="addr" id="addr" name="addr" class="form-control"/></td>
					<td><form:errors path="addr" class="error" /><span id="guide" style="color:#999;display:none"></span></td>
				</tr>
				<tr>
					<td><label for="addr2">상세 주소: </label></td>
					<td><form:input type="text" path="addr2" id="addr2" name="addr2" class="form-control"/></td>
					<td><form:errors path="addr2" class="error" /></td>
				</tr>
				<tr>
					<td><label for="mailing">메일 수신: </label></td>
					<td class="notInput">
						<form:radiobutton path="mailing" value="Y" />사용 |
						<form:radiobutton path="mailing" value="N" />미사용
					</td>
					<td><form:errors path="mailing" class="error" /></td>
				</tr>
				<tr>
					<td><label for="sms">sms 수신: </label></td>
					<td class="notInput">
						<form:radiobutton path="sms" value="Y"/>사용 | 
						<form:radiobutton path="sms" value="N" />미사용
					</td>
					<td></td>
				</tr>
				<tr>
					<td colspan="3">
						<input type="submit" id="submitBtn" value="회원가입" class="btn btn-outline-dark" />
						<input type="button" value="돌아가기" class="btn btn-outline-dark" onclick="location.href='${pageContext.request.contextPath}/'"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form:form>
</body>
</html>