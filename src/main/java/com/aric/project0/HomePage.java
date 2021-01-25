package com.aric.project0;

import java.sql.*;
import java.util.Scanner;

public class HomePage {

	private int choice;
	Scanner scan = new Scanner(System.in);
	UserDAO_Ops uDO = new UserDAO_Ops();
	AccountDAO_Ops aDO = new AccountDAO_Ops();
	
	public HomePage(int user_id) throws SQLException {
		
		User user = uDO.selectUser(user_id);
		System.out.println("Hello " + user.getUserId() + " " + user.getLastName() + " " + user.getFirstName() + " " + user.getLastName() + " " + user.getUserType());
		
		// UserDAO to input user_id and return a User object with states
		// if the user.userType == "Customer" call the Customer method options
		// if the user.userType == "Employee" call the Employee method options
		if (user.getUserType().equals("Customer")){
			System.out.println("Customer Home method");
			try {
				aDO.viewUserAccounts(user_id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (user.getUserType() == "Employee") {
			System.out.println("Employee Home method");
		} 
			
			// print their accounts
			// print a newline then their options: deposit, withdraw, transfer, exit
			
	}
	
	public void CustomerHome(User user) {
		while (choice < 1 || choice > 3 ) {
			choice = 0;
			
			try {
				aDO.viewUserAccounts(2);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			//aDO.viewUserAccounts(user.getUserId());
			System.out.println("\nPlease input one of the choices below:");
			System.out.println("1. Deposit\n2. Withdraw\n3. Transfer\n4. Create New Account\n5. Exit");
			System.out.print("Intake field: ");
			
			try {
				choice = Integer.parseInt(scan.nextLine());
				
				if (choice == 1) {
					System.out.println("Choice 1");
				}
				else if (choice == 2) {
					System.out.println("Choice 2");
				}
				else if (choice == 3) {
					System.out.println("Choice 3");
				}
				else {
					System.out.println("\nInvalid choice detected\n"); 
				}
			}
			catch(NumberFormatException e) {
				System.out.println("\nInvalid input detected"); 
			}
		}
	}
}
