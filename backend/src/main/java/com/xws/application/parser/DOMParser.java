package com.xws.application.parser;

import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

import static org.apache.xerces.jaxp.JAXPConstants.JAXP_SCHEMA_LANGUAGE;
import static org.apache.xerces.jaxp.JAXPConstants.W3C_XML_SCHEMA;

public class DOMParser implements ErrorHandler {

	private static DocumentBuilderFactory factory;

	/*
	 * Factory initialization static-block
	 */
	static {
		factory = DocumentBuilderFactory.newInstance();

		/* Uključuje validaciju. */
		factory.setValidating(true);

		factory.setNamespaceAware(true);
		factory.setIgnoringComments(true);
		factory.setIgnoringElementContentWhitespace(true);

		/* Validacija u odnosu na XML šemu. */
		factory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
	}

	/**
	 * Generates document object model for a given XML file.
	 * XML schema has to be in same directory as given XML file for successful validation.
	 *
	 * @param filePath XML document file path
	 */
	// TODO Promeniti na File umesto String filePath ?
	public Document buildDocument(String filePath) {

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();

			/* Postavlja error handler. */
			builder.setErrorHandler(this);

			Document document = builder.parse(new File(filePath));

			/* Detektuju eventualne greske */
			if (document != null)
				System.out.println("[INFO] File parsed with no errors.");
			else
				System.out.println("[WARN] Document is null.");

			return document;
		} catch (SAXParseException e) {

			System.out.println("[ERROR] Parsing error, line: " + e.getLineNumber() + ", uri: " + e.getSystemId());
			System.out.println("[ERROR] " + e.getMessage() );
			System.out.print("[ERROR] Embedded exception: ");

			Exception embeddedException = e;
			if (e.getException() != null)
				embeddedException = e.getException();

			// Print stack trace...
			embeddedException.printStackTrace();

			return null;
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	@Override
	public void warning(SAXParseException exception) {
		System.out.println("[WARN] Warning, line: " + exception.getLineNumber() + ", uri: " + exception.getSystemId());
		System.out.println("[WARN] " + exception.getMessage());
	}

	@Override
	public void error(SAXParseException exception) throws SAXException {
		throw exception;
	}

	@Override
	public void fatalError(SAXParseException exception) throws SAXException {
		throw exception;
	}
}
