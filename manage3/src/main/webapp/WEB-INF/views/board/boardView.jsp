<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/includePageTag.jsp" %>
<!doctype html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<title>Document</title>
<style>
	table{text-align: left; margin-bottom: 20px;}
	h1{text-align: left; margin: 20px 0 20px 0;}
	.subject{font-size: 20px; font-weight: bold; padding: 15px 15px 0px 20px;}
	.commDiv{margin: 10px 10px 20px 10px;}
	.date{font-size:12px; color:gray; padding-left: 20px;}
	.content{padding: 10px 10px 10px 30px;}
	textarea {
		vertical-align: top;
	}
	.commImg{
		text-align: right;
		vertical-align: -3px;
		width: 16px;
		cursor: pointer;
	}
	#submitBtn, #cancle{ vertical-align: 10px;}
</style>
<script>
	$(function(){
		$("#deleteBtn").click(function(){
		    if(confirm("정말 삭제하시겠습니까?") == true){
		        alert("삭제되었습니다.");
		        location.href="${pageContext.request.contextPath }/board/delete?p=${commVO.currentPage }&s=${commVO.pageSize}&idx=${boardVO.idx }&no=${boardVO.cf_idx }&writer=${vo.id}"
		    }
		    else{
		        return ;
		    }
		});
	});
	var password = "${boardVO.password}";
	var writer = "${boardVO.writer}";
	if(password != "" && (writer != "${vo.id}" || writer != "admin")){
		var pwdChk = prompt("게시글 비밀번호를 입력해주세요.");
		if(pwdChk != password){
			history.back();
		}
	}
	function commentUpdate(idx){
		$("#submitBtn").val("댓글 수정");
		$("#cancle").css("display","inline");
		$("#commt").attr("action", '${pageContext.request.contextPath}/board/commentForm');
		$("#commentContent").text($("#content"+idx).text());
		$("#idxVal").val(idx);
	}
	function commentDelete(idx){
	 	if(confirm("정말 삭제하시겠습니까?") == true){
	 		$("#commt").attr("action", '${pageContext.request.contextPath}/board/commentDelete');
	 		$("#idxVal").val(idx);
	 		$("#commentContent").text($("#content"+idx).text());
	 		$("#commt").submit();
	 	}else{
	        return ;
	    }
	}
	function resetBtn(){
		$("#submitBtn").val("댓글 작성");
		$("#cancle").css("display","none");
		$("#commt").attr("action", '${pageContext.request.contextPath}/board/commentForm');
		$("#commentContent").text("");
		$("#idxVal").val(-1);
	}
	function subChk(){
		if($("#commentContent").val() == "" || $("#commentContent").val().trim().length < 1){
			alert("댓글의 내용은 최소 1글자 이상이어야 합니다.");
			return false;
		}
	}
