<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/includePageTag.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>index page</title>
<jsp:include page="/WEB-INF/views/include/includeHead.jsp"/>
<style>
	h1, h3{margin-top: 20px; text-align: left;}
	.anno {text-align: right;}
	.trs {cursor: pointer;}
	.trs:hover {
		background: rgb(233,236,239);
	}
	#news{
		margin: 0 auto;
		width:800px;
	}
	.tables{
		position: relative;
	}
	table{
		width: 100%;
		border: 1px solid rgb(52,58,63);
	}
	.plusImg{
		position: absolute;
		top: 10px; right: 10px;
		cursor: pointer;
		width: 25px;
	}
	.boards{ cursor: pointer;}
</style>
<script>
</script>
</head>
<body>
	<div>
		<h1>메인 페이지</h1>
		<c:if test="${not empty newBoard }">
			<h2>최신 글</h2>
			<section id="news">
				<c:forEach var="i" items="${newBoard }">
					<c:set var="key" value="${fn:split(i.key,'@#')}"></c:set>
					<div class="tables">
						<table class="table">
							<h3>${key[0]}</h3>
							<thead class="thead-dark">
								<tr>
									<th width=70%>제목</th>
									<th>작성일</th>
								</tr>
							</thead>
							<tbody>
								
								<c:if test="${empty i.value}">
									<tr>
										<td colspan="2">최신 글이 없습니다.</td>
									</tr>
								</c:if>
								<c:forEach var="list" items="${i.value }">	
									<tr class="boards" 
										onclick="location.href='${pageContext.request.contextPath }/board/view?idx=${list.idx }&no=${list.cf_idx }&m=1'">
										<td>
											<c:out value="${list.subject}"/>
											<c:if test="${not empty list.password }">
												<img src="${pageContext.request.contextPath }/resources/image/lock.png" alt="비밀글" style="height: 13px; "/>
											</c:if>
										</td>
										<td><fmt:formatDate value="${list.createDate}" pattern="yy-MM-dd"/></td>
									</tr>
								</c:forEach>
							</tbody>
							<img class="plusImg" src="${pageContext.request.contextPath }/resources/image/plus.png" alt="${key[0] } 글 더보기" title="${key[0] } 글 더보기" 
								onclick="location.href='${pageContext.request.contextPath }/board/List?no=${key[1]}'"/>
						</table>
					</div>
				</c:forEach>
			</section>
		</c:if>
	</div>
</body>
</html>