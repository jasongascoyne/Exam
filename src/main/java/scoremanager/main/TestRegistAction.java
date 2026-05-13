package scoremanager.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ==========================
        // 初期データ
        // ==========================
        ClassNumDao classNumDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();

        req.setAttribute("classNums",
                classNumDao.filter(teacher.getSchool()));
        req.setAttribute("subjects",
                subjectDao.filter(teacher.getSchool().getCd()));

        // ==========================
        // パラメータ取得
        // ==========================
        String f1 = req.getParameter("f1"); // 入学年度
        String f2 = req.getParameter("f2"); // クラス
        String f3 = req.getParameter("f3"); // 科目
        String f4 = req.getParameter("f4"); // 回数

        // 初期表示
        if (f1 == null) {
            req.getRequestDispatcher("test_regist.jsp")
               .forward(req, res);
            return;
        }

        // ==========================
        // 入力チェック
        // ==========================
        Map<String, String> errors = new HashMap<>();

        if (f1 == null || f1.isEmpty()) {
            errors.put("f1", "入学年度を選択してください");
        }
        if (f2 == null || f2.isEmpty()) {
            errors.put("f2", "クラスを選択してください");
        }
        if (f3 == null || f3.isEmpty()) {
            errors.put("f3", "科目を選択してください");
        }
        if (f4 == null || f4.isEmpty()) {
            errors.put("f4", "回数を選択してください");
        }

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);

            // 再表示用
            req.setAttribute("f1", f1);
            req.setAttribute("f2", f2);
            req.setAttribute("f3", f3);
            req.setAttribute("f4", f4);

            req.getRequestDispatcher("test_regist.jsp")
               .forward(req, res);
            return;
        }

        // ==========================
        // 成績検索（回数も含める）
        // ==========================
        int entYear = Integer.parseInt(f1);
        int num = Integer.parseInt(f4);

        TestDao testDao = new TestDao();
        List<Test> testList =
                testDao.filter(entYear, f2, f3, num);

        // ==========================
        // JSPへ渡す
        // ==========================
        req.setAttribute("testList", testList);

        req.setAttribute("f1", f1);
        req.setAttribute("f2", f2);
        req.setAttribute("f3", f3);
        req.setAttribute("f4", f4);

        req.getRequestDispatcher("test_regist.jsp")
           .forward(req, res);
    }
}