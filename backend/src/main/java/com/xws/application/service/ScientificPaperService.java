package com.xws.application.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.io.*;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import com.xws.application.exception.BadRequestException;
import com.xws.application.exception.InternalServerErrorException;
import com.xws.application.exception.NotFoundException;
import com.xws.application.model.*;
import com.xws.application.parser.JAXB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.xws.application.dto.PaperLetterDTO;
import com.xws.application.dto.ScientificPaperMetadataSearchDTO;
import com.xws.application.parser.DOMParser;
import com.xws.application.repository.ScientificPaperRepository;
import com.xws.application.util.XPathExpressionHandlerNS;
import com.xws.application.util.XSLFOTransformer;
import com.xws.application.util.rdf.DOMToXMLFile;
import com.xws.application.util.rdf.MetadataExtractor;
import com.xws.application.util.rdf.RDFFileToString;
import org.xml.sax.SAXException;

@Service
public class ScientificPaperService {

	@Autowired
	private ScientificPaperRepository repository;

	@Autowired
	private CoverLetterService letterService;

	@Autowired
	private BusinessProcessService processService;
	
	@Autowired
	private MetadataExtractor metadataExtractor;
	
	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private XSLFOTransformer transformer;
	
	private static String xmlFilePath = "src/main/resources/rdfa/xml_file.xml";
	private static String rdfFilePath = "src/main/resources/rdfa/rdf_file.rdf";

