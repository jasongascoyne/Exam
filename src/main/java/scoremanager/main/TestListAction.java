package scoremanager.main;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;
public class TestListAction extends Action {
	@Override
 
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// セッション
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");
		// ローカル変数の指定
		LocalDate todaysDate = LocalDate.now(); // 今日の日付
		int year = todaysDate.getYear(); // 現在の年
		ClassNumDao classNumDao = new ClassNumDao(); // クラス番号Dao
		SubjectDao subjectDao = new SubjectDao(); // 科目Dao
		StudentDao studentDao = new StudentDao(); // 学生Dao
		// 入学年度リスト
 
		List<Integer> entYearSet = new ArrayList<>();
		// 学生リスト
 
		List<Student> students = null;
		// 10年前から現在まで追加
 
		for (int i = year - 10; i <= year; i++) {
 
			entYearSet.add(i);
 
		}
		// クラス一覧取得
		List<String> classNumSet = classNumDao.filter(teacher.getSchool());
		// 科目一覧取得
		List<Subject> subjectSet = subjectDao.filter(teacher.getSchool());
		// 学生一覧取得
		students = studentDao.filter(teacher.getSchool(), true);
		// リクエストにセット
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set", classNumSet);
		req.setAttribute("subject_set", subjectSet);
		req.setAttribute("students_num_set", students);
		// JSPへフォワード
		req.getRequestDispatcher("test_list.jsp").forward(req, res);
	}
}