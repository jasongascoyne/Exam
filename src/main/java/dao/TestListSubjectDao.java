
package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bean.TestListSubject;

public class TestListSubjectDao extends Dao {

	public List<TestListSubject> filter(int entYear, String classNum, String subjectCd) throws Exception {
		String sql = "SELECT s.ENT_YEAR, s.CLASS_NUM, s.NO AS STUDENT_NO, s.NAME AS STUDENT_NAME, t.NO AS TEST_NO, t.POINT "
	               + "FROM TEST t JOIN STUDENT s ON t.STUDENT_NO = s.NO AND t.SCHOOL_CD = s.SCHOOL_CD "
				   + "WHERE s.ENT_YEAR = ? AND s.CLASS_NUM = ? AND t.SUBJECT_CD = ? "
                   + "ORDER BY s.NO, t.NO";
		try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
			st.setInt(1, entYear);
			st.setString(2, classNum);
			st.setString(3, subjectCd);
			ResultSet rs = st.executeQuery();
			List<TestListSubject> list = postFilter(rs);
			rs.close();
			return list;
			}
		}

	private List<TestListSubject> postFilter(ResultSet rs) throws Exception {
		List<TestListSubject> list = new ArrayList<>();
		String currentNo = null;
		TestListSubject current = null;
		
		while (rs.next()) {
			String studentNo = rs.getString("STUDENT_NO");

			if (!studentNo.equals(currentNo)) {
				current = new TestListSubject();
				current.setEntYear(rs.getInt("ENT_YEAR"));
				current.setClassNum(rs.getString("CLASS_NUM"));
				current.setStudentNo(studentNo);
				current.setStudentName(rs.getString("STUDENT_NAME"));
				current.setPoints(new HashMap<>());
				list.add(current);
				currentNo = studentNo;
				}

			current.getPoints().put(rs.getInt("TEST_NO"), rs.getInt("POINT"));
			}
		
		return list;
		}
	}