	public boolean save(PaperLetterDTO dto) {
		dto.setPaper("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + dto.getPaper());
		dto.setLetter("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + dto.getLetter());
		// System.out.println(dto.getPaper());

		/*ScientificPaper paper = (ScientificPaper) JAXB.unmarshal(dto.getPaper(), DocType.SCIENTIFIC_PAPER);
		Document document = domParser.buildDocument(xml);*/

		try {
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(new File("src/main/resources/schemas/scientific_paper.xsd"));

			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new StringReader(dto.getPaper())));

			schema = schemaFactory.newSchema(new File("src/main/resources/schemas/cover_letter.xsd"));
			validator = schema.newValidator();
			validator.validate(new StreamSource(new StringReader(dto.getLetter())));

			Document paperDOM = domParser.buildDocument(dto.getPaper());

			Element version = paperDOM.createElement("sp:version");
			version.setTextContent("1");
			version.setAttribute("property", "pred:version");
			version.setAttribute("datatype", "xs:string");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			String date = sdf.format(new Date());
			Element dateReceived = paperDOM.createElement("sp:dateReceived");
			dateReceived.setTextContent(date);
			dateReceived.setAttribute("property", "pred:dateReceived");
			dateReceived.setAttribute("datatype", "xs:string");
			
			Element status = paperDOM.createElement("sp:state");
			status.setTextContent("in_procedure");
			status.setAttribute("property", "pred:state");
			status.setAttribute("datatype", "xs:string");
			
			paperDOM.getDocumentElement().appendChild(version);
			paperDOM.getDocumentElement().appendChild(dateReceived);
			paperDOM.getDocumentElement().appendChild(status);
			
			XPathExpressionHandlerNS handler = new XPathExpressionHandlerNS();
			handler.addNamespaceMapping("sp", "https://github.com/nikolina97/xws-tim16-siit-2019");

			// Generate IDs for paper
			String paperId = "paper" + (repository.getDocumentCount() + 1);
			paperDOM = generateIDs(paperDOM, paperId, handler);

			/*DOMSource domSource = new DOMSource(paperDOM);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(domSource, result);

			System.out.println(writer.toString());*/
			
			DOMToXMLFile.toXML(paperDOM, xmlFilePath);
			metadataExtractor.extractMetadata(new FileInputStream(new File(xmlFilePath)), new FileOutputStream(new File(rdfFilePath)));
			String metadata = RDFFileToString.toString(rdfFilePath);
			repository.storeMetadata(metadata, "/scientific_paper");

			repository.store(paperDOM, paperId + ".xml");

			Document letterDOM = domParser.buildDocument(dto.getLetter());
			letterService.save(letterDOM);

			String title = handler.evaluateXPath(paperDOM, "//sp:title/text()").item(0).getNodeValue();

			BusinessProcess process = new BusinessProcess();
			process.setScientificPaperId(paperId);
			process.setScientificPaperTitle(title);
			process.setId(paperId);
			process.setState(TState.PUBLISHED);
			process.setVersion(BigInteger.valueOf(1));
			process.setReviewAssignments(new BusinessProcess.ReviewAssignments());

			processService.save(process, paperId + ".xml");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	private Document generateIDs(Document document, String paperId, XPathExpressionHandlerNS handler) {
		document.getDocumentElement().setAttribute("about", "http://ftn.uns.ac.rs/paper/" + paperId);
		document.getDocumentElement().setAttribute("id", paperId);

		String abstractPath = "//sp:abstract";
		String abstractId = paperId + "/abstract";

		Element abstractElement = (Element) handler.evaluateXPath(document, abstractPath).item(0);
		abstractElement.setAttribute("id", abstractId);

		document = generateIDsForParagraph(document, abstractPath, abstractId, handler);

		String chaptersPath = "//sp:chapter";
		NodeList chapters = handler.evaluateXPath(document, chaptersPath);

		for (int i = 0; i < chapters.getLength(); i++) {
			String chapterId = paperId + "/chapter" + (i + 1);

			Element chapter = (Element) chapters.item(i);
			chapter.setAttribute("id", chapterId);

			document = generateIDsForParagraph(document, chaptersPath, chapterId, handler);
			document = generateIDsForSubchapters(document, chaptersPath, chapterId, handler);
		}

		String referencesPath = "//sp:reference";
		NodeList references = handler.evaluateXPath(document, referencesPath);

		for (int i = 0; i < references.getLength(); i++) {
			String chapterId = paperId + "/reference" + (i + 1);

			Element reference = (Element) references.item(i);
			reference.setAttribute("id", chapterId);
		}

		return document;
	}

	private Document generateIDsForParagraph(Document document, String parentPath, String parentID, XPathExpressionHandlerNS handler) {
		String paragraphsPath = parentPath + "/sp:paragraph";
		NodeList paragraphs = handler.evaluateXPath(document, paragraphsPath);
		for (int i = 0; i < paragraphs.getLength(); i++) {
			String paragraphId = parentID + "/paragraph" + (i + 1);

			Element paragraph = (Element) paragraphs.item(i);
			paragraph.setAttribute("id", paragraphId);

			String tablesPath = paragraphsPath + "/sp:table";
			NodeList tables = handler.evaluateXPath(document, tablesPath);

			for (int j = 0; j < tables.getLength(); j++) {
				String tableId = paragraphId + "/table" + (j + 1);

				Element table = (Element) tables.item(j);
				table.setAttribute("id", tableId);
			}

			String figuresPath = paragraphsPath + "/sp:figure";
			NodeList figures = handler.evaluateXPath(document, figuresPath);

			for (int j = 0; j < figures.getLength(); j++) {
				String figureId = paragraphId + "/figure" + (j + 1);

				Element figure = (Element) figures.item(j);
				figure.setAttribute("id", figureId);
			}

			String formulasPath = paragraphsPath + "/sp:formula";
			NodeList formulas = handler.evaluateXPath(document, formulasPath);

			for (int j = 0; j < formulas.getLength(); j++) {
				String formulaId = paragraphId + "/formula" + (j + 1);

				Element formula = (Element) formulas.item(j);
				formula.setAttribute("id", formulaId);
			}

			String listsPath = paragraphsPath + "/sp:list";
			NodeList lists = handler.evaluateXPath(document, listsPath);

			for (int j = 0; j < lists.getLength(); j++) {
				String listId = paragraphId + "/list" + (j + 1);

				Element list = (Element) lists.item(j);
				list.setAttribute("id", listId);
			}
		}

		return document;
	}

	private Document generateIDsForSubchapters(Document document, String parentPath, String parentID, XPathExpressionHandlerNS handler) {
		String subchaptersPath = parentPath + "/sp:subchapter";
		NodeList subchapters = handler.evaluateXPath(document, subchaptersPath);

		for (int i = 0; i < subchapters.getLength(); i++) {
			String subchapterId = parentID + "/subchapter" + (i + 1);

			Element subchapter = (Element) subchapters.item(i);
			subchapter.setAttribute("id", subchapterId);

			document = generateIDsForParagraph(document, subchaptersPath, subchapterId, handler);
			document = generateIDsForSubchapters(document, subchaptersPath, subchapterId, handler);
		}

		return document;
	}

	public void revise(String revision, String id) {
		try {
			ScientificPaper original = repository.retrieveJAXB(id + ".xml");
			if(original == null)
				throw new NotFoundException("Paper not found.");

			Users.User user = (Users.User) SecurityContextHolder.getContext().getAuthentication();

			List<ScientificPaper> papers = repository.getQuerySP("scientific_paper", user.getUserInfo().getEmail());
			if(papers.stream().noneMatch(paper -> paper.getAuthors().getAuthor().stream().anyMatch(author -> author.getEmail().equals(user.getUserInfo().getEmail()))))
				throw new BadRequestException("This paper is not yours.");

			BusinessProcess process = processService.get(id + ".xml");

			if(!process.getState().equals(TState.ON_REVISE) || original.getState().getValue().equals(TSPState.REJECTED) || original.getState().getValue().equals(TSPState.REVOKED))
				throw new BadRequestException("Your don't have permission to write revision of this paper.");

			// Validate against schema
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(new File("src/main/resources/schemas/scientific_paper.xsd"));

			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new StringReader(revision)));

			// Change version
			ScientificPaper revisionModel = (ScientificPaper) JAXB.unmarshal(revision, DocType.SCIENTIFIC_PAPER);
			revisionModel.getVersion().setValue(BigInteger.valueOf(revisionModel.getVersion().getValue().intValue() + 1));

			// Set date of revision
			GregorianCalendar c = new GregorianCalendar();
			c.clear();
			c.setTime(new Date());
			ScientificPaper.DateRevised dateRevised = new ScientificPaper.DateRevised();
			dateRevised.setValue(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
			revisionModel.setDateRevised(dateRevised);

			// Update the business process for this paper
			process.setState(TState.REVISED);
			process.setVersion(revisionModel.getVersion().getValue());

			processService.save(process, id + ".xml");

		} catch (SAXException | IOException e) {
			e.printStackTrace();
			throw new BadRequestException("Your revision is incorrect, check it.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new InternalServerErrorException("Something bad happend on the server.");
		}
	}

	public ScientificPaper get(String paperID) {
		try {
			return (ScientificPaper) repository.retrieve(paperID);
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}
	
	public List<ScientificPaper> getPapersFromUser() throws Exception {
		String email = "/person/john.doe@uns.ac.rs";
		String graphName = "scientific_paper/";
		List<ScientificPaper> papers = repository.getQuerySP(graphName, email);
		return papers;
	}
	
	public List<ScientificPaper> getAllPapersByUser(ScientificPaperMetadataSearchDTO metadataSearch) throws Exception {
		String email = SecurityContextHolder.getContext().getAuthentication().getName(); //hard coded
		System.out.println(email);
		String graphName = "scientific_paper";
		String advancedQuery = "";
		if (metadataSearch.getCategory() != null) {
			advancedQuery += "\n?subject <https://schema.org/category> \"" + metadataSearch.getCategory() + "\" .";
		}
		if (metadataSearch.getDateRecieved() != null) {
			advancedQuery += "\n?subject <https://schema.org/dateReceived> ?dateReceived\n \tfilter contains(?dateReceived,\"" + metadataSearch.getDateRecieved() + "\") .";
		}
		if (metadataSearch.getDateAccepted() != null) {
			advancedQuery += "\n?subject <https://schema.org/dateAccepted> ?dateAccepted\n \tfilter contains(?dateAccepted,\"" + metadataSearch.getDateAccepted() + "\") .";
		}
		if (metadataSearch.getDateRevised() != null) {
			advancedQuery += "\n?subject <https://schema.org/dateRevised> ?dateRevised\n \tfilter contains(?dateRevised,\"" + metadataSearch.getDateRevised() + "\") .";
		}
		if (metadataSearch.getAuthorFirstName() != null) {
			advancedQuery += "\n?subject <https://schema.org/author> ?author . \n\t?author <https://schema.org/first_name> \"" + metadataSearch.getAuthorFirstName() + "\" .";
		}
		if (metadataSearch.getAuthorLastName() != null) {
			advancedQuery += "\n?subject <https://schema.org/author> ?author . \n\t?author <https://schema.org/last_name> \"" + metadataSearch.getAuthorLastName() + "\" .";
		}
		if (metadataSearch.getTitle() != null) {
			advancedQuery += "\n?subject <https://schema.org/headline> \"" + metadataSearch.getTitle() + "\" .";
		}
		if (metadataSearch.getVersion() != null) {
			advancedQuery += "\n?subject <https://schema.org/version> \"" + metadataSearch.getVersion() + "\" .";
		}
		if (metadataSearch.getKeywords() != null) {
			advancedQuery += "\n?subject <https://schema.org/keyword> \"" + metadataSearch.getKeywords() + "\" .";
		}
		Boolean loggedIn = false;
		if (email == "anonymousUser") {
			loggedIn = false;
		}
		else {
			loggedIn = true;
		}
		List<ScientificPaper> papers = repository.getAllPapersByAuthor(graphName, "/person/" + email, advancedQuery, loggedIn);
		System.out.println(papers.size());
		return papers;
	}

	public List<ScientificPaper> basicSearch(String searchText) throws Exception {
		String graphName = "scientific_paper";
		String email = SecurityContextHolder.getContext().getAuthentication().getName(); 
		System.out.println(email);
		Boolean loggedIn = false;
		if (email == "anonymousUser") {
			loggedIn = false;
		}
		else {
			loggedIn = true;
		}
		if (searchText == null || searchText == "") {
			List<ScientificPaper> papers = repository.getAllSPbasicSearch(graphName, email, "", loggedIn);
			return papers;
		}
		List<ScientificPaper> papers = repository.getAllSPbasicSearch(graphName, email, searchText, loggedIn);
		return papers;
	}
	
	public String getPaperHTML(String id) throws Exception {
		String xml = repository.getPaperById(id);

		String html = transformer.generateHTML(xml, "src/main/resources/xslt/scientific_paper.xsl");
		return html;
	}
	
	public ByteArrayOutputStream getPaperPDF(String id) throws Exception {
		String xml = repository.getPaperById(id);

		ByteArrayOutputStream html = transformer.generatePDF(xml, "src/main/resources/xsl-fo/scientific_paper_fo.xsl");
		return html;
	}
	
	public String getPaperXML(String id) throws Exception {
		String xml = repository.getPaperById(id);
		return xml;
	}


}
