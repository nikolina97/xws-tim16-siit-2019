package com.xws.application.repository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.exist.xmldb.EXistResource;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Node;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.xws.application.dto.ScientificPaperMetadataSearchDTO;
import com.xws.application.model.ScientificPaper;
import com.xws.application.util.rdf.RdfConnectionProperties;
import com.xws.application.util.rdf.SparqlUtil;

@Repository
public class ScientificPaperRepository {

	public void store(Object model, String documentId) throws Exception {
		XMLDBManager.store(model, "/db/papers", documentId);
	}

	public Object retrieve(String documentId) throws Exception {
		return XMLDBManager.retrieve("/db/papers", documentId);
	}

	public int getDocumentCount() throws Exception {
		return XMLDBManager.getDocumentCount("/db/papers");
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
	
	public List<ScientificPaper> getQuerySP(String graphName, String condition) throws Exception {
		RdfConnectionProperties conn = RdfConnectionProperties.loadProperties();
		condition = "?subject <https://schema.org/author> <http://ftn.uns.ac.rs" + condition + ">";
		String sparqlQuery = SparqlUtil.selectData(conn.getDataEndpoint() + "/" + graphName, condition);
		System.out.println("endpoint " + conn.getDataEndpoint() + graphName + "  ----  "+ sparqlQuery);
		QueryExecution query = QueryExecutionFactory.sparqlService(conn.getQueryEndpoint(), sparqlQuery);
		ResultSet results = query.execSelect();
		String varName;
		RDFNode varValue;
		
		List<String> ids = new ArrayList<String>();
		while(results.hasNext()) {
		    
			// A single answer from a SELECT query
			QuerySolution querySolution = results.next() ;
			Iterator<String> variableBindings = querySolution.varNames();
			
			// Retrieve variable bindings
		    while (variableBindings.hasNext()) {
		   
		    	varName = variableBindings.next();
		    	varValue = querySolution.get(varName);
		    	
		    	String[] idList = varValue.toString().split("/");
		    	ids.add(idList[idList.length - 1]);
		    }
		    System.out.println();
		}
		List<ScientificPaper> papers = new ArrayList<>();
		
		String xpathExp = null;
		for (String s : ids) {
			xpathExp = String.format("/sp:scientific_paper[@sp:id=\"%s\"]", s);
			ResourceSet result = XMLDBManager.retrieveWithXPath("/db/papers/", xpathExp, "https://github.com/nikolina97/xws-tim16-siit-2019");
			
			if (result == null) {
				return papers;
			}
	        ResourceIterator i = result.getIterator();
			XMLResource res = null;
			ScientificPaper sp = null;
			while (i.hasMoreResources()) {
				try {
					res = (XMLResource) i.nextResource();
					// pretvori ga u TPerson
					sp = unmarshalling(res);
					papers.add(sp);
				} finally {
					// don't forget to cleanup resources
					try {
						((EXistResource) res).freeResources();
					} catch (XMLDBException xe) {
						xe.printStackTrace();
					}
				}
			}
			
			//			papers.add((ScientificPaper) XMLDBManager.retrieveWithXPath("/db/library/papers", xpathExp, TARGET_NAMESPACE));
		}
		for (ScientificPaper sp : papers) {
			System.out.println("ID " + sp.getId());
		}
		return papers;
	}
	
	public List<ScientificPaper> getAllPapersByAuthor(String graphName, String condition, String advancedQuery, Boolean loggedIn) throws Exception {
		RdfConnectionProperties conn = RdfConnectionProperties.loadProperties();
		String condition1 = "?subject <https://schema.org/state> \"accepted\"";
		if (!loggedIn) {
			condition = "";
		}else {
			condition = "?subject <https://schema.org/author> <http://ftn.uns.ac.rs" + condition + ">";
		}
		String sparqlQuery = SparqlUtil.selectDistinctUnionMeta(conn.getDataEndpoint() + "/" + graphName, condition1, condition, advancedQuery);
//		String sparqlQuery = SparqlUtil.selectData(conn.getDataEndpoint() + "/" + graphName, condition);
		System.out.println("endpoint " + conn.getDataEndpoint() + "/" + graphName + "  ----  "+ sparqlQuery);
		QueryExecution query = null;
		query = QueryExecutionFactory.sparqlService(conn.getQueryEndpoint(), sparqlQuery);
		ResultSet results = null;
		results = query.execSelect();
		String varName;
		RDFNode varValue;
		List<String> ids = new ArrayList<String>();
		while(results.hasNext()) {
			// A single answer from a SELECT query
			QuerySolution querySolution = results.next() ;
			Iterator<String> variableBindings = querySolution.varNames();
			
			// Retrieve variable bindings
				while (variableBindings.hasNext()) {
					   
			    	varName = variableBindings.next();
			    	varValue = querySolution.get(varName);
			    	
			    	System.out.println(varName + ": " + varValue);
			    	String[] idList = varValue.toString().split("/");
			    	ids.add(idList[idList.length - 1]);
			    }
		    
			}
		query.close();
		
		List<ScientificPaper> papers = new ArrayList<>();
		
		String xpathExp = null;
		for (String s : ids) {
			xpathExp = String.format("/sp:scientific_paper[@sp:id=\"%s\"]", s);
			ResourceSet result = XMLDBManager.retrieveWithXPath("/db/papers/", xpathExp, "https://github.com/nikolina97/xws-tim16-siit-2019");
			
			if (result == null) {
				return papers;
			}
	        ResourceIterator i = result.getIterator();
			XMLResource res = null;
			ScientificPaper sp = null;
			while (i.hasMoreResources()) {
				try {
					res = (XMLResource) i.nextResource();
					// pretvori ga u TPerson
					sp = unmarshalling(res);
					sp.setId(s);
					papers.add(sp);
				} finally {
					// don't forget to cleanup resources
					try {
						((EXistResource) res).freeResources();
					} catch (XMLDBException xe) {
						xe.printStackTrace();
					}
				}
			}
			
			//			papers.add((ScientificPaper) XMLDBManager.retrieveWithXPath("/db/library/papers", xpathExp, TARGET_NAMESPACE));
		}
		for (ScientificPaper sp : papers) {
			System.out.println("ID " + sp.getId());
		}
		return papers;
	}
	
	public List<ScientificPaper> getAllSPbasicSearch(String graphName, String authorEmail, String searchText, Boolean loggedIn) throws Exception {
		RdfConnectionProperties conn = RdfConnectionProperties.loadProperties();
		String condition1 = "?subject <https://schema.org/state> \"accepted\"";
		String condition2 = "";
		if (loggedIn) {
			condition2 = "?subject <https://schema.org/author> <http://ftn.uns.ac.rs" + authorEmail + ">";
		}
		String sparqlQuery = SparqlUtil.selectDistinctUnionMeta(conn.getDataEndpoint() + "/" + graphName, condition1, condition2, "");
//		String sparqlQuery = SparqlUtil.selectData(conn.getDataEndpoint() + "/" + graphName, condition);
		System.out.println("endpoint " + conn.getDataEndpoint() + "/" + graphName + "  ----  "+ sparqlQuery);
		QueryExecution query = null;
		query = QueryExecutionFactory.sparqlService(conn.getQueryEndpoint(), sparqlQuery);
		ResultSet results = null;
		results = query.execSelect();
		String varName;
		RDFNode varValue;
		List<String> ids = new ArrayList<String>();
		while(results.hasNext()) {
			// A single answer from a SELECT query
			QuerySolution querySolution = results.next() ;
			Iterator<String> variableBindings = querySolution.varNames();
			
			// Retrieve variable bindings
				while (variableBindings.hasNext()) {
					   
			    	varName = variableBindings.next();
			    	varValue = querySolution.get(varName);
			    	
			    	System.out.println(varName + ": " + varValue);
			    	String[] idList = varValue.toString().split("/");
			    	ids.add(idList[idList.length - 1]);
			    }
		    
			}
		query.close();
		
		List<ScientificPaper> papers = new ArrayList<>();
		
		String xpathExp = null;
		for (String s : ids) {
			if (searchText == "") {
				xpathExp = String.format("/sp:scientific_paper[@sp:id=\"%s\"]", s);
			}
			else {
				xpathExp = String.format("//*[@sp:id=\"%1$s\" and contains(.,\"%2$s\")]", s, searchText);
			}
			ResourceSet result = XMLDBManager.retrieveWithXPath("/db/papers/", xpathExp, "https://github.com/nikolina97/xws-tim16-siit-2019");
			
			if (result == null) {
				return papers;
			}
	        ResourceIterator i = result.getIterator();
			XMLResource res = null;
			ScientificPaper sp = null;
			while (i.hasMoreResources()) {
				try {
					res = (XMLResource) i.nextResource();
					// pretvori ga u TPerson
					sp = unmarshalling(res);
					sp.setId(s);
					papers.add(sp);
				} finally {
					// don't forget to cleanup resources
					try {
						((EXistResource) res).freeResources();
					} catch (XMLDBException xe) {
						xe.printStackTrace();
					}
				}
			}
			
			//			papers.add((ScientificPaper) XMLDBManager.retrieveWithXPath("/db/library/papers", xpathExp, TARGET_NAMESPACE));
		}
		for (ScientificPaper sp : papers) {
			System.out.println("ID " + sp.getId());
		}
		return papers;
	}
	
	 public ScientificPaper unmarshalling(XMLResource res) throws Exception {
	        JAXBContext context = JAXBContext.newInstance("com.xws.application.model");     
	    	Unmarshaller unmarshaller = context.createUnmarshaller();
	    	Node node = res.getContentAsDOM();
	    	ScientificPaper sPaper = (ScientificPaper) unmarshaller.unmarshal(node);
	    	return sPaper;
    }
	

}
