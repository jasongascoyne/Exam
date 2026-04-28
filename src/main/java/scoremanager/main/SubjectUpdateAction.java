package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッション取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // パラメータ取得（科目コード）
        String subjectCd = req.getParameter("subject_cd");

        // DAO生成
        SubjectDao subjectDao = new SubjectDao();

        // 科目データ取得（DAOの find に合わせる）
        Subject subject = subjectDao.find(
            teacher.getSchool().getCd(),
            subjectCd
        );

        // 科目が存在しない場合は一覧へ戻す
        if (subject == null) {
            res.sendRedirect("SubjectList.action");
            return;
        }

        // リクエストスコープにセット
        req.setAttribute("subject", subject);

        // 変更画面へフォワード
        req.getRequestDispatcher("subject_update.jsp")
           .forward(req, res);
    }
}
