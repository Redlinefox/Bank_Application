package com.aric.project0;

public class User {
	private Integer user_id;
	private String username;
	private String password;
	private String first_name;
	private String last_name;
	private String user_type;
	
	public User() {}
	
	public User(String username, String pass) {
		this.username = username;
		this.password = pass;
	}
	
	public User(String username, String password, String first_name, String last_name, String user_type) {
		this.username = username;
		this.password = password;
		this.first_name = first_name;
		this.last_name = last_name;
		this.user_type = user_type;
	}
	
	public User(Integer user_id, String username, String password, String first_name, String last_name, String user_type) {
		this.user_id = user_id;
		this.username = username;
		this.password = password;
		this.first_name = first_name;
		this.last_name = last_name;
		this.user_type = user_type;
	}
	
	public Integer getUserId() {
		return user_id;
	}
	
	public void setUserId(Integer user_id) {
		this.user_id = user_id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFirstName() {
		return first_name;
	}
	
	public void setFirstName(String first_name) {
		this.first_name = first_name;
	}
	
	public String getLastName() {
		return last_name;
	}
	
	public void setLastName(String last_name) {
		this.last_name = last_name;
	}
	
	public String getUserType() {
		return this.user_type;
	}
	
	public void setUserType(String user_type) {
		this.user_type = user_type;
	}
	
}



