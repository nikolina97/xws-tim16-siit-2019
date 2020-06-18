package com.xws.application.util.rdf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RdfConnectionProperties {
	
	private String endpoint;
	private String dataset;
	
	private String queryEndpoint;
	private String updateEndpoint;
	private String dataEndpoint;
	
	public RdfConnectionProperties() {
		super();
	}
	
	public RdfConnectionProperties(Properties props) {
		dataset = props.getProperty("conn.dataset").trim();
		endpoint = props.getProperty("conn.endpoint").trim();
		
		queryEndpoint = String.join("/", endpoint, dataset, props.getProperty("conn.query").trim());
		updateEndpoint = String.join("/", endpoint, dataset, props.getProperty("conn.update").trim());
		dataEndpoint = String.join("/", endpoint, dataset, props.getProperty("conn.data").trim());
		
		System.out.println("[INFO] Parsing connection properties:");
		System.out.println("[INFO] Query endpoint: " + queryEndpoint);
		System.out.println("[INFO] Update endpoint: " + updateEndpoint);
		System.out.println("[INFO] Graph store endpoint: " + dataEndpoint);
	}
	
	/**
	 * Read the configuration properties for the example.
	 * 
	 * @return the configuration object
	 */
	public static RdfConnectionProperties loadProperties() throws IOException {
		
		
		
		
		String propsName = "fuseki.properties";

		InputStream propsStream = openStream(propsName);
		if (propsStream == null)
			throw new IOException("Could not read properties " + propsName);

		Properties props = new Properties();
		props.load(propsStream);

		return new RdfConnectionProperties(props);
	}
	
	public static InputStream openStream(String fileName) throws IOException {
		return RdfConnectionProperties.class.getClassLoader().getResourceAsStream(fileName);
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getDataset() {
		return dataset;
	}

	public void setDataset(String dataset) {
		this.dataset = dataset;
	}

	public String getQueryEndpoint() {
		return queryEndpoint;
	}

	public void setQueryEndpoint(String queryEndpoint) {
		this.queryEndpoint = queryEndpoint;
	}

	public String getUpdateEndpoint() {
		return updateEndpoint;
	}

	public void setUpdateEndpoint(String updateEndpoint) {
		this.updateEndpoint = updateEndpoint;
	}

	public String getDataEndpoint() {
		return dataEndpoint;
	}

	public void setDataEndpoint(String dataEndpoint) {
		this.dataEndpoint = dataEndpoint;
	}
	
}
