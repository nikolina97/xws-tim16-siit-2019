package com.xws.application.service;

import com.xws.application.model.DocType;
import com.xws.application.model.Review;
import com.xws.application.parser.JAXB;
import com.xws.application.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository repository;

	public boolean save(String xml) {
		try {
			Review review = (Review) JAXB.unmarshal(xml, DocType.REVIEW);
			repository.store(review, "review.xml");

			//XMLDBManager.store(paper, "scientific_paper.xml");

			return true;
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}
	}

	public Review get(String paperID) {
		try {
			return (Review) repository.retrieve("review.xml");
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

}
