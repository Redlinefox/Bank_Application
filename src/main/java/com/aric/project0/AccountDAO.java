package com.aric.project0;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface AccountDAO<T> {
	
	void createAccount(User u, T t, double d) throws SQLException;
	
	void viewUserAccounts(int id) throws SQLException;

	List<T> viewAllAccounts();
	
	void setApproved(User u, int id) throws SQLException;
	
	void setRejected(User u, int id) throws SQLException;
	
	void deposit(double d, int id) throws SQLException;
	
	void withdraw(double d, int id) throws SQLException;
	
	void transfer(double d, int id1, int id2) throws SQLException;
	
	BigDecimal checkBalance(int id) throws SQLException;
	
	boolean checkAccountOwnerId(int userID, int ownerID) throws SQLException;
	
}