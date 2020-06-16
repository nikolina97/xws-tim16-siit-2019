package com.xws.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xws.application.model.Users;
import com.xws.application.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService service;

	@PostMapping(value = "/users/save", consumes = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> save(@RequestBody String xml) {
		return service.save(xml) ? new ResponseEntity<>("Users successfully saved", HttpStatus.OK) : new ResponseEntity<>("Something is wrong with your users, check it.", HttpStatus.BAD_REQUEST);
	}

	@GetMapping(value = "/users/{usersID}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity get(@PathVariable String usersID) {
		Users users = service.get(usersID);
		return users != null ? new ResponseEntity<>(users, HttpStatus.OK) : new ResponseEntity<>("Users doesn't exist.", HttpStatus.NOT_FOUND);
	}
	
//	@GetMapping(value = "/test")
//	@PreAuthorize("hasRole('ROLE_AUTHOR')")
//	public String getTest() {
//		System.out.println("EEEE");
//		return "HIIIII";
//	}
	
}
