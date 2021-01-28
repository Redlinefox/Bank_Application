package com.bankapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.bankapp.model.User;

public class UserDAO_Ops implements UserDAO<User>{
	private String pgURL = "jdbc:postgresql://localhost:5432/bank_project0";
	private String pgUsername = "postgres";
	private String pgPassword = "pgadmin";
	
	private final String sqlCreateUser = "INSERT INTO public.bank_user(username, password, first_name, last_name, user_type, created_on) VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
	private final String getUser = "SELECT user_id, first_name, last_name, user_type FROM public.bank_user WHERE user_id = ?";
	private final String checkUsernamePass = "SELECT username, password, user_id FROM bank_user WHERE username =?";
	private final String updateLogin = "UPDATE public.bank_user SET last_login=CURRENT_TIMESTAMP WHERE user_id = ?";
	
	
	Scanner scan = new Scanner(System.in);
	User user = new User();
	
	
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
	public void createUser(User user) throws SQLException {
		try(Connection connection = getConnection()){
			PreparedStatement prep = connection.prepareStatement(sqlCreateUser);
			prep.setString(1, user.getUsername());
			prep.setString(2, user.getPassword());
			prep.setString(3, user.getFirstName());
			prep.setString(4, user.getLastName());
			prep.setString(5, user.getUserType());
			prep.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}	
	}

	@Override
	public User selectUser(int id) throws SQLException {
		try(Connection connection = getConnection()){
			PreparedStatement prep = connection.prepareStatement(getUser);
			prep.setInt(1, id);

			ResultSet rs = prep.executeQuery();
			if (rs.next()) {
				user.setUserId(rs.getInt(1));
				user.setFirstName(rs.getString(2));
				user.setLastName(rs.getString(3));
				user.setUserType(rs.getString(4));
				return user;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<User> selectAllUsers() {

		return null;
	}

	@Override
	public boolean updateUser(User user) throws SQLException {

		return false;
	}

	@Override
	public boolean deleteUser(int id) throws SQLException {

		return false;
	}

	@Override
	public int matchUsernamePass() {
		try(Connection connection = getConnection()){
			PreparedStatement prep = connection.prepareStatement(checkUsernamePass);
			
			System.out.print("Input your Username: ");
	        String username = scan.nextLine();
	        prep.setString(1, username);
			ResultSet rs = prep.executeQuery();
			
			if (rs.next()) {
				if (rs.getString(1).equals(username)) {
					System.out.print("Input a Password: ");
			        String password = scan.nextLine();	 
			        
			        if (rs.getString(2).equals(password)) {		
		        		return rs.getInt(3);
			        }
			        else {
			        	System.out.println("\nPassword does not match user account");
			        	return 0;
			        }
				}
				else {
					System.out.println("\nUsername does not exist.");
					return 0;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public boolean checkUsername(String username) throws SQLException {
		try(Connection connection = getConnection()){
			PreparedStatement prep = connection.prepareStatement(checkUsernamePass);
	        prep.setString(1, username);
			ResultSet rs = prep.executeQuery();
			
			if (rs.next()) {
				if (rs.getString(1).equals(username)) {
					System.out.println("Username already exists.\n");
					return false;
				}
			}
			else {
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void loginTime(int id) {
		try(Connection connection = getConnection()){
			PreparedStatement prep = connection.prepareStatement(updateLogin);
			prep.setInt(1, id);
			prep.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}	
		
	}
	
	
}
