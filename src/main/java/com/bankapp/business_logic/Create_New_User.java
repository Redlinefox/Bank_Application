package com.bankapp.business_logic;

import java.sql.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bankapp.dao.UserDAO_Ops;
import com.bankapp.model.User;

public class Create_New_User {
	final static Logger logger = LogManager.getLogger(Create_New_User.class.getName());
	private boolean running = true;
	
	Scanner scan=new Scanner(System.in);
	private String username, password = "";
	private String firstName, lastName, user_type = "";
	
	String userRegex = "^[a-zA-Z0-9]+$";
	String nameRegex = "^[a-zA-Z]+$";
	Pattern pattern = Pattern.compile(userRegex);
	Pattern pattern2 = Pattern.compile(nameRegex);
	public int choice = 0;
	private int userType_choice = 0;
	
	
	public Create_New_User() {
		logger.info("Entering Create_New_User");
		System.out.println("\nCreate New User Page\n");
		User user = new User();
		UserDAO_Ops userDO = new UserDAO_Ops();
		
        while(running) {				
			System.out.print("Input a Username: ");
	        username = scan.nextLine();
	        Matcher matcher = pattern.matcher(username);
	        try {
	        	if(username.equals("")) {
	        		System.out.println("Error: no input detected");
	        		logger.warn("No user input with ENTRY");
	        		retry();
	        		continue;
	        	}
	        	else if(!matcher.matches()) {
	        		System.out.println("Error: choose a username with letters and numbers in it");
	        		logger.warn("Improper text with REGEX");
	        		retry();
	        		continue;
	        	}
		        else if(userDO.checkUsername(username)) {
					user.setUsername(username);
					System.out.print("Input a Password: ");
				    password = scan.nextLine();
				    if(password.equals("")) {
				    	System.out.println("Error: no input detected");
				    	logger.warn("No user input with ENTRY");
		        		retry();
		        		continue;
				    }
				    user.setPassword(password);
				    
				    System.out.print("Input your legal first name: ");
				    firstName = scan.nextLine();
				    matcher = pattern2.matcher(firstName);
				    if(firstName.equals("")) {
				    	System.out.println("Error: no input detected");
				    	logger.warn("No user input with ENTRY");
		        		retry();
		        		continue;
				    }
				    else if(!matcher.matches()) {
				    	System.out.println("Error: first name must only have letters in it");
				    	logger.warn("Improper text with REGEX");
		        		retry();
		        		continue;
				    }
				    else {
				    	user.setFirstName(firstName);
				    }
				    
				    System.out.print("Input your legal last name: ");
				    lastName = scan.nextLine();
				    matcher = pattern2.matcher(lastName);
				    if(lastName.equals("")) {
				    	System.out.println("Error: no input detected");
				    	logger.warn("No user input with ENTRY");
		        		retry();
		        		continue;
				    }
				    else if(!matcher.matches()) {
				    	System.out.println("Error: last name must only have letters in it");
				    	logger.warn("Improper text with REGEX");
		        		retry();
		        		continue;
				    }
				    else {
				    	user.setLastName(lastName);
				    }
				    
				    //Choose User Type: Customer or Employee
				    chooseType();
				    user.setUserType(user_type);

					userDO.createUser(user);
				    System.out.println("\nNew User Created!");
				}
				else {
					retry();	
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}		
        }
	}
	
	public void retry() {
		choice = 0;
		while (choice < 1 || choice > 2) {
			try{
				System.out.println("\nDo you want to start over or exit?\n1: Start Over\n2: Exit to Login Menu");
				System.out.print("Input: ");
				choice = Integer.parseInt(scan.nextLine());
				if (choice == 1) {
					System.out.println("\nStarting over\n\n");
					running = true;
					username = "";		
				}
				else if (choice ==2) {
					System.out.println("\nExitting to Login Menu");
					running = false;
				}
				else {
					System.out.println("\nInvalid choice detected");
					logger.warn("Invalid selection detected");
					choice = 0;
				}
			} catch(Exception e) {
				System.out.println("\nInvalid input.");
				logger.warn("Invalid input detected");
				//scan.next();
				choice = 0;
			}
		}
	}
	
	public void chooseType() {
		userType_choice = 0;
		while(userType_choice < 1 || userType_choice > 2) {
			try {
				System.out.print("Are you a Customer (1) or an Employee (2): ");
		        userType_choice = Integer.parseInt(scan.nextLine());
		        if (userType_choice == 1) {
					user_type = "Customer";
					running = false;
				}
				else if (userType_choice ==2) {
					user_type = "Employee";
					running = false;
				}
				else {
					System.out.println("\nInvalid choice detected");
				}
			}catch (NumberFormatException e) {
				System.out.println("\nInvalid input detected");
				userType_choice = 0;
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
	}
}
