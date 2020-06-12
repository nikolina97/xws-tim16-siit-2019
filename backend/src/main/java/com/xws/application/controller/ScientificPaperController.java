package com.xws.application.controller;

import com.xws.application.model.ScientificPaper;
import com.xws.application.service.ScientificPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ScientificPaperController {

	@Autowired
	private ScientificPaperService service;

	@PostMapping(value = "/paper/save", consumes = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<String> save(@RequestBody String xml) {
		return service.save(xml) ? new ResponseEntity<>("Paper successfully saved", HttpStatus.OK) : new ResponseEntity<>("Something is wrong with your paper, check it.", HttpStatus.BAD_REQUEST);
	}

	@GetMapping(value = "/paper/{paperID}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity get(@PathVariable String paperID) {
		ScientificPaper paper = service.get(paperID);
		return paper != null ? new ResponseEntity<>(paper, HttpStatus.OK) : new ResponseEntity<>("Paper doesn't exist.", HttpStatus.NOT_FOUND);
	}

}
