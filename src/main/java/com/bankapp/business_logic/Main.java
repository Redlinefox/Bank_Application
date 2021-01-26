package com.bankapp.business_logic;

public class Main {

	public static void main(String[] args) {
		//GUI_Login login = new GUI_Login();
		

		new EntryPage();
		
		System.out.println("End of Main method");
		// EntryPage entry = new EntryPage() that has the choices for login, new_user and EXIT
		
		// if EntryPage returns 1 then call login method
		// if EntryPage returns 2 then call new_user method to create new user and password
		// if EntryPage returns 3 then Exit program
		
		
		// login method()
		// prompt user for entry of username 
		// prompt user for entry of password
		// if else statement to see if username and password == the database items		
		// check username against the userType in DB
		// if Customer then call CustomerLogin()
		// if Employee then call EmployeeLogin()
		
		// on Employee login successful display EmployeeHomePage()
		// on HomePage clear console then display:
		// - "Welcome Name\n"
				// - display all associated accounts with account_id, account_type and balance
				// - 1. Choose_An_Account()
				// - 2. Create new Account
				// - 3. Exit to Login
		
		
		// on Customer login successful, display CustomerHomePage()
		// on HomePage clear console then display:
		// - "Welcome Name\n"
		// - display all associated accounts with account_id, account_type, balance and if approved
		// - 1. Choose_An_Account()
		// - 2. Create new Account
		// - 3. Exit to Login
		
		// If Choose an Account:
		// Input the Account ID
		// if Account ID != account you own then Fail;
		// else display choices:
		// 1. Deposit() -> 
		//		prompt for money to be deposited -> check for numerical/non-negative -> 
		//		update database with new amount -> SysOut Balance -> 
		//		SysOut "Anything else you want to do with this account?" -> Return to Display Choices
		// 2. Withdraw() -> 
		//		prompt for money to be withdrawn -> check for numerical/non-negative -> 
		//		check database to see if withdrawn amount is greater than current balance ->
		//		if so ERROR: Insufficient funds; else -> update database with new amount -> SysOut Balance-> 
		//		SysOut "Anything else you want to do with this account?" -> Return to Display Choices
		// 3. Transfer() -> 
		//		prompt for account to transfer into -> check if account exists -> 
		//		prompt for money to be transfered -> check for numerical/non-negative -> 
		//		check database to see if transfer amount is greater than current balance ->
		//		update database with new amount -> SysOut Balance ->
		//		SysOut "Anything else you want to do with this account?" -> Return to Display Choices
		// 4. Exit to HomePage()
		
		
		
	}

}
