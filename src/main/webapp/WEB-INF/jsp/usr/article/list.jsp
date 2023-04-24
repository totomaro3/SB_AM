<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE ${board.code} LIST" />
<%@ include file="../common/head.jspf" %>
		
	<table style="border-collapse: collapse; border-color: green" border="2px">
		<tr>
			<th>번호</th>
			<th>작성날짜</th>
			<th>제목</th>
			<th>작성자</th>
			<th>수정</th>
			<th>삭제</th>
		</tr>
	<c:forEach var="article" items="${articles }">
		<tr style="text-align: center;">
			<td><div class="badge badge-lg text-xl">${article.id }</div></td>
			<td>${article.regDate.substring(0,10) }</td>
			<td><a href="detail?id=${article.id }">${article.title }</a></td>
			<td>${article.extra__writer }</td>
			<td><a href="modify?id=${article.id }">
			<c:if test="${article.memberId eq loginedMemberId}">수정</c:if>
			</a></td>
			<td><a onclick="if(confirm('정말 삭제하시겠습니까?')==false) return false;" href="doDelete?id=${article.id }">
			<c:if test="${article.memberId eq loginedMemberId}">삭제</c:if>
			</a></td>
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