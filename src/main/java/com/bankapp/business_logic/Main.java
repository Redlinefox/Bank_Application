package com.bankapp.business_logic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

	final static Logger logger = LogManager.getLogger(Main.class.getName());
	
	public static void main(String[] args) {
		//GUI_Login login = new GUI_Login();
		logger.info("Main method opened");
		
		new EntryPage();		
		System.out.println("End of Main method");
	}

}
