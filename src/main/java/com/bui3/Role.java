package com.bui3;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class Role {

	public enum RoleValues {
		GUEST, USER, ADMIN
	}

	@Id
	@Enumerated(EnumType.STRING)
	private RoleValues role;
	
	public RoleValues getRole() {
		return role;
	}
	
	public void setRole(RoleValues roleValue) {
		this.role = roleValue;
	}	
}
