package jp.co.ssd.bi.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ssd.bi.dao.UserDao;
import jp.co.ssd.bi.model.User;
import jp.co.ssd.bi.util.DBUtil;

@Service
public class UserDaoImpl implements UserDao {
	@Autowired
	DBUtil dbutil;
	@Override
	public User getUser(String username) {
		User user = new User();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = dbutil.getConn();
        try {
            String sql = "select * from public.user where username = '" + username + "'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
            	user.setUsername(rs.getString("username"));
            	user.setPassword(rs.getString("password"));
            	user.setRoles(rs.getString("roles"));
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return user;	
	}

}
