package com.xws.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xws.application.dto.ReviewDTO;
import com.xws.application.model.Review;
import com.xws.application.service.ReviewService;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

	@Autowired
	private ReviewService service;

	@PostMapping
	public ResponseEntity<String> save(@RequestBody ReviewDTO dto) {
		service.save(dto);

		return new ResponseEntity<>("Review successfully saved", HttpStatus.OK);
	}
	@PreAuthorize("hasRole('ROLE_REVIEWER')")
	@GetMapping(value = "/review/{reviewID}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity get(@PathVariable String reviewID) {
		Review review = service.get(reviewID);
		return review != null ? new ResponseEntity<>(review, HttpStatus.OK) : new ResponseEntity<>("Review doesn't exist.", HttpStatus.NOT_FOUND);
	}
	
	@PreAuthorize("hasRole('ROLE_REVIEWER')")
	@PostMapping(value = "accept/{spId}")
	public ResponseEntity accept(@PathVariable String spId) {
		try {	
			return new ResponseEntity<>(service.accept(spId), HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "reject/{spId}")
	public ResponseEntity reject(@PathVariable String spId) {
		try {	
			return new ResponseEntity<>(service.reject(spId), HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
