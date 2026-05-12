package scoremanager.main;

import java.util.List;

import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッション取得（ログインユーザー）
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        String schoolCd = teacher.getSchool().getCd();

        /* ==========================
         * 初期表示用データ取得
         * ========================== */
        ClassNumDao classNumDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();

        List<String> classList = classNumDao.filter(teacher.getSchool());
        List<Subject> subjectList = subjectDao.filter(schoolCd);

        req.setAttribute("classList", classList);
        req.setAttribute("subjectList", subjectList);

        /* ==========================
         * パラメータ取得
         * ========================== */
        String entYearStr = req.getParameter("entYear");
        String classNum   = req.getParameter("classNum");
        String subjectCd  = req.getParameter("subjectCd");
        String numStr     = req.getParameter("num");

        // 初期表示（検索前）
        if (entYearStr == null) {
            req.getRequestDispatcher("test_regist.jsp").forward(req, res);
            return;
        }

        /* ==========================
         * 入力チェック（未入力）
         * ========================== */
        if (entYearStr.isEmpty() || classNum.isEmpty()
                || subjectCd.isEmpty() || numStr.isEmpty()) {

            req.setAttribute(
                "message",
                "入学年度とクラスと科目と回数を選択してください"
            );

            req.getRequestDispatcher("test_regist.jsp").forward(req, res);
            return;
        }

        int entYear = Integer.parseInt(entYearStr);
        int num = Integer.parseInt(numStr);

        /* ==========================
         * 成績検索
         * ========================== */
        TestDao testDao = new TestDao();
        List<Test> testList = testDao.filter(entYear, classNum, subjectCd);

        req.setAttribute("testList", testList);
        req.setAttribute("entYear", entYear);
        req.setAttribute("classNum", classNum);
        req.setAttribute("subjectCd", subjectCd);
        req.setAttribute("num", num);

        /* ==========================
         * 画面表示
         * ========================== */
        req.getRequestDispatcher("test_regist.jsp").forward(req, res);
    }
}
