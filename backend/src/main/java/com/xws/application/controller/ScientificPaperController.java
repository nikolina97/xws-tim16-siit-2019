package com.xws.application.controller;

import com.xws.application.dto.PaperLetterDTO;
import com.xws.application.dto.ScientificPaperMetadataSearchDTO;
import com.xws.application.model.ScientificPaper;
import com.xws.application.service.ScientificPaperService;

import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paper")
@CrossOrigin
public class ScientificPaperController {

	@Autowired
	private ScientificPaperService service;

	@PostMapping
	public ResponseEntity<String> save(@RequestBody PaperLetterDTO dto) {
		service.save(dto);

		return new ResponseEntity<>("Paper and cover letter successfully saved", HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> revise(@RequestBody String revision, @PathVariable String id) {
		service.revise(revision, id);

		return new ResponseEntity<>("Paper successfully saved.", HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<String> get(@PathVariable String id) {
		return new ResponseEntity<>(service.get(id), HttpStatus.OK);
	}

	@GetMapping(value = "/getByUser")
	public ResponseEntity<?> getPapersFromUser() {
		try {
			return new ResponseEntity<>(service.getPapersFromUser(), HttpStatus.OK);	
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PostMapping(value = "/advancedSearch")
	public ResponseEntity<?> advancedSearch(@RequestBody ScientificPaperMetadataSearchDTO metadataSearch) {
		try {
			return new ResponseEntity<>(service.getAllPapersByUser(metadataSearch), HttpStatus.OK);	
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PostMapping(value = "/basicSearch")
	public ResponseEntity<?> basicSearch(@RequestBody String searchText) {
		try {
			return new ResponseEntity<>(service.basicSearch(searchText), HttpStatus.OK);	
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping(value = "/getReviewrsPapersToAccept")
	public ResponseEntity<?> getReviewersPapers() {
		try {
			return new ResponseEntity<>(service.getReviewersPapers(), HttpStatus.OK);	
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping(value = "/revoke/{paperId}")
	public ResponseEntity<?> revoke(@PathVariable String paperId) {
		try {
			return new ResponseEntity<>(service.revokePaper(paperId), HttpStatus.OK);	
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/getHtmlById/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<?> getHtmlById(@PathVariable("id") String id) {
		try {
			return new ResponseEntity<>(service.getPaperHTML(id), HttpStatus.OK);	
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping(value = "/getHtmlByIdAnonymous/{id}", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<?> getHtmlByIdAnonymous(@PathVariable("id") String id) {
		try {
			return new ResponseEntity<>(service.getPaperHTMLAnonymous(id), HttpStatus.OK);	
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping(value = "/getPDFById/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> getPDFById(@PathVariable("id") String id) {
		try {
			ByteArrayOutputStream bs = service.getPaperPDF(id);	
			return new ResponseEntity<>(bs.toByteArray(), HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping(value = "/getXMLById/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<byte[]> getXMLById(@PathVariable("id") String id) {
		try {
			String bs = service.getPaperXML(id);	
			return new ResponseEntity<>(bs.getBytes(), HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping(value = "/getSubmittedPapers")
	public ResponseEntity<?> getSubmittedPapers() {
		try {
			return new ResponseEntity<>(service.getSubmittedPapers(), HttpStatus.OK);	
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/getOnRevisePapers")
	public ResponseEntity<?> getOnRevisePapers() {
		try {
			return new ResponseEntity<>(service.getOnRevisePapers(), HttpStatus.OK);	
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@GetMapping(value = "/getReferenced/{paperId}")
	public ResponseEntity<?> getReferenced(@PathVariable String paperId) {
		try {
			return new ResponseEntity<>(service.getReferenced(paperId), HttpStatus.OK);	
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/reject/{paperId}")
	public ResponseEntity<?> rejectPaper(@PathVariable String paperId) {
		try {
			return new ResponseEntity<>(service.rejectPaper(paperId), HttpStatus.OK);	
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/publish/{paperId}")
	public ResponseEntity<?> publishPaper(@PathVariable String paperId) {
		try {
			return new ResponseEntity<>(service.publishPaper(paperId), HttpStatus.OK);	
		}catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
