<%-- 科目登録JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
 
<c:import url="/common/base.jsp" >
	<c:param name="title">
		得点管理システム
	</c:param>
 
	<c:param name="scripts"></c:param>
 
	<c:param name="content">
		<section>
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報登録</h2>
 
			<form action="SubjectCreateExecute.action" method="post">
 
				<div>
					<label for="cd">科目コード</label><br>
					<input class="form-control" type="text" id="cd" name="cd"
						value="${cd }" maxlength="3"
						placeholder="科目コードを入力してください" required />
				</div>
				
				<div>
					<label for="name">科目名</label><br>
					<input class="form-control" type="text" id="name" name="name"
						value="${name }" maxlength="30"
						placeholder="科目名を入力してください" required />
				</div>
				<div class="mx-auto py-2">
					<button class="btn btn-secondary" id="create-button" name="end">
						登録して終了
					</button>
				</div>
 
			</form>
 
			<a href="SubjectList.action">戻る</a>
		</section>
	</c:param>
</c:import>