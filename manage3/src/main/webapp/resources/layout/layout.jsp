<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/includePageTag.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/reset.css" />
<jsp:include page="/WEB-INF/views/include/includeHead.jsp" />
<sitemesh:write property='head' />
<style>
	tbody{
		font-family: 'Nanum Gothic', sans-serif;
	}
	/*main*/
	.mainBody{
		text-align: center; width: 70%; margin: 0 auto;
		min-width: 700px; min-height: 850px; 
	}
	/*nav*/
	ul#grpCat>li{
		position: relative;
	}
	ul#seqCat{
		display: none;
		z-index: 10;
		position: absolute;
		top: 2.5rem;
		width: 150px;
		background-color: rgba(52,58,64,0.9);
	}
	#seqCat>li{
		height: 50px;
		/*background-color: rgba(57,172,231,0.93);*/
	}
	#seqCat>li>a{
		display: block;
	}
	
	/* 이벤트 */
	ul#grpCat>li:hover>a{
		color: #39ace7;
	}
	ul#grpCat>li:hover>ul{
		display: block;
	}
	ul#grpCat>li>ul>li:hover>a{
		color: #ddd;
	}
</style>
</head>
<body>
<!-- Navigation -->
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark static-top">
    <div class="container">
      <a class="navbar-brand" href="${pageContext.request.contextPath }/">
      	 <img src="${pageContext.request.contextPath }/resources/image/home.png" alt="인덱스로 이동" title="index로 이동"/>
	  </a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarResponsive">
        <ul class="navbar-nav ml-auto" id="grpCat">
          <c:if test="${not empty grpList }">
	         <c:forEach var="grpCat" items="${grpList }">
        		<!-- 대항목이라면 -->
        		<c:if test="${grpCat.grp eq commVO.no }">
        			<li class="nav-item active">
        		</c:if>
        		<c:if test="${grpCat.grp ne commVO.no }">
        			<li class="nav-item">
        		</c:if>
    	    		<a class="nav-link" href="${pageContext.request.contextPath }/board/List?no=${grpCat.ref}">${grpCat.content }</a>
				    <ul id="seqCat">
    	    			<!-- 대항목이 아니라면 -->
	    	    		<c:forEach var="seqCat" items="${seqList }">
		    	    		<c:if test="${seqCat.grp eq grpCat.grp }">
		        				<li class="nav-item">
		        					<a class="nav-link" href="${pageContext.request.contextPath }/board/List?no=${seqCat.ref}">${seqCat.content }</a>
		        					<c:if test="${seqCat.lvl>1 }">
		        						<ul>
		        							<li>
		        								<c:forEach begin="1" end="${seqCat.lvl }">
		        									&nbsp;&nbsp;
		        								</c:forEach>
		        							</li>
		        						</ul>
		        					</c:if>
		        				</li>
		        			</c:if>
	    	    		</c:forEach>
				   </ul>
        		</li>
        	</c:forEach>
	      </c:if>
        </ul>
		    <div id="userMenu">
				<sec:authorize access="isAnonymous()">
					<button class="btn btn-outline-secondary" onclick="location.href='${pageContext.request.contextPath}/user/form'">회원가입</button>
					<button class="btn btn-outline-secondary" onclick="location.href='${pageContext.request.contextPath}/user/login'">로그인</button>
				</sec:authorize>
			
				<sec:authorize access="hasRole('USER')">
					<button class="btn btn-outline-secondary" onclick="post_to_url('${pageContext.request.contextPath }/logout')">로그아웃</button>
					<button class="btn btn-outline-secondary" onclick='location.href="${pageContext.request.contextPath}/user/info"'>내 정보</button>
				</sec:authorize>
				<sec:authorize access="hasRole('ADMIN')">
					<button class="btn btn-outline-secondary" onclick="location.href='${pageContext.request.contextPath}/admin'">관리 페이지</button>
					<button class="btn btn-outline-secondary" onclick="location.href='${pageContext.request.contextPath}/admin/userList'">회원 관리 페이지</button>
				</sec:authorize>
			</div>
	    </div>
    </div>
  </nav>
  
	<div class='mainBody'>
		<sitemesh:write property='body' />
	</div>
    <script src="${pageContext.request.contextPath }/resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	
</body>
</html>