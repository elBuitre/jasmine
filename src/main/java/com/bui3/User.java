package com.bui3;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.joda.time.LocalDate;

@Entity
public class User {

	@Id
	private String username;
	
	private String password;
	private String firstname;
	private String lastname;
	
	private int role;
	
	private boolean enabled;
	
	private LocalDate expireon;
	
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
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public int getRole() {
		return role;
	}
	
	public void setRole(int role) {
		this.role = role;
	}
	
	public LocalDate getExpireon() {
		return expireon;
	}
	
	public void setExpireon(LocalDate expireon) {
		this.expireon = expireon;
	}
	
	public boolean isEnabled() {
		LocalDate now = LocalDate.now();
		return (enabled && (expireon.isAfter(now)));
	}
	
	public void enable(boolean enabled) {
		this.enabled = enabled;
	}
}
