import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        String cd = req.getParameter("cd");

        if (cd == null || cd.isEmpty()) {
            res.sendRedirect("subject_list.action");
            return;
        }

        SubjectDao dao = new SubjectDao();

        int result = dao.delete(teacher.getSchool().getCd(), cd);

        if (result == 0) {
            req.setAttribute("error", "削除に失敗しました");
            req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
            return;
        }

        req.getRequestDispatcher("subject_delete_done.jsp").forward(req, res);
    }
}