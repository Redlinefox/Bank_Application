package com.bankapp.business_logic;

import java.sql.SQLException;
import java.util.*;

import com.bankapp.dao.UserDAO_Ops;

public class Login {
	
	private boolean running = true;
	private int userID = 0;
	private int choice;
	Scanner scan = new Scanner(System.in);
	UserDAO_Ops uDAO = new UserDAO_Ops();
	
	public Login(){
		System.out.println("\n\nLogin Page\n");

        while(running) {				
        	userID = uDAO.matchUsernamePass();
	        if(userID > 0) {
	        	try {
	        		new HomePage(userID);
	        		running = false;
	        	} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
	        else {
	        	loginRetry();
	        }
        }       
	}
	
	
	public void loginRetry() {
		choice = 0;
		while (choice < 1 || choice > 2) {
			try{
				System.out.println("Do you want to start over or exit?\n1: Start Over\n2: Exit to Login Menu");
				choice = Integer.parseInt(scan.nextLine());
				if (choice == 1) {
					System.out.println("\nStarting over");
					running = true;		
				}
				else if (choice ==2) {
					System.out.println("\nExitting to Login Menu");
					running = false;
				}
				else {
					System.out.println("\nInvalid choice detected");
				}
			}
			catch(InputMismatchException e) {
				System.out.println("\nInvalid input detected"); 
				choice = 0;
			}
		}
	}
}
