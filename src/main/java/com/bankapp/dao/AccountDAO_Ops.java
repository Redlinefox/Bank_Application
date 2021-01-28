package com.bankapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AccountDAO_Ops implements AccountDAO {
	final static Logger logger = LogManager.getLogger(AccountDAO_Ops.class.getName());
	
	private String pgURL = "jdbc:postgresql://localhost:5432/bank_project0";
	private String pgUsername = "postgres";
	private String pgPassword = "pgadmin";
	
	private final String createAccount = "INSERT INTO public.account(account_type, balance, last_update, approved, approved_by, owner_id) VALUES (?, ?, CURRENT_TIMESTAMP, false, 0, ?)";
	private final String viewUserAccs = "SELECT acc_id, account_type, balance, last_update, approved, owner_id FROM public.account WHERE owner_id=?";
	private final String viewAll = "SELECT acc_id, account_type, balance, CONCAT(first_name,  ' ', last_name), account.approved FROM public.account JOIN bank_user ON account.owner_id=bank_user.user_id";
	//private final String viewAll = "SELECT acc_id, account_type, balance, CONCAT(first_name,  ' ', last_name), account.approved FROM public.account JOIN bank_user ON account.owner_id=bank_user.user_id WHERE approved=false";
	private final String approved = "UPDATE public.account SET approved=true, last_update=CURRENT_TIMESTAMP, approved_date=CURRENT_TIMESTAMP, approved_by=? WHERE acc_id = ?";
	private final String rejected = "UPDATE public.account SET approved=false, last_update=CURRENT_TIMESTAMP, approved_date=CURRENT_TIMESTAMP, approved_by=? WHERE acc_id = ?";
	private final String checkBal = "SELECT balance FROM public.account WHERE acc_id=?";
	private final String accExists = "SELECT acc_id FROM public.account WHERE acc_id=?";
	private final String deposit = "UPDATE account SET balance = balance + ?, last_update = CURRENT_TIMESTAMP WHERE acc_id = ?";
	private final String withdraw = "UPDATE account SET balance = balance - ?, last_update = CURRENT_TIMESTAMP WHERE acc_id = ?";
	private final String transupdate = "INSERT INTO transaction_log(account_id, account_id2, amount, trans_type, trans_date) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP);";
	private final String transactionLog = "SELECT trans_id, account_id, account_id2, amount, tt.type_name, trans_date FROM transaction_log tl JOIN transaction_type tt ON tl.trans_type=tt.id ORDER BY trans_date DESC";
	private final String approveAll = "UPDATE public.account SET approved=true, approved_by=? WHERE approved IS NULL";
	private final String checkApproved = "SELECT approved FROM public.account WHERE acc_id = ?";
	private final String verifyOwnership = "SELECT owner_id FROM public.account WHERE acc_id=?";
	
	
	protected Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(pgURL, pgUsername, pgPassword);
			logger.info("Connected to database");
		} catch (SQLException e) {
			logger.error("Error while connecting to database. Message: " + e.getMessage());
			System.out.println("Error: unable to connect to database");
		} catch (Exception e) {
			logger.error("Error while connecting to database. Message: " + e.getMessage());
			e.printStackTrace();
		}
		return connection;
	}
	
	
	@Override
	public void createAccount(int id, String type, double amount) throws SQLException {
		try(Connection connection = getConnection()){
			PreparedStatement prep = connection.prepareStatement(createAccount);
			prep.setString(1, type);
			prep.setDouble(2, amount);
			prep.setInt(3, id);
			prep.executeUpdate();
			logger.info("Account created");
		}catch (Exception e) {
			logger.error("Error while connecting to database. Message: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public boolean checkAccountOwnerId(int userID, int account_id) throws SQLException {
		try(Connection connection = getConnection()){
			PreparedStatement prep = connection.prepareStatement(verifyOwnership);
			prep.setInt(1, account_id);
			ResultSet rs = prep.executeQuery();		
			if(rs.next()) {
				if (rs.getInt(1) == userID) {
					logger.info("Matched account owner");
					return true; 
				}
				else {
					logger.warn("Account owner not matched");
					return false; 
				}
			}
		}catch (Exception e) {
			logger.error("Error while connecting to database. Message: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public void viewUserAccounts(int id) throws SQLException {
		try(Connection connection = getConnection()){
			PreparedStatement prep = connection.prepareStatement(viewUserAccs);
			prep.setInt(1, id);
			ResultSet rs = prep.executeQuery();
			String format = "%-10s%-15s%-15s%-20.11s%-15s%n";
			System.out.printf(format, "Acc#", "Type", "Balance", "Last Update", "Status");
			
			if(rs.next()==false) {
				System.out.println("No accounts owned");
			}
			else {
				do {
					if (rs.getBoolean(5)==true) {
						System.out.printf(format, rs.getInt(1), rs.getString(2), "$"+rs.getBigDecimal(3), rs.getString(4), "Approved");
					}
					else {
						System.out.printf(format, rs.getInt(1), rs.getString(2), "$"+rs.getBigDecimal(3), rs.getString(4), "Not Approved");
					}
				}while (rs.next());
			}
		}catch (Exception e) {
			logger.error("Error while connecting to database. Message: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void viewAllAccounts() {
		try(Connection connection = getConnection()){
			PreparedStatement prep = connection.prepareStatement(viewAll);
			ResultSet rs = prep.executeQuery();
			String format = "%-10s%-15s%-15s%-20.20s%-15s%n";
			System.out.printf(format, "Acc#", "Type", "Balance", "Name", "Status");
			//System.out.print("-------------------------------------------------------------------------");
			while (rs.next()) {
				if (rs.getBoolean(5)==true) {
					System.out.printf(format, rs.getInt(1), rs.getString(2), "$"+rs.getBigDecimal(3), rs.getString(4), "Approved");
				}
				else {
					System.out.printf(format, rs.getInt(1), rs.getString(2), "$"+rs.getBigDecimal(3), rs.getString(4), "Not Approved");
				}
				
			}
		}catch (Exception e) {
			logger.error("Error while connecting to database. Message: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void viewUnapproved() {
		try(Connection connection = getConnection()){
			PreparedStatement prep = connection.prepareStatement(viewAll);
			ResultSet rs = prep.executeQuery();
			logger.info("View unapproved accounts");
			String format = "%-10s%-15s%-20.20s%-15s%n";
			System.out.println("Accounts Not Approved");
			System.out.printf(format, "Acc#", "Type", "Name", "Status");
			if(rs.next()) {
				while (rs.next()) {
					if(rs.getBoolean(5) != true) {
						System.out.printf(format, rs.getInt(1), rs.getString(2), rs.getString(4), "Not Approved");
					}
				}
			}
			else {
				System.out.println("Empty\n\n");
			}
			
		}catch (Exception e) {
			logger.error("Error while connecting to database. Message: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void setApproved(int employee, int accountID) throws SQLException {
		try(Connection connection = getConnection()){
			PreparedStatement prep = connection.prepareStatement(approved);
			prep.setInt(1, employee);
			prep.setInt(2, accountID);
			prep.executeUpdate();
			logger.info("Account approved by Employee ID: " + employee);
		}catch (Exception e) {
			logger.error("Error while connecting to database. Message: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Override
	public void approveAll(int employee) throws SQLException {
		try(Connection connection = getConnection()){
			PreparedStatement prep = connection.prepareStatement(approveAll);
			prep.setInt(1, employee);
			prep.executeUpdate();
			logger.info("All accounts approved by Employee ID: " + employee);
		}catch (Exception e) {
			logger.error("Error while connecting to database. Message: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Override
	public void setRejected(int employee, int accountID) throws SQLException {
		try(Connection connection = getConnection()){
			PreparedStatement prep = connection.prepareStatement(rejected);
			prep.setInt(1, employee);
			prep.setInt(2, accountID);
			prep.executeUpdate();
		}catch (Exception e) {
			logger.error("Error while connecting to database. Message: " + e.getMessage());
			e.printStackTrace();
		}
	}


	@Override
	public void deposit(double d, int accountID) throws SQLException {
		try(Connection connection = getConnection()){
			PreparedStatement prep = connection.prepareStatement(deposit);
			prep.setDouble(1, d);
			prep.setInt(2, accountID);
			prep.executeUpdate();
			prep = connection.prepareStatement(transupdate);
			prep.setInt(1, accountID);
			prep.setInt(2, 0);
			prep.setDouble(3, d);
			prep.setInt(4, 1);
			prep.executeUpdate();
			System.out.println("Deposit successful");
			logger.info("Deposit successful");
		}catch (Exception e) {
			logger.error("Error while connecting to database. Message: " + e.getMessage());
			e.printStackTrace();
		}
		
	}


	@Override
	public void withdraw(double d, int accountID) throws SQLException {
		try(Connection connection = getConnection()){
			PreparedStatement prep = connection.prepareStatement(withdraw);
			prep.setDouble(1, d);
			prep.setInt(2, accountID);
			prep.executeUpdate();
			prep = connection.prepareStatement(transupdate);
			prep.setInt(1, accountID);
			prep.setInt(2, 0);
			prep.setDouble(3, d);
			prep.setInt(4, 2);
			prep.executeUpdate();
			System.out.println("Withdraw successful");
			logger.info("Withdraw successful");
		}catch (Exception e) {
			logger.error("Error while connecting to database. Message: " + e.getMessage());
			e.printStackTrace();
		}
		
	}


	@Override
	public void transfer(double d, int id1, int id2) throws SQLException {
		try(Connection connection = getConnection()){
			PreparedStatement prep = connection.prepareStatement(withdraw);
			prep.setDouble(1, d);
			prep.setInt(2, id1);
			prep.executeUpdate();
			prep = connection.prepareStatement(deposit);
			prep.setDouble(1, d);
			prep.setInt(2, id2);
			prep.executeUpdate();
			prep = connection.prepareStatement(transupdate);
			prep.setInt(1, id1);
			prep.setInt(2, id2);
			prep.setDouble(3, d);
			prep.setInt(4, 3);
			prep.executeUpdate();
			System.out.println("Transfer successful");
			logger.info("Transfer successful");
		}catch (Exception e) {
			logger.error("Error while connecting to database. Message: " + e.getMessage());
			e.printStackTrace();
		}
	}


	@Override
	public double checkBalance(int accountID) throws SQLException {
		try(Connection connection = getConnection()){
			PreparedStatement prep = connection.prepareStatement(checkBal);
			prep.setInt(1, accountID);
			ResultSet rs = prep.executeQuery();
			if (rs.next()) {
				return rs.getDouble(1);
			}
		}catch (Exception e) {
			logger.error("Error while connecting to database. Message: " + e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}


	@Override
	public boolean checkAccountExists(int accountID) {
		try(Connection connection = getConnection()){
			PreparedStatement prep = connection.prepareStatement(accExists);
			prep.setInt(1, accountID);
			ResultSet rs = prep.executeQuery();
			if(rs.next()) {
				return (rs.getInt(1) == accountID) ? true : false;
			}
		}catch (Exception e) {
			logger.error("Error while connecting to database. Message: " + e.getMessage());
			e.printStackTrace();
		}
		logger.error("Failure to check account");
		return false;
	}


	@Override
	public void viewTransactionLog() {
		try(Connection connection = getConnection()){
			PreparedStatement prep = connection.prepareStatement(transactionLog);
			ResultSet rs = prep.executeQuery();
			String format = "%-20s%-15s%-15s%-20.11s%-15s%-15s%n";
			System.out.printf(format, "TRANSACTION#", "TYPE", "ACCOUNT 1", "ACCOUNT 2", "AMOUNT", "TIMESTAMP");
			while (rs.next()) {
				if (rs.getInt(3)==0) {
					System.out.printf(format, rs.getInt(1), rs.getString(5), rs.getInt(2), "NA", rs.getDouble(4), rs.getTimestamp(6));
				}
				else {
					System.out.printf(format, rs.getInt(1), rs.getString(5), rs.getInt(2), rs.getInt(3), rs.getDouble(4), rs.getTimestamp(6));
				}
			}
		}catch (Exception e) {
			logger.error("Error while connecting to database. Message: " + e.getMessage());
			e.printStackTrace();
		}
	}


	@Override
	public boolean checkApproved(int account_id) {
		try(Connection connection = getConnection()){
			PreparedStatement prep = connection.prepareStatement(checkApproved);
			prep.setInt(1, account_id);
			ResultSet rs = prep.executeQuery();
			if(rs.next()) {
				System.out.println("checkApproved rs.getBoolean: " + rs.getBoolean(1));
				return rs.getBoolean(1);
			}
		}catch (Exception e) {
			logger.error("Error while connecting to database. Message: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	
}
