package com.techlon.base.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class LoginUser extends org.springframework.security.core.userdetails.User {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2271225898586681437L;
	
	private Integer roleId;
	private Integer userId;
	private String name;

	public LoginUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		// TODO Auto-generated constructor stub
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
