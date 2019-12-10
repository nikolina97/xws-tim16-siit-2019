package com.xws.application.repository;

import org.springframework.stereotype.Repository;

@Repository
public class ScientificPaperRepository {

	public void store(Object model, String documentId) throws Exception {
		XMLDBManager.store(model, "/db/library/papers", documentId);
	}

	public Object retrieve(String documentId) throws Exception {
		return XMLDBManager.retrieve("/db/library/papers", documentId);
	}

}
