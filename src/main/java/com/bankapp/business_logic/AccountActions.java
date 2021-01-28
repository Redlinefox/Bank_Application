package com.bankapp.business_logic;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import com.bankapp.dao.AccountDAO_Ops;
import com.bankapp.dao.UserDAO_Ops;
import com.bankapp.model.User;

public class AccountActions {

	Scanner scan = new Scanner(System.in);
	UserDAO_Ops userOps = new UserDAO_Ops();
	AccountDAO_Ops accountOps = new AccountDAO_Ops();
	double money = 0;
	String accountType;
	User user = new User();
	
	AccountActions(){}
	
	public void Deposit(int user_id) {
		try {
			System.out.print("Which account ID are you depositing into: ");
			int account_id = scan.nextInt();
			if (accountOps.checkAccountOwnerId(user_id, account_id)) {
				if(accountOps.checkApproved(account_id)) {
					System.out.print("Deposit amount: ");
					money = scan.nextDouble();
					if(check2Decimals(money)) {
						if (money > 0) {
							accountOps.deposit(money, account_id);
						}
						else {System.out.println("Error: will not accept zero or negative money.");}
					}
					else {
						System.out.println("Error: more than 2 decimal placed detected");
					}
				}
				else {System.out.println("Account not approved for transactions");}
			}
			else {System.out.println("Error: You do not own that account.");}
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (NumberFormatException e) {
			System.out.println("Error: invalid input");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void Withdraw(int user_id) {
		try {
			System.out.print("Which account ID are you withdrawing from: ");
			int acc_choice = scan.nextInt();
			if (accountOps.checkAccountOwnerId(user_id, acc_choice)) {
				if(accountOps.checkApproved(acc_choice)) {
					System.out.print("Withdraw amount: ");
					money = scan.nextDouble();
					if(check2Decimals(money)) {
						if (money > 0) {
							if(accountOps.checkBalance(acc_choice) > money) {
								accountOps.withdraw(money, acc_choice);
							}
							else {System.out.println("Error: cannot withdraw more than $" + accountOps.checkBalance(acc_choice));}
						}
						else {System.out.println("Error: will not accept zero or negative money.");}
					}
					else {
						System.out.println("Error: more than 2 decimal placed detected");
					}
				}
				else {System.out.println("Account not approved for transactions");}
			}
			else {System.out.println("Error: You do not own that account.");}
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (NumberFormatException e) {
			System.out.println("Error: invalid input");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void Transfer(int user_id) {
		try {
			System.out.print("Which account ID are you transfering from: ");
			int acc_choice = scan.nextInt();
			if (accountOps.checkAccountOwnerId(user_id, acc_choice)) {
				System.out.println("TESTING checkAccountOwnerId: "+user_id + " " + acc_choice);
				if(accountOps.checkApproved(acc_choice)) {
					System.out.print("Which account ID are you transfering into: ");
					int receiver = Integer.parseInt(scan.nextLine());
					if (accountOps.checkAccountExists(receiver)) {
						System.out.print("Transfer amount: ");
						money = scan.nextDouble();
						if(check2Decimals(money)) {
							if (money < 0) {
								System.out.println("Error: will not accept zero or negative money.");
							}
							else {
								if(accountOps.checkBalance(acc_choice) < money) {
									System.out.println("Error: cannot withdraw more than $" + accountOps.checkBalance(acc_choice));
								}
								else {accountOps.transfer(money, acc_choice, receiver);}
							}
						}
						else {System.out.println("Error: more than 2 decimal placed detected");}
					}
					else {System.out.println("Error: That account does not exist.");}
				}
				else {System.out.println("Account not approved for transactions");}				
			}
			else {System.out.println("Error: You do not own that account.");}
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (NumberFormatException e) {
			System.out.println("Error: invalid input");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createAccount(int user_id) {
		try {
			System.out.println("\n\nChoose new account type:\n1. Saving\n2. Checking");
			System.out.print("Input: ");
			int type_choice = scan.nextInt();
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
				if(check2Decimals(money)) {
					if (money < 0) {
						System.out.println("Error: will not accept zero or negative money.");
					}
					else {
						accountOps.createAccount(user_id, accountType, money);
						System.out.println("\nNew " + accountType + " with $" + money +" created.  Awaiting approval.\n");
					}
				}
				else {System.out.println("Error: more than 2 decimal placed detected");}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(NumberFormatException e) {
			System.out.println("\nInvalid input detected"); 
		}catch (InputMismatchException e) {
			System.out.println("\nInvalid input detected");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void ApproveAccounts(int employee_id) {
		int choice = 0;
		while(choice < 1 || choice > 3) {
			try {
				System.out.println("\n\n[Account Approval Menu]\n");
				accountOps.viewUnapproved();
				System.out.println("\n1. Approve an Account\n2. Approve All Accounts\n3. Exit");
				System.out.print("Input: ");
				choice = Integer.parseInt(scan.nextLine());
				if(choice == 1) {
					System.out.print("Choose account id: ");
					int account_id = Integer.parseInt(scan.nextLine());
					accountOps.setApproved(employee_id, account_id);
					choice = 0;
				}
				else if(choice == 2) {
					accountOps.approveAll(employee_id);
					System.out.println("\nAll accounts approved");
					choice = 0;
				}
				else if(choice == 3) {
					System.out.println("\nExiting Approve Accounts Menu");
				}
			} catch(NumberFormatException e) {
				System.out.println("\nInvalid input detected"); 
				choice = 0;
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void ViewAllAccounts() {
		System.out.println("\n");
		accountOps.viewAllAccounts(); 
	}
	
	public void ViewTransactionLog() {
		accountOps.viewTransactionLog();
	}
	
	public boolean check2Decimals(double amount) {
		Double d = amount;
		String[] splitter = d.toString().split("\\.");
		System.out.println(splitter[0].length());
		System.out.println(splitter[1].length());
	
		if(splitter[1].length() > 2) {
			return false;
		}
		else {
			return true;
		}
	}
}
