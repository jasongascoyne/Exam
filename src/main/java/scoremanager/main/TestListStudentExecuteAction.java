package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import bean.Student;
import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

    @Override
    public void execute(
            HttpServletRequest req,
            HttpServletResponse res)
            throws Exception {

        // セッション取得
        HttpSession session = req.getSession();

        // ログイン中の先生情報取得
        Teacher teacher =
                (Teacher) session.getAttribute("user");

        // ----------------------------
        // パラメータ取得
        // ----------------------------
        String studentNo = req.getParameter("f4");

        // ----------------------------
        // プルダウン用データ取得
        // ----------------------------

        // 現在日付取得
        LocalDate todaysDate = LocalDate.now();

        // 現在年取得
        int year = todaysDate.getYear();

        // 入学年度リスト
        List<Integer> entYearSet
                = new ArrayList<>();

        // 10年前～現在年まで追加
        for (int i = year - 10; i <= year; i++) {
            entYearSet.add(i);
        }

        // ----------------------------
        // クラス一覧取得
        // ----------------------------
        ClassNumDao classNumDao
                = new ClassNumDao();

        List<String> classNumSet
                = classNumDao.filter(
                        teacher.getSchool());

        // ----------------------------
        // 科目一覧取得
        // ----------------------------
        SubjectDao subjectDao
                = new SubjectDao();

        List<Subject> subjectSet
                = subjectDao.filter(
                        teacher.getSchool());

        // ----------------------------
        // 学生一覧取得
        // ----------------------------
        StudentDao studentDao
                = new StudentDao();

        List<Student> students
                = studentDao.filter(
                        teacher.getSchool(),
                        true);

        // ----------------------------
        // JSPへセット
        // ----------------------------
        req.setAttribute(
                "ent_year_set",
                entYearSet);

        req.setAttribute(
                "class_num_set",
                classNumSet);

        req.setAttribute(
                "subject_set",
                subjectSet);

        req.setAttribute(
                "students_num_set",
                students);

        // ----------------------------
        // 入力チェック
        // ----------------------------
        if (studentNo == null
                || studentNo.isEmpty()
                || studentNo.equals("0")) {

            // エラーメッセージ
            req.setAttribute(
                    "error",
                    "学生番号を選択してください");

            // JSPへフォワード
            req.getRequestDispatcher(
                    "test_list_student.jsp")
                    .forward(req, res);

            return;
        }

        // ----------------------------
        // Student生成
        // ----------------------------
        Student student = new Student();

        // 学生番号セット
        student.setNo(studentNo);

        // ----------------------------
        // 成績一覧取得
        // ----------------------------
        TestListStudentDao dao
                = new TestListStudentDao();

        List<TestListStudent> list
                = dao.filter(student);

        // ----------------------------
        // 学生情報取得
        // ----------------------------
        StudentDao sDao = new StudentDao();

        Student studentData =
                sDao.get(studentNo);

        // ----------------------------
        // JSPへデータ渡し
        // ----------------------------

        // 学生名
        req.setAttribute(
                "studentName",
                studentData.getName());

        // 学生番号
        req.setAttribute(
                "studentNo",
                studentNo);

        // 選択状態保持
        req.setAttribute(
                "f4",
                studentNo);

        // 成績一覧
        req.setAttribute(
                "studentList",
                list);

        // JSP表示
        req.getRequestDispatcher(
                "test_list_student.jsp")
                .forward(req, res);
    }
}