package dao;

 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

 

import bean.Student;
import bean.TestListStudent;

 

public class TestListStudentDao extends Dao {
	
	
	// SQL共通部分
	private String baseSql =
			"SELECT "
	  + "sub.CD AS SUBJECT_CD, "
	  + "sub.NAME AS SUBJECT_NAME, "
      + "t.NO AS TEST_NO, "
	  + "t.POINT, "
      + "s.NAME AS STUDENT_NAME "
	  + "FROM TEST t "
      + "JOIN SUBJECT sub "
	  + "ON t.SUBJECT_CD = sub.CD "
      + "JOIN STUDENT s "
	  + "ON t.STUDENT_NO = s.NO ";

	// ResultSet → List<TestListStudent>
	private List<TestListStudent> postFilter(ResultSet rs) throws Exception {
		List<TestListStudent> list = new ArrayList<>();
		while (rs.next()) {
			TestListStudent test = new TestListStudent();
			test.setSubjectCd(rs.getString("SUBJECT_CD"));
			test.setSubjectName(rs.getString("SUBJECT_NAME"));
			test.setNum(rs.getInt("TEST_NO"));
			test.setPoint(rs.getInt("POINT"));
			list.add(test);
			}
		
		
		
		return list;
		}

	// 成績参照
	public List<TestListStudent> filter(Student student)
			throws Exception {
		List<TestListStudent> list = new ArrayList<>();
		String sql =baseSql+ "WHERE t.STUDENT_NO = ? "+ "ORDER BY sub.CD, t.NO";
		try(
		    Connection con = getConnection();
		    PreparedStatement st = con.prepareStatement(sql)
		    ) {
			st.setString(1, student.getNo());
			ResultSet rs = st.executeQuery();
			list = postFilter(rs);
			rs.close();
			}
		
		return list;
		}
	}

