package com.bankapp.business_logic;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.bankapp.dao.UserDAO_Ops;
import com.bankapp.model.User;

public class Create_New_User {

	private boolean running = true;
	
	Scanner scan=new Scanner(System.in);
	private String username, password = "";
	private String firstName, lastName, user_type = "";
	
	public int choice = 0;
	private int userType_choice = 0;
	
	
	public Create_New_User() {
		System.out.println("\nCreate New User Page\n");
		User user = new User();
		UserDAO_Ops userDO = new UserDAO_Ops();
		
        while(running) {				
			System.out.print("Input a Username: ");
	        username = scan.nextLine();
	        
	        try {
	        	if(username.equals("")) {
	        		System.out.println("Error: no input detected");
	        		retry();
	        	}
		        else if(userDO.checkUsername(username)) {
					user.setUsername(username);
					
					System.out.print("Input a Password: ");
				    password = scan.nextLine();
				    if(password.equals("")) {
				    	System.out.println("Error: no input detected");
		        		retry();
		        		continue;
				    }
				    user.setPassword(password);
				    
				    System.out.print("Input your legal first name: ");
				    firstName = scan.nextLine();
				    if(firstName.equals("")) {
				    	System.out.println("Error: no input detected");
		        		retry();
		        		continue;
				    }
				    user.setFirstName(firstName);
				    
				    System.out.print("Input your legal last name: ");
				    lastName = scan.nextLine();
				    if(lastName.equals("")) {
				    	System.out.println("Error: no input detected");
		        		retry();
		        		continue;
				    }
				    user.setLastName(lastName);

				    //call method to choose type
				    chooseType();
				    user.setUserType(user_type);

				    try {
						userDO.createUser(user);
					} catch (SQLException e) {
						e.printStackTrace();
					}
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
					choice = 0;
				}
			} catch(Exception e) {
				System.out.println("\nInvalid input.");
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
					System.out.println("Invalid choice detected");
				}
			}catch (NumberFormatException e) {
				System.out.println("\nInvalid input detected");
				userType_choice = 0;
			}
        }
	}
}