</script>
</head>
<body>
<sec:csrfMetaTags />
<!-- 목록가기, 이전 글, 다음 글 가기 
	제목, 글, 작성일, 수정일, 조회수 / 댓글-->
	<div id="tableDiv">
		<h1>게시글 보기</h1>
	<table class="table">
		<thead class="thead-dark">
			<tr>
				<th class="subject" colspan="2">${boardVO.subject }</th>
			</tr>
		</thead>
		<tbody>
			<sec:authorize access="${read_level }">
				<tr>
					<td class="date" colspan="2">
						<c:out value="${boardVO.writer_nick }"/>
						작성일: <fmt:formatDate value="${boardVO.createDate }" pattern="yyyy_MM_dd HH:mm"/> 
						수정일: <fmt:formatDate value="${boardVO.updateDate }" pattern="yyyy_MM_dd HH:mm"/>
						조회수: ${boardVO.hit }
					</td>
				</tr>
					<c:if test="${not empty boardVO.fileList }">
						<tr>
							<td>첨부파일: </td>
							<td>
								<c:forEach var="fvo" items="${boardVO.fileList }">
									 <c:url var="url" value="/board/download">
										<c:param name="of" value="${fvo.origName }"/>
										<c:param name="sf" value="${fvo.saveName }"/>
									</c:url>
									<a href="${url}" title="${fvo.origName }">${fvo.origName}</a><br>
								</c:forEach>	
							</td>
						</tr>
					</c:if>
				<tr>
					<td class="content" colspan="2">${boardVO.content }</td>
				</tr>
				<tr>
					<td colspan="2">
						<c:if test="${boardVO.writer eq vo.id }">
							<input type="button" value="수정하기" class="btn btn-outline-dark btn-sm"
				     		   onclick='post_to_url("${pageContext.request.contextPath }/board/form",{"idx":${boardVO.idx },"m":0, "p":${commVO.currentPage },"s":${commVO.pageSize},"no":${boardVO.cf_idx },"${_csrf.parameterName }":"${_csrf.token }"})'>
							<button id="deleteBtn" class="btn btn-outline-primary btn-sm">삭제하기</button>
						</c:if>
						<c:if test="${boardVO.writer ne vo.id }">
							<sec:authorize access="hasRole('ADMIN')">
								<button id="deleteBtn" class="btn btn-outline-primary btn-sm">삭제하기</button>
							</sec:authorize>
						</c:if>
						<button class="btn btn-outline-dark btn-sm"
							 onclick='location.href="${pageContext.request.contextPath }/board/List?p=${commVO.currentPage }&s=${commVO.pageSize}&idx=${boardVO.idx }&no=${boardVO.cf_idx }"'
							>목록으로</button>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<c:if test="${empty boardVO.commentList }">
							<p>등록된 댓글이 없습니다.</p>
						</c:if>
						<c:if test="${not empty boardVO.commentList }">
							<p class="anno">
							전체 ${fn:length(boardVO.commentList) }개의 댓글이 있습니다.
							</p>
						</c:if>
					</td>
				</tr>
				<sec:authorize access="${comment_level }">
					<tr>
						<td colspan="2">
							<%-- 댓글 폼 --%>
							<form:form id="commt" action="${pageContext.request.contextPath}/board/commentForm" method="POST" onsubmit="return subChk()">
								<input type="hidden" id="idxVal" name="idx" value="-1">
								<input type="hidden" name="ref" value="${boardVO.idx}">
								<input type="hidden" name="mb_id" value="${vo.id}">
								<input type="hidden" name="mb_nick" value="${vo.nick}">
								<input type="hidden" name="p" value="${commVO.currentPage }">
								<input type="hidden" name="s" value="${commVO.pageSize }">
								<input type="hidden" name="b" value="${commVO.blockSize }">
								<input type="hidden" name="m" value="0">
								<input type="hidden" name="no" value="${commVO.no }">
								<textarea rows="4" cols="80" name="content" id="commentContent"></textarea>
								<input type="submit" value="댓글저장" id="submitBtn" class="btn btn-sm btn-outline-dark">
								<input type="button" id="cancle" value="취소" style="display: none;"  class="btn btn-sm btn-outline-dark"  onclick="resetBtn()"/>
							</form:form>
						</td>
					</tr>
					<tr>
						<td colspan="2">
						<c:if test="${not empty boardVO.commentList }">
							<c:forEach var="comment" items="${boardVO.commentList }">
								<div class="commDiv">
									<hr />
									<p>
										<strong>
											<span id="name${comment.idx }">${comment.mb_nick}(${comment.mb_id })</span>
										</strong>
										<span class="anno">
											작성일: <fmt:formatDate value="${comment.createDate }" pattern="yyyy-MM-dd HH:mm"/>
											<c:if test="${not empty comment.updateDate }">
												수정일: <fmt:formatDate value="${comment.updateDate }" pattern="yyyy-MM-dd HH:mm"/>									
											</c:if>
										</span>
										<c:if test="${comment.mb_id eq vo.id }">
											<img class="commImg" src="${pageContext.request.contextPath }/resources/image/edit.png" alt="수정이미지" onclick="commentUpdate('${comment.idx }')" />
											<img class="commImg" src="${pageContext.request.contextPath }/resources/image/delete.png" alt="삭제이미지" onclick="commentDelete('${comment.idx }')"/>
										</c:if>
										<c:if test="${comment.mb_id ne vo.id }">
											<sec:authorize access="hasRole('ADMIN')">
											<img class="commImg" src="${pageContext.request.contextPath }/resources/image/edit.png" alt="수정이미지" onclick="commentUpdate('${comment.idx }')" />
											<img class="commImg" src="${pageContext.request.contextPath }/resources/image/delete.png" alt="삭제이미지" onclick="commentDelete('${comment.idx }')" />
											</sec:authorize>	
										</c:if>
									</p>
									<div id="content${comment.idx }" style="display:none;">${comment.content }</div>
									<div>
										<c:set var="content2" value="${comment.content }"/>
										<c:set var="content2" value="${fn:replace(content2,'<','&lt') }"/>
										<c:set var="content2" value="${fn:replace(content2, newLine,br) }"/>
										${content2 }	
									</div>
								</div>
							</c:forEach>
						</c:if>
						</td>
					</tr>
				</sec:authorize>
			</sec:authorize>
		</tbody>
	</table>
	</div>
</body>
</html>