package com.xws.application.service;

import com.xws.application.model.CoverLetter;
import com.xws.application.parser.DOMParser;
import com.xws.application.repository.CoverLetterRepository;
import com.xws.application.repository.ScientificPaperRepository;
import com.xws.application.util.rdf.MetadataExtractor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

@Service
public class CoverLetterService {

	@Autowired
	private CoverLetterRepository letterrepository;

	@Autowired
	private ScientificPaperRepository paperRepository;
	
	@Autowired
	private MetadataExtractor metadataExtractor;
	
	@Autowired
	private DOMParser domParser;
	
	private static String xmlFilePath = "src/main/resources/rdfa/xml_file.xml";
	private static String rdfFilePath = "src/main/resources/rdfa/rdf_file.rdf";

	public boolean save(Document letterDOM) {
		try {
			// Generate ID for letter
			String letterId = "letter" + (letterrepository.getDocumentCount() + 1);

			letterDOM.getDocumentElement().setAttribute("about", "http://ftn.uns.ac.rs/letter/" + letterId);
			letterDOM.getDocumentElement().setAttribute("rel", "pred:letter");
			letterDOM.getDocumentElement().setAttribute("href", "http://ftn.uns.ac.rs/paper/paper" + (paperRepository.getDocumentCount()));
			letterDOM.getDocumentElement().setAttribute("id", letterId);

			/*metadataExtractor.extractMetadata(new FileInputStream(new File(xmlFilePath)), new FileOutputStream(new File(rdfFilePath)));
			String metadata = RDFFileToString.toString(rdfFilePath);
			letterrepository.storeMetadata(metadata, "/cover_letter");*/

			letterrepository.store(letterDOM, letterId + ".xml");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public CoverLetter get(String letterID) {
		try {
			return (CoverLetter) letterrepository.retrieve(letterID);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

}
