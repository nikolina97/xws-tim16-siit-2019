package com.xws.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xws.application.model.DocType;
import com.xws.application.model.Users;
import com.xws.application.parser.DOMParser;
import com.xws.application.parser.JAXB;
import com.xws.application.repository.UserRepository;
import com.xws.application.util.rdf.MetadataExtractor;

@Service
public class UserService {
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private MetadataExtractor metadataExtractor;
	
	@Autowired
	private DOMParser domParser;
	
	private static String xmlFilePath = "src/main/resources/rdfa/xml_file.xml";
	private static String rdfFilePath = "src/main/resources/rdfa/rdf_file.rdf";

	public boolean save(String xml) {
		try {
			Users users = (Users) JAXB.unmarshal(xml, DocType.USER);
//			System.out.println(paper);
//			Document document = domParser.buildDocument(xml);
			
//			StringToXMLFile.stringToDom(xml, xmlFilePath);
			
			repository.store(users, "users.xml");

			//XMLDBManager.store(paper, "scientific_paper.xml");

			return true;
		} catch (Exception e) {
			e.printStackTrace();

			return false;
		}
	}
	public Users get(String letterID) {
		try {
			return (Users) repository.retrieve("users.xml");
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}
}