package com.bankapp.business_logic;

import java.sql.*;
import java.util.Scanner;
import com.bankapp.dao.AccountDAO_Ops;
import com.bankapp.dao.UserDAO_Ops;
import com.bankapp.model.User;

public class HomePage {

	private int menuChoice;
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
			CustomerHome(user);
		}
		else if (user.getUserType() == "Employee") {
			EmployeeHome(user);
		} 
	}
	
	public void CustomerHome(User user) {
		System.out.println("Customer Menu");
		while (menuChoice < 1 || menuChoice > 4 ) {
			menuChoice = 0;
			try {
				accountOps.viewUserAccounts(user.getUserId());
			
				System.out.println("\nPlease input one of the choices below:");
				System.out.println("1. Deposit\n2. Withdraw\n3. Transfer\n4. Create New Account\n5. Exit");
				System.out.print("Input: ");
			
				menuChoice = Integer.parseInt(scan.nextLine());
				
				if (menuChoice == 1) {
					System.out.println("Deposit choice");
					menuChoice = 0;
				}
				else if (menuChoice == 2) {
					System.out.println("Withdraw");
					menuChoice = 0;
				}
				else if (menuChoice == 3) {
					System.out.println("Transfer");
					menuChoice = 0;
				}
				else if (menuChoice == 4) {
					System.out.println("Exit");
				}
				else {
					System.out.println("\nInvalid choice detected\n"); 
					menuChoice = 0;
				}
			} catch(NumberFormatException e) {
				System.out.println("\nInvalid input detected"); 
				menuChoice = 0;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void EmployeeHome(User user) {
		System.out.println("Employee Menu");
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
