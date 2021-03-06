package com.xws.application.parser;

import java.io.File;
import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import com.xws.application.model.*;

import com.xws.application.util.MyValidationEventHandler;

public class JAXB {

	public static Object unmarshal(String xml, DocType type) throws JAXBException, SAXException {
		System.out.println("[INFO] JAXB unmarshalling.\n");

		// Definiše se JAXB kontekst (putanja do paketa sa JAXB bean-ovima)
		JAXBContext context = JAXBContext.newInstance("com.xws.application.model");

		// Unmarshaller je objekat zadužen za konverziju iz XML-a u objektni model
		Unmarshaller unmarshaller = context.createUnmarshaller();

		String schemaFile = null;
		if(type == DocType.SCIENTIFIC_PAPER)
			schemaFile = "scientific_paper.xsd";
		else if(type == DocType.REVIEW)
			schemaFile = "review.xsd";
		else if(type == DocType.COVER_LETTER)
			schemaFile = "cover_letter.xsd";
		else if (type == DocType.BUSINESS_PROCESS)
			schemaFile = "business_process.xsd";
		else if (type == DocType.NOTIFICATION)
			schemaFile = "notification.xsd";
		else if (type == DocType.USER)
			schemaFile = "user.xsd";

		// XML schema validacija
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File("src/main/resources/schemas/" + schemaFile));

		// Podešavanje unmarshaller-a za XML schema validaciju
		unmarshaller.setSchema(schema);
		unmarshaller.setEventHandler(new MyValidationEventHandler());

		// Prvo se moraju generisati klase da bi se radilo sa unmarshalerom i marshalerom
		// Unmarshalling generiše objektni model na osnovu XML fajla

		return unmarshaller.unmarshal(new StringReader(xml));
	}

	/**
	* @param article objekat koji se marsaluje
	* @param out output u koji se marsaluje (moze i File/FileOutputStream)
	*/
	public static void marshal(Object article, OutputStream out) throws JAXBException, SAXException {
		JAXBContext context = JAXBContext.newInstance("com.xws.application.model");

		// Marshaller je objekat zadužen za konverziju iz objektnog u XML model
		Marshaller marshaller = context.createMarshaller();

		// Podešavanje marshaller-a
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		String schemaFile = null;
		if(article instanceof ScientificPaper)
			schemaFile = "scientific_paper.xsd";
		else if(article instanceof Review)
			schemaFile = "review.xsd";
		else if(article instanceof CoverLetter)
			schemaFile = "cover_letter.xsd";
		else if(article instanceof BusinessProcess)
			schemaFile = "business_process.xsd";
		else if(article instanceof Users)
			schemaFile = "user.xsd";

		// XML schema validacija
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File("src/main/resources/schemas/" + schemaFile));

		// Podešavanje unmarshaller-a za XML schema validaciju
		marshaller.setSchema(schema);
		marshaller.setEventHandler(new MyValidationEventHandler());

		// Umesto System.out-a, može se koristiti FileOutputStream
		marshaller.marshal(article, out);
	}

}
