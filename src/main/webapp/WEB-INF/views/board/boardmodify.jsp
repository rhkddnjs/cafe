<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정하기</title>
<style type="text/css">
	div.grid {
		display: grid;
		grid-template-columns: 80px 1fr;
		grid-template-rows: 28px 28px 320px 1fr;
		row-gap: 10px;
	}
	div.grid > div.btn-group {
		display: grid;
		grid-column: 1 / 3;
	}
	div.grid div.right-align {
		text-align: right;
	}
	div.grid div.right-align input {
		background-color: white;
		border: 1px solid black;
		padding: 5px;
		cursor: pointer;
		font-size: 16px;
	}
	div.grid div.right-align a {
		border: 1px solid black;
		padding: 5px;
		cursor: pointer;
		text-decoration: none;
		color: black;
	}
	label {
		padding-left: 10px;
	}
	button, input, textarea {
		padding: 10px
	}
	input[type=file] {
		padding: 0px
	}
	div.errors {
		background-color: #ff00004a;
		opacity: 0.8;
		padding: 10px;
		color: #333;
	}
	div.errors:last-child {
		margin-bottom: 15px;
	}
</style>
</head>
<body>

	<jsp:include page="../member/membermenu.jsp"></jsp:include>
	
	<h1>게시글 수정</h1>
	<form:form modelAttribute="boardVO" method="post" 
			   action="/board/modify"
			   enctype="multipart/form-data">
		<input type="hidden" name="id" value="${boardVO.id}" />
		
		<div>
			<form:errors path="subject" element="div" cssClass="errors" />
			<form:errors path="content" element="div" cssClass="errors" />
		</div>
		
		<div class="grid">
			<label for="subject">제목</label>
			<input id="subject" type="text" name="subject"
				   value="${boardVO.subject}"/>
			<label for="file">첨부파일</label>
			<div>
				<input type="file" id="file" name="file" />
				현재 업로드된 파일: ${boardVO.originFileName}
			</div>
			<label for="content">내용</label>
			<textarea id="content" name="content" 
					  style="height: 300px">${boardVO.content}</textarea>
			<div class="btn-group">
				<div class="right-align">
					<input type="submit" value="수정" />
					<a href="/board/list">목록</a>
				</div>
			</div>
		</div>
	</form:form>
</body>
</html>