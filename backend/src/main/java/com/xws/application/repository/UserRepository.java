package com.xws.application.repository;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.exist.xmldb.EXistResource;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

import com.xws.application.model.TPerson;
import com.xws.application.model.Users;

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
}
