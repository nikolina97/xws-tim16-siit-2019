package com.xws.application.util;

import java.util.Iterator;
import java.util.Map;

public class NamespaceContext implements javax.xml.namespace.NamespaceContext {

	private Map<String, String> prefixes;

	public NamespaceContext() {
	}

	public NamespaceContext(Map<String, String> prefixes) {
		this.prefixes = prefixes;
	}

	@Override
	public String getNamespaceURI(String prefix) {
		String uri = null;

		if (prefixes.containsKey(prefix))
			uri = prefixes.get(prefix);

		return uri;
	}

	@Override
	public String getPrefix(String namespaceURI) {
		return null;
	}

	@Override
	public Iterator getPrefixes(String namespaceURI) {
		return null;
	}

	public void setPrefixes(Map<String, String> prefixes) {
		this.prefixes = prefixes;
	}

}
