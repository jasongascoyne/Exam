package scoremanager.main;
 
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;
 
public class SubjectDeleteExecuteAction extends Action {
 
    @Override
    public void execute(HttpServletRequest req,
                        HttpServletResponse res)
                        throws Exception {
 
        // セッション取得
        HttpSession session = req.getSession();
 
        Teacher teacher =
                (Teacher) session.getAttribute("user");
 
        // 未ログイン対策
        if (teacher == null) {
            res.sendRedirect("login.jsp");
            return;
        }
 
        // 科目コード取得
        String cd =
                req.getParameter("subject_cd");
 
        // 入力チェック
        if (cd == null || cd.isEmpty()) {
            res.sendRedirect("SubjectList.action");
            return;
        }
 
        // Subject生成
        Subject subject = new Subject();
 
        subject.setCd(cd);
        subject.setSchool(teacher.getSchool());
 
        // DAO
        SubjectDao dao = new SubjectDao();
 
        boolean result = dao.delete(subject);
 
        // 削除失敗
        if (!result) {
 
            req.setAttribute(
                    "error",
                    "削除に失敗しました");
 
            req.getRequestDispatcher(
                    "subject_delete.jsp")
                    .forward(req, res);
 
            return;
        }
 
        // 完了画面
        req.getRequestDispatcher(
                "subject_delete_done.jsp")
                .forward(req, res);
    }
}