package com.xws.application.service;


import java.util.ArrayList;
import java.util.List;

import com.xws.application.model.Users;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.xws.application.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	protected final Log LOGGER = LogFactory.getLog(getClass());

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	// Funkcija koja na osnovu username-a iz baze vraca objekat User-a
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users.User user = null;
		try {
			user = userRepository.findByEmail(username);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException(String.format("User with username '%s' not found", username));
		}
		System.out.println(user);
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
		return new org.springframework.security.core.userdetails.User(
				user.getUserInfo().getEmail(),
	    		user.getPassword(),
	    		authorities);

	}
		
	public void encodePassword(Users.User u) {
		String pass =  this.passwordEncoder.encode(u.getPassword());
		u.setPassword(pass);
	}
	
	public String encodePassword(String password) {
		return this.passwordEncoder.encode(password);		
	}
	
}