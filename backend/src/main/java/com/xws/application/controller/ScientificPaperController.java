package com.xws.application.controller;

import com.xws.application.dto.PaperLetterDTO;
import com.xws.application.dto.ScientificPaperMetadataSearchDTO;
import com.xws.application.model.ScientificPaper;
import com.xws.application.service.ScientificPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
		return service.save(dto) ? new ResponseEntity<>("Paper and cover letter successfully saved", HttpStatus.OK) : new ResponseEntity<>("Something is wrong with your paper or letter, check them.", HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/{id}")
	public ResponseEntity get(@PathVariable String id) {
		ScientificPaper paper = service.get(id);
		return paper != null ? new ResponseEntity<>(paper, HttpStatus.OK) : new ResponseEntity<>("Paper doesn't exist.", HttpStatus.NOT_FOUND);
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
}
