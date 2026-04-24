<%-- 学生一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp" >
	<c:param name="title">
		科目管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me=4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目管理</h2>
			<div class="my-2 text-end px-4">
				<a href="SubjectCreate.action">新規登録</a>
			</div>
			<form method="get">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					
					
					<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button">絞込み</button>
					</div>
					<div class="mt-2 text-warning">${errors.get("f1") }</div>
				</div>
			</form>


			<c:choose>
				<c:when test="${subjects.size()>0 }">
					<div>検索結果：${subjects.size() }件</div>
					<table class="table table-hover">
						<tr>
							<th>科目</th>
						</tr>
						<c:forEach var="student" items="${subjects }">
							<tr>
				
								<td>${subject.name }</td>
								<td><a href="SubjectUpdate.action?no=${student.no }">変更</a></td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					<div>科目情報が存在しませんでした。</div>
				</c:otherwise>
			</c:choose>
		</section>
	</c:param>
</c:import>