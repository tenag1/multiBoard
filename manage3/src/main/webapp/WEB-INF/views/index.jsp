<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>index page</title>
<script>
</script>
</head>
<body>
	<div>
		<h1>메인 페이지</h1>
		<c:if test="${not empty newBoard }">
			<c:forEach var="sub" items="${newBoard }">
				<p>${sub.subject }</p>
			</c:forEach>
		</c:if>
	</div>
</body>
</html>