package com.xws.application.repository;

import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepository {

	public void store(Object model, String documentId) throws Exception {
		XMLDBManager.store(model, "/db/library/reviews", documentId);
	}

	public Object retrieve(String documentId) throws Exception {
		return XMLDBManager.retrieve("/db/library/reviews", documentId);
	}

}
