package com.piy.pma.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user_accounts")
public class UserAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_accounts_seq")
	@SequenceGenerator(name = "user_accounts_seq", sequenceName = "user_accounts_seq", allocationSize = 1, initialValue = 1)
	@Column(name = "user_id")
	private long userID;
	
	@Column(name = "username")
	@NotBlank(message = "Must enter user name")
	@Size(min = 5, max = 50, message = "User name must lie between {min} to {max} characters")
	private String userName;
	
	@NotBlank(message = "Email cannot be empty")
	@Email(message = "Must be a valid email address")
	private String email;
	
	@NotBlank(message = "Must enter password")
	@Size(min = 5, message = "Password must be at least {min} characters long")
	private String password;
	
	private String role = "ROLE_ADMIN";
	
	private boolean enabled = true;
	
	public UserAccount () {
		
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
