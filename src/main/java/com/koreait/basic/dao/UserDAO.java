package com.koreait.basic.dao;

import com.koreait.basic.DbUtils;
import com.koreait.basic.user.model.UserEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserDAO {
    public static int join(UserEntity entity) {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = " INSERT INTO t_user (uid, upw, nm, gender) " +
                    " VALUES (?, ?, ?, ?) ";

        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setString(1, entity.getUid());
            ps.setString(2, entity.getUpw());
            ps.setString(3, entity.getNm());
            ps.setInt(4, entity.getGender());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps);
        }
        return 0;
    }
}
