package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteAction extends Action {

    @Override

    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // セッション取得

        HttpSession session = req.getSession();

        Teacher teacher = (Teacher) session.getAttribute("user");

        // パラメータ取得（科目コード）

        String cd = req.getParameter("subject_cd");

        // DAO

        SubjectDao dao = new SubjectDao();

        // 科目取得（学校 + cd）

        Subject subject = dao.get(cd, teacher.getSchool());

        // リクエストにセット

        req.setAttribute("subject", subject);

        // 削除確認画面へ

        req.getRequestDispatcher("subject_delete.jsp").forward(req, res);

    }

}

 