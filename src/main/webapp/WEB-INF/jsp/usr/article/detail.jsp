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
	
	function ArticleDetail__increaseGoodReactionPoint() {
		alert("liked");
	}
	
	function ArticleDetail__increaseBadReactionPoint() {
		alert("disliked");
	}
	
	$(function() {
		ArticleDetail__increaseHitCount();
	})
</script>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		
		<div class="table-box-type-1">
			<table>
				<colgroup>
					<col width="200" />
					<col />
					<col width="200" />
					<col />
				</colgroup>
				
				<tbody>
					<tr>
						<th>번호</th>
						<td colspan="3">${article.id }</td>
					</tr>
					<tr>
						<th>작성날짜</th>
						<td colspan="3">${article.regDate }</td>
					</tr>
					<tr>
						<th>수정날짜</th>
						<td colspan="3">${article.updateDate }</td>
					</tr>
					<tr>
						<th>작성자</th>
						<td colspan="3">${article.extra__writerName }</td>
					</tr>
					<tr>
						<th>조회수</th>
						<td colspan="3">
							<span class='article-detail__hit-count'>${article.hitCount }</span>
						</td>
					</tr>
					<tr>
						<th>제목</th>
						<td colspan="3">${article.title }</td>
					</tr>
					<tr>
						<th>내용</th>
						<td colspan="3">${article.body }</td>
					</tr>
					<tr>
						<th>
							<button type="button" onclick="ArticleDetail__increaseGoodReactionPoint();">
								<i class="fa-solid fa-heart"></i>
							</button>
						</th>
						<td>${article.extra__goodReactionPoint }</td>
						<th>
							<button type="button" onclick="ArticleDetail__increaseBadReactionPoint();">
								<i class="fa-solid fa-heart-crack"></i>
							</button>
						</th>
						<td>${-article.extra__badReactionPoint }</td>
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

<!-- <iframe src="http://localhost:8080/usr/article/doIncreaseHitCountRd?id=248" frameborder="0"></iframe> -->
	
<%@ include file="../common/foot.jspf" %>