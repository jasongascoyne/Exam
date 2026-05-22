<%-- 成績管理JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
 
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>
	<c:param name="content">
		<section class="me-4">
			<!-- タイトル -->
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
				成績管理
			</h2>
			<!-- =========================
				検索フォーム
			========================= -->
			<form action="TestRegist.action" method="get">
				<div class="row border mx-3 mb-3 py-2 align-items-center rounded">
					<!-- 入学年度 -->
					<div class="col-2">
						<label class="form-label">入学年度</label>
						<select class="form-select" name="f1">
							<option value="0">--------</option>
							<c:forEach var="year" items="${ent_year_set}">
								<option value="${year}"
									${year eq f1 ? 'selected="selected"' : ''}>
									${year}
								</option>
							</c:forEach>
						</select>
					</div>
					<!-- クラス -->
					<div class="col-2">
						<label class="form-label">クラス</label>
						<select class="form-select" name="f2">
							<option value="0">--------</option>
							<c:forEach var="num" items="${class_num_set}">
								<option value="${num}"
									${num eq f2 ? 'selected="selected"' : ''}>
									${num}
								</option>
							</c:forEach>
						</select>
					</div>
					<!-- 科目 -->
					<div class="col-4">
						<label class="form-label">科目</label>
						<select class="form-select" name="f3">
							<option value="0">--------</option>
							<c:forEach var="subject" items="${subject_set}">
								<option value="${subject.cd}"
									${subject.cd eq f3 ? 'selected="selected"' : ''}>
									${subject.name}
								</option>
							</c:forEach>
						</select>
					</div>
                    <!-- 回数 -->
                    <div class="col-2">
                        <label class="form-label">回数</label>
                        <select class="form-select" name="f4">
 
                            <option value="0">--------</option>
 
                            <option value="1"
                                ${f4 eq '1' ? 'selected="selected"' : ''}>
                                1回
                            </option>
 
                            <option value="2"
                                ${f4 eq '2' ? 'selected="selected"' : ''}>
                                2回
                            </option>
 
                        </select>
                    </div>
 
                    <!-- 検索ボタン -->
                    <div class="col-2 text-center">
                        <button class="btn btn-secondary">
                            検索
                        </button>
                    </div>
                </div>
                <!-- エラー -->
                <div class="mt-2 text-warning">
                    ${errors.f1}
                    ${errors.f2}
                    ${errors.f3}
                    ${errors.f4}
                </div>
            </form>
            <!-- =========================
                 検索結果
            ========================= -->
            <c:if test="${not empty list}">
                <!-- 科目名 -->
                <div class="mb-2 fw-bold">
                    科目:${selectedSubject.name} (${f4}回)
                </div>
                <!-- 更新フォーム -->
                <form action="TestRegistExecute.action" method="post">
                    <!-- 検索条件保持 -->
                    <input type="hidden" name="f1" value="${f1}">
                    <input type="hidden" name="f2" value="${f2}">
                    <input type="hidden" name="f3" value="${f3}">
                    <input type="hidden" name="f4" value="${f4}">
                    <table class="table table-hover">
                        <tr>
                            <th>入学年度</th>
                            <th>クラス</th>
                            <th>学生番号</th>
                            <th>氏名</th>
                            <th>点数</th>
                        </tr>
                        <c:forEach var="test" items="${list}">
                            <tr>
                                <!-- 入学年度 -->
                                <td>
                                    ${test.student.entYear}
                                </td>
                                <!-- クラス -->
                                <td>
                                    ${test.student.classNum}
                                </td>
                                <!-- 学生番号 -->
                                <td>
                                    ${test.student.no}
                                    <input type="hidden"
                                           name="studentNo"
                                           value="${test.student.no}">
                                </td>
                                <!-- 氏名 -->
                                <td>
                                    ${test.student.name}
                                </td>
                                <!-- 点数 -->
                                <td>
									<input type="text"
										name="point_${test.student.no}"
										value="${test.point}"
										class="form-control"
										pattern="^(100|[0-9]{1,2})$"
										inputmode="numeric"
										required
										oninvalid="this.setCustomValidity('0～100の整数で入力してください')"
										oninput="this.setCustomValidity('')">
								</td>
							</tr>
						</c:forEach>
					</table>
					<!-- 更新ボタン -->
					<div class="text-end mt-3">
						<button type="submit" class="btn btn-primary">
							変更を完了
                        </button>
                    </div>
                </form>
            </c:if>
        </section>
    </c:param>
</c:import>
