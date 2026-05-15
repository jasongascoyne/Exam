package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ----------------------------
        // パラメータ取得
        // ----------------------------
        String entYearStr = req.getParameter("f1");
        String classNum = req.getParameter("f2");
        String subjectCd = req.getParameter("f3");

        // ----------------------------
        // プルダウン用データ
        // ----------------------------
        LocalDate today = LocalDate.now();
        int year = today.getYear();

        List<Integer> entYearSet = new ArrayList<>();

        for (int i = year - 10; i <= year; i++) {
            entYearSet.add(i);
        }

        ClassNumDao classNumDao = new ClassNumDao();

        List<String> classNumSet =
                classNumDao.filter(teacher.getSchool());

        SubjectDao subjectDao = new SubjectDao();
        List<Subject> subjectSet = subjectDao.filter(teacher.getSchool());
        
        StudentDao studentDao = new StudentDao();
        List<Student> students = studentDao.filter(teacher.getSchool(), true);
        req.setAttribute("students_num_set", students);

        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("class_num_set", classNumSet);
        req.setAttribute("subject_set", subjectSet);

        // ----------------------------
        // 入力チェック
        // ----------------------------
        Map<String, String> errors = new HashMap<>();

        if (entYearStr == null || entYearStr.equals("0")) {
            errors.put("f1", "入学年度を選択してください");
        }

        if (classNum == null || classNum.equals("0")) {
            errors.put("f2", "クラスを選択してください");
        }

        if (subjectCd == null || subjectCd.equals("0")) {
            errors.put("f3", "科目を選択してください");
        }

        if (!errors.isEmpty()) {

            req.setAttribute("errors", errors);

            req.getRequestDispatcher("test_list_subject.jsp")
               .forward(req, res);

            return;
        }

        // ----------------------------
        // 検索処理
        // ----------------------------
        int entYear = Integer.parseInt(entYearStr);

        TestListSubjectDao dao =
                new TestListSubjectDao();

        List<TestListSubject> list =
                dao.filter(entYear, classNum, subjectCd);

        // 科目情報取得（修正済み）
        Subject selectedSubject =
                subjectDao.get(
                        subjectCd,
                        teacher.getSchool());

        // ----------------------------
        // JSPへ渡す
        // ----------------------------
       
        req.setAttribute("selectedSubject", selectedSubject);

        req.setAttribute("f1", entYearStr);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);

        req.setAttribute("list", list);

        req.getRequestDispatcher("test_list_subject.jsp")
           .forward(req, res);
    }
}