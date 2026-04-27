package scoremanager.main;

import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectListAction extends Action {
	
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // セッション取得

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 科目Dao生成
        SubjectDao subjectDao = new SubjectDao();

        // ログインユーザーの学校に紐づく科目一覧を取得
        List<Subject> subjects = subjectDao.filter(teacher.getSchool().getCd());

        // リクエストスコープにセット
        req.setAttribute("subjects", subjects);

        // 科目一覧画面へフォワード
        req.getRequestDispatcher("subject_list.jsp").forward(req, res);
    }
}