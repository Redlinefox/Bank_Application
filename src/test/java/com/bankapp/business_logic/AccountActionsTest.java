package com.bankapp.business_logic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AccountActionsTest {

	
	@BeforeAll
	public boolean check2Decimals(double amount) {
		Double d = amount;
		String[] splitter = d.toString().split("\\.");
		splitter[0].length();
		splitter[1].length();
		return false;
	}
	
	@Test
	void test1(){
		Assertions.assertFalse(check2Decimals(1234.544));
	}
	
	
}
