package com.bankapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TransactionLog_Test {

	private String pgURL = "jdbc:postgresql://localhost:5432/bank_project0";
	private String pgUsername = "postgres";
	private String pgPassword = "pgadmin";
	private final String transactionLog = "SELECT trans_id, account_id, account_id2, amount, tt.type_name, trans_date FROM transaction_log tl JOIN transaction_type tt ON tl.trans_type=tt.id ORDER BY trans_date DESC";

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
	
	@Test
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
			e.printStackTrace();
		}
	}
}
