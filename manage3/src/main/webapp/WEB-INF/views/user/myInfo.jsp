<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/includePageTag.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>my page</title>
<jsp:include page="/WEB-INF/views/include/includeHead.jsp"/>
<style>
th {
	font-weight: bold;
	font-size: 15px;
}
	h1{
		text-align: left;
		margin: 20px 0;
	}
	.table{
		width: 600px; margin: 0 auto 20px auto;
	}
	.table td, .table th{
		padding: 0.5rem;
	}
</style>
<script>
	$(function(){
		$("#leaveBtn").click(function(){
			var userIdx = '${vo.idx}';
			var check = confirm("정말 탈퇴하시겠습니까?\n탈퇴 후 7일간 정보가 유지됩니다.(24시 기준)"
					+"\n7일 내로 재접속하시면 탈퇴가 취소됩니다.");
			if(check){
				//탈퇴한다면
				location.href='${pageContext.request.contextPath}/user/leave?idx='+userIdx;
			}
		});
	});
</script>
</head>
<body>
 <h1>내 정보 확인 페이지</h1>
 <hr>
 <table class="table">
 	<tbody>
 		<tr>
 			<th class="table-secondary">아이디:</th>
 			<td>${vo.id }</td>
 		</tr>
 		<tr>
 			<th class="table-secondary">이름:</th>
 			<td>${vo.name }</td>
 		</tr>
 		<tr>
 			<th class="table-secondary">닉네임:</th>
 			<td>${vo.nick }</td>
 		</tr>
 		<tr>
 			<th class="table-secondary">이메일:</th>
 			<td>${vo.email }</td>
 		</tr>
 		<tr>
 			<th class="table-secondary">성별:</th>
 			<td>
 				<c:if test="${vo.sex eq 0}">선택 안 함</c:if>
 				<c:if test="${vo.sex eq 1}">여성</c:if>
 				<c:if test="${vo.sex eq 2}">남성</c:if>
 			</td>
 		</tr>
 		<tr>
 			<th class="table-secondary">생일:</th>
 			<td>
 				<fmt:formatDate value="${vo.birth }" pattern="yyyy-MM-dd"/>
 			</td>
 		</tr>
 		<tr>
 			<th class="table-secondary">핸드폰 번호:</th>
 			<td>${vo.hp }</td>
 		</tr>
 		<tr>
 			<th class="table-secondary">인증:</th>
 			<td>${vo.certify}</td>
 		</tr>
 		<tr>
 			<th class="table-secondary">주소:</th>
 			<td>
 				(${vo.zipCode})${vo.addr } ${vo.addr2 }
 			</td>
 		</tr>
 		<tr>
 			<th class="table-secondary">가입일:</th>
 			<td>
 				<fmt:formatDate value="${vo.joinDate }" pattern="yyyy-MM-dd HH:mm"/>
 			</td>
 		</tr>
 		<tr>
 			<th class="table-secondary">메일 수신:</th>
 			<td>${vo.mailing}</td>
 		</tr>
 		<tr>
 			<th class="table-secondary">sms 수신:</th>
 			<td>${vo.sms}</td>
 		</tr>
 	</tbody>
 </table>
 
		<button onclick="location.href='${pageContext.request.contextPath}/user/form?id=${vo.id }'" class="btn btn-outline-dark">회원 정보 수정</button>
		<button id="leaveBtn" class="btn btn-outline-dark">회원 탈퇴</button>
		<button onclick="location.href='${pageContext.request.contextPath}/'" class="btn btn-outline-dark">돌아가기</button>
</body>
</html>