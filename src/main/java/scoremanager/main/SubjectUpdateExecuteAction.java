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
 
        // Subject 作成
        Subject subject = new Subject();
        subject.setCd(subjectCd);
        subject.setName(subjectName);
        subject.setSchool(teacher.getSchool());
 
        // エラー時
        if (!errors.isEmpty()) {
 
            req.setAttribute("errors", errors);
            req.setAttribute("subject", subject);
 
            req.getRequestDispatcher("subject_update.jsp")
               .forward(req, res);
 
            return;
        }
 
        // DAO
        SubjectDao dao = new SubjectDao();
 
        // 保存（更新）
        boolean result = dao.save(subject);
 
        // 更新失敗
        if (!result) {
 
            errors.put("update", "更新に失敗しました");
 
            req.setAttribute("errors", errors);
            req.setAttribute("subject", subject);
 
            req.getRequestDispatcher("subject_update.jsp")
               .forward(req, res);
 
            return;
        }
 
        // 完了画面
        req.getRequestDispatcher("subject_update_done.jsp")
           .forward(req, res);
    }
}