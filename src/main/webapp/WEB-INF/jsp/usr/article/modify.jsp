<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 수정</title>
<link rel="stylesheet" href="/resource/common.css" />
<script src="/resource/common.js" defer="defer"></script>
</head>
<body>
	<div>
		<a href="../home/main">메인페이지로 이동</a>
	</div>

	<h1>${article.id }번 게시물 수정
	</h1>
	<form method="post" action="doModify">
		<input value="${param.id }" type="hidden" name="id" />
		<div>
			번호 :
			${article.id }</div>
		<div>
			날짜 :
			${article.regDate.substring(0,10) }</div>
		<div>
			제목 : <input value="${article.title }" type="text" name="title"
				placeholder="제목을 입력해주세요" />
		</div>
		<div>
			내용 :
			<textarea type="text" name="body" placeholder="내용을 입력해주세요">${article.body }</textarea>
		</div>
		<button type="submit">수정</button>
	</form>
	<div>
		<a style="color: green" href="list">리스트로 돌아가기</a>
	</div>

<%@ include file="../common/foot.jspf"%>
