<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE LIST" />
<%@ include file="../common/head.jspf" %>
		
	<table style="border-collapse: collapse; border-color: green" border="2px">
		<tr>
			<th>번호</th>
			<th>작성날짜</th>
			<th>제목</th>
			<th>작성자</th>
			<!--<th>수정</th>
			<th>삭제</th>-->
		</tr>
	<c:forEach var="article" items="${articles }">
		<tr style="text-align: center;">
			<td>${article.id }</td>
			<td>${article.regDate.substring(0,10) }</td>
			<td><a href="detail?id=${article.id }">${article.title }</a></td>
			<td>${article.memberId }</td>
			<!--<td><a href="modify?id=">수정</a></td>
			<td><a href="doDelete?id=">삭제</a></td>-->
		</tr>
	</c:forEach>


	</table>

	<style type="text/css">
.page {
	background-color: gold;
}
.page>a {
	color: black;
}
.page>a.red {
	color: red;
}
</style>
<%@ include file="../common/foot.jspf"%>