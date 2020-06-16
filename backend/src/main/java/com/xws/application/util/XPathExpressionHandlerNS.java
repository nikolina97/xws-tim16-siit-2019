package com.xws.application.util;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.xpath.*;
import java.util.HashMap;
import java.util.Map;

public class XPathExpressionHandlerNS {

	private static final XPathFactory xPathFactory;

	// Prefiks/namespace URI mapiranja
	private static final Map<String, String> namespaceMappings;

	static {
		/* Inicijalizacija XPath fabrike */
		xPathFactory = XPathFactory.newInstance();

		/* Inicijalizacija namespace mapiranja */
		namespaceMappings = new HashMap<>();
	}

	public void addNamespaceMapping(String prefix, String namespace) {
		namespaceMappings.put(prefix, namespace);
	}

	/**
	 * Evaluates the provided XPath expression.
	 *
	 * @param expression an XPath expression to be evaluated
	 */
	public NodeList evaluateXPath(Document document, String expression) {

		XPath xPath = xPathFactory.newXPath();

		/* Konfiguracija Namespace Aware XPath izraza */
		xPath.setNamespaceContext(new NamespaceContext(namespaceMappings));

		XPathExpression xPathExpression;

		try {

			xPathExpression = xPath.compile(expression);

			System.out.println("[INFO] Evaluating the expression (\"" + expression + "\")");

			// String singleResult = xPathExpression.evaluate(document);
			// Node singleNode = (Node) xPathExpression.evaluate(document, XPathConstants.NODE);
			// NodeList nodeList = (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);

			return (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);

		} catch (XPathExpressionException e) {
			System.out.println("[ERROR] Error evaluationg \"" + expression + "\" expression, line: " + e.getMessage());
			return null;
		}

	}

}
