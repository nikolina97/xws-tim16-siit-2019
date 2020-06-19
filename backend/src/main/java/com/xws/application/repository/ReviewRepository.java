package com.xws.application.repository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

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

import com.xws.application.model.BusinessProcess;
import com.xws.application.model.ScientificPaper;
import com.xws.application.model.TReviewAssignementState;
import com.xws.application.model.TReviewAssignment;
import com.xws.application.model.TState;
import com.xws.application.model.Users;
import com.xws.application.util.XUpdateTemplate;
import com.xws.application.util.rdf.RdfConnectionProperties;
import com.xws.application.util.rdf.SparqlUtil;

@Repository
public class ReviewRepository {
	public static final String TARGET_NAMESPACE = "https://github.com/nikolina97/xws-tim16-siit-2019";


	public void store(Object model, String documentId) throws Exception {
		XMLDBManager.store(model, "/db/reviews", documentId);
	}

	public Object retrieve(String documentId) throws Exception {
		return XMLDBManager.retrieve("/db/reviews", documentId);
	}

	public int getDocumentCount() throws Exception {
		return XMLDBManager.getDocumentCount("/db/reviews");
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
	
	public BusinessProcess acceptOrReject(String paperID, TReviewAssignementState state) throws Exception {
		String xpath = String.format("//sp:businessProcess[sp:scientificPaperId =\"%s\"]", paperID);
		ResourceSet result = XMLDBManager.retrieveWithXPath("/db/processes", xpath, TARGET_NAMESPACE);
        if (result == null) {
			throw new Exception("Process error!");
		}
        ResourceIterator i=i= result.getIterator();
        XMLResource res = null;
        BusinessProcess bp = null;
		while (i.hasMoreResources()) {
			try {
				res = (XMLResource) i.nextResource();
				// pretvori ga u TPerson
				bp = unmarshalling(res);
			} finally {
				// don't forget to cleanup resources
				try {
					((EXistResource) res).freeResources();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}
		}
		if(state.equals(TReviewAssignementState.ACCEPTED)) {
			bp.setState(TState.ON_REVIEW);
		}
		for(TReviewAssignment ra : bp.getReviewAssignments().getReviewAssignment()) {
			System.out.println(ra.getStatus());
			if(ra.getReviewer().getEmail().equalsIgnoreCase("0@00.AA")) {
				System.out.println(ra.getReviewer().getEmail());
				ra.setStatus(state);
				System.out.println(ra.getStatus());
			}
		}
		return bp;
       
	}

	
    public BusinessProcess unmarshalling(XMLResource res) throws Exception {
    	System.out.println(res.toString());
        JAXBContext context = JAXBContext.newInstance("com.xws.application.model");     
    	Unmarshaller unmarshaller = context.createUnmarshaller();
    	BusinessProcess process = (BusinessProcess) unmarshaller.unmarshal(res.getContentAsDOM());
    	return process;
    }
    
    public String marshalling(BusinessProcess process) throws JAXBException {
    	JAXBContext context = JAXBContext.newInstance("com.xws.application.model");
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        OutputStream os = new ByteArrayOutputStream();
        marshaller.marshal(process, os);
        String xml = os.toString();
        System.out.println(xml);
        return xml;
    }
}
