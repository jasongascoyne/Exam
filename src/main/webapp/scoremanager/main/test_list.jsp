<%-- 成績一覧 検索条件入力 JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                成績参照
            </h2>

            <form action="TestListExecute.action" method="post">

                <!-- 入学年度 -->
                <div class="mb-3 px-4">
                    <label class="form-label">入学年度</label>
                    <select name="school_year" class="form-select">
                        <option value="">-- 選択してください --</option>
                        <c:forEach var="year" items="${schoolYears}">
                            <option value="${year}"
                                <c:if test="${year == param.school_year}">selected</c:if>>
                                ${year}
                            </option>
                        </c:forEach>
                    </select>
                    <div class="text-warning">
                        ${errors.get("school_year")}
                    </div>
                </div>

                <!-- クラス -->
                <div class="mb-3 px-4">
                    <label class="form-label">クラス</label>
                    <select name="class_num" class="form-select">
                        <option value="">-- 選択してください --</option>
                        <c:forEach var="classNum" items="${classNums}">
                            <option value="${classNum}"
                                <c:if test="${classNum == param.class_num}">selected</c:if>>
                                ${classNum}
                            </option>
                        </c:forEach>
                    </select>
                    <div class="text-warning">
                        ${errors.get("class_num")}
                    </div>
                </div>

                <!-- 科目 -->
                <div class="mb-3 px-4">
                    <label class="form-label">科目</label>
                    <select name="subject_cd" class="form-select">
                        <option value="">-- 選択してください --</option>
                        <c:forEach var="subject" items="${subjects}">
                            <option value="${subject.cd}"
                                <c:if test="${subject.cd == param.subject_cd}">selected</c:if>>
                                ${subject.name}
                            </option>
                        </c:forEach>
                    </select>
                    <div class="text-warning">
                        ${errors.get("subject_cd")}
                    </div>
                </div>

                <!-- 検索 -->
                <div class="text-center px-4">
                    <input type="submit" class="btn btn-primary" value="検索">
                </div>

            </form>
        </section>
    </c:param>
</c:import>