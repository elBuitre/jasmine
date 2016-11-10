package com.bui3;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import org.joda.time.LocalDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class User implements UserDetails {

	private final String ROLE_PREFIX = "ROLE_";
	private static final long serialVersionUID = 1L;

	public enum Role {
		ADMIN, GUEST, USER 
	}
	@Id
	private String username;
	
	private String password;
	private String firstname;
	private String lastname;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
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
	
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	public LocalDate getExpireon() {
		return expireon;
	}
	
	public void setExpireon(LocalDate expireon) {
		this.expireon = expireon;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void enable(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return Arrays.asList(new SimpleGrantedAuthority(
				ROLE_PREFIX + role.toString()));
	}
	
	@Override
	public boolean isAccountNonExpired() {
		if (null != expireon)
			return expireon.isAfter(LocalDate.now());
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return enabled;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
}
