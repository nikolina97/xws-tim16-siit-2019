package com.xws.application.controller;

import com.xws.application.dto.PaperLetterDTO;
import com.xws.application.model.ScientificPaper;
import com.xws.application.service.ScientificPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paper")
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

}
