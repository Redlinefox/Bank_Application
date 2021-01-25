package com.aric.project0;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

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
				if(userDO.checkUsername(username)) {
					System.out.print("Input a Password: ");
				    password = scan.nextLine();
				    user.setPassword(password);
				    
				    System.out.print("Input your legal first name: ");
				    firstName = scan.nextLine();
				    user.setFirstName(firstName);
				    
				    System.out.print("Input your legal last name: ");
				    lastName = scan.nextLine();
				    user.setLastName(lastName);
				    // call method to set user_type
				    chooseType();
				    user.setUserType(user_type);

				    try {
						userDO.createUser(user);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				    System.out.println("New User Created!");
				}
				else {
					retry();
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
        }
	}
	
	public void retry() {
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
					scan.next();
				}
			}
			catch(InputMismatchException e) {
				System.out.println("\nInvalid input detected"); 
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
