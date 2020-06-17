package com.xws.application.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xws.application.dto.UserRegistrationDTO;
import com.xws.application.model.TRole;
import com.xws.application.security.JwtAuthenticationRequest;
import com.xws.application.security.TokenUtils;
import com.xws.application.service.CustomUserDetailsService;
import com.xws.application.service.UserService;

//Kontroler zaduzen za autentifikaciju korisnika
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
	TokenUtils tokenUtils;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<String> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
			HttpServletResponse response) throws AuthenticationException, IOException {

		UserDetails user = null;
		final Authentication authentication;
		try {
			user = this.userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
			authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
						authenticationRequest.getPassword()));
		}
		catch(UsernameNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_ACCEPTABLE);
		}
		catch(BadCredentialsException e) {
			return new ResponseEntity<String>("Wrong password",HttpStatus.NOT_ACCEPTABLE);
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenUtils.generateToken(user);
		return new ResponseEntity<String>(jwt, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ResponseEntity<?> register(@RequestBody UserRegistrationDTO dto){
		System.out.println(dto);
		try {
			userService.registerAuthor(dto);
			return new ResponseEntity<String>("Registration successfull!", HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>("Registration error!", HttpStatus.BAD_REQUEST);
		}
	}
}