<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE DETAIL" />
<%@ include file="../common/head.jspf" %>
	<div>
		번호 :
		${article.id }</div>
	<div>
		날짜 :
		${article.regDate.substring(0,10) }</div>
	<div>
		제목 :
		${article.title }</div>
	<div>
		내용 :
		${article.body }</div>
	<div>
		내용 :
		${article.body }</div>
	<div>
		작성자 :
		${article.memberId }</div>

	<!--<div>		
	<a href="modify?id=">수정</a>
		<a href="doDelete?id=">삭제</a>
	</div> -->

	<div>
		<a style="color: green" href="list">리스트로 돌아가기</a>
	</div>
<%@ include file="../common/foot.jspf"%>