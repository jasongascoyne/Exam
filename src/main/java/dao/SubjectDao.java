package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
// ※ここは自作のSubjectクラスに変更すること

// import model.Subject;

public class SubjectDao extends Dao {
	// 学校コードに紐づく科目一覧を取得するメソッド
	public List<Subject> filter(String schoolCd) throws Exception {
		// 結果を格納するリスト
		List<Subject> list = new ArrayList<>();
		
		// DB接続を取得
		Connection con = getConnection();
		
		// SQL文を準備（? はプレースホルダ）
		PreparedStatement st = con.prepareStatement(
				"SELECT * FROM SUBJECT WHERE SCHOOL_CD = ?"
				);
		
		// プレースホルダに値をセット（1番目の ? に schoolCd を入れる）
		st.setString(1, schoolCd);
		
		// SQLを実行して結果を取得
		ResultSet rs = st.executeQuery();
		
		// 結果を1行ずつ取り出す
		while (rs.next()) {
			Subject p = new Subject();
			
			// 科目名
			p.setName(rs.getString("NAME"));
			
			// Schoolオブジェクトを作成してセット
			School school = new School();
			school.setCd(rs.getString("SCHOOL_CD"));
			p.setSchool(school);
			list.add(p);
			}
		
		// リソースを解放
		st.close();
		con.close();
		
		// 結果を返す
		return list;
		}
	
	// ----------------------------
	//  新規登録
	// ----------------------------
	public void insert(Subject subject) throws Exception {
		try (Connection con = getConnection();
				PreparedStatement st = con.prepareStatement(
						"INSERT INTO SUBJECT (SCHOOL_CD, SUBJECT_CD, NAME) VALUES (?, ?, ?)"
						)) {
			
			// ★ Schoolオブジェクトから取得
			st.setString(1, subject.getSchool().getCd());
			// ★ cd
			st.setString(2, subject.getCd());
			// ★ name
			st.setString(3, subject.getName());
			st.executeUpdate();
			}
		}
	}
