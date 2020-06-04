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
	#configTh { vertical-align: middle; border-left: 2px solid #454d55;}
</style>
<script>
	$(function() {

	});
	function deleteBtn(idx) {
		var result = confirm("정말로 삭제하시겠습니까?");
		if(result){
			var contextPath = '${pageContext.request.contextPath}/admin/bannerDelete';
			post_to_url(contextPath, {'idx' : idx});
		}
	}
</script>
</head>
<body>
	<button class="btn btn-outline-dark" style="text-align: right; margin-top: 20px;"
		onclick="location.href='${pageContext.request.contextPath}/admin/bannerForm'">배너 추가</button>
	<div class="table-responsive">
		<table class="table">
			<p class="anno">${ pagingVO.pageInfo}</p>
			<thead class="thead-dark">
				<tr>
					<th>번호</th>
					<th>시작일</th>
					<th>종료일</th>
					<th>등록일</th>
					<th rowspan="2" id="configTh">관리</th>
				</tr>
				<tr>
					<th colspan="2">url</th>
					<th colspan="2">설명</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${pagingVO.totalCount le 0}">
					<tr>
						<td colspan="5">배너가 없습니다.</td>
					</tr>
				</c:if>
				<c:if test="${pagingVO.totalCount gt 0}">
					<c:forEach var="vo" items="${pagingVO.list }" varStatus="vs">
						<tr class="trs">
							<td>${vo.idx }</td>
							<td><fmt:formatDate value="${vo.startDate }" pattern="yy-MM-dd hh:mm"/></td>
							<td><fmt:formatDate value="${vo.endDate }" pattern="yy-MM-dd hh:mm"/></td>
							<td><fmt:formatDate value="${vo.uploadDate }" pattern="yy-MM-dd hh:mm" /></td>
							<td>
								<td onclick="event.cancelBubble=true">
								<button type="button" class="btn btn-outline-success"
									onclick="location.href='${pageContext.request.contextPath}/admin/bannerForm?idx=${vo.idx }'">E</button>
								<button type="button" onclick="deleteBtn(${vo.idx})"
									class="btn btn-outline-dark">D</button>
								</td>
							</td>
						</tr>
						<tr>
							<td colspan="2">${vo.url }</td>
							<td colspan="2">${vo.title }</td>
							<td><img src="${pageContext.request.contextPath }/${vo.image }" alt="배너썸네일" /></td>
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
	</div>
</body>
</html>