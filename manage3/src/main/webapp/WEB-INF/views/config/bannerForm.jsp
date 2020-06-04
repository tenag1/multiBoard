<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/includePageTag.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 관리</title>
<jsp:include page="/WEB-INF/views/include/includeHead.jsp" />
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
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
	/*datepicker*/
	.datepicker{
		display: block;
	    width: :100%;
	    height: calc(1.5em + .75rem + 2px);
	    padding: .375rem .75rem;
	    font-size: 1rem;
	    font-weight: 400;
	    line-height: 1.5;
	    color: #495057;
	    background-color: #fff;
	    background-clip: padding-box;
	    border: 1px solid #ced4da;
	    border-radius: .25rem;
	    transition: border-color .15s ease-in-out,box-shadow .15s ease-in-out;
    }
	#ui-datepicker-div{
		background-color: #fff; 
	}
	#ui-datepicker-div{
		padding: 10px;
		text-align: center;}
	.ui-datepicker-month{
		margin-right: 15px;	
	}
	.ui-datepicker-prev{
		margin-right:130px;
	}
	.ui-datepicker-prev,.ui-datepicker-next{
		background-color: skyblue;
		border-radius: 5px;
		cursor: pointer;
	}
	.ui-datepicker-header,.ui-datepicker-calendar{
		width: 200px; 
	}
</style>
<script>
	$(function(){

		
		$('.datepicker').datepicker	({
			dateFormat: 'yy-mm-dd',
			timeFormat: 'hh:mm',
			changeYear:true,
			changeMonth:true,
			yearRange: '1900:2020',
			monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			dayNamesMin:['일','월','화','수','목','금','토']
		});
		$( "#datepicker" ).datepicker();
	});

</script>
</head>
<body>
	
		<form:form id="form" action="bannerFormOk" method="POST" modelAttribute="bannerVO">
			<form:hidden path="idx" name="idx" />
			<h1>배너 
				<c:set var="idx" value="${bannerVO.idx }"/>
				<c:if test="${idx gt 0}">수정</c:if>
				<c:if test="${idx le 0}">추가</c:if>
			</h1>
			<table class="table table-borderless">
				<tbody>
					<tr>
						<td><label for="startDate">배너 시작일<span class="notNull">*</span>: </label></td>
						<td>
							<form:input type="text" path="startDate" id="startDate" name="startDate" 
							class="datepicker"/>
							<form:errors path="startDate" class=" has-error" />
						</td>
					</tr>
					<tr>
						<td><label for="endDate">배너 종료일<span class="notNull">*</span>: </label></td>
						<td>
							<form:input type="text" path="endDate" id="endDate" name="endDate"
							class="datepicker"/>
							<form:errors path="endDate" class=" has-error" />
						</td>
					</tr>
					<tr>
						<td><label for="title">설명(이미지에 마우스를 올리면 나오는 설명)<span class="notNull">*</span>: </label></td>
						<td>
							<form:input type="text" path="title" id="title" name="title" class="form-control" />
							<form:errors path="title" class=" has-error" />
						</td>
					</tr>
					<tr>
						<td><label for="url">이미지 클릭시 이동할 주소<span class="notNull">*</span></label></td>
						<td>
							<form:input type="text" path="url" id="url" name="url" class="form-control" />
							<form:errors path="url" class=" has-error" />
						</td>
					</tr>
					<tr>
						<td><label for="image">이미지<span class="notNull">*</span></label></td>
						<td>
							<input type="file" name="file">
							<c:if test="${bannerVO.image }">
								<img src="${pageContext.request.contextPath }/${bannerVO.image }" alt="이미지썸네일" />
							</c:if>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="submit" id="submitBtn" value="입력완료" class="btn btn-outline-dark" />
							<input type="button" value="돌아가기" class="btn btn-outline-dark" onclick="location.href='${pageContext.request.contextPath}/admin/bannerList'"/>
						</td>
					</tr>
				</tbody>
			</table>
			
		</form:form>
</body>
</html>