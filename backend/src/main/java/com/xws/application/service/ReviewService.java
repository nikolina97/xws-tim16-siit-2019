package com.xws.application.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.xws.application.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xws.application.model.DocType;
import com.xws.application.parser.DOMParser;
import com.xws.application.parser.JAXB;
import com.xws.application.repository.ReviewRepository;
import com.xws.application.util.rdf.MetadataExtractor;
import com.xws.application.util.rdf.RDFFileToString;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository repository;
	
	@Autowired
	private MetadataExtractor metadataExtractor;
	
	@Autowired
	private DOMParser domParser;
	
	private static String xmlFilePath = "src/main/resources/rdfa/xml_file.xml";
	private static String rdfFilePath = "src/main/resources/rdfa/rdf_file.rdf";

	public boolean save(String xml) {
		try {
			Review review = (Review) JAXB.unmarshal(xml, DocType.REVIEW);
//			System.out.println(paper);
//			Document document = domParser.buildDocument(xml);
			
			// StringToXMLFile.stringToDom(xml);
			metadataExtractor.extractMetadata(new FileInputStream(new File(xmlFilePath)), new FileOutputStream(new File(rdfFilePath)));
			String metadata = RDFFileToString.toString(rdfFilePath);
			repository.storeMetadata(metadata, "/review/" + review.getId());
			
			repository.store(review, "review.xml");

			//XMLDBManager.store(paper, "scientific_paper.xml");

			return true;
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}
	}

	public Review get(String reviewID) {
		try {
			return (Review) repository.retrieve(reviewID);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

}
