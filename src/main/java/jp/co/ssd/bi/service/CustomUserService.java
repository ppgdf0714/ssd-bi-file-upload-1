package jp.co.ssd.bi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import jp.co.ssd.bi.dao.UserDao;
import jp.co.ssd.bi.model.User;

public class CustomUserService implements UserDetailsService { 

	@Autowired
    UserDao userDao;
	
    @Override
    public UserDetails loadUserByUsername(String username) {
    	
    	try {
            User user = userDao.getUser(username);
            if (user == null) {
                throw new UsernameNotFoundException("ユーザーが存在しません。");
            }
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            if (!"".equals(user.getRoles())) {
                String[] roles = user.getRoles().split(",");
                for (String role : roles) {
                    if (!"".equals(role)) {
                        authorities.add(new SimpleGrantedAuthority(role.trim()));
                    }
                }
            }
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
//	/**
//	 * ユーザー検索
//	 * 
//	 * @param filetype ファイルタイプ
//	 * @param excelData エクセル情報
//	 * @return String cell情報
//	 * @throws SQLException 
//	 * @throws IllegalAccessException 
//	 * @throws InstantiationException 
//	 * @throws ClassNotFoundException 
//	 */	
//	public User getUser(String username){
//		User user = new User();
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        try {
//            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "test", "test");
//            String sql = "select * from public.user where username = '" + username + "'";
//            ps = conn.prepareStatement(sql);
//            rs = ps.executeQuery();
//            while(rs.next()) {
//            	user.setUsername(rs.getString("username"));
//            	user.setPassword(rs.getString("password"));
//            	user.setRoles(rs.getString("roles"));
//            }
//        }  catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            if(conn != null) {
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//            if(rs != null) {
//                try {
//                    rs.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//            if(ps != null) {
//                try {
//                    ps.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return user;
//	}
    
}
