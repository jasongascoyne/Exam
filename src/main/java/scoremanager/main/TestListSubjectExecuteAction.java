package scoremanager.main;

import java.util.List;

import bean.Test;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TestListSubjectExecuteAction {

    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // ----------------------------
        // ① パラメータ取得
        // ----------------------------
        String entYearStr = request.getParameter("entYear");
        String classNum = request.getParameter("classNum");
        String subjectCd = request.getParameter("subjectCd");

        // ----------------------------
        // ② 入力チェック
        // ----------------------------
        if (entYearStr == null || entYearStr.isEmpty()
         || classNum == null || classNum.isEmpty()
         || subjectCd == null || subjectCd.isEmpty()) {

            request.setAttribute("error", "すべての項目を入力してください");
            return "test_list.jsp";
        }

        int entYear = Integer.parseInt(entYearStr);

        // ----------------------------
        // ③ DAO呼び出し
        // ----------------------------
        TestDao dao = new TestDao();
        List<Test> list = dao.filter(entYear, classNum, subjectCd);

        // ----------------------------
        // ④ JSPへ渡す
        // ----------------------------
        request.setAttribute("list", list);

        return "test_list.jsp";
    }
}