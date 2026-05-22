<%-- 科目情報削除JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">

		<section>

			<!-- タイトル -->
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
				科目情報削除
			</h2>

			<!-- 確認メッセージ -->
			<p class="mb-4">
				「${subject.name}(${subject.cd})」を削除してもよろしいですか？
			</p>

			<!-- 削除フォーム -->
			<form action="SubjectDeleteExecute.action" method="post">
				<!-- 科目コードを送信 -->
				<input type="hidden"
				       name="subject_cd"
				       value="${subject.cd}">
				<!-- 削除ボタン -->
				<div class="mb-3">
					<input
						class="btn btn-danger"
						type="submit"
						value="削除">
				</div>
			</form>
			<!-- 戻る -->
			<a href="SubjectList.action">戻る</a>
		</section>
	</c:param>
</c:import>