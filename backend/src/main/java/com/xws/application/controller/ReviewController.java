package com.xws.application.controller;

import com.xws.application.dto.ReviewDTO;
import com.xws.application.model.Review;
import com.xws.application.service.ReviewService;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
			
	@GetMapping(value = "/recommended/{paperId}")
	public ResponseEntity<?> getRecommendedReviewers(@PathVariable String paperId) {
		try {
			return new ResponseEntity<>(service.getRecommendedReviewers(paperId), HttpStatus.OK);	
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/assignReviewer/{paperId}")
	public ResponseEntity<?> assigneReviewer(@RequestBody String email,@PathVariable String paperId) {
		try {
			return new ResponseEntity<>(service.assigneReviewer(email, paperId), HttpStatus.OK);	
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/getReviewsForPapper/{paperID}")
	public ResponseEntity<?> getReviewersPapers(@PathVariable String paperID) {
		try {
			return new ResponseEntity<>(service.getReviewes(paperID), HttpStatus.OK);	
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	@PreAuthorize("hasRole('ROLE_EDITOR')")
	@GetMapping(value = "/getHtmlById/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<?> getHtmlById(@PathVariable("id") String id) {
		try {
			return new ResponseEntity<>(service.getHTML(id), HttpStatus.OK);	
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PreAuthorize("hasRole('ROLE_EDITOR')")
	@GetMapping(value = "/getPDFById/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> getPDFById(@PathVariable("id") String id) {
		try {
			ByteArrayOutputStream bs = service.getPDF(id);	
			return new ResponseEntity<>(bs.toByteArray(), HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping(value = "/getHtmlByIdAnonymoys/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<?> getHtmlByIdAnonymoys(@PathVariable("id") String id) {
		try {
			return new ResponseEntity<>(service.getHTMLAnonymous(id), HttpStatus.OK);	
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping(value = "/getPDFByIdAnonymoys/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> getPDFByIdAnonymous(@PathVariable("id") String id) {
		try {
			ByteArrayOutputStream bs = service.getPDFAnonymous(id);	
			return new ResponseEntity<>(bs.toByteArray(), HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}

}
