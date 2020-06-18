package com.xws.application.util;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import net.sf.saxon.TransformerFactoryImpl;

@Component
public class XSLFOTransformer {
	
	public static final String FOP_XCONF = "src/main/java/com/xws/application/util/fop.xconf";
		
	private FopFactory fopFactory;
	
	private TransformerFactory transformerFactory;
	
//	public static final String INPUT_FILE = "src/main/resources/examples/scientific_paper.xml";
////	
//	public static final String XSL_FILE = "src/main/resources/xsl-fo/scientific_paper_fo.xsl";
////	
//	public static final String OUTPUT_FILE = "src/main/resources/examples/spp.pdf";
	
	
	public XSLFOTransformer() throws SAXException, IOException {
		
		// Initialize FOP factory object
		fopFactory = FopFactory.newInstance(new File(FOP_XCONF));
		
		// Setup the XSLT transformer factory
		transformerFactory = new TransformerFactoryImpl();
	}

	public ByteArrayOutputStream generatePDF(String xmlString, String xslFilePath) throws Exception {

		System.out.println("[INFO] " + XSLFOTransformer.class.getSimpleName());
		
		// Point to the XSL-FO file
		File xslFile = new File(xslFilePath);

		// Create transformation source
		StreamSource transformSource = new StreamSource(xslFile);
		
		// Initialize the transformation subject
		StreamSource source = new StreamSource(new StringReader(xmlString));

		// Initialize user agent needed for the transformation
		FOUserAgent userAgent = fopFactory.newFOUserAgent();
		
		// Create the output stream to store the results
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		// Initialize the XSL-FO transformer object
		Transformer xslFoTransformer = transformerFactory.newTransformer(transformSource);
		
		// Construct FOP instance with desired output format
		Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, outStream);

		// Resulting SAX events 
		Result res = new SAXResult(fop.getDefaultHandler());

		// Start XSLT transformation and FOP processing
		xslFoTransformer.transform(source, res);
		
		

		// Generate PDF file
//		File pdfFile = new File(OUTPUT_FILE);
//		if (!pdfFile.getParentFile().exists()) {
//			System.out.println("[INFO] A new directory is created: " + pdfFile.getParentFile().getAbsolutePath() + ".");
//			pdfFile.getParentFile().mkdir();
//		}
//		
//		OutputStream out = new BufferedOutputStream(new FileOutputStream(pdfFile));
//		out.write(outStream.toByteArray());
//
//		System.out.println("[INFO] File \"" + pdfFile.getCanonicalPath() + "\" generated successfully.");
//		out.close();
//		
//		System.out.println("[INFO] End.");

		return outStream;
	}
	
	public String generateHTML(String xmlFilePath, String xsltFilePath) throws Exception
	{

		File tf = new File(xsltFilePath);
		StringWriter wr = new StringWriter();
		StringReader read = new StringReader(xmlFilePath);

		Transformer t = transformerFactory.newTransformer(new StreamSource(tf));

		Source s = new StreamSource(read);
		Result r = new StreamResult(wr);
		t.transform(s,r);
		return wr.toString();
	}
}