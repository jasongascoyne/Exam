<%-- 科目一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		科目管理システム
	</c:param>
	<c:param name="scripts"></c:param>
	<c:param name="content">
		<section class="me=4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">
				科目管理
			</h2>
			<div class="my-2 text-end px-4">
				<a href="SubjectCreate.action">新規登録</a>
			</div>
			<c:choose>
				<c:when test="${subjects.size()>0 }">
					<table class="table table-hover">
						<tr>
							<th style="width:20%" >科目コード</th>
							<th style="width:50%" >科目名</th>
							<th></th>
							<th></th>
						</tr>
						<c:forEach var="subject" items="${subjects }">
							<tr>
								<!-- 科目コード -->
								<td>${subject.cd }</td>
								<!-- 科目名 -->
								<td>${subject.name }</td>
								<!-- 変更 -->
								<td style="width:15%">
									<a href="SubjectUpdate.action?subject_cd=${subject.cd }">
										変更
									</a>
								</td>
								<!-- 削除 -->
								<td style="width:20%">
									<a href="SubjectDelete.action?subject_cd=${subject.cd }">
										削除
									</a>
								</td>
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