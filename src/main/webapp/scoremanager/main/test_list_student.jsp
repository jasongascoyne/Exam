<%-- 成績一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp" >
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me=4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>
			<form method="get">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-2">　科目情報　　</div>
					<div class="col-2">
						<label class="form-label" for="student-f1-select">入学年度</label>
						<select class="form-select" id="student-f1-select" name="f1">
							<option value="0">--------</option>
							<c:forEach var="year" items="${ent_year_set }">
								<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
								<option value="${year}" ${year eq f1 ? 'selected="selected"' : ''}>${year}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-2">
						<label class="form-label" for="student-f2-select">クラス</label>
						<select class="form-select" id="student-f2-select" name="f2">
							<option value="0">--------</option>
							<c:forEach var="num" items="${class_num_set }">
								<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
								<option value="${num}" ${num eq f2 ? 'selected="selected"' : ''}>${num}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-4">
						<label class="form-label" for="subject-f3-select">科目</label>
						<select class="form-select" id="subject-f3-select" name="f3">
							<option value="0">--------</option>
							<c:forEach var="subject" items="${subject_set}">
								<%-- 現在選択されていた科目コードと一致ならselected --%>
								<option value="${subject.cd}" ${subject.cd eq f3 ? 'selected="selected"' : ''}>${subject.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button">検索</button>
					</div>
					<div class="mt-2 text-warning">${errors.get("f1") }</div>
				</div>
			</form>
			<form method="get">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
					<div class="col-2">
						　学生情報　　
					</div>
					<div class="col-4">
						<label class="form-label" for="student-f4-select">学生番号</label>
						<select class="form-select" id="student-f4-select" name="f4">
							<option value="0">学生番号を入力してください</option>
							<c:forEach var="num" items="${students_num_set }">
								<%-- 現在のnumと選択されていたf4が一致していた場合selectedを追記 --%>
								<option value="${num }" <c:if test="${num==f4 }">selected</c:if>>${num }</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-2 text-center">
						<button class="btn btn-secondary" id="filter-button">検索</button>
					</div>
					<div class="mt-2 text-warning">${errors.get("f1") }</div>
				</div>
			</form>
		</section>
		<c:choose>
			<c:when test="${list.size()>0 }">
				<div>検索結果：${list.size() }件</div>
				<table class="table table-hover">
					<tr>
						<th>入学年度</th>
						<th>クラス</th>
						<th>学生番号</th>
						<th>氏名</th>
						<th>1回</th>
						<th>2回</th>
					</tr>
					<c:forEach var="test" items="${list }">
						<tr>
							<td>${test.student.entYear}</td>
							<td>${test.student.classNum}</td>
							<td>${test.student.no}</td>
							<td>${test.student.name}</td>
							<td>${test.point}</td>
						</tr>
					</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
				<div>学生情報が存在しませんでした。</div>
			</c:otherwise>
		</c:choose>
	</c:param>
</c:import>