<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE DETAIL" />
<%@ include file="../common/head.jspf"%>

<!-- <iframe src="http://localhost:8081/usr/article/doIncreaseHitCountRd?id=2" frameborder="0"></iframe> -->
<script>
	const params = {}
	params.id = parseInt('${param.id}');
</script>

<script>
	function ArticleDetail__increaseHitCount() {
		const localStorageKey = 'article__' + params.id + '__alreadyView';
		if (localStorage.getItem(localStorageKey)) {
			return;
		}
		localStorage.setItem(localStorageKey, true);
		$.get('../article/doIncreaseHitCountRd', {
			id : params.id,
			ajaxMode : 'Y'
		}, function(data) {
			$('.article-detail__hit-count').empty().html(data.data1);
		}, 'json');
	}
	
	function ArticleDetail__increaseReaction() {
		$.get('../reaction/doIncreaseGoodReaction', {
			id : params.id,
			ajaxMode : 'Y'
		}, function(data) {
			$('.article-detail__reaction-point').empty().html(data.data1);
		}, 'json');
	}
	
	$(function() {
		// 실전코드
		// 		ArticleDetail__increaseHitCount();
		// 연습코드
		setTimeout(ArticleDetail__increaseHitCount, 2000);
		setTimeout(ArticleDetail__increaseReaction, 2000);
	})
	
	$(function() {
	  $('#increment-button').click(function() {
			$.get('../reaction/doIncreaseGoodReactionRd?id=${article.id }', {
				id : params.id,
				ajaxMode : 'Y'
			}, function(data) {
				$('.article-detail__reaction-point').empty().html(data.data1);
			}, 'json');
    });
  });
});
</script>

<table>
	<tr>
		<th>번호</th>
		<td>
		<div class="badge badge-lg text-xl">${article.id }</div>
		</td>
	</tr>
	<tr>
		<th>작성날짜</th>
		<td>${article.regDate }</td>
	</tr>
	<tr>
		<th>수정날짜</th>
		<td>${article.updateDate }</td>
	</tr>
	<tr>
		<th>작성자</th>
		<td>${article.extra__writer }</td>
	</tr>
	<tr>
		<th>조회수</th>
		<td>
		<span class="article-detail__hit-count">${article.hitCount }</span>
		</td>
	</tr>
	<tr>
		<th>좋아요</th>
		<td>
		<span class="article-detail__reaction-point">${article.extra__goodReactionPoint }</span>
		</td>
	</tr>
	<tr>
		<th>싫어요</th>
		<td>${article.extra__badReactionPoint }</td>
	</tr>
	<tr>
		<th>추천 총합</th>
		<td>${article.extra__sumReactionPoint }</td>
	</tr>
	<tr>
		<th>제목</th>
		<td>${article.title }</td>
	</tr>
	<tr>
		<th>내용</th>
		<td>${article.body }</td>
	</tr>

	
	
</table>
<div class="button">
	<button class="btn btn-active btn-ghost text-xl" type="button" onclick="history.back();">뒤로가기</button>
	<c:if test="${article.memberId eq loginedMemberId}">
		<a class="btn btn-active btn-ghost text-xl" href="../article/modify?id=${article.id }">수정</a>
	</c:if>
	<c:if test="${article.memberId eq loginedMemberId}">
		<a class="btn btn-active btn-ghost text-xl" onclick="if(confirm('정말 삭제하시겠습니까?')==false) return false;"
			href="../article/doDelete?id=${article.id }">삭제</a>
	</c:if>
</div>
<div class="button">
<button class="btn btn-active btn-ghost text-xl" id="increment-button">좋아요</button>
	<a class="btn btn-active btn-ghost text-xl" href="../reaction/doIncreaseGoodReaction?id=${article.id }">좋아요</a>
	<a class="btn btn-active btn-ghost text-xl" href="../reaction/doIncreaseBadReaction?id=${article.id }">싫어요</a>
</div>


<%@ include file="../common/foot.jspf"%>