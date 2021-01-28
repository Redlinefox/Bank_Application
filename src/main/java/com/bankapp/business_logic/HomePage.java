package com.bankapp.business_logic;

import java.sql.*;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bankapp.dao.AccountDAO_Ops;
import com.bankapp.dao.UserDAO_Ops;
import com.bankapp.model.User;

public class HomePage {
	final static Logger logger = LogManager.getLogger(HomePage.class.getName());
	private int menuChoice;
	Scanner scan = new Scanner(System.in);
	UserDAO_Ops userOps = new UserDAO_Ops();
	AccountDAO_Ops accountOps = new AccountDAO_Ops();
	AccountActions aa = new AccountActions();
	
	public HomePage(int user_id) throws SQLException {	
		User user = userOps.selectUser(user_id);		
		if (user.getUserType().equals("Customer")){
			CustomerHome(user);
		}
		else if (user.getUserType().equals("Employee")){
			EmployeeHome(user);
		} 
	}
	
	public void CustomerHome(User user) {
		System.out.println("\n\nCustomer Menu - " + user.getFirstName() + " " + user.getLastName());
		while (menuChoice < 1 || menuChoice > 5 ) {
			menuChoice = 0;
			try {
				accountOps.viewUserAccounts(user.getUserId());
			
				System.out.println("\n[Account Options]");
				System.out.println("1. Deposit\n2. Withdraw\n3. Transfer\n4. Create New Account\n5. Exit");
				System.out.print("Input: ");
			
				menuChoice = Integer.parseInt(scan.nextLine());
				
				if (menuChoice == 1) {
					System.out.println("Deposit choice");
					aa.Deposit(user.getUserId());
					menuChoice = 0;
				}
				else if (menuChoice == 2) {
					System.out.println("Withdraw choice");
					aa.Withdraw(user.getUserId());
					menuChoice = 0;
				}
				else if (menuChoice == 3) {
					System.out.println("Transfer choice");
					aa.Transfer(user.getUserId());
					menuChoice = 0;
				}
				else if (menuChoice == 4) {
					System.out.println("Create new Account");
					aa.createAccount(user.getUserId());
					menuChoice = 0;
				}
				else if (menuChoice == 5) {
					System.out.println("\nExiting Customer Menu");
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
		System.out.println("\n\nEmployee Menu - " + user.getFirstName() + " " + user.getLastName());
		while (menuChoice < 1 || menuChoice > 4 ) {
			menuChoice = 0;
			try {
				System.out.println("\n[Employee Options]");
				System.out.println("1. Approve Accounts\n2. View Customer Accounts\n3. Transaction Log\n4. Exit");
				System.out.print("Input: ");
			
				menuChoice = Integer.parseInt(scan.nextLine());
				
				if (menuChoice == 1) {
					aa.ApproveAccounts(user.getUserId());
					menuChoice = 0;
				}
				else if (menuChoice == 2) {
					aa.ViewAllAccounts();
					menuChoice = 0;
				}
				else if (menuChoice == 3) {
					System.out.println();
					aa.ViewTransactionLog();
					menuChoice = 0;
				}
				else if (menuChoice == 4) {
					System.out.println("\nExiting Employee Menu");
				}
				else {
					System.out.println("\nInvalid choice detected\n"); 
					menuChoice = 0;
				}
			} catch(NumberFormatException e) {
				System.out.println("\nInvalid input detected"); 
				menuChoice = 0;
			}
		}
	}
}
