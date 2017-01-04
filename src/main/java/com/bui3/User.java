package com.bui3;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Transient
	private final String ROLE_PREFIX = "ROLE_";

	@Id
	private String username;
	private String password;
	private String firstname;
	private String lastname;

	@Enumerated(EnumType.STRING)
	private Role.RoleValues role;

	private boolean enabled;	
	private Date expireon;

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

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Role.RoleValues getRole() { return role; }
 
	public void setRole(Role.RoleValues role) { this.role = role; }

	public Date getExpireon() {
		return expireon;
	}

	public void setExpireon(Date expireon) {
		this.expireon = expireon;
	}

	@Override
	public boolean isEnabled() {
		return getEnabled();
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
			return expireon.after(new Date());
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
