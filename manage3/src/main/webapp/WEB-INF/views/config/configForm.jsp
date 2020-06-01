<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/includePageTag.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 관리</title>
<jsp:include page="/WEB-INF/views/include/includeHead.jsp" />
<style>
	tbody{
		font-family: 'Nanum Gothic', sans-serif;
	}
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
	
	.has-error { color: red;}
	.form-control{
		width: 50%;}
	tbody>tr:last-of-type {
		margin-top: 20px;
	}
</style>
<script>
	$(function(){
		var idx = $("#idx").val();
		
		if(idx>0){
			$("#tableName").attr("readonly",true);
			$("#category").attr("readonly",true);
			$("#category option").not(":selected").remove();
			$("#submitBtn").val("게시판 수정");
		}
		
		
		$("#tableName").change(function(){
			var tableName = $("#tableName").val();
			$.ajax({
				url: "${pageContext.request.contextPath}/admin/nameCheck?tableName="+tableName,
				success: function(data){
					if(data == 1){
						//중복이 있거나 영어가 아님
						$("#nameCheck").css("display","inline").text("테이블 이름이 중복이거나 영어가 아닙니다.");
						$("#submitBtn").attr("disabled",true);
					}else{
						$("#nameCheck").css("display","none").text("");
						$("#submitBtn").attr("disabled", false);
					}
				}
			});
		});
	});

	function btnClick(){
		var hostIndex = location.href.indexOf(location.host)+ location.host.length;
		var contextPath = location.href.substring(hostIndex, location.href.indexOf('/', hostIndex + 1));
		location.href=contextPath+"/admin/configList";
	}
</script>
</head>
<body>
	
		<form:form id="form" action="configFormOk" method="POST" modelAttribute="configVO">
			<form:hidden path="idx" name="idx" />
			<h1>게시판 
				<c:set var="idx" value="${configVO.idx }"/>
				<c:if test="${idx gt 0}">수정</c:if>
				<c:if test="${idx le 0}">추가</c:if>
			</h1>
			<table class="table table-borderless">
				<tbody>
					<tr>
						<td><label for="tableName">게시판 영어 이름<span class="notNull">*</span>: </label></td>
						<td>
							<form:input type="text" path="tableName" id="tableName" name="tableName" 
							class="form-control" placeholder="TableName"/>
							<form:errors path="tableName" class=" has-error" />
							<span id="nameCheck" class="error" display ="none"></span>
						</td>
					</tr>
					<tr>
						<td><label for="subject">게시판 한글 이름<span class="notNull">*</span>: </label></td>
						<td>
							<form:input type="text" path="subject" id="subject" name="subject"
							class="form-control" placeholder="테이블명"/>
							<form:errors path="subject" class=" has-error" />
						</td>
					</tr>
					<tr>
						<td><label for="admin">게시판 관리자<span class="notNull">*</span>: </label></td>
						<td>
							<form:input type="text" path="admin" id="admin" name="admin" class="form-control" />
							<form:errors path="admin" class=" has-error" />
						</td>
					</tr>
					<tr>
						<td><label for="category">카테고리: </label></td>
						<td>
							<form:select path="category" id="category" name="category" class="form-control" >
								<form:options items="${category }"/>
							</form:select>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="anno">
							권한 설정: -1은 사용 안 함, 0은 모두 사용 가능, 1은 user이상만 사용 가능, 2는 admin만 사용 가능
						</td>
					</tr>
					<tr>
						<td><label for="list_level">목록 읽기 권한: </label></td>
						<td>
							<form:select path="list_level" id="list_level" name="list_level" class="form-control">
								<form:options items="${level }" />
							</form:select>
							<form:errors path="list_level" class=" has-error"/>
						</td>
					</tr>
					<tr>
						<td><label for="read_level">글 읽기 권한: </label></td>
						<td>
							<form:select path="read_level" id="read_level" name="read_level" class="form-control" >
								<form:options items="${level }" />
							</form:select>
							<form:errors path="read_level" class=" has-error"/>
						</td>
					</tr>
					<tr>
						<td><label for="writer_level">쓰기 권한: </label></td>
						<td>
							<form:select path="writer_level" id="writer_level" name="writer_level"  class="form-control">
								<form:options items="${level }"/>
							</form:select>
							<form:errors path="writer_level" class=" has-error"/>
						</td>
					</tr>
					<tr>
						<td><label for="comment_level">댓글 작성 권한: </label></td>
						<td>
							<form:select path="comment_level" id="comment_level" name="comment_level"   class="form-control">
								<form:options items="${level }"/>
							</form:select>
							<form:errors path="comment_level" class=" has-error"/>
						</td>
					</tr>
					<tr>
						<td><label for="use_secret">비밀 글 사용 여부: </label></td>
						<td>
							<form:radiobutton path="use_secret" value="Y" checked='checked'/>사용 | 
							<form:radiobutton path="use_secret" value="N"/>미사용
							<form:errors path="use_secret" class=" has-error"/>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="anno">
							지정 설정: 0은 사용 안 함, 1 = 1일/1열
						</td>
					</tr>
					<tr>
						<td><label for="newIcon">새글 날짜 지정: </label></td>
						<td>
							<form:input type="text" path="newIcon" id="newIcon" name="newIcon" class="form-control"/>
							<form:errors path="newIcon" class=" has-error"/>
						</td>
					</tr>
					<tr>
						<td><label for="hotIcon">추천글 개수 지정: </label></td>
						<td>
							<form:input type="text" path="hotIcon" id="hotIcon" name="hotIcon" class="form-control"/>
							<form:errors path="hotIcon" class=" has-error"/>
						</td>
					</tr>
					<tr>
						<td><label for="gallery_cols">갤러리 열 수: </label></td>
						<td>
							<form:input type="text" path="gallery_cols" id="gallery_cols" name="gallery_cols" class="form-control"/>
							<form:errors path="gallery_cols" class=" has-error"/>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="submit" id="submitBtn" class="btn btn-outline-dark" value="게시판 생성">
							<input type="button" class="btn btn-outline-dark" value="목록으로" onclick="btnClick()">
						</td>
					</tr>
				</tbody>
			</table>
			
		</form:form>
</body>
</html>