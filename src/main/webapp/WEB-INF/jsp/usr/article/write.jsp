<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="pageTitle" value="ARTICLE WRITE" />
<%@ include file="../common/head.jspf" %>

<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">
		
		<form class="table-box-type-1" method="POST" action="../article/doWrite">
			<table>
				<colgroup>
					<col width="200" />
				</colgroup>
				
				<tbody>
					<tr>
						<th>제목</th>
						<td>
							<input class="w-96" type="text" name="title" placeholder="제목을 입력해주세요" />
						</td>
					</tr>
					<tr>
						<th>비밀번호</th>
						<td>
							<input class="w-96" type="text" name="body" placeholder="내용을 입력해주세요" />
						</td>
					</tr>
					<tr>
						<th></th>
						<td>
							<button class="btn btn-ghost" type="submit" value="작성" />작성</button>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
		
		<div class="container mx-auto btns">
			<button class="btn-text-link btn btn-ghost" type="button" onclick="history.back();">뒤로가기</button>
		</div>
	
	</div>
	
	
</section>
	
<%@ include file="../common/foot.jspf" %>