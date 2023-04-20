<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE MODIFY" />
<%@ include file="../common/head.jspf"%>

<form method="post" action="doWrite">
	<table>
		<tr>
			<th>제목</th>
			<td><input value="${article.title }" type="text" name="title"
				placeholder="제목을 입력해주세요" /></td>
		</tr>
		<tr>
			<th>내용</th>
			<td><textarea name="body" placeholder="내용을 입력해주세요">
			${article.body }</textarea></td>
		</tr>
	</table>
	<button type="submit">작성</button>
</form>
<button type="button" onclick="history.back();">뒤로가기</button>

<%@ include file="../common/foot.jspf"%>
