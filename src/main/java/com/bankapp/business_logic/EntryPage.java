package com.bankapp.business_logic;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntryPage {
	final static Logger logger = LogManager.getLogger(EntryPage.class.getName());
	private int choice = 0;
	Scanner scan = new Scanner(System.in);
	
	public EntryPage() {
		logger.info("Entering the EntryPage method");
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
					logger.info("Exiting bank app. Session Closed.");
					System.out.println("\nBankai Bank exit.  Session Closed.");
					scan.close();
					System.exit(0);
				}
				else {
					logger.error("User invalid choice detected");
					System.out.println("\nInvalid choice detected\n"); 
				}
			}
			catch(NumberFormatException e) {
				logger.error("User invalid input detected");
				System.out.println("\nInvalid input detected"); 
			}
		} 	
	}
}
