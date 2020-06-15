package com.xws.application.service;

import com.xws.application.model.CoverLetter;
import com.xws.application.model.DocType;
import com.xws.application.parser.DOMParser;
import com.xws.application.parser.JAXB;
import com.xws.application.repository.CoverLetterRepository;
import com.xws.application.util.rdf.MetadataExtractor;
import com.xws.application.util.rdf.RDFFileToString;
import com.xws.application.util.rdf.StringToXMLFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoverLetterService {

	@Autowired
	private CoverLetterRepository repository;
	
	@Autowired
	private MetadataExtractor metadataExtractor;
	
	@Autowired
	private DOMParser domParser;
	
	private static String xmlFilePath = "src/main/resources/rdfa/xml_file.xml";
	private static String rdfFilePath = "src/main/resources/rdfa/rdf_file.rdf";

	public boolean save(String xml) {
		try {
			CoverLetter letter = (CoverLetter) JAXB.unmarshal(xml, DocType.COVER_LETTER);
//			System.out.println(paper);
//			Document document = domParser.buildDocument(xml);
			
			StringToXMLFile.stringToDom(xml, xmlFilePath);
			metadataExtractor.extractMetadata(new FileInputStream(new File(xmlFilePath)), new FileOutputStream(new File(rdfFilePath)));
			String metadata = RDFFileToString.toString(rdfFilePath);
			repository.storeMetadata(metadata, "/cover_letter/" + letter.getId());
			
			repository.store(letter, "cover_letter.xml");

			//XMLDBManager.store(paper, "scientific_paper.xml");

			return true;
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}
	}

	public CoverLetter get(String letterID) {
		try {
			return (CoverLetter) repository.retrieve("scientific_paper.xml");
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

}
