package com.bankapp.business_logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.bankapp.dao.UserDAO_Ops;

public class Login {

	private String jdbcURL = "jdbc:postgresql://localhost:5432/bank_project0";
	private String pg_user = "postgres";
	private String pg_password = "pgadmin";
	
	private boolean running = true;
	Scanner scan = new Scanner(System.in);
	private int userID = 0;
	private String username, password = "";
	
	private String sql_CheckName;
	private int choice;
	
	UserDAO_Ops uDAO = new UserDAO_Ops();
	
	public Login(){
		System.out.println("\nLog In Page\n");

        while(running) {				
        	userID = uDAO.matchUsernamePass();
	        if(userID > 0) {
	        	try {
	        		new HomePage(userID);
	        	} 
	        	catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	        else {
	        	loginRetry();
	        }
        }       
	}
	
	
	public void loginRetry() {
		choice = 0;
		while (choice < 1 || choice > 2) {
			try{
				System.out.println("Do you want to start over or exit?\n1: Start Over\n2: Exit to Login Menu");
				choice = Integer.parseInt(scan.nextLine());
				if (choice == 1) {
					System.out.println("\nStarting over");
					running = true;
					username = "";		
				}
				else if (choice ==2) {
					System.out.println("\nExitting to Login Menu");
					running = false;
				}
				else {
					System.out.println("\nInvalid choice detected");
				}
			}
			catch(InputMismatchException e) {
				System.out.println("\nInvalid input detected"); 
				choice = 0;
			}
		}
	}
}
