<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/includePageTag.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!doctype html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<title>리스트</title>
<jsp:include page="/WEB-INF/views/include/includeHead.jsp"/>
<style>

	#deleteCheck {
		display: none;
		position: absolute;
		text-align: center;
		padding: 30px;
		left: 40%;
		top: 30%;
		border: 1px solid black;
		background-color: #ffffff;
	}
	.anno {text-align: right;}
	
	table>tbody>tr:last-of-type {
		border-top: 2px solid rgb(66,75,82);
	}
	td{
		font-family: 'Nanum Gothic', sans-serif;
	}
</style>
<script>
	$(function() {

	});
	// 제목을 누르면 가져갈 no를 위한 idx
	var idx = -1;
	
	function deleteBtn(idx) {
		$("#deleteCheck").css("display", "block").css("z-index",100);
		this.idx = idx;
	}
	function cancleBtn() {
		$("#deleteCheck").css("display", "none");
		this.idx = -1;
	}
	
	// 제목을 누르면 가져갈 no를 위한 idx
	function realDelete() {
		//url
		var hostIndex = location.href.indexOf(location.host)+ location.host.length;
		var contextPath = location.href.substring(hostIndex, location.href.indexOf('/', hostIndex + 1));
		contextPath += '/admin/configDelete';
		post_to_url(contextPath, {
			'idx' : idx,
			'${_csrf.parameterName}':'${_csrf.token}'
		});
	}
</script>
</head>
<body>
	<button class="btn btn-outline-dark" style="text-align: right; margin-top: 20px;"
		onclick="location.href='${pageContext.request.contextPath}/admin/configForm'">게시판 추가</button>
	<div class="table-responsive">
		<table class="table">
			<p class="anno">${ pagingVO.pageInfo}</p>
			<thead class="thead-dark">
				<tr>
					<th>번호</th>
					<th>게시판 이름</th>
					<th>생성 날짜</th>
					<th>관리자</th>
					<th>관리</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${pagingVO.totalCount le 0}">
					<tr>
						<td colspan="5">게시판이 없습니다.</td>
					</tr>
				</c:if>
				<c:if test="${pagingVO.totalCount gt 0}">
					<c:forEach var="vo" items="${pagingVO.list }" varStatus="vs">
						<tr class="trs" onclick="location.href='${pageContext.request.contextPath}/board/List?no=${vo.idx }'">
							<td>${vo.idx }</td>
							<td>${vo.subject }</td>
							<td><fmt:formatDate value="${vo.createDate }" pattern="yy-MM-dd" /></td>
							<td>${vo.admin }</td>
							<td onclick="event.cancelBubble=true">
								<button type="button" class="btn btn-outline-success"
									onclick="location.href='${pageContext.request.contextPath}/admin/configForm?no=${vo.idx }'">E</button>
								<button type="button" onclick="deleteBtn(${vo.idx})"
									class="btn btn-outline-dark">D</button>
							</td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="5" style="border: none;">
							${pagingVO.pageListPost }
						</td>
					</tr>
				</c:if>
				</tbody>
			</table>
	
	<div id="deleteCheck">
		<h3>정말로 삭제하시겠습니까?</h3>
		<p>
			<button type="button" class="btn btn-outline-success"
				onclick="cancleBtn()">취소</button>
		<button type="button" class="btn btn-outline-dark"
			onclick="realDelete()">삭제</button>
			</p>
		</div>
	</div>
</body>
</html>