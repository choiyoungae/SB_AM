<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="pageTitle" value="${board.name } 게시판" />
<%@ include file="../common/head.jspf" %>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		<div class="flex justify-between items-center">
			<div class="articleCountNum">${articlesCount }개</div>
			<div>
				<form class="table-box-type-1" method="POST" action="../article/list">
					<input type="hidden" name="boardId" value="${param.boardId }" />
					<select class="select select-bordered lh-48px" name="searchKeywordTypeCode">
						<option disabled>검색할 항목을 선택해주세요.</option>
						<option value="title">제목</option>
						<option value="body">내용</option>
					</select>
					<input class="w-96 searchInput lh-48px" type="text" name="searchKeyword" placeholder="검색할 내용을 입력해주세요" />
					<button class="btn btn-ghost lh-48px" type="submit" value="검색" />검색</button>
				</form>
			</div>
			<div>
				<a class="mb-2 btn btn-active btn-ghost" href="../article/write">게시물 작성하기</a>
			</div>
		</div>
		<div class="table-box-type-1">
			<table class="table w-full">
				<colgroup>
					<col width="80" />
					<col width="140" />
					<col />
					<col width="140" />
					<col width="80" />
				</colgroup>
				
				<thead>
					<tr>
						<th>번호</th>
						<th>날짜</th>
						<th>제목</th>
						<th>작성자</th>
						<th>조회수</th>
					</tr>
				</thead>
				
				<tbody>
					<c:forEach var="article" items="${articles }">
						<tr class="hover">
							<td>${article.id }</td>
							<td>${article.regDate.substring(0,10) }</td>
							<td><a href="../article/detail?id=${article.id }">${article.title }</a></td>
							<td>${article.extra__writerName }</td>
							<td>${article.hit }</td>
						</tr>
					</c:forEach>
					<c:if test="${articles.size() == 0 }">
						<tr>
							<td colspan="5">게시글이 존재하지 않습니다.</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
		<div class="mt-3 flex justify-center">
			<div class="btn-group">
				<!-- 현재 페이지 번호의 앞뒤에 몇 개의 페이지 번호를 보여줄지 결정 -->
				<c:set var="pageMenuLen" value="4" />
				<!-- 페이지 번호의 시작을 지정 -->
				<c:set var="startPageNum" value="${page - pageMenuLen >= 1 ? page - pageMenuLen : 1 }" />
				<!-- 페이지 번호의 끝을 지정 -->
				<c:set var="endPageNum" value="${page + pageMenuLen <= pagesCount ? page + pageMenuLen : pagesCount }" />
				
				<c:if test="${startPageNum != 1 }">
					<a class="pageChangeBtn left" href="../article/list?boardId=${param.boardId }&page=${1 }">◀◀</a>
					<a class="pageChangeBtn left" href="../article/list?boardId=${param.boardId }&page=${page - pageMenuLen < 1 ? 1 : page - pageMenuLen }">◀</a>
				</c:if>
				<!-- 페이지 번호 표시 -->
				<c:forEach var="i" begin="${startPageNum }" end="${endPageNum }" step="1">
					<a href="../article/list?boardId=${param.boardId }&page=${i }">
						<p class="btn btn-ghost ${page == i ? 'btn-active' : '' }">
							${i }
						</p>
					</a>
				</c:forEach>
				<c:if test="${endPageNum != pagesCount }">
					<a class="pageChangeBtn right" href="../article/list?boardId=${param.boardId }&page=${page + pageMenuLen > pagesCount ? pagesCount : page + pageMenuLen }">▶</a>
					<a class="pageChangeBtn right" href="../article/list?boardId=${param.boardId }&page=${pagesCount }">▶▶</a>
				</c:if>
			</div>
		</div>
	</div>
</section>
	
<%@ include file="../common/foot.jspf" %>