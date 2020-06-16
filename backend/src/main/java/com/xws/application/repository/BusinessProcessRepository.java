package com.xws.application.repository;

import org.springframework.stereotype.Repository;

@Repository
public class BusinessProcessRepository {

	public void store(Object model, String documentId) throws Exception {
		XMLDBManager.store(model, "/db/processes", documentId);
	}

}
