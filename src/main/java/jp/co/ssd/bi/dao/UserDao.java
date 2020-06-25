package jp.co.ssd.bi.dao;

import jp.co.ssd.bi.model.User;


public interface UserDao {
	public User getUser(String username);
}
