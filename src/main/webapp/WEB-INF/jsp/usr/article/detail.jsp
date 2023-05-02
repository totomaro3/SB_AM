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
	})
	
	$(function() {
	  $('#increment-button').click(function() {
		  $.ajax({
		      url: '/../reaction/doIncreaseGoodReactionRd',  // 함수를 실행할 엔드포인트 URL
		      type: 'POST',
		      dataType: 'json',
		      success: function(data) {
		        $('.article-detail__reaction-point').text(data.data1);  // 응답 데이터의 result 속성을 페이지에 적용
		      },
		      error: function() {
		        alert('서버와 통신하는 중에 오류가 발생하였습니다.');
		      }
		    });
		  });
		});
</script>

<script type="text/javascript">
	let ReplyWrite__submitFormDone = false;
	function ReplyWrite__submitForm(form) {
		if (ReplyWrite__submitFormDone) {
			return;
		}
		form.body.value = form.body.value.trim();
		if (form.body.value.length < 3) {
			alert('3글자 이상 입력하세요');
			form.body.focus();
			return;
		}
		ReplyWrite__submitFormDone = true;
		form.submit();
	}
</script>

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
		<th>추천</th>
		<td>
			<span>&nbsp;좋아요 : ${article.goodReactionPoint }&nbsp;</span>
			<span>&nbsp;싫어요 : ${article.badReactionPoint }&nbsp;</span>
			<div>
				<span>
					<span>&nbsp;</span>
					<a href="/usr/reactionPoint/doGoodReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri}"
						class="btn btn-xs ${actorHasGoodReaction ? "deepSkyBlue" : ""}">좋아요 👍</a>
				</span>
				<span>
					<span>&nbsp;</span>
					<a href="/usr/reactionPoint/doBadReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri}"
						class="btn btn-xs ${actorHasBadReaction ? "deepSkyBlue" : ""}">싫어요 👎</a>
				</span>
			</div>
		</td>
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

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="table-box-type-1">
			<c:if test="${rq.isLogined() }">
				<form action="../reply/doWrite" method="POST" onsubmit="ReplyWrite__submitForm(this); return false;">
					<input type="hidden" name="relTypeCode" value="article" />
					<input type="hidden" name="relId" value="${article.id }" />
					<table>
						<colgroup>
							<col width="200" />
						</colgroup>

						<tbody>
							<tr>
								<th>댓글</th>
								<td>
									<textarea class="input input-bordered w-full max-w-xs" type="text" name="body" placeholder="내용을 입력해주세요" /></textarea>
								</td>
							</tr>
							<tr>
								<th></th>
								<td>
									<button type="submit" value="작성" />
									댓글 작성
									</button>
								</td>
							</tr>
						</tbody>

					</table>
				</form>
			</c:if>
			<c:if test="${!rq.isLogined() }">
				<a class="btn-text-link btn btn-active btn-ghost" href="/usr/member/login">로그인</a> 후 이용해줘
			</c:if>
		</div>
	</div>
</section>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<c:forEach var="reply" items="${replies }">
			<table class = "my-2">
			<tr>
				<td>작성날짜 : ${reply.regDate }</td>
				<td>수정날짜 : ${reply.updateDate }</td>
				<td>작성자 : ${reply.extra__writer }</td>
				<td>
					<span>&nbsp;좋아요 : ${reply.goodReactionPoint }&nbsp;</span>
					<span>&nbsp;싫어요 : ${reply.badReactionPoint }&nbsp;</span>
					<div>
						<span>
						<span>&nbsp;</span>
						<a href="/usr/reactionPoint/doGoodReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri}"
							class="btn btn-xs ">좋아요 👍</a>
						</span>
						<span>
						<span>&nbsp;</span>
						<a href="/usr/reactionPoint/doBadReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri}"
							class="btn btn-xs ">싫어요 👎</a>
						</span>
					</div>
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>${reply.body }</td>
			</tr>
				</table>
		</c:forEach>
	</div>
</section>





<%@ include file="../common/foot.jspf"%>