package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {

    // 一覧取得
    public List<Subject> filter(String schoolCd) throws Exception {

        List<Subject> list = new ArrayList<>();
        Connection con = getConnection();

        PreparedStatement st = con.prepareStatement(
            "SELECT * FROM SUBJECT WHERE SCHOOL_CD = ?"
        );

        st.setString(1, schoolCd);

        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Subject p = new Subject();

            // ★ CDに変更
            p.setCd(rs.getString("CD"));
            p.setName(rs.getString("NAME"));

            School school = new School();
            school.setCd(rs.getString("SCHOOL_CD"));

            p.setSchool(school);

            list.add(p);
        }

        rs.close();
        st.close();
        con.close();

        return list;
    }

    // 1件取得
    public Subject find(String schoolCd, String cd) throws Exception {

        Subject subject = null;

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(
                 "SELECT * FROM SUBJECT WHERE SCHOOL_CD = ? AND CD = ?"
             )) {

            st.setString(1, schoolCd);
            st.setString(2, cd);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                subject = new Subject();

                subject.setCd(rs.getString("CD"));
                subject.setName(rs.getString("NAME"));

                School school = new School();
                school.setCd(rs.getString("SCHOOL_CD"));

                subject.setSchool(school);
            }

            rs.close();
        }

        return subject;
    }

    // 新規登録
    public void insert(Subject subject) throws Exception {

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(
                 "INSERT INTO SUBJECT (SCHOOL_CD, CD, NAME) VALUES (?, ?, ?)"
             )) {

            st.setString(1, subject.getSchool().getCd());
            st.setString(2, subject.getCd());
            st.setString(3, subject.getName());

            st.executeUpdate();
        }
    }

    // 更新
    public int update(Subject subject) throws Exception {

        int result = 0;

        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(
                 "UPDATE SUBJECT SET NAME = ? WHERE SCHOOL_CD = ? AND CD = ?"
             )) {

            st.setString(1, subject.getName());
            st.setString(2, subject.getSchool().getCd());
            st.setString(3, subject.getCd());

            result = st.executeUpdate();
        }

        return result;
    }
}
