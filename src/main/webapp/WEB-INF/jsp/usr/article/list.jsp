<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="pageTitle" value="${board.name } 게시판" />
<%@ include file="../common/head.jspf" %>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
	<div><a class="float-right mb-2 btn btn-active btn-ghost" href="../article/write">게시물 작성하기</a></div>
		<div>${articlesCount }개</div>
		<div class="table-box-type-1">
			<table class="table w-full">
				<colgroup>
					<col width="80" />
					<col width="140" />
					<col />
					<col width="140" />
				</colgroup>
				
				<thead>
					<tr>
						<th>번호</th>
						<th>날짜</th>
						<th>제목</th>
						<th>작성자</th>
					</tr>
				</thead>
				
				<tbody>
					<c:forEach var="article" items="${articles }">
						<tr class="hover">
							<td>${article.id }</td>
							<td>${article.regDate.substring(0,10) }</td>
							<td><a href="../article/detail?id=${article.id }">${article.title }</a></td>
							<td>${article.extra__writerName }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</section>
	
<%@ include file="../common/foot.jspf" %>