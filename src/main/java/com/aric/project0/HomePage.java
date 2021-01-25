package com.aric.project0;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Scanner;

public class HomePage {

	private int choice;
	Scanner scan = new Scanner(System.in);
	UserDAO_Ops userOps = new UserDAO_Ops();
	AccountDAO_Ops accountOps = new AccountDAO_Ops();
	double money = 0;
	
	
	public HomePage(int user_id) throws SQLException {
		
		User user = userOps.selectUser(user_id);
		System.out.println("Hello " + user.getUserId() + " " + user.getLastName() + " " + user.getFirstName() + " " + user.getLastName() + " " + user.getUserType());
		
		// UserDAO to input user_id and return a User object with states
		// if the user.userType == "Customer" call the Customer method options
		// if the user.userType == "Employee" call the Employee method options
		if (user.getUserType().equals("Customer")){
			
			System.out.println(" Call Customer Home method");
		}
		else if (user.getUserType() == "Employee") {
			System.out.println("Employee Home method");
		} 
			
			// print their accounts
			// print a newline then their options: deposit, withdraw, transfer, exit
			
	}
	
	public void CustomerHome(User user) {
		while (choice < 1 || choice > 4 ) {
			choice = 0;
			try {
				accountOps.viewUserAccounts(user.getUserId());
			
				System.out.println("\nPlease input one of the choices below:");
				System.out.println("1. Deposit\n2. Withdraw\n3. Transfer\n4. Create New Account\n5. Exit");
				System.out.print("Intake field: ");
			
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
				else if (choice == 4) {
					System.out.println("Exit Home Page");
				}
				else {
					System.out.println("\nInvalid choice detected\n"); 
				}
			} catch(NumberFormatException e) {
				System.out.println("\nInvalid input detected"); 
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void Deposit(int user_id) {
		System.out.println("Which account ID are you depositing into: ");
		try {
			int acc_choice = Integer.parseInt(scan.nextLine());
			if (accountOps.checkAccountOwnerId(user_id, acc_choice)) {
				System.out.println("How much money will you put in?");
				money = scan.nextDouble();
				if (money < 0) {
					System.out.println("Error: will not accept zero or negative money.");
				}
				else {
					accountOps.deposit(money, acc_choice);
				}
			}
			else {
				System.out.println("Error: You do not own that account.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
