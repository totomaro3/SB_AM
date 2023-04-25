<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE ${board.code} LIST" />
<%@ include file="../common/head.jspf" %>
	
	<div>${articlesCount } 개</div>
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
	<div class="btn-group">
		<% 
		%>
  		<button class="btn">1</button>
		<button class="btn btn-active">2</button>
		<button class="btn">3</button>
		<button class="btn">4</button>
	</div>
	
	<div class="page">
		<c:if test="${page > 1 + 3}"><a href="list?boardId=${boardId }&page=1">1</a> ... </c:if>
		<%
		int pageSize = 3;
		int from = (int)request.getAttribute("page") - pageSize;
		if (from < 1) {
			from = 1;
		}
		int end = (int)request.getAttribute("page") + pageSize;
		if (end > (int)request.getAttribute("pagesCount")) {
			end =(int)request.getAttribute("pagesCount");
		}
		for (int i = from; i <= end; i++) {
		%>
		<a class="<%=(int)request.getAttribute("page") == i ? "red" : ""%>" href="list?boardId=${boardId }&page=<%=i%>"><%=i%></a>
		<%
		}
		%>
		<c:if test="${page < pagesCount - 3}">... <a href="list?boardId=${boardId }&page=${pagesCount }">${pagesCount }</a></c:if>
	</div>
	
	<style type="text/css">
.page>a {
	color: black;
}
.page>a.red {
	color: red;
}
</style>
<%@ include file="../common/foot.jspf"%>