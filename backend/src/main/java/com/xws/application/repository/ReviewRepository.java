package com.xws.application.repository;

import com.xws.application.model.*;
import com.xws.application.service.BusinessProcessService;
import com.xws.application.util.rdf.RdfConnectionProperties;
import com.xws.application.util.rdf.SparqlUtil;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.exist.xmldb.EXistResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Node;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewRepository {
	public static final String TARGET_NAMESPACE = "https://github.com/nikolina97/xws-tim16-siit-2019";

	@Autowired
	private BusinessProcessService processService;

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
		BusinessProcess process = processService.get(paperID + ".xml");

		if (state.equals(TReviewAssignementState.ACCEPTED)) {
			process.setState(TState.ON_REVIEW);
		}
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		for (TReviewAssignment ra : process.getReviewAssignments().getReviewAssignment()) {
			if (ra.getReviewer().getEmail().equals(email)) {
				ra.setStatus(state);
				break;
			}
		}
		return process;
	}

	public BusinessProcess unmarshallingBP(XMLResource res) throws Exception {
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

	public List<Users.User> getUsersByExpertise(List<String> keywords) throws Exception {

		String xPathExp = null;
		List<Users.User> usersS = new ArrayList<Users.User>();
		for (String s : keywords) {
			xPathExp = String.format("/sp:users/sp:user[sp:userInfo/sp:expertise/text()[contains(., \"%s\")]]", s);
			ResourceSet result = XMLDBManager.retrieveWithXPath("/db/library/users/", xPathExp, "https://github.com/nikolina97/xws-tim16-siit-2019");
			ResourceIterator i = result.getIterator();
			XMLResource res = null;
			Users.User sp = null;
			while (i.hasMoreResources()) {
				try {
					res = (XMLResource) i.nextResource();
					// pretvori ga u TPerson
					sp = unmarshalling(res);
					usersS.add(sp);
				} finally {
					// don't forget to cleanup resources
					try {
						((EXistResource) res).freeResources();
					} catch (XMLDBException xe) {
						xe.printStackTrace();
					}
				}
			}

		}
		return usersS;
	}

	public Users.User unmarshalling(XMLResource res) throws Exception {
		JAXBContext context = JAXBContext.newInstance("com.xws.application.model");
		Unmarshaller unmarshaller = context.createUnmarshaller();
		Node node = res.getContentAsDOM();
		Users.User user = (Users.User) unmarshaller.unmarshal(node);
		return user;
	}
}
