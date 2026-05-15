package dao;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;


public class TestDao extends Dao {
	 
    // SQL共通部分
    private String baseSql =
        "SELECT " +
        "s.ENT_YEAR, " +
        "s.CLASS_NUM, " +
        "s.NO AS STUDENT_NO, " +
        "s.NAME AS STUDENT_NAME, " +
        "t.SUBJECT_CD, " +
        "t.NO AS TEST_NO, " +
        "t.POINT " +
        "FROM TEST t " +
        "JOIN STUDENT s " +
        "ON t.STUDENT_NO = s.NO " +
        "AND t.SCHOOL_CD = s.SCHOOL_CD ";
 
    // ==========================
    // ResultSet → List<Test>
    // ==========================
    private List<Test> postFilter(ResultSet rs,School school) throws Exception {
 
        List<Test> list = new ArrayList<>();
 
        while (rs.next()) {
 
            Test test = new Test();
 
            // -------------------
            // Student
            // -------------------
            Student student = new Student();
 
            student.setEntYear(
                    rs.getInt("ENT_YEAR"));
 
            student.setClassNum(
                    rs.getString("CLASS_NUM"));
 
            student.setNo(
                    rs.getString("STUDENT_NO"));
 
            student.setName(
                    rs.getString("STUDENT_NAME"));
 
            student.setSchool(school);
 
            test.setStudent(student);
 
            // -------------------
            // Subject
            // -------------------
            Subject subject = new Subject();
 
            subject.setCd(
                    rs.getString("SUBJECT_CD"));
 
            test.setSubject(subject);
 
            // -------------------
            // Test
            // -------------------
            test.setNo(
                    rs.getInt("TEST_NO"));
 
            test.setPoint(
                    rs.getInt("POINT"));
 
            list.add(test);
        }
 
        return list;
    }
 
    // ==========================
    // 成績検索
    // ==========================
    public List<Test> filter(
            int entYear,
            String classNum,
            String subjectCd,
            int num,
            School school) throws Exception {
 
        List<Test> list = new ArrayList<>();
 
        String sql =
            baseSql +
            "WHERE s.ENT_YEAR = ? " +
            "AND s.CLASS_NUM = ? " +
            "AND t.SUBJECT_CD = ? " +
            "AND t.NO = ? " +
            "AND t.SCHOOL_CD = ? " +
            "ORDER BY s.NO";
 
        try (
            Connection con = getConnection();
            PreparedStatement st =
                    con.prepareStatement(sql)
        ) {
 
            st.setInt(1, entYear);
            st.setString(2, classNum);
            st.setString(3, subjectCd);
            st.setInt(4, num);
            st.setString(5, school.getCd());
 
            ResultSet rs = st.executeQuery();
 
            list = postFilter(rs, school);
 
            rs.close();
        }
 
        return list;
    }
 
    // ==========================
    // 保存
    // ==========================
    public boolean save(
            List<Test> list) throws Exception {
        boolean result = false;
        String sql =
            "UPDATE TEST SET POINT = ? " +
            "WHERE STUDENT_NO = ? " +
            "AND SUBJECT_CD = ? " +
            "AND NO = ? " +
            "AND SCHOOL_CD = ?";
        try (
            Connection con = getConnection();
            PreparedStatement st =
                    con.prepareStatement(sql)
        ) {
            for (Test test : list) {
                st.setInt(1,test.getPoint());
                st.setString(2,test.getStudent().getNo());
                st.setString(3,test.getSubject().getCd());
                st.setInt(4,test.getNo());
                st.setString(5,test.getStudent().getSchool().getCd());
                st.addBatch();
            }
            st.executeBatch();
            result = true;
        }
        return result;
    }
}