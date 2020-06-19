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
import org.exist.xmldb.EXistResource;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.xws.application.util.rdf.RdfConnectionProperties;
import com.xws.application.util.rdf.SparqlUtil;

@Repository
public class CoverLetterRepository {

	public static final String TARGET_NAMESPACE = "https://github.com/nikolina97/xws-tim16-siit-2019";

	public void store(Object model, String documentId) throws Exception {
		XMLDBManager.store(model, "/db/letters", documentId);
	}

	public Object retrieve(String documentId) throws Exception {
		return XMLDBManager.retrieve("/db/letters", documentId);
	}

	public int getDocumentCount() throws Exception {
		return XMLDBManager.getDocumentCount("/db/letters");
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
	
	public String getLetterById(String id) throws Exception {
		String xml = null;
		String xpath = String.format("//sp:cover_letter[@id=\"%s\"]", id);
//        String xpath = "//sp:scientific_paper[@sp:id=\"paper5\"]";
		ResourceSet result = XMLDBManager.retrieveWithXPath("/db/letters", xpath, TARGET_NAMESPACE);
        if (result == null) {
			return xml;
		}
        ResourceIterator i = result.getIterator();
		XMLResource res = null;
		while (i.hasMoreResources()) {
			System.out.println("HEJ!");
			try {
				res = (XMLResource) i.nextResource();
				xml = res.getContent().toString();
				return xml;
			} finally {
				try {
					((EXistResource) res).freeResources();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}
		}
		return null;
    }

}
