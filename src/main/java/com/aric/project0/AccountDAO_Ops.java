package com.aric.project0;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
//import java.math.BigDecimal;

public class AccountDAO_Ops implements AccountDAO<Account> {

	private String pgURL = "jdbc:postgresql://localhost:5432/bank_project0";
	private String pgUsername = "postgres";
	private String pgPassword = "pgadmin";
	
	private final String createAccount = "INSERT INTO public.account(account_type, balance, last_update, owner_id) VALUES (?, ?, CURRENT_TIMESTAMP, ?)";
	private final String viewUserAccs = "SELECT acc_id, account_type, balance, last_update, approved, owner_id FROM public.account WHERE owner_id=?";
	private final String approved = "UPDATE public.account SET approved=true, last_update=CURRENT_TIMESTAMP, approved_date=CURRENT_TIMESTAMP, approved_by=? WHERE acc_id = ?";
	private final String rejected = "UPDATE public.account SET approved=false, last_update=CURRENT_TIMESTAMP, approved_date=CURRENT_TIMESTAMP, approved_by=? WHERE acc_id = ?";
	private final String deposit = "UPDATE public.account SET balance = balance + ?, last_update=CURRENT_TIMESTAMP, WHERE acc_id = ?";
	private final String withdraw = "UPDATE public.account SET balance = balance - ?, last_update=CURRENT_TIMESTAMP, WHERE acc_id = ?";
	private final String checkBal = "SELECT balance FROM public.account WHERE acc_id=?";
	private final String checkUsername = "SELECT username FROM bank_user WHERE username =?";
	
	protected Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(pgURL, pgUsername, pgPassword);
		} catch (SQLException e) {
			System.out.println("Error: unable to connect to database");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	
	@Override
	public void createAccount(User user, Account acc, double start) throws SQLException {
		try(Connection connection = getConnection()){
			PreparedStatement prep = connection.prepareStatement(viewUserAccs);
			prep.setString(1, acc.getAccountType());
			prep.setDouble(2, start);
			prep.setInt(4, user.getUserId());
			prep.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean checkAccountOwnerId(int userID, int ownerID) throws SQLException {
		try(Connection connection = getConnection()){
			PreparedStatement prep = connection.prepareStatement(viewUserAccs);
			prep.setInt(1, userID);
			ResultSet rs = prep.executeQuery();
			
			if(rs.next()) {
				if (rs.getInt(5) == userID) { return true; }
				else { return false; }
			}
		}catch (Exception e) {
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
			while (rs.next()) {
				System.out.printf(format, rs.getInt(1), rs.getString(2), rs.getBigDecimal(3), rs.getString(4), rs.getBoolean(5));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Account> viewAllAccounts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setApproved(User user, int accountID) throws SQLException {
		try(Connection connection = getConnection()){
			PreparedStatement prep = connection.prepareStatement(approved);
			prep.setInt(1, user.getUserId());
			prep.setInt(2, accountID);
			prep.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setRejected(User user, int accountID) throws SQLException {
		try(Connection connection = getConnection()){
			PreparedStatement prep = connection.prepareStatement(rejected);
			prep.setInt(1, user.getUserId());
			prep.setInt(2, accountID);
			prep.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void deposit(double d, int accountID) throws SQLException {
		try(Connection connection = getConnection()){
			PreparedStatement prep = connection.prepareStatement(approved);
			prep.setDouble(1, d);
			prep.setInt(2, accountID);
			prep.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	@Override
	public void withdraw(double d, int accountID) throws SQLException {
		try(Connection connection = getConnection()){
			PreparedStatement prep = connection.prepareStatement(approved);
			prep.setDouble(1, d);
			prep.setInt(2, accountID);
			prep.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	@Override
	public void transfer(double d, int id1, int id2) throws SQLException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public BigDecimal checkBalance(int accountID) throws SQLException {
		try(Connection connection = getConnection()){
			PreparedStatement prep = connection.prepareStatement(checkBal);
			prep.setInt(1, accountID);
			ResultSet rs = prep.executeQuery();
			if (rs.next()) {
				return rs.getBigDecimal(1);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
