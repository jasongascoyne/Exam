package scoremanager.main;
 
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Student;
import bean.Subject;
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
 
        LocalDate today = LocalDate.now();
        int year = today.getYear();
 
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year; i++) {
            entYearSet.add(i);
        }
 
        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("class_num_set",
                classNumDao.filter(teacher.getSchool()));
 
        req.setAttribute("subject_set",
                subjectDao.filter(teacher.getSchool()));
        // ==========================
        // パラメータ取得
        // ==========================
        String f1 = req.getParameter("f1");
        String f2 = req.getParameter("f2");
        String f3 = req.getParameter("f3");
        String f4 = req.getParameter("f4");
        // 初期表示
        if (f1 == null) {
            req.getRequestDispatcher("test_regist.jsp").forward(req, res);
            return;
        }
 
        // ==========================
        // 入力チェック
        // ==========================
        Map<String, String> errors = new HashMap<>();
 
        if (f1 == null || f1.isEmpty() || f1.equals("0")) {
            errors.put("f1", "入学年度を選択してください");
        }
        if (f2 == null || f2.isEmpty() || f2.equals("0")) {
            errors.put("f2", "クラスを選択してください");
        }
        if (f3 == null || f3.isEmpty() || f3.equals("0")) {
            errors.put("f3", "科目を選択してください");
        }
        if (f4 == null || f4.isEmpty() || f4.equals("0")) {
            errors.put("f4", "回数を選択してください");
        }
 
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("f1", f1);
            req.setAttribute("f2", f2);
            req.setAttribute("f3", f3);
            req.setAttribute("f4", f4);
 
            req.getRequestDispatcher("test_regist.jsp").forward(req, res);
            return;
        }
 
        // ==========================
        // 成績検索
        // ==========================
        int entYear = Integer.parseInt(f1);
        int num = Integer.parseInt(f4);
 
        TestDao dao = new TestDao();
        List<Test> testList = dao.filter(entYear, f2, f3, num, teacher.getSchool());
        // ==========================
        // ★ポイント：Map初期化（JSP対策）
        // ==========================
        for (Test t : testList) {
            // null防止（安全対策）
            if (t.getStudent() == null) {
                t.setStudent(new Student());
            }
            if (t.getSubject() == null) {
                t.setSubject(new Subject());
            }
        }
 
        // ==========================
        // JSPへ渡す
        // ==========================
        req.setAttribute("list", testList);
 
        req.setAttribute("f1", f1);
        req.setAttribute("f2", f2);
        req.setAttribute("f3", f3);
        req.setAttribute("f4", f4);
 
        req.getRequestDispatcher("test_regist.jsp").forward(req, res);
    }
}