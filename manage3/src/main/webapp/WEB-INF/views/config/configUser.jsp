<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/includePageTag.jsp" %>
<!doctype html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<title>리스트</title>
<jsp:include page="/WEB-INF/views/include/includeHead.jsp"/>
<style>
	.anno {text-align: right;}
	.lastTrs {
		border-bottom: 2px solid rgb(66,75,82);
	}
	th, td{
		font-family: 'Nanum Gothic', sans-serif;
	}
	.table td, .table th{
		padding: 0.5rem;
	}
	.trs{
		cursor: auto;
	}
	.trs:hover {
		background: none;
	}
	
</style>
<script>
	$(function() {

	});
</script>
</head>
<body>
	<div>
		<table class="table">
			<thead class="thead-dark">
				<tr style="border: none; text-align: left; ">
					<td class="anno" colspan="8" style="border: none; padding-left: 10px;"> 회원 총 ${userCount }명</td>
				</tr>
				<tr>
					<th>id</th>
					<th>이름</th>
					<th>닉네임</th>
					<th>email</th>
					<th>성별</th>
					<th>생일</th>
					<th>핸드폰</th>
					<th>인증여부</th>
				</tr>
				<tr class="bg-info">
					<th>우편번호</th>
					<th>주소</th>
					<th>상세주소</th>
					<th>가입일</th>
					<th>메일</th>
					<th>sms</th>
					<th>탈퇴일</th>
					<th>권한</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${pagingVO.totalCount le 0}">
					<tr>
						<td>회원이 없습니다.</td>
					</tr>
				</c:if>
				<c:if test="${pagingVO.totalCount gt 0}">
					<c:forEach var="pVO" items="${pagingVO.list}">
						<tr class="trs">
							<td>${pVO.id }</td>
							<td>${pVO.name }</td>
							<td>${pVO.nick }</td>
							<td>${pVO.email }</td>
							<td>
								<c:if test="${pVO.sex eq 0}">선택 안 함</c:if>
				 				<c:if test="${pVO.sex eq 1}">여성</c:if>
				 				<c:if test="${pVO.sex eq 2}">남성</c:if>
							</td>
							<td><fmt:formatDate value="${pVO.birth }" pattern="yyyy-MM-dd"/></td>
							<td>${pVO.hp }</td>
							<td>${pVO.certify }</td>
						</tr>
						<tr class="lastTrs">
							<td>${pVO.zipCode }</td>
							<td>${pVO.addr }</td>
							<td>${pVO.addr2 }</td>
							<td><fmt:formatDate value="${pVO.joinDate }" pattern="yyyy-MM-dd HH:mm"/></td>
							<td>${pVO.mailing }</td>
							<td>${pVO.sms }</td>
							<td><fmt:formatDate value="${pVO.leaveDate }" pattern="yyyy-MM-dd HH:mm"/></td>
							<td>${pVO.memberLoleVO.role }</td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="19" style="border: none;">
							${pagingVO.pageListPost }
						</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
</body>
</html>