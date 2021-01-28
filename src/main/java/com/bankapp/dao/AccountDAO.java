package com.bankapp.dao;

import java.sql.SQLException;

public interface AccountDAO {
	
	void createAccount(int id, String t, double amount) throws SQLException;
	
	void viewUserAccounts(int id) throws SQLException;

	void viewAllAccounts();
	void viewUnapproved();
	
	void setApproved(int employee_id, int id) throws SQLException;
	void approveAll(int employee_id) throws SQLException;
	
	void setRejected(int employee_id, int id) throws SQLException;
	
	void deposit(double d, int id) throws SQLException;
	
	void withdraw(double d, int id) throws SQLException;
	
	void transfer(double d, int id1, int id2) throws SQLException;
	
	double checkBalance(int id) throws SQLException;
	
	boolean checkAccountOwnerId(int userID, int ownerID) throws SQLException;
	
	boolean checkAccountExists(int accountID);
	
	void viewTransactionLog();
	
	boolean checkApproved(int id);
}