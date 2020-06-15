package com.xws.application.util.rdf;

import java.io.File;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

@Component
public class DOMToXMLFile {
	 public static void toXML(Document doc, String filePath) {
	        try {
	        	File xmlFile = new File(filePath);
	        	Transformer transformer = TransformerFactory.newInstance().newTransformer();
	        	transformer.transform(new DOMSource(doc), new StreamResult(xmlFile));
	        } catch (Exception ex) {
	            throw new RuntimeException("Error converting to String", ex);
	        }
	    }
}
