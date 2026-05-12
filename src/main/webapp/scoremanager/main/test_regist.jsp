<%-- 成績管理一覧（検索条件入力） JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="content">
        <section class="me-4">

            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                成績管理一覧
            </h2>

            <!-- ===== 検索条件入力 ===== -->
            <form action="TestRegistAction.action" method="post">

                <!-- 入学年度 -->
                <div class="mb-3 px-4">
                    <label class="form-label">入学年度</label>
                    <select name="f1" class="form-select">
                        <option value="">-- 選択してください --</option>
                        <c:forEach var="year" items="${schoolYears}">
                            <option value="${year}"
                                <c:if test="${year == f1}">selected</c:if>>
                                ${year}
                            </option>
                        </c:forEach>
                    </select>
                    <div class="text-warning">
                        ${errors.get("f1")}
                    </div>
                </div>

                <!-- クラス -->
                <div class="mb-3 px-4">
                    <label class="form-label">クラス</label>
                    <select name="f2" class="form-select">
                        <option value="">-- 選択してください --</option>
                        <c:forEach var="c" items="${classNums}">
                            <option value="${c}"
                                <c:if test="${c == f2}">selected</c:if>>
                                ${c}
                            </option>
                        </c:forEach>
                    </select>
                    <div class="text-warning">
                        ${errors.get("f2")}
                    </div>
                </div>

                <!-- 科目 -->
                <div class="mb-3 px-4">
                    <label class="form-label">科目</label>
                    <select name="f3" class="form-select">
                        <option value="">-- 選択してください --</option>
                        <c:forEach var="subject" items="${subjects}">
                            <option value="${subject.cd}"
                                <c:if test="${subject.cd == f3}">selected</c:if>>
                                ${subject.name}
                            </option>
                        </c:forEach>
                    </select>
                    <div class="text-warning">
                        ${errors.get("f3")}
                    </div>
                </div>

                <!-- 回数 -->
                <div class="mb-3 px-4">
                    <label class="form-label">回数</label>
                    <select name="f4" class="form-select">
                        <option value="">-- 選択してください --</option>
                        <c:forEach begin="1" end="5" var="n">
                            <option value="${n}"
                                <c:if test="${n == f4}">selected</c:if>>
                                ${n}
                            </option>
                        </c:forEach>
                    </select>
                    <div class="text-warning">
                        ${errors.get("f4")}
                    </div>
                </div>

                <!-- 検索ボタン -->
                <div class="text-center px-4">
                    <input type="submit" class="btn btn-primary" value="検索">
                </div>

            </form>

            <!-- ===== 検索結果一覧 ===== -->
            <c:if test="${not empty testList}">

                <hr>

                <form action="TestRegistExecuteAction.action" method="post">

                    <!-- 検索条件保持 -->
                    <input type="hidden" name="f1" value="${f1}">
                    <input type="hidden" name="f2" value="${f2}">
                    <input type="hidden" name="f3" value="${f3}">
                    <input type="hidden" name="f4" value="${f4}">

                    <table class="table table-bordered mt-3">
                        <thead>
                            <tr>
                                <th>学籍番号</th>
                                <th>氏名</th>
                                <th>点数</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="test" items="${testList}">
                                <tr>
                                    <td>${test.student.no}</td>
                                    <td>${test.student.name}</td>
                                    <td>
                                        <input type="text"
                                               name="point_${test.student.no}"
                                               value="${test.point}"
                                               class="form-control">
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <div class="text-center">
                        <input type="submit"
                               class="btn btn-success"
                               value="登録">
                    </div>

                </form>
            </c:if>

        </section>
    </c:param>
</c:import>
``