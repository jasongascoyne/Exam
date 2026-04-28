package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッション取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // パラメータ取得
        String subjectCd = req.getParameter("subject_cd");
        String subjectName = req.getParameter("subject_name");

        // エラーメッセージ
        Map<String, String> errors = new HashMap<>();

        // 入力チェック
        if (subjectName == null || subjectName.isEmpty()) {
            errors.put("subject_name", "科目名を入力してください");
        }

        // エラー時は変更画面へ戻す
        if (!errors.isEmpty()) {

            Subject subject = new Subject();
            subject.setCd(subjectCd);
            subject.setName(subjectName);
            subject.setSchool(teacher.getSchool());

            req.setAttribute("errors", errors);
            req.setAttribute("subject", subject);

            req.getRequestDispatcher("subject_update.jsp")
               .forward(req, res);
            return;
        }

        // Subject 作成
        Subject subject = new Subject();
        subject.setCd(subjectCd);
        subject.setName(subjectName);
        subject.setSchool(teacher.getSchool());

        // 更新
        SubjectDao dao = new SubjectDao();
        int result = dao.update(subject);

        // 更新失敗時の保険
        if (result == 0) {
            errors.put("update", "更新に失敗しました");
            req.setAttribute("errors", errors);
            req.setAttribute("subject", subject);
            req.getRequestDispatcher("subject_update.jsp")
               .forward(req, res);
            return;
        }

        // 更新完了画面へ
        req.getRequestDispatcher("subject_update_done.jsp")
           .forward(req, res);
    }
}
