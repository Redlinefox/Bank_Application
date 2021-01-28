package com.bankapp.dao;

import java.sql.SQLException;
import java.util.List;

import com.bankapp.model.User;

public interface UserDAO<T> {
	
	void createUser(T t) throws SQLException;
	
	User selectUser(int id) throws SQLException;

	List<T> selectAllUsers();
	
	boolean updateUser(T t) throws SQLException;
	
	boolean deleteUser(int id) throws SQLException;
	
	int matchUsernamePass() throws SQLException;
	
	boolean checkUsername(String username) throws SQLException;
	void loginTime(int id);
}
