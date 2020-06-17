package com.xws.application.repository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.xws.application.model.Users;
import com.xws.application.util.XUpdateTemplate;

import org.exist.xmldb.EXistResource;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

@Repository
public class UserRepository {
	public static String usersCollectionId = "/db/library/users";
	public static final String TARGET_NAMESPACE = "https://github.com/nikolina97/xws-tim16-siit-2019";

	public void store(Object model, String documentId) throws Exception {
		XMLDBManager.store(model, "/db/library/users", documentId);
	}

	public Object retrieve(String documentId) throws Exception {
		return XMLDBManager.retrieve("/db/library/users", documentId);
	}
	
	public void save(Users.User user) throws JAXBException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, IOException {
		String userXml = marshallUser(user);
//		System.out.println(userXml);
		XMLDBManager.update("users.xml", "/db/library/users", "/users", userXml, XUpdateTemplate.APPEND);
	}
	
	public Users.User findByEmail(String email) throws Exception {
        Users.User user = null;
        System.out.println(email);
        String xpath = String.format("collection(\"/db/library/users\")/users/user[userInfo/email=\"%s\"]", email);
        ResourceSet result = XMLDBManager.retrieveWithXPath(usersCollectionId, xpath, TARGET_NAMESPACE);
        if (result == null) {
			return user;
		}
        ResourceIterator i = result.getIterator();
		XMLResource res = null;
		while (i.hasMoreResources()) {
			System.out.println("HEJ!");
			try {
				res = (XMLResource) i.nextResource();
				user = unmarshallUser(res);
				return user;
			} finally {
				try {
					((EXistResource) res).freeResources();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}
		}
		return null;
    }
	
    public Users.User unmarshallUser(XMLResource res) throws Exception {
    	System.out.println(res.toString());
        JAXBContext context = JAXBContext.newInstance("com.xws.application.model");     
    	Unmarshaller unmarshaller = context.createUnmarshaller();
    	Users.User person = (Users.User) unmarshaller.unmarshal(res.getContentAsDOM());
    	return person;
    }
    
    public String marshallUser(Users.User user) throws JAXBException {
    	JAXBContext context = JAXBContext.newInstance("com.xws.application.model");
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        OutputStream os = new ByteArrayOutputStream();
        marshaller.marshal(user, os);
        String xml = os.toString();
        xml = xml.substring(xml.indexOf("<user"));
		xml = xml.replace(" xmlns=\"https://github.com/nikolina97/xws-tim16-siit-2019\"","");
        System.out.println(xml);
        return xml;
    }
}
