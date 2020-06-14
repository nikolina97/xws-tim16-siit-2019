package com.xws.application.service;

import com.xws.application.model.CoverLetter;
import com.xws.application.model.DocType;
import com.xws.application.parser.JAXB;
import com.xws.application.repository.CoverLetterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoverLetterService {

	@Autowired
	private CoverLetterRepository repository;

	public boolean save(String xml) {
		try {
			CoverLetter letter = (CoverLetter) JAXB.unmarshal(xml, DocType.COVER_LETTER);
			repository.store(letter, "cover_letter.xml");

			//XMLDBManager.store(paper, "scientific_paper.xml");

			return true;
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}
	}

	public CoverLetter get(String letterID) {
		try {
			return (CoverLetter) repository.retrieve("scientific_paper.xml");
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

}
