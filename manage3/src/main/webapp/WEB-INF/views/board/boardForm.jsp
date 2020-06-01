<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/includePageTag.jsp" %>

<!doctype html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<title>Document</title>
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.16/dist/summernote.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.16/dist/summernote.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/summernote-ko-KR.js"></script>
<style>
	h1{margin: 20px 0 20px 0; text-align: left;}
	#sub{margin-bottom: 10px;}
	table{ text-align: left; margin-bottom: 5rem;}
	tr>td:nth-of-type(1){width: 10%;}
	tr>td:nth-of-type(2){width: 50%;}
	tr>td:nth-of-type(3){width: 40%;}
	tr{
		margin: 5px;
	}
</style>
<script>
	$(function(){
		if(fileCount > 0 && fileCount <10){
			$("#file1").attr('id','file'+fileCount+1);
		}
		
		if($("#idx").val()>0 && $("#idx").val() != "" ){
			$("#submitBtn").val("게시글 수정");
		}
		$('#summernote').summernote({
			lang: 'ko-KR',
			height: 400,
			focus: true,
			placeholder: 'write here', //내용에 설명 적기
			spellCheck: true, //맞춤법 검사
			callbacks: {
				onImageUpload: function(files){
					sendFile(files[0], this);
				}
			}
		});
		function sendFile(file, editor) {
			var data = new FormData();
			  data.append("file", file);
			  $.ajax({ 
			      data : data,
			      type : "POST",
			      url : "${pageContext.request.contextPath}/board/summernoteUpload",
			      enctype: 'multipart/form-data',
			      contentType : false,
			      processData : false,
			      success : function(data) { 
			    	  alert("${pageContext.request.contextPath}/"+data.url);
			     	  $(editor).summernote('insertImage', "${pageContext.request.contextPath}/"+data.url);
			      }
			  });
	 	}
	});
	//editor.insertImage(welEditable,data.image);
	
	// 파일 관련 
	var fileCount = ${boardVO.fileCount}; // 최소
	if(fileCount == 0) fileCount = 1;
	var maxCount = 10; // 최대 첨부 파일 개수
	function plusClick(){
		if(fileCount==maxCount){
			alert('최대 ' + fileCount + '개까지만 첨부 가능 합니다.');
			return;
		}
		fileCount++;
		var tag = $("<div id='file"+fileCount+"'><input type='file' name='files'></div>");
		$("#fileList").append(tag);
	}
	
	function minusClick(){
		if(fileCount==1){
			return;
		}
		$("#file" + fileCount).remove();
		fileCount--;
	}
	
	function  deleteToggle(idx){
		if($("#fvo"+idx).css('display')=='none'){
			$("#fvo"+idx).css('display','inline');	
			var files = $("#deleteFiles").val();
			$("#deleteFiles").val(files + ' ' + idx);
		}else{
			$("#file" + fileCount).remove();
			$("#fvo"+idx).css('display','none');
			var files = $("#deleteFiles").val().split(' ');
			var str = "";
			for(var i in files){
				if(files[i]==idx) continue;
				str += files[i] + ' ';
			} 
			$("#deleteFiles").val(str.trim());
		}
	}
</script>
</head>
<body>
	<h1>게시글 
		<c:set var="idx" value="${boardVO.idx }"/>
		<c:if test="${not empty idx && idx gt 0}">수정</c:if>
		<c:if test="${empty idx || idx le 0}">추가</c:if>
	</h1>
	<form:form action="${pageContext.request.contextPath }/board/FormOk" method="POST" modelAttribute="boardVO" enctype="multipart/form-data">
		<form:hidden path="idx" name="idx"/>
		<form:hidden path="cf_idx" name="cf_idx" value="${commVO.no }"/>
		<input type="hidden" name="no" value="${commVO.no }"/>
		<form:hidden path="writer" id="writer" name="writer" value="${vo.id }" readonly="true"/>
		<form:hidden path="writer_nick" id="writer_nick" name="writer_nick" value="${vo.nick }" readonly="true"/>
		<table>
			<tbody>
				<tr id="sub">
					<td><label for="subject">제목: </label></td>
					<td>
						<form:input type="text" path="subject" id="subject" name="subject" class="form-control"/>
					</td>
					<td><div><form:errors path="subject" class="error" /></div></td>
				</tr>
				<tr>
					<td colspan="3"><form:textarea path="content" id="summernote" name="content"></form:textarea></td>
				</tr>
				<tr>
					<td colspan="3"><span><form:errors path="content" class="error" /></span></td>
				</tr>
				<tr>
					<td colspan="3"  id="fileList">
						<%-- 첨부파일 개수만큼 디스크 이미지를 출력 --%>
						<c:if test="${not empty boardVO.fileList }">
							<c:forEach var="fvo" items="${boardVO.fileList }">
								<div>
									${fvo.origName }
									<a href="#fileList" onclick="deleteToggle(${fvo.idx})"><img src="${pageContext.request.contextPath }/resources/image/delete.png" style="width: 15px; vertical-align: -1px; "/></a>
									<span id="fvo${fvo.idx }" style="display: none; color: red;">삭제</span>
								</div>
							</c:forEach>
						</c:if>
						<input type="hidden" name="deleteFiles" id="deleteFiles">
						<input type="button" onclick="plusClick()" value=" + " class="btn btn-primary btn-sm">
						<!-- 수정 폼이라면 -버튼 없음 -->
						<c:if test="${boardVO.idx <= 0 }">
							<input type="button" onclick="minusClick()" value=" - " class="btn btn-primary btn-sm">
						</c:if>
						<c:if test="${boardVO.fileCount < 10 }">
							<div id="file1"><input type="file" name="files"></div>
						</c:if>
					</td>
				</tr>
				<c:if test="${fn:contains(use_secret, 'Y')}">
					<tr>
						<td><label for="password">비밀번호: </label></td>
						<td>
							<form:input type="password" path="password" id="password" name="password" class="form-control"	/>
						</td>
						<td><span class="anno">비밀번호 설정 시 비밀글이 됩니다.</span></td>
					</tr>
				</c:if>
				<tr>
					<td colspan="3">
						<input type="submit" id="submitBtn" value="게시글 생성" class="btn btn-outline-dark">
						<c:if test="${not empty idx && idx gt 0}">
							<input type="button" value="돌아가기" class="btn btn-outline-dark"
								onclick="location.href='${pageContext.request.contextPath }/board/view?no=${commVO.no }&idx=${boardVO.idx }&p=${commVO.p }&s=${commVO.s }'">
						</c:if>
						<c:if test="${empty idx || idx le 0}">
							<input type="button" value="돌아가기" class="btn btn-outline-dark" 
								onclick="location.href='${pageContext.request.contextPath }/board/List?no=${commVO.no }&p=${commVO.p }&s=${commVO.s }'">
						</c:if>
					</td>
				</tr>
			</tbody>
		</table>
	</form:form>
</body>
</html>