package com.xws.application.security;


import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class TokenBasedAuthentication extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 1L;

	private String token;
	private final UserDetails principle;

	public TokenBasedAuthentication(UserDetails principle) {
		super(principle.getAuthorities());
		this.principle = principle;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public boolean isAuthenticated() {
		return true;
	}

	public Object getCredentials() {
		return token;
	}

	public UserDetails getPrincipal() {
		return principle;
	}

}