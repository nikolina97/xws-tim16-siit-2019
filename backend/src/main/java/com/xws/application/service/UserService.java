package com.xws.application.service;

import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.xws.application.dto.UserRegistrationDTO;
import com.xws.application.model.DocType;
import com.xws.application.model.TPerson;
import com.xws.application.model.TPerson.University;
import com.xws.application.model.TRole;
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
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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
	
	public void registerAuthor(UserRegistrationDTO dto) throws Exception {
		if(dto.getRole().equals(TRole.ROLE_AUTHOR)) {
			if(Stream.of(dto.getEmail(), dto.getFirstName(), dto.getLastName(), dto.getPassword(), dto.getUniversityCity(), dto.getUniversityCountry(), dto.getUniversityName()).anyMatch(Objects::isNull))
				throw new Exception("Some data is missing");
		}
		else {
			if(Stream.of(dto.getEmail(), dto.getExpertises(), dto.getFirstName(), dto.getLastName(), dto.getPassword(), dto.getUniversityCity(), dto.getUniversityCountry(), dto.getUniversityName()).anyMatch(Objects::isNull))
				throw new Exception("Some data is missing");
		}
		
		if(!dto.getEmail().matches("[0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.)+[a-zA-Z]{2,9}")) {
			throw new Exception("Invalid email");
		}
		Users.User uu = repository.findByEmail(dto.getEmail());
		if(uu != null) {
			throw new Exception("Email taken");
		}
		
		Users.User user = new Users.User();
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setRole(dto.getRole());
		user.setUserInfo(new TPerson());
		user.getUserInfo().setEmail(dto.getEmail());
		TPerson.FirstName fName = new TPerson.FirstName();
		fName.setValue(dto.getFirstName());
		TPerson.LastName lName = new TPerson.LastName();
		lName.setValue(dto.getLastName());
		user.getUserInfo().setFirstName(fName);
		user.getUserInfo().setLastName(lName);
		user.getUserInfo().setUniversity(new University());
		user.getUserInfo().getUniversity().setName(dto.getUniversityName());
		user.getUserInfo().getUniversity().setCountry(dto.getUniversityCountry());
		user.getUserInfo().getUniversity().setCity(dto.getUniversityCity());
		if(dto.getRole() == TRole.ROLE_REVIEWER) {
			user.getUserInfo().setExpertise(dto.getExpertises());
		}
		repository.save(user);
	}
}