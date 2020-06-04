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
	function deleteBtn(idx) {
		var result = confirm("정말로 삭제하시겠습니까?");
		if(result){
			var contextPath = '${pageContext.request.contextPath}/admin/configDelete';
			post_to_url(contextPath, {'idx' : idx});
		}
	}
	function updateSelect(idx, mainSelect){
		$.ajax({
			url: '${pageContext.request.contextPath}/admin/updateSelect?idx='+idx+'&mainSelect='+mainSelect,
			type: 'POST',
			success: function(data){
				var img = $("#imgPlace"+idx);
				if(data == 1){
					img.attr("src", "${pageContext.request.contextPath }/resources/image/check.png");
					img.attr("alt", "Check");
					img.attr("onclick", "updateSelect("+idx+", 0 )");
				}else{
					img.attr("src", "${pageContext.request.contextPath }/resources/image/checkbox.png");
					img.attr("alt", "nonCheck");
					img.attr("onclick", "updateSelect("+idx+", 1 )");
				}
			}
		});
	}
</script>
</head>
<body>
	<button class="btn btn-outline-dark" style="text-align: right; margin-top: 20px;"
		onclick="location.href='${pageContext.request.contextPath}/admin/configForm'">게시판 추가</button>
	<!-- <button class="btn btn-outline-dark" style="text-align: right; margin-top: 20px;"
		onclick="location.href='${pageContext.request.contextPath}/admin/bannerList'">배너 관리</button> -->
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
								<c:if test="${vo.mainSelect eq 1 }">
									<img id="imgPlace${vo.idx }" src="${pageContext.request.contextPath }/resources/image/check.png" alt="Check" width=30px;
										onclick="updateSelect(${vo.idx}, 0 )"/>
								</c:if>
								<c:if test="${vo.mainSelect eq 0 }">
									<img id="imgPlace${vo.idx }" src="${pageContext.request.contextPath }/resources/image/checkbox.png" alt="nonCheck" width=30px;
										onclick="updateSelect(${vo.idx}, 1)"/>
								</c:if>
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
		</div>
</body>
</html>