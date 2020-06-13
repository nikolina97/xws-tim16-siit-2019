package com.xws.application.service;

import com.xws.application.model.ScientificPaper;
import com.xws.application.parser.JAXB;
import com.xws.application.repository.ScientificPaperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScientificPaperService {

	@Autowired
	private ScientificPaperRepository repository;

	public boolean save(String xml) {
		try {
			ScientificPaper paper = (ScientificPaper) JAXB.unmarshal(xml);
			repository.store(paper, "scientific_paper.xml");

			//XMLDBManager.store(paper, "scientific_paper.xml");

			return true;
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}
	}

	public ScientificPaper get(String paperID) {
		try {
			return (ScientificPaper) repository.retrieve("scientific_paper.xml");
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

}
