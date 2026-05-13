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

public class TestRegistExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ==========================
        // セッション取得
        // ==========================
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ==========================
        // パラメータ取得（hidden）
        // ==========================
        String f1 = req.getParameter("f1"); // 入学年度
        String f2 = req.getParameter("f2"); // クラス
        String f3 = req.getParameter("f3"); // 科目
        String f4 = req.getParameter("f4"); // 回数

        int entYear = Integer.parseInt(f1);
        int num = Integer.parseInt(f4);

        // ==========================
        // 成績データ取得（回数も含める）
        // ==========================
        TestDao testDao = new TestDao();
        List<Test> testList = testDao.filter(entYear, f2, f3, num);

        // ==========================
        // 入力チェック
        // ==========================
        Map<String, String> errors = new HashMap<>();

        for (Test test : testList) {

            String paramName = "point_" + test.getStudent().getNo();
            String pointStr = req.getParameter(paramName);

            // 未入力はスキップ（変更しない）
            if (pointStr == null || pointStr.isEmpty()) {
                continue;
            }

            int point;

            try {
                point = Integer.parseInt(pointStr);
            } catch (NumberFormatException e) {
                errors.put(paramName, "点数は数値で入力してください");
                continue;
            }

            if (point < 0 || point > 100) {
                errors.put(paramName, "点数は0〜100で入力してください");
            } else {
                test.setPoint(point);
            }
        }

        // ==========================
        // エラー処理
        // ==========================
        if (!errors.isEmpty()) {

            req.setAttribute("errors", errors);
            req.setAttribute("testList", testList);

            req.setAttribute("f1", f1);
            req.setAttribute("f2", f2);
            req.setAttribute("f3", f3);
            req.setAttribute("f4", f4);

            // プルダウン再取得
            ClassNumDao classNumDao = new ClassNumDao();
            SubjectDao subjectDao = new SubjectDao();

            req.setAttribute("classNums",
                    classNumDao.filter(teacher.getSchool()));
            req.setAttribute("subjects",
                    subjectDao.filter(teacher.getSchool().getCd()));

            req.getRequestDispatcher("test_regist.jsp")
               .forward(req, res);
            return;
        }

        // ==========================
        // 保存処理（登録 + 更新）
        // ==========================
        for (Test test : testList) {

            // 点数が設定されている場合のみ保存
            if (test.getPoint() != 0) {

                testDao.save(
                    test,
                    teacher.getSchool().getCd(),
                    f3,
                    num
                );
            }
        }

        // ==========================
        // 完了画面
        // ==========================
        req.getRequestDispatcher("test_regist_done.jsp")
           .forward(req, res);
    }
}