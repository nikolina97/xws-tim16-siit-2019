package com.xws.application.repository;

import org.springframework.stereotype.Repository;

@Repository
public class CoverLetterRepository {

	public void store(Object model, String documentId) throws Exception {
		XMLDBManager.store(model, "/db/library/letters", documentId);
	}

	public Object retrieve(String documentId) throws Exception {
		return XMLDBManager.retrieve("/db/library/letters", documentId);
	}

}
