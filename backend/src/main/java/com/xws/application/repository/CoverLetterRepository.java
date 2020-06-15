package com.xws.application.repository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.springframework.stereotype.Repository;

import com.xws.application.util.rdf.RdfConnectionProperties;
import com.xws.application.util.rdf.SparqlUtil;

@Repository
public class CoverLetterRepository {

	public void store(Object model, String documentId) throws Exception {
		XMLDBManager.store(model, "/db/library/letters", documentId);
	}

	public Object retrieve(String documentId) throws Exception {
		return XMLDBManager.retrieve("/db/library/letters", documentId);
	}
	
	public void storeMetadata(String metadata, String graphName) throws IOException {
		
		RdfConnectionProperties conn = RdfConnectionProperties.loadProperties();
		Model model = ModelFactory.createDefaultModel();
        model.read(new ByteArrayInputStream(metadata.getBytes()), null);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        model.write(out, SparqlUtil.NTRIPLES); 
        
		String sparqlUpdate = SparqlUtil.insertData(conn.getDataEndpoint() + graphName, new String(out.toByteArray()));
		UpdateRequest request = UpdateFactory.create(sparqlUpdate);
        UpdateProcessor processor = UpdateExecutionFactory.createRemote(request, conn.getUpdateEndpoint());
        processor.execute();
        
		
	}

}
