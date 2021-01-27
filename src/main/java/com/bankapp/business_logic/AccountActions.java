package com.bankapp.business_logic;

import java.sql.SQLException;
import java.util.Scanner;
import com.bankapp.dao.AccountDAO_Ops;
import com.bankapp.dao.UserDAO_Ops;

public class AccountActions {

	Scanner scan = new Scanner(System.in);
	UserDAO_Ops userOps = new UserDAO_Ops();
	AccountDAO_Ops accountOps = new AccountDAO_Ops();
	double money = 0;
	String accountType;
	
	AccountActions(){}
	
	public void Deposit(int user_id) {
		try {
			System.out.print("Which account ID are you depositing into: ");
			int acc_choice = Integer.parseInt(scan.nextLine());
			System.out.println("UserID: " + user_id + " acc_choice: " + acc_choice);

			if (accountOps.checkAccountOwnerId(user_id, acc_choice)) {
				System.out.print("Deposit amount: ");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void Withdraw(int user_id) {
		try {
			System.out.print("Which account ID are you withdrawing from: ");
			int acc_choice = Integer.parseInt(scan.nextLine());
			if (accountOps.checkAccountOwnerId(user_id, acc_choice)) {
				System.out.println("Withdraw amount: ");
				money = scan.nextDouble();
				if (money < 0) {
					System.out.println("Error: will not accept zero or negative money.");
				}
				else {
					if(accountOps.checkBalance(acc_choice) < money) {
						System.out.println("Error: cannot withdraw more than $" + accountOps.checkBalance(acc_choice));
					}
					else {
						accountOps.withdraw(money, acc_choice);
					}
				}
			}
			else {
				System.out.println("Error: You do not own that account.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void Transfer(int user_id) {
		try {
			System.out.print("Which account ID are you transfering from: ");
			int acc_choice = Integer.parseInt(scan.nextLine());
			if (accountOps.checkAccountOwnerId(user_id, acc_choice)) {
				System.out.print("Which account ID are you transfering into: ");
				int receiver = Integer.parseInt(scan.nextLine());
				if (accountOps.checkAccountExists(receiver)) {
					System.out.println("Transfer amount: ");
					money = scan.nextDouble();
					if (money < 0) {
						System.out.println("Error: will not accept zero or negative money.");
					}
					else {
						if(accountOps.checkBalance(acc_choice) < money) {
							System.out.println("Error: cannot withdraw more than $" + accountOps.checkBalance(acc_choice));
						}
						else {
							accountOps.transfer(money, acc_choice, receiver);
						}
					}
				}
				else {
					System.out.println("Error: That account does not exist.");
				}
			}
			else {
				System.out.println("Error: You do not own that account.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createAccount(int user_id) {
		try {
			System.out.print("Creating Account\n\nChoose new account type:\n1. Saving\n2. Checking");
			System.out.print("Input: ");
			int type_choice = Integer.parseInt(scan.nextLine());
			switch (type_choice) {
				case 1 : 
					accountType = "Saving";
					break;
				case 2 :
					accountType = "Checking";
					break;
			}
			if (type_choice < 1 || type_choice > 2) {
				System.out.println("Error: invalid input");
			}
			else {
				System.out.print("Starting Balance: ");
				money = scan.nextDouble();
				if (money < 0) {
					System.out.println("Error: will not accept zero or negative money.");
				}
				else {
					accountOps.createAccount(user_id, accountType, money);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(NumberFormatException e) {
			System.out.println("\nInvalid input detected"); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
