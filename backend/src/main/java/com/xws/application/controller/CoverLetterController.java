package com.xws.application.controller;

import com.xws.application.model.CoverLetter;
import com.xws.application.service.CoverLetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CoverLetterController {

	@Autowired
	private CoverLetterService service;

	@GetMapping(value = "/letter/{letterID}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity get(@PathVariable String letterID) {
		CoverLetter letter = service.get(letterID);
		return letter != null ? new ResponseEntity<>(letter, HttpStatus.OK) : new ResponseEntity<>("Letter doesn't exist.", HttpStatus.NOT_FOUND);
	}

}
