package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;




// ※ここは自作のSubjectクラスに変更すること
// import model.Subject;

public class SubjectDao extends Dao {

    // 学校コードに紐づく科目一覧を取得するメソッド
    public List<Subject> findBySchoolCd(String schoolCd) throws Exception {

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

            // Subjectオブジェクトを生成
            Subject p = new Subject();

            // DBの値をSubjectにセット
            p.setSchoolCd(rs.getString("SCHOOL_CD")); // 学校コード
            p.setNAME(rs.getString("NAME"));          // 科目名

            // リストに追加
            list.add(p);
        }

        // リソースを解放
        st.close();
        con.close();

        // 結果を返す
        return list;
    }
}
