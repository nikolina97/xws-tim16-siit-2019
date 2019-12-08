package com.xws.application.parser;

import com.xws.application.model.CoverLetter;
import com.xws.application.model.Review;
import com.xws.application.model.ScientificPaper;
import com.xws.application.util.MyValidationEventHandler;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.OutputStream;

public class JAXB {

	public ScientificPaper unmarshalScientificPaper(String filePath) throws JAXBException, SAXException {
		System.out.println("[INFO] JAXB unmarshalling.\n");

		// Definiše se JAXB kontekst (putanja do paketa sa JAXB bean-ovima)
		JAXBContext context = JAXBContext.newInstance("com.xws.application.model");

		// Unmarshaller je objekat zadužen za konverziju iz XML-a u objektni model
		Unmarshaller unmarshaller = context.createUnmarshaller();

		// XML schema validacija
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File("../xml/scientific_paper.xsd"));

		// Podešavanje unmarshaller-a za XML schema validaciju
		unmarshaller.setSchema(schema);
		unmarshaller.setEventHandler(new MyValidationEventHandler());

		// Prvo se moraju generisati klase da bi se radilo sa unmarshalerom i marshalerom
		// Unmarshalling generiše objektni model na osnovu XML fajla
		return (ScientificPaper) unmarshaller.unmarshal(new File(filePath));
	}

	public Review unmarshalReview(String filePath) throws JAXBException, SAXException {
		System.out.println("[INFO] JAXB unmarshalling.\n");

		// Definiše se JAXB kontekst (putanja do paketa sa JAXB bean-ovima)
		JAXBContext context = JAXBContext.newInstance("com.xws.application.model");

		// Unmarshaller je objekat zadužen za konverziju iz XML-a u objektni model
		Unmarshaller unmarshaller = context.createUnmarshaller();

		// XML schema validacija
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File("../xml/review.xsd"));

		// Podešavanje unmarshaller-a za XML schema validaciju
		unmarshaller.setSchema(schema);
		unmarshaller.setEventHandler(new MyValidationEventHandler());

		// Prvo se moraju generisati klase da bi se radilo sa unmarshalerom i marshalerom
		// Unmarshalling generiše objektni model na osnovu XML fajla
		return (Review) unmarshaller.unmarshal(new File(filePath));
	}

	public CoverLetter unmarshalCoverLetter(String filePath) throws JAXBException, SAXException {
		System.out.println("[INFO] JAXB unmarshalling.\n");

		// Definiše se JAXB kontekst (putanja do paketa sa JAXB bean-ovima)
		JAXBContext context = JAXBContext.newInstance("com.xws.application.model");

		// Unmarshaller je objekat zadužen za konverziju iz XML-a u objektni model
		Unmarshaller unmarshaller = context.createUnmarshaller();

		// XML schema validacija
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File("../xml/cover_letter.xsd"));

		// Podešavanje unmarshaller-a za XML schema validaciju
		unmarshaller.setSchema(schema);
		unmarshaller.setEventHandler(new MyValidationEventHandler());

		// Prvo se moraju generisati klase da bi se radilo sa unmarshalerom i marshalerom
		// Unmarshalling generiše objektni model na osnovu XML fajla
		return (CoverLetter) unmarshaller.unmarshal(new File(filePath));
	}

	/**
	* @param article objekat koji se marsaluje
	* @param out output u koji se marsaluje (moze i File/FileOutputStream)
	*/
	public void marshal(Object article, OutputStream out) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance("com.xws.application.model");

		// Marshaller je objekat zadužen za konverziju iz objektnog u XML model
		Marshaller marshaller = context.createMarshaller();

		// Podešavanje marshaller-a
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		// Umesto System.out-a, može se koristiti FileOutputStream
		marshaller.marshal(article, out);
	}

}
