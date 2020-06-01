<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/includePageTag.jsp" %>
<!doctype html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<title>접근 거부</title>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"/>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
<style>
	h1{
		margin: 20px 0 40px 0;
	}
</style>
<script>
	$(function(){
		
	});
</script>
</head>
<body>
	<h1>접근 불가 페이지입니다.</h1>
	<button onclick="location.href='${pageContext.request.contextPath}/'" class="btn btn-outline-dark">돌아가기</button>
</body>
</html>