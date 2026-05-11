package dao;

 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

 

import bean.Student;
import bean.Subject;
import bean.Test;

 

public class TestDao extends Dao {public List<Test> filter(int entYear, String classNum, String subjectCd) throws Exception {

    List<Test> list = new ArrayList<>();

    try (Connection con = getConnection();
         PreparedStatement st = con.prepareStatement(

            "SELECT * FROM TEST t "
          + "JOIN STUDENT s ON t.STUDENT_NO = s.NO "
          + "WHERE s.ENT_YEAR = ? "
          + "AND s.CLASS_NUM = ? "
          + "AND t.SUBJECT_CD = ?"

         )) {

        st.setInt(1, entYear);
        st.setString(2, classNum);
        st.setString(3, subjectCd);

        ResultSet rs = st.executeQuery();

        while (rs.next()) {

            Test test = new Test();

            // ----------------------------
            // Student
            // ----------------------------
            Student student = new Student();
            student.setEntYear(rs.getInt("ENT_YEAR"));
            student.setClassNum(rs.getString("CLASS_NUM"));
            student.setNo(rs.getString("NO"));
            student.setName(rs.getString("NAME"));

            test.setStudent(student);

            // ----------------------------
            // 点数
            // ----------------------------
            test.setPoint(rs.getInt("POINT"));

            list.add(test);
        }

        rs.close();
    }

    return list;
}
