package com.abcwebportal.webportal.security.responsehandler.invlaidresponsehandler;

import org.springframework.stereotype.Component;
/**
 * <p> To handle invalid response while authinication process. 
 *
 */
@Component
public class InvalidLoginResponse  {

	private String username;
	private String password;
	
	public InvalidLoginResponse() {
		this.username = " invalid username";
		this.password = " invalid password";
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
	
	
}
