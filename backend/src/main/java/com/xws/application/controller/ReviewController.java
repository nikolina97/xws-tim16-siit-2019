package com.xws.application.controller;

import com.xws.application.model.Review;
import com.xws.application.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ReviewController {

	@Autowired
	private ReviewService service;

	@PostMapping(value = "/review/save", consumes = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> save(@RequestBody String xml) {
		return service.save(xml) ? new ResponseEntity<>("Review successfully saved", HttpStatus.OK) : new ResponseEntity<>("Something is wrong with your review, check it.", HttpStatus.BAD_REQUEST);
	}

	@GetMapping(value = "/review/{reviewID}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity get(@PathVariable String reviewID) {
		Review review = service.get(reviewID);
		return review != null ? new ResponseEntity<>(review, HttpStatus.OK) : new ResponseEntity<>("Paper doesn't exist.", HttpStatus.NOT_FOUND);
	}

}
