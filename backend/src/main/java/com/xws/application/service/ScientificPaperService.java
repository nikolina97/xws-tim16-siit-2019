package com.xws.application.service;

import com.xws.application.dto.PaperLetterDTO;
import com.xws.application.model.DocType;
import com.xws.application.model.ScientificPaper;
import com.xws.application.parser.DOMParser;
import com.xws.application.parser.JAXB;
import com.xws.application.repository.ScientificPaperRepository;
import com.xws.application.util.rdf.DOMToXMLFile;
import com.xws.application.util.rdf.MetadataExtractor;
import com.xws.application.util.rdf.StringToXMLFile;
import com.xws.application.util.rdf.RDFFileToString;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

@Service
public class ScientificPaperService {

	@Autowired
	private ScientificPaperRepository repository;
	
	@Autowired
	private MetadataExtractor metadataExtractor;
	
	@Autowired
	private DOMParser domParser;
	
	private static String xmlFilePath = "src/main/resources/rdfa/xml_file.xml";
	private static String rdfFilePath = "src/main/resources/rdfa/rdf_file.rdf";

	public boolean save(PaperLetterDTO dto) {
		try {
			ScientificPaper paper = (ScientificPaper) JAXB.unmarshal(xml, DocType.SCIENTIFIC_PAPER);
//			System.out.println(paper);
//			Document document = domParser.buildDocument(xml);
			
			StringToXMLFile.stringToDom(xml, xmlFilePath);

			metadataExtractor.extractMetadata(new FileInputStream(new File(xmlFilePath)), new FileOutputStream(new File(rdfFilePath)));
			String metadata = RDFFileToString.toString(rdfFilePath);
			
			repository.storeMetadata(metadata, "/scientific_paper/"+paper.getId());
			repository.store(paper, "scientific_paper.xml");		
			
			//XMLDBManager.store(paper, "scientific_paper.xml");

			return true;
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}
	}

	public ScientificPaper get(String paperID) {
		try {
			return (ScientificPaper) repository.retrieve("scientific_paper.xml");
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

}
