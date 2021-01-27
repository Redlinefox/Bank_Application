package com.bankapp.dao;

import java.sql.SQLException;
import java.util.List;

import com.bankapp.model.User;

public interface AccountDAO<T> {
	
	void createAccount(int id, String t, double d) throws SQLException;
	
	void viewUserAccounts(int id) throws SQLException;

	List<T> viewAllAccounts();
	
	void setApproved(User u, int id) throws SQLException;
	
	void setRejected(User u, int id) throws SQLException;
	
	void deposit(double d, int id) throws SQLException;
	
	void withdraw(double d, int id) throws SQLException;
	
	void transfer(double d, int id1, int id2) throws SQLException;
	
	double checkBalance(int id) throws SQLException;
	
	boolean checkAccountOwnerId(int userID, int ownerID) throws SQLException;
	
	boolean checkAccountExists(int accountID);
	
	void updateLog(int id, int id2, double d, int type);
	void updateLog(int id, double d, int type);
	
}