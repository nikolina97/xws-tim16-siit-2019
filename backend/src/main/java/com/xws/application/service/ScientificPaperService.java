package com.xws.application.service;

import com.xws.application.dto.AssignmentDTO;
import com.xws.application.dto.PaperDTO;
import com.xws.application.dto.PaperLetterDTO;
import com.xws.application.dto.ScientificPaperMetadataSearchDTO;
import com.xws.application.exception.BadRequestException;
import com.xws.application.exception.InternalServerErrorException;
import com.xws.application.exception.NotFoundException;
import com.xws.application.model.*;
import com.xws.application.parser.DOMParser;
import com.xws.application.parser.JAXB;
import com.xws.application.repository.ScientificPaperRepository;
import com.xws.application.repository.XMLDBManager;
import com.xws.application.util.XPathExpressionHandlerNS;
import com.xws.application.util.XSLFOTransformer;
import com.xws.application.util.rdf.DOMToXMLFile;
import com.xws.application.util.rdf.MetadataExtractor;
import com.xws.application.util.rdf.RDFFileToString;
import org.exist.xmldb.EXistResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
			process.setState(TState.SUBMITTED);
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
		revision = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + revision;
		try {
			ScientificPaper original = repository.retrieveJAXB(id + ".xml");
			if(original == null)
				throw new NotFoundException("Paper not found.");

			String email = SecurityContextHolder.getContext().getAuthentication().getName();

			if(original.getAuthors().getAuthor().stream().noneMatch(author -> author.getEmail().equals(email)))
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

			Document revisionDOM = domParser.buildDocument(revision);

			XPathExpressionHandlerNS handler = new XPathExpressionHandlerNS();
			handler.addNamespaceMapping("sp", "https://github.com/nikolina97/xws-tim16-siit-2019");

			// Set date of revision
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String date = sdf.format(new Date());

			Element dateRevised = (Element) handler.evaluateXPath(revisionDOM, "//sp:dateRevised").item(0);
			boolean dateRevisedExist = true;
			if(dateRevised == null) {
				dateRevised = revisionDOM.createElement("sp:dateRevised");
				dateRevisedExist = false;
			}

			dateRevised.setTextContent(date);
			dateRevised.setAttribute("property", "pred:dateRevised");
			dateRevised.setAttribute("datatype", "xs:string");

			if(!dateRevisedExist)
				revisionDOM.getDocumentElement().insertBefore(dateRevised, handler.evaluateXPath(revisionDOM, "//sp:state").item(0));

			// Change version
			Element version = (Element) handler.evaluateXPath(revisionDOM, "//sp:version").item(0);
			version.setTextContent("" + (Integer.parseInt(version.getTextContent()) + 1));

			// Update the business process for this paper
			process.setState(TState.REVISED);
			process.setVersion(revisionModel.getVersion().getValue());
			process.setScientificPaperTitle(revisionModel.getTitle().getValue());

			DOMToXMLFile.toXML(revisionDOM, xmlFilePath);
			metadataExtractor.extractMetadata(new FileInputStream(new File(xmlFilePath)), new FileOutputStream(new File(rdfFilePath)));
			String metadata = RDFFileToString.toString(rdfFilePath);
			System.out.println(metadata);
			repository.updateMetadata(id, metadata, "http://ftn.uns.ac.rs/paper/" + id, "'" + (Integer.parseInt(revisionModel.getVersion().getValue().toString()) - 1) + "'", "https://schema.org/version" ,"/scientific_paper");
			repository.updateMetadata(id, metadata, "http://ftn.uns.ac.rs/paper/" + id, "'" + original.getTitle().getValue() + "'", "https://schema.org/headline" ,"/scientific_paper");

			repository.store(revisionDOM, id + ".xml");

			processService.save(process, id + ".xml");

		} catch (SAXException | IOException e) {
			e.printStackTrace();
			throw new BadRequestException("Your revision is incorrect, check it.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new InternalServerErrorException("Something bad happened on the server.");
		}
	}

	public String get(String paperID) {
		try {
			ScientificPaper original = repository.retrieveJAXB(paperID + ".xml");
			String email = SecurityContextHolder.getContext().getAuthentication().getName();

			if(original.getAuthors().getAuthor().stream().noneMatch(author -> author.getEmail().equals(email)))
				throw new BadRequestException("This paper is not yours.");

			return (String) repository.retrieve(paperID + ".xml");
		} catch (Exception e) {
			e.printStackTrace();
			throw new NotFoundException("Paper not found.");
		}
	}

	public List<PaperDTO> getPapersFromUser() throws Exception {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		String graphName = "scientific_paper";
		List<ScientificPaper> papers = repository.getQuerySP(graphName, "/person/" + email);
		List<PaperDTO> result = new ArrayList<>();

		papers.forEach(paper -> {
			try {
				BusinessProcess process = processService.get(paper.getId() + ".xml");
				result.add(new PaperDTO(paper, process.getState().value()));
			} catch (Exception e) {
				e.printStackTrace();
				throw new InternalServerErrorException("Something bad happened on the server.");
			}
		});

		return result;
	}
	
	public List<ScientificPaper> getAllPapersByUser(ScientificPaperMetadataSearchDTO metadataSearch) throws Exception {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
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
		List<ScientificPaper> papers = repository.getAllPapersByAuthor(graphName, "/person/" + email, advancedQuery, email);
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
	
	@PreAuthorize("hasRole('ROLE_REVIEWER')")
	public List<AssignmentDTO> getReviewersPapers() throws Exception {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		List<ScientificPaper> papers = repository.getReviewersPapers(email);
		List<AssignmentDTO> result = new ArrayList<>();

		papers.forEach(paper -> {
			try {
				BusinessProcess process = processService.get(paper.getId() + ".xml");
				result.add(new AssignmentDTO(paper, process.getReviewAssignments().getReviewAssignment().stream().filter(assignment -> assignment.getReviewer().getEmail().equals(email)).collect(Collectors.toList()).get(0).getStatus().value()));
			} catch (Exception e) {
				e.printStackTrace();
				throw new InternalServerErrorException("Something bad happened on the server.");
			}
		});
		return result;
	}
	
	public String getPaperHTML(String id) throws Exception {
		String xml = repository.getPaperById(id);

		String html = transformer.generateHTML(xml, "src/main/resources/xslt/scientific_paper.xsl");
		return html;
	}
	
	public String getPaperHTMLAnonymous(String id) throws Exception {
		String xml = repository.getPaperById(id);

		String html = transformer.generateHTML(xml, "src/main/resources/xslt/scientific_paper_anonymous.xsl");
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


	public Boolean revokePaper(String paperId) {
		String schemaPath = "src/main/resources/schemas/scientific_paper.xsd";
		
		BusinessProcess process;

		try {
			String sp = repository.findOneById(paperId, schemaPath);
			System.out.println(sp);
			Document paperDOM = domParser.buildDocument(sp);
			Node staff = paperDOM.getElementsByTagName("sp:state").item(0);
			staff.setTextContent("revoked");

			repository.store(paperDOM, paperId + ".xml");
			
			DOMToXMLFile.toXML(paperDOM, xmlFilePath);
			metadataExtractor.extractMetadata(new FileInputStream(new File(xmlFilePath)), new FileOutputStream(new File(rdfFilePath)));
			String metadata = RDFFileToString.toString(rdfFilePath);
			System.out.println(metadata);
//			repository.storeMetadata(metadata, "/scientific_paper");
			repository.updateMetadata(paperId, metadata, "http://ftn.uns.ac.rs/paper/" + paperId, "'in_procedure'" , "https://schema.org/state" ,"/scientific_paper");
			repository.store(paperDOM, paperId + ".xml");
			
			// Update the business process for this paper
			process = processService.get(paperId + ".xml");
			process.setState(TState.REVOKED);
			processService.save(process, paperId + ".xml");
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<ScientificPaper> getSubmittedPapers() throws Exception {
		String xpathExp = "/sp:businessProcess[sp:state = 'submitted']//sp:scientificPaperId//text()";
		System.out.println("xpath: "  + xpathExp);
		ResourceSet result = XMLDBManager.retrieveWithXPath("/db/processes/", xpathExp, "https://github.com/nikolina97/xws-tim16-siit-2019");
		List<String> ids = new ArrayList<>();
		ResourceIterator i = result.getIterator();
		XMLResource res = null;
		while (i.hasMoreResources()) {
			try {
				res = (XMLResource) i.nextResource();
				ids.add(res.getContent().toString());
			} finally {
				// don't forget to cleanup resources
				try {
					((EXistResource) res).freeResources();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}
		}
		List<ScientificPaper> papers = repository.getPapersByIds(ids);
		return papers;
	}
	public List<ScientificPaper> getOnRevisePapers() throws Exception {
		String xpathExp = "/sp:businessProcess[sp:state = 'onRevise' or sp:state = 'revised']//sp:scientificPaperId//text()";
		System.out.println("xpath: "  + xpathExp);
		ResourceSet result = XMLDBManager.retrieveWithXPath("/db/processes/", xpathExp, "https://github.com/nikolina97/xws-tim16-siit-2019");
		List<String> ids = new ArrayList<>();
		ResourceIterator i = result.getIterator();
		XMLResource res = null;
		while (i.hasMoreResources()) {
			try {
				res = (XMLResource) i.nextResource();
				ids.add(res.getContent().toString());
			} finally {
				// don't forget to cleanup resources
				try {
					((EXistResource) res).freeResources();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}
		}
		List<ScientificPaper> papers = repository.getPapersByIds(ids);
		return papers;
	}

	public List<ScientificPaper> getReferenced(String paperId) throws Exception {
		
		List<ScientificPaper> papers = repository.getReferenced(paperId);
		return papers;
	}

}
