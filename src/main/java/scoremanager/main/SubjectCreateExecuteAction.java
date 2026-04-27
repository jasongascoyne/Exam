package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // ① セッション取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ② リクエストパラメータ取得
        String cd = req.getParameter("cd");      // 科目コード
        String name = req.getParameter("name");  // 科目名

        // ③ Subject オブジェクトにセット
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(teacher.getSchool());

        // ④ DB 登録
        SubjectDao subjectDao = new SubjectDao();
        subjectDao.insert(subject);

        // ⑤ 登録完了画面へフォワード
        req.getRequestDispatcher("subject_create_done.jsp")
           .forward(req, res);
    }
}