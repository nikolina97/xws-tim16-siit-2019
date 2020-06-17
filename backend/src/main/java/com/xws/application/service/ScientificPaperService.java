package com.xws.application.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.xws.application.dto.PaperLetterDTO;
import com.xws.application.dto.ScientificPaperMetadataSearchDTO;
import com.xws.application.model.BusinessProcess;
import com.xws.application.model.ScientificPaper;
import com.xws.application.model.TState;
import com.xws.application.parser.DOMParser;
import com.xws.application.repository.ScientificPaperRepository;
import com.xws.application.util.XPathExpressionHandlerNS;
import com.xws.application.util.rdf.DOMToXMLFile;
import com.xws.application.util.rdf.MetadataExtractor;
import com.xws.application.util.rdf.RDFFileToString;

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

	private Document generateIDs(Document document, String paperId, XPathExpressionHandlerNS handler) throws Exception {
		document.getDocumentElement().setAttribute("about", "http://ftn.uns.ac.rs/paper/" + paperId);
		document.getDocumentElement().setAttribute("sp:id", paperId);

		String abstractPath = "//sp:abstract";
		String abstractId = paperId + "/abstract";

		Element abstractElement = (Element) handler.evaluateXPath(document, abstractPath).item(0);
		abstractElement.setAttribute("sp:id", abstractId);

		document = generateIDsForParagraph(document, abstractPath, abstractId, handler);

		String chaptersPath = "//sp:chapter";
		NodeList chapters = handler.evaluateXPath(document, chaptersPath);

		for (int i = 0; i < chapters.getLength(); i++) {
			String chapterId = paperId + "/chapter" + (i + 1);

			Element chapter = (Element) chapters.item(i);
			chapter.setAttribute("sp:id", chapterId);

			document = generateIDsForParagraph(document, chaptersPath, chapterId, handler);
			document = generateIDsForSubchapters(document, chaptersPath, chapterId, handler);
		}

		String referencesPath = "//sp:reference";
		NodeList references = handler.evaluateXPath(document, referencesPath);

		for (int i = 0; i < references.getLength(); i++) {
			String chapterId = paperId + "/reference" + (i + 1);

			Element reference = (Element) references.item(i);
			reference.setAttribute("sp:id", chapterId);
		}

		return document;
	}

	private Document generateIDsForParagraph(Document document, String parentPath, String parentID, XPathExpressionHandlerNS handler) {
		String paragraphsPath = parentPath + "/sp:paragraph";
		NodeList paragraphs = handler.evaluateXPath(document, paragraphsPath);
		for (int i = 0; i < paragraphs.getLength(); i++) {
			String paragraphId = parentID + "/paragraph" + (i + 1);

			Element paragraph = (Element) paragraphs.item(i);
			paragraph.setAttribute("sp:id", paragraphId);

			String tablesPath = paragraphsPath + "/sp:table";
			NodeList tables = handler.evaluateXPath(document, tablesPath);

			for (int j = 0; j < tables.getLength(); j++) {
				String tableId = paragraphId + "/table" + (j + 1);

				Element table = (Element) tables.item(j);
				table.setAttribute("sp:id", tableId);
			}

			String figuresPath = paragraphsPath + "/sp:figure";
			NodeList figures = handler.evaluateXPath(document, figuresPath);

			for (int j = 0; j < figures.getLength(); j++) {
				String figureId = paragraphId + "/figure" + (j + 1);

				Element figure = (Element) figures.item(j);
				figure.setAttribute("sp:id", figureId);
			}

			String formulasPath = paragraphsPath + "/sp:formula";
			NodeList formulas = handler.evaluateXPath(document, formulasPath);

			for (int j = 0; j < formulas.getLength(); j++) {
				String formulaId = paragraphId + "/formula" + (j + 1);

				Element formula = (Element) formulas.item(j);
				formula.setAttribute("sp:id", formulaId);
			}

			String listsPath = paragraphsPath + "/sp:list";
			NodeList lists = handler.evaluateXPath(document, listsPath);

			for (int j = 0; j < lists.getLength(); j++) {
				String listId = paragraphId + "/list" + (j + 1);

				Element list = (Element) lists.item(j);
				list.setAttribute("sp:id", listId);
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
			subchapter.setAttribute("sp:id", subchapterId);

			document = generateIDsForParagraph(document, subchaptersPath, subchapterId, handler);
			document = generateIDsForSubchapters(document, subchaptersPath, subchapterId, handler);
		}

		return document;
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
		String email = "/person/john.doe@uns.ac.rs"; //hard coded
		String graphName = "scientific_paper/";
		List<ScientificPaper> papers = repository.getQuerySP(graphName, email);
		return papers;
	}
	
	public List<ScientificPaper> getAllPapersByUser(ScientificPaperMetadataSearchDTO metadataSearch) throws Exception {
		String email = "/person/john.doe@uns.ac.rs"; //hard coded
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
			advancedQuery += "\n?subject <https://schema.org/author> ?author\n\t?author <https://schema.org/firstName> \"" + metadataSearch.getAuthorFirstName() + "\" .";
		}
		if (metadataSearch.getAuthorLastName() != null) {
			advancedQuery += "\n?subject <https://schema.org/author> ?author .\n\t?author <https://schema.org/lastName> \"" + metadataSearch.getAuthorLastName() + "\" .";
		}
		if (metadataSearch.getTitle() != null) {
			advancedQuery += "\n?subject <https://schema.org/title> \"" + metadataSearch.getTitle() + "\" .";
		}
		if (metadataSearch.getVersion() != null) {
			advancedQuery += "\n?subject <https://schema.org/version> \"" + metadataSearch.getVersion() + "\" .";
		}
		if (metadataSearch.getKeywords() != null) {
			advancedQuery += "\n?subject <https://schema.org/keyword> \"" + metadataSearch.getKeywords() + "\" .";
		}
		
		List<ScientificPaper> papers = repository.getAllPapersByAuthor(graphName, email, advancedQuery);
		return papers;
	}

}
