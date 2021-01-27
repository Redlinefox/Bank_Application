package com.bankapp.business_logic;

import java.util.*;

public class EntryPage {
	
	private int choice = 0;
	Scanner scan = new Scanner(System.in);
	
	public EntryPage() {
		System.out.println("Welcome to Bankai Bank");
		
		while (choice < 1 || choice > 3 ) {
			choice = 0;
			System.out.println("\n\nMain Menu\n");
			System.out.println("1. Login\n2. New User\n3. Exit\n");
			System.out.print("Input field: ");
			
			try {
				choice = Integer.parseInt(scan.nextLine());
				
				if (choice == 1) {
					new Login();
					choice = 0;
				}
				else if (choice == 2) {
					new Create_New_User();
					choice = 0;
				}
				else if (choice == 3) {
					System.out.println("\nBankai Bank exit.  Session Closed.");
					scan.close();
					System.exit(0);
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
