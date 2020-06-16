package com.xws.application.repository;

import com.xws.application.parser.JAXB;
import com.xws.application.util.AuthenticationUtilities;
import org.exist.xmldb.EXistResource;
import org.w3c.dom.Document;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;

public class XMLDBManager {

	public static void store(Object model, String collectionId, String documentId) throws Exception {
		AuthenticationUtilities.ConnectionProperties conn = AuthenticationUtilities.loadProperties();

		// initialize database driver
		Class<?> cl = Class.forName(conn.driver);

		// encapsulation of the database driver functionality
		Database database = (Database) cl.newInstance();
		database.setProperty("create-database", "true");

		// entry point for the API which enables you to get the Collection reference
		DatabaseManager.registerDatabase(database);

		// a collection of Resources stored within an XML database
		Collection col = null;
		XMLResource res = null;

		try {
			col = getOrCreateCollection(collectionId, conn);

			/*
			 *  create new XMLResource with a given id
			 *  an id is assigned to the new resource if left empty (null)
			 */
			System.out.println("[INFO] Inserting the document: " + documentId);
			res = (XMLResource) col.createResource(documentId, XMLResource.RESOURCE_TYPE);

			if(model instanceof Document) {
				DOMSource domSource = new DOMSource((Document) model);
				StringWriter writer = new StringWriter();
				StreamResult result = new StreamResult(writer);
				TransformerFactory tf = TransformerFactory.newInstance();
				Transformer transformer = tf.newTransformer();
				transformer.transform(domSource, result);

				res.setContent(writer.toString());
			} else {
				OutputStream out = new ByteArrayOutputStream();
				JAXB.marshal(model, out);

				res.setContent(out);
			}
			System.out.println("[INFO] Storing the document: " + res.getId());

			col.storeResource(res);
			System.out.println("[INFO] Done.");

		} finally {

			//don't forget to cleanup
			if(res != null) {
				try {
					((EXistResource)res).freeResources();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}

			if(col != null) {
				try {
					col.close();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}
		}
	}

	private static Collection getOrCreateCollection(String collectionUri, AuthenticationUtilities.ConnectionProperties conn) throws XMLDBException {
		return getOrCreateCollection(collectionUri, 0, conn);
	}

	private static Collection getOrCreateCollection(String collectionUri, int pathSegmentOffset, AuthenticationUtilities.ConnectionProperties conn) throws XMLDBException {
		Collection col = DatabaseManager.getCollection(conn.uri + collectionUri, conn.user, conn.password);

		// create the collection if it does not exist
		if(col == null) {

			if(collectionUri.startsWith("/")) {
				collectionUri = collectionUri.substring(1);
			}

			String pathSegments[] = collectionUri.split("/");

			if(pathSegments.length > 0) {
				StringBuilder path = new StringBuilder();

				for(int i = 0; i <= pathSegmentOffset; i++) {
					path.append("/" + pathSegments[i]);
				}

				Collection startCol = DatabaseManager.getCollection(conn.uri + path, conn.user, conn.password);

				if (startCol == null) {

					// child collection does not exist

					String parentPath = path.substring(0, path.lastIndexOf("/"));
					Collection parentCol = DatabaseManager.getCollection(conn.uri + parentPath, conn.user, conn.password);

					CollectionManagementService mgt = (CollectionManagementService) parentCol.getService("CollectionManagementService", "1.0");

					System.out.println("[INFO] Creating the collection: " + pathSegments[pathSegmentOffset]);
					col = mgt.createCollection(pathSegments[pathSegmentOffset]);

					col.close();
					parentCol.close();

				} else {
					startCol.close();
				}
			}
			return getOrCreateCollection(collectionUri, ++pathSegmentOffset, conn);
		} else {
			return col;
		}
	}

	public static String retrieve(String collectionId, String documentId) throws Exception {
		AuthenticationUtilities.ConnectionProperties conn = AuthenticationUtilities.loadProperties();

		// initialize collection and document identifiers
		System.out.println("\t- collection ID: " + collectionId);
		System.out.println("\t- document ID: " + documentId + "\n");

		// initialize database driver
		System.out.println("[INFO] Loading driver class: " + conn.driver);
		Class<?> cl = Class.forName(conn.driver);

		Database database = (Database) cl.newInstance();
		database.setProperty("create-database", "true");

		DatabaseManager.registerDatabase(database);

		Collection col = null;
		XMLResource res = null;

		String model;

		try {
			// get the collection
			System.out.println("[INFO] Retrieving the collection: " + collectionId);
			// col = DatabaseManager.getCollection(conn.uri + collectionId);
			col = getOrCreateCollection(collectionId, conn);
			col.setProperty(OutputKeys.INDENT, "yes");

			System.out.println("[INFO] Retrieving the document: " + documentId);
			res = (XMLResource)col.getResource(documentId);

			if(res == null) {
				System.out.println("[WARNING] Document '" + documentId + "' can not be found!");
				return null;
			}

			model = res.toString();
		} finally {
			//don't forget to clean up!

			if(res != null) {
				try {
					((EXistResource)res).freeResources();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}

			if(col != null) {
				try {
					col.close();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}
		}

		return model;
	}

	public static int getDocumentCount(String collectionId) throws Exception {
		AuthenticationUtilities.ConnectionProperties conn = AuthenticationUtilities.loadProperties();

		// initialize collection and document identifiers
		System.out.println("\t- collection ID: " + collectionId);

		// initialize database driver
		System.out.println("[INFO] Loading driver class: " + conn.driver);
		Class<?> cl = Class.forName(conn.driver);

		Database database = (Database) cl.newInstance();
		database.setProperty("create-database", "true");

		DatabaseManager.registerDatabase(database);

		Collection col = null;
		int count = 0;

		try {
			// get the collection
			System.out.println("[INFO] Retrieving the collection: " + collectionId);
			// col = DatabaseManager.getCollection(conn.uri + collectionId);
			col = getOrCreateCollection(collectionId, conn);
			col.setProperty(OutputKeys.INDENT, "yes");

			count = col.getResourceCount();
		} finally {
			//don't forget to clean up!
			if(col != null) {
				try {
					col.close();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}
		}

		return count;
	}

}
