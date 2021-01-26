package com.bankapp.business_logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GUI_Login implements ActionListener{

	private static JLabel userLabel;
	private static JTextField userText;
	private static JLabel passwordLabel;
	private static JTextField passwordText; 
	private static JButton loginButton; 
	private static JLabel success; 
	
	public GUI_Login() {
		
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		
		frame.setSize(600, 250);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(panel);
		
		panel.setLayout(null);
		
		userLabel = new JLabel("User");
		userLabel.setBounds(10, 20, 80, 25);
		panel.add(userLabel);
		
		userText = new JTextField(20);
		userText.setBounds(100, 20, 165, 25);
		panel.add(userText);
		
		passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10, 50, 80, 25);
		panel.add(passwordLabel);
		
		passwordText = new JPasswordField(20);
		passwordText.setBounds(100, 50, 165, 25);
		panel.add(passwordText);
		
		loginButton = new JButton("Login");
		loginButton.setBounds(10, 80, 80, 25);
		loginButton.addActionListener(this);
		panel.add(loginButton);
		
		success = new JLabel("");
		success.setBounds(10, 110, 500, 25);
		panel.add(success);
		success.setText("");
		
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String user = userText.getText();
		String password = passwordText.getText();
		System.out.println(user + ", " + password);
		
		//connect Database and check to see if the username and password matches 
		
		if(user.equals("") || password.equals("")) {
			success.setText("Invalid login.  Fields empty");
		}
		else if(user.equals("iaakbani") && password.equals("password1")) {
			success.setText("Login successful!");
		}
		else if(!user.equals("iaakbani")) {
			success.setText("User does not exist in the system.  Please make a new account.");
		}
		else{
			success.setText("Password does not match user.");
		}
	}
}
