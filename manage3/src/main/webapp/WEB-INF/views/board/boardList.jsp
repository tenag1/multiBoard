<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/includePageTag.jsp" %>
<!doctype html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<title>Document</title>
<jsp:include page="/WEB-INF/views/include/includeHead.jsp"/>
<style>
	h1{margin-top: 20px; text-align: left;}
	.anno {text-align: right;}
	.trs {cursor: pointer;}
	.trs:hover {
		background: rgb(233,236,239);
	}
	table>tbody>tr:last-of-type {
		border-top: 2px solid rgb(66,75,82);
	}
	h1, td{
		font-family: 'Nanum Gothic', sans-serif;
	}
</style>
<script>
	$(function(){
		var text = $("#listLvl").text().trim();
		if(text == null || text == ""){
			alert("목록 읽기 권한이 없습니다.\n이전 페이지로 이동합니다.");
			window.history.back();
		}
	});
</script>
</head>
<body>
	
	<h1>${configVO.subject }</h1>
	<h2 class="hidden">${configVO.subject } 게시글 목록</h2>
	<div id="listLvl">
		<sec:authorize access="${list_level }">
			<sec:authorize access="${writer_level }" >
				<button id="insertBtn" class="btn btn-outline-dark"
				onclick="location.href='${pageContext.request.contextPath}/board/form?no=${commVO.no }'">게시글 추가</button>
			</sec:authorize>
				<p class="anno">${ pagingVO.pageInfo}</p>
				<table class="table">
					<thead class="thead-dark">
						<tr>
							<th>번호</th>
							<th width="50%">제목</th>
							<th>작성자</th>
							<th>만든 날짜</th>
							<th>조회수</th>
							<th>추천</th>
						</tr>
					</thead>
					<tbody>
				<c:if test="${pagingVO.totalCount le 0}">
					<tr>
						<th colspan="6" style="border-bottom: 2px solid rgb(66,75,82)">게시글이 없습니다.</th>
					</tr>
				</c:if>
				<c:if test="${pagingVO.totalCount gt 0}">
					<c:set var="no" value="${pagingVO.totalCount -(pagingVO.currentPage-1)*pagingVO.pageSize }"/>
					<c:forEach var="pVO" items="${pagingVO.list }" varStatus="vs">
					<tr class="trs" onclick='location.href="${pageContext.request.contextPath }/board/view?idx=${pVO.idx }&p=${commVO.p }&s=${commVO.s }&no=${commVO.no }&m=1"'>
						<td>${no-vs.index }</td>
						<td>
							<fmt:formatDate var="createDate" value="${pVO.createDate }" pattern="yyyyMMdd"/>
							<c:if test="${newIcon ne 0}">
								<c:if test="${createDate ge newIconDate}"><img src="${pageContext.request.contextPath }/resources/image/new.png" alt="새 글" style="height:13px;"/></c:if>
							</c:if>
							<c:if test="${hotIcon ne 0 }">
								<c:if test="${pVO.hit ge hotIcon}"><img src="${pageContext.request.contextPath }/resources/image/hot.png" alt="인기글" style="height:13px;"/></c:if>
							</c:if>
							<c:out value="${pVO.subject }"/>
							<c:if test="${pVO.commentCount gt 0 }">(${pVO.commentCount})</c:if>
							<c:if test="${not empty pVO.fileList }">
									<c:forEach var="fvo" items="${pVO.fileList }">
										<img src="${pageContext.request.contextPath }/resources/image/save.png" title="${fvo.origName }" style="height:16px; vertical-align: -2px;"/>
									</c:forEach>
							</c:if>
							<c:if test="${not empty pVO.password }">
								<img src="${pageContext.request.contextPath }/resources/image/lock.png" alt="비밀글" style="height: 13px; "/>
							</c:if>
						</td>
						<td>${pVO.writer_nick}</td>
						<td><fmt:formatDate value="${pVO.createDate }"  pattern="yy-MM-dd"/> </td>
						<td>${pVO.hit }</td>
					</tr>
					</c:forEach>
					
					<tr>
						<td colspan="5" >
							${pagingVO.pageListPost }
						</td>
					</tr>
				</c:if>
				</tbody>
			</table>
		</sec:authorize>
	</div>
</body>
</html>