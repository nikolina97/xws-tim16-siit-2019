package com.xws.application.repository;

import com.xws.application.model.BusinessProcess;
import org.springframework.stereotype.Repository;

@Repository
public class BusinessProcessRepository {

	public void store(Object model, String documentId) throws Exception {
		XMLDBManager.store(model, "/db/processes", documentId);
	}

	public BusinessProcess retrieve(String documentId) throws Exception {
		return (BusinessProcess) XMLDBManager.retrieveJAXB("/db/processes", documentId);
	}

}
