package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

    // 成績検索
    public List<Test> filter(int entYear, String classNum, String subjectCd) throws Exception {

        List<Test> list = new ArrayList<>();

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(

                 "SELECT * FROM TEST "
               + "WHERE ENT_YEAR = ? "
               + "AND CLASS_NUM = ? "
               + "AND SUBJECT_CD = ?"

             )) {

            st.setInt(1, entYear);
            st.setString(2, classNum);
            st.setString(3, subjectCd);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                Test test = new Test();

                // 学生
                Student student = new Student();
                student.setNo(rs.getString("STUDENT_NO"));
                student.setName(rs.getString("STUDENT_NAME"));

                // 科目
                Subject subject = new Subject();
                subject.setCd(rs.getString("SUBJECT_CD"));
                subject.setName(rs.getString("SUBJECT_NAME"));

                test.setStudent(student);
                test.setSubject(subject);

                // 点数
                test.setPoint(rs.getInt("POINT"));

                list.add(test);
            }

            rs.close();
        }

        return list;
    }
}
