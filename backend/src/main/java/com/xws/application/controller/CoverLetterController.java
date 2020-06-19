package com.xws.application.controller;

import com.xws.application.model.CoverLetter;
import com.xws.application.service.CoverLetterService;

import java.io.ByteArrayOutputStream;

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

	@GetMapping(value = "letter/getHtmlById/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<?> getHtmlById(@PathVariable("id") String id) {
		try {
			return new ResponseEntity<>(service.getHTML(id), HttpStatus.OK);	
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping(value = "letter/getPDFById/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> getPDFById(@PathVariable("id") String id) {
		try {
			ByteArrayOutputStream bs = service.getPDF(id);	
			return new ResponseEntity<>(bs.toByteArray(), HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
}
