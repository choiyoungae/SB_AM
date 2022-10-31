<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="pageTitle" value="ARTICLE DETAIL" />
<%@ include file="../common/head.jspf" %>

<script>
	const params = {};
	params.id = parseInt('${param.id}');
</script>

<script>
	function ArticleDetail__increaseHitCount() {
		const localStorageKey = 'article__' + params.id + '__alreadyView'
		
		if(localStorage.getItem(localStorageKey)) {
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
	
	$(function() {
		ArticleDetail__increaseHitCount();
	})
</script>

<script>
	// 댓글 관련
	let ReplyWrite__submitFormDone = false;
	function ReplyWrite__submitForm(form) {
		if(ReplyWrite__submitFormDone) {
			return;
		}
		
		form.body.value = form.body.value.trim();
		
		if(form.body.value.length < 2) {
			alert("2글자 이상 입력해주세요.");
			form.body.focus();
			return;
		}
		
		ReplyWrite__submitFormDone = true;
		form.submit();
	}
</script>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		
		<div class="table-box-type-1">
			<table>
				<colgroup>
					<col width="200" />
				</colgroup>
				
				<tbody>
					<tr>
						<th>번호</th>
						<td>${article.id }</td>
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
						<td>${article.extra__writerName }</td>
					</tr>
					<tr>
						<th>조회수</th>
						<td>
							<span class='article-detail__hit-count'>${article.hitCount }</span>
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
					<tr>
						<th>추천</th>
						<td>
							<span>${article.goodReactionPoint }</span>
							
							<c:if test="${actorCanMakeReaction }">
								<span>&nbsp;</span>
								<a href="/usr/reactionPoint/doGoodReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri }"
								 class="btn btn-outline btn-xs">좋아요 👍</a>
								<span>&nbsp;</span>
								<a href="/usr/reactionPoint/doBadReaction?relTypeCode=article&relId=${param.id }&replaceUri=${rq.encodedCurrentUri }"
								 class="btn btn-outline btn-xs">별로예요 👎</a>
							</c:if>
							
							<c:if test="${actorCanCancelGoodReaction}">
								<span>&nbsp;</span>
								<a href="/usr/reactionPoint/doCancelGoodReaction?relTypeCode=article&relId=${param.id}&replaceUri=${rq.encodedCurrentUri} "
								 class="btn btn-xs">좋아요 👍</a>
								<span>&nbsp;</span>
								<a onclick="alert(this.title); return false;" title="좋아요를 먼저 취소해주세요" href="#"
								 class="btn btn-outline btn-xs">별로예요👎</a>
							</c:if>
							
							<c:if test="${actorCanCancelBadReaction}">
								<span>&nbsp;</span>
								<a onclick="alert(this.title); return false;" title="싫어요를 먼저 취소해주세요" href="#"
								 class="btn btn-outline btn-xs">좋아요👍</a>
								<span>&nbsp;</span>
								<a href="/usr/reactionPoint/doCancelBadReaction?relTypeCode=article&relId=${param.id}&replaceUri=${rq.encodedCurrentUri}"
								 class="btn btn-xs">별로예요 👎</a>
							</c:if>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<div class="btns">
			<button class="btn-text-link btn btn-ghost" type="button" onclick="history.back();">뒤로가기</button>
			<c:if test="${article.extra__actorCanModify }">
				<a class="btn-text-link btn btn-ghost" href="../article/modify?id=${article.id }">수정</a>
			</c:if>
			<c:if test="${article.extra__actorCanDelete }">
				<a class="btn-text-link btn btn-ghost" onclick="if(confirm('정말 삭제하시겠습니까?') == false) return false;" href="../article/doDelete?id=${article.id }">삭제</a>
			</c:if>
		</div>
	
	</div>
	
</section>

<!-- 댓글 부분 -->
<section class="mt-5">
	<div class="container mx-auto px-3">
		<h2>댓글 작성</h2>
		<c:if test="${rq.logined }">
			<form class="table-box-type-1" method="POST" action="../reply/doWrite" onsubmit="ReplyWrite__submitForm(this); return false;">
				<input type="hidden" name="relTypeCode" value="article" />
				<input type="hidden" name="relId" value="${article.id }" />
				<table class="table w-full">
					<colgroup>
						<col width="200" />
					</colgroup>

					<tbody>
						<tr>
							<th>작성자</th>
							<td>${rq.loginedMember.nickname }</td>
						</tr>
						<tr>
							<th>내용</th>
							<td>
								<textarea class="textarea textarea-bordered w-full" type="text" name="body"
									placeholder="댓글을 입력해주세요" rows="5" /></textarea>
							</td>
						</tr>
						<tr>
							<th></th>
							<td>
								<button class="btn btn-active btn-ghost" type="submit">댓글작성</button>
							</td>
						</tr>
					</tbody>

				</table>
			</form>
		</c:if>
		<c:if test="${rq.notLogined }">
			<a class="btn-text-link btn  btn-ghost" href="/usr/member/login">로그인</a> 후 이용해주세요
		</c:if>
		
		<h2 class="mt-5">댓글 목록</h2>
		<div class="table-box-type-1">
			<table class="table w-full">
				<colgroup>
					<col width="200" />
				</colgroup>
		
				<tbody>
					<c:forEach var="reply" items="${replies }">
						<tr>
							<th>${reply.extra__writerName }</th>
							<td>${reply.body }</td>
						</tr>
					</c:forEach>
					<c:if test="${repliesCount == 0 }">
						<tr>
							<td>댓글이 존재하지 않습니다.</td>
						</tr>
					</c:if>
				</tbody>
		
			</table>
		</div>
	</div>
</section>

<%@ include file="../common/foot.jspf" %>