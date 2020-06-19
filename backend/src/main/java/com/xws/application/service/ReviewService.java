package com.xws.application.service;

import com.xws.application.dto.ReviewDTO;
import com.xws.application.exception.BadRequestException;
import com.xws.application.exception.InternalServerErrorException;
import com.xws.application.exception.NotFoundException;
import com.xws.application.model.*;
import com.xws.application.parser.DOMParser;
import com.xws.application.repository.ReviewRepository;
import com.xws.application.repository.ScientificPaperRepository;
import com.xws.application.repository.UserRepository;
import com.xws.application.util.XPathExpressionHandlerNS;
import com.xws.application.util.rdf.MetadataExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private ScientificPaperRepository paperRepository;

	@Autowired
	private BusinessProcessService processService;
	
	@Autowired
	private MetadataExtractor metadataExtractor;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DOMParser domParser;
	
	private static String xmlFilePath = "src/main/resources/rdfa/xml_file.xml";
	private static String rdfFilePath = "src/main/resources/rdfa/rdf_file.rdf";

	public void save(ReviewDTO dto) {
		dto.setReview("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + dto.getReview());
		try {
			ScientificPaper original = paperRepository.retrieveJAXB(dto.getPaperId() + ".xml");
			if(original == null)
				throw new NotFoundException("Paper not found.");

			BusinessProcess process = processService.get(dto.getPaperId() + ".xml");
			String email = SecurityContextHolder.getContext().getAuthentication().getName();

			if(process.getReviewAssignments().getReviewAssignment().stream().noneMatch(assignment -> assignment.getReviewer().getEmail().equals(email)))
				throw new BadRequestException("Your don't have permission to write review of this paper.");

			if(original.getState().getValue().equals(TSPState.REJECTED) || original.getState().getValue().equals(TSPState.REVOKED) || process.getState().equals(TState.REVOKED) || process.getState().equals(TState.REJECTED) || process.getState().equals(TState.ON_REVISE) || process.getState().equals(TState.PUBLISHED))
				throw new BadRequestException("Your don't have permission to write review of this paper.");

			if(process.getReviewAssignments().getReviewAssignment().stream().filter(assignment -> assignment.getReviewer().getEmail().equals(email)).collect(Collectors.toList()).get(0).getStatus().equals(TReviewAssignementState.REVIEWED))
				throw new BadRequestException("You have already reviewed this paper.");

			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(new File("src/main/resources/schemas/review.xsd"));

			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new StringReader(dto.getReview())));

			Document reviewDOM = domParser.buildDocument(dto.getReview());

			XPathExpressionHandlerNS handler = new XPathExpressionHandlerNS();
			handler.addNamespaceMapping("sp", "https://github.com/nikolina97/xws-tim16-siit-2019");

			// Generate IDs
			String reviewId = "review" + (reviewRepository.getDocumentCount() + 1);
			reviewDOM = generateIDs(reviewDOM, reviewId, handler);

			/*DOMToXMLFile.toXML(reviewDOM, xmlFilePath);
			metadataExtractor.extractMetadata(new FileInputStream(new File(xmlFilePath)), new FileOutputStream(new File(rdfFilePath)));
			String metadata = RDFFileToString.toString(rdfFilePath);
			repository.storeMetadata(metadata, "/scientific_paper");*/

			reviewRepository.store(reviewDOM, reviewId + ".xml");

			// Update the business process for this paper
			process.getReviewAssignments().getReviewAssignment().stream().filter(assignment -> assignment.getReviewer().getEmail().equals(email)).forEach(assignment -> assignment.setStatus(TReviewAssignementState.REVIEWED));

			/*for(TReviewAssignment assignment : process.getReviewAssignments().getReviewAssignment()) {
				if(assignment.getReviewer().getEmail().equals(user.getUserInfo().getEmail())) {
					assignment.setStatus(TReviewAssignementState.REVIEWED);
					break;
				}
			}*/

			if(process.getReviewAssignments().getReviewAssignment().stream().allMatch(assignment -> assignment.getStatus().equals(TReviewAssignementState.REVIEWED)))
				process.setState(TState.ON_REVISE);

			// Check if there's a reviewer who didn't submit a review
			/*boolean notReviewedExist = false;
			for(TReviewAssignment assignment : process.getReviewAssignments().getReviewAssignment()) {
				if(!assignment.getStatus().equals(TReviewAssignementState.REVIEWED)) {
					notReviewedExist = true;
					break;
				}
			}*/

			// Each reviewer submitted their review
			/*if(!notReviewedExist)
				process.setState(TState.ON_REVISE);*/

			processService.save(process, dto.getPaperId() + ".xml");

		} catch (SAXException | IOException e) {
			e.printStackTrace();
			throw new BadRequestException("Your review is incorrect, check it.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new InternalServerErrorException("Something bad happened on the server.");
		}
	}

	private Document generateIDs(Document document, String reviewId, XPathExpressionHandlerNS handler) {
		document.getDocumentElement().setAttribute("about", "http://ftn.uns.ac.rs/review/" + reviewId);
		document.getDocumentElement().setAttribute("id", reviewId);

		String answersPath = "//sp:answer";
		NodeList answers = handler.evaluateXPath(document, answersPath);
		for (int i = 0; i < answers.getLength(); i++) {
			String answerId = "answer" + (i + 1);

			Element answer = (Element) answers.item(i);
			answer.setAttribute("id", answerId);

			document = generateIDsForParagraph(document, answersPath, answerId, handler);
		}

		String commentsPath = "//sp:comment";
		NodeList comments = handler.evaluateXPath(document, commentsPath);
		for (int i = 0; i < comments.getLength(); i++) {
			String commentId = "comment" + (i + 1);

			Element comment = (Element) comments.item(i);
			comment.setAttribute("id", commentId);

			document = generateIDsForParagraph(document, commentsPath, commentId, handler);
		}

		return document;
	}

	private Document generateIDsForParagraph(Document document, String parentPath, String parentID, XPathExpressionHandlerNS handler) {
		String tablesPath = parentPath + "/sp:table";
		NodeList tables = handler.evaluateXPath(document, tablesPath);

		for (int i = 0; i < tables.getLength(); i++) {
			String tableId = parentID + "/table" + (i + 1);

			Element table = (Element) tables.item(i);
			table.setAttribute("id", tableId);
		}

		String figuresPath = parentPath + "/sp:figure";
		NodeList figures = handler.evaluateXPath(document, figuresPath);

		for (int i = 0; i < figures.getLength(); i++) {
			String figureId = parentID + "/figure" + (i + 1);

			Element figure = (Element) figures.item(i);
			figure.setAttribute("id", figureId);
		}

		String formulasPath = parentPath + "/sp:formula";
		NodeList formulas = handler.evaluateXPath(document, formulasPath);

		for (int i = 0; i < formulas.getLength(); i++) {
			String formulaId = parentID + "/formula" + (i + 1);

			Element formula = (Element) formulas.item(i);
			formula.setAttribute("id", formulaId);
		}

		String listsPath = parentPath + "/sp:list";
		NodeList lists = handler.evaluateXPath(document, listsPath);

		for (int i = 0; i < lists.getLength(); i++) {
			String listId = parentID + "/list" + (i + 1);

			Element list = (Element) lists.item(i);
			list.setAttribute("id", listId);
		}

		return document;
	}

	public Review get(String reviewID) {
		try {
			return (Review) reviewRepository.retrieve(reviewID);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}
	
	public boolean accept(String paperId) throws Exception {
		BusinessProcess bp =reviewRepository.acceptOrReject(paperId, TReviewAssignementState.ACCEPTED);
		processService.save(bp, paperId+".xml");
		return true;
	}
	
	public boolean reject(String paperId) throws Exception {
		BusinessProcess bp =reviewRepository.acceptOrReject(paperId, TReviewAssignementState.REJECTED);
		processService.save(bp, paperId+".xml");
		return true;
	}

	public List<Users.User> getRecommendedReviewers(String paperId) throws Exception {
		String graphName = "users";
		
		List<String> keywords = paperRepository.getKeywords(paperId);
		List<Users.User> users = reviewRepository.getUsersByExpertise(keywords);
		List<Users.User> filteredUsers = new ArrayList<>();
		
		BusinessProcess process;
		process = processService.get(paperId + ".xml");
		
		for (Users.User u : users) { 
			if(process.getReviewAssignments().getReviewAssignment().stream().noneMatch(assignment -> assignment.getReviewer().getEmail().equals(u.getUserInfo().getEmail()))) {
				filteredUsers.add(u);
			}
		}
		
		return filteredUsers;
	}

	public Boolean assigneReviewer(String email, String paperId) throws Exception {
		
		Users.User reviewer = userRepository.findByEmail(email);
		BusinessProcess process;
		process = processService.get(paperId + ".xml");
		
		if (process.getState() != TState.SUBMITTED) {
			throw new BadRequestException("Paper is not submitted");
		}

		TReviewAssignment ra = new TReviewAssignment();
		ra.setReviewer(reviewer.getUserInfo());
		ra.setStatus(TReviewAssignementState.ASSIGNED);
		
		process.getReviewAssignments().getReviewAssignment().add(ra);
		processService.save(process, paperId + ".xml");
		return true;
	}

}
