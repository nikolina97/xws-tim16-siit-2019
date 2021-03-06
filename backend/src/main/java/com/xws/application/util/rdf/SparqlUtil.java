package com.xws.application.util.rdf;

public class SparqlUtil {

	/* The following operation causes all of the triples in all of the graphs to be deleted */
	private static final String DROP_ALL = "DROP ALL";
	
	/* Removes all of the triples from a named graphed */
	private static final String DROP_GRAPH_TEMPLATE = "DROP GRAPH <%s>";

	/**
	 * A template for creating SPARUL (SPARQL Update) query can be found here:
	 * https://www.w3.org/TR/sparql11-update/
	 */
	/* Insert RDF data into the default graph */
	private static final String UPDATE_TEMPLATE = "INSERT DATA { %s }";
	
	/* Insert RDF data to an arbitrary named graph */
	private static final String UPDATE_TEMPLATE_NAMED_GRAPH = "INSERT DATA { GRAPH <%1$s> { %2$s } }";
	

	/* Simple SPARQL query on a named graph */
	private static final String SELECT_NAMED_GRAPH_TEMPLATE = "SELECT * FROM <%1$s> WHERE { %2$s }";
	
	private static final String SELECT_DISTINCT_UNION_META =  "SELECT DISTINCT ?subject FROM <%1$s> WHERE { {%2$s} UNION {%3$s} . %4$s}";
	
	private static final String DELETE_TRIPLES = "DELETE DATA { GRAPH <%1$s>  { <%2$s> <%3$s> %4$s . }};";
	
	private static final String SELECT_OBJECT = "SELECT ?object FROM <%1$s> WHERE { <%2$s> <%3$s> ?object . }";
	
	/* Plain text RDF serialization format */
	public static final String NTRIPLES = "N-TRIPLES";

	/* An XML serialization format for RDF data */
	public static final String RDF_XML = "RDF/XML";
	
	public static String dropAll() {
		return DROP_ALL;
	}
	
	public static String dropGraph(String graphURI) {
		return String.format(DROP_GRAPH_TEMPLATE, graphURI);
	}
	
	/* Inserts data to the default graph */
	public static String insertData(String ntriples) {
		return String.format(UPDATE_TEMPLATE, ntriples);
	}
	
	public static String insertData(String graphURI, String ntriples) {
		return String.format(UPDATE_TEMPLATE_NAMED_GRAPH, graphURI, ntriples);
	}
	
	public static String selectData(String graphURI, String sparqlCondition) {
		return String.format(SELECT_NAMED_GRAPH_TEMPLATE, graphURI, sparqlCondition);
	}
	
	public static String selectDistinctUnionMeta(String graphUri,String sparqlCondition1,  String sparqlCondition2, String sparqlCondition3) {
		return String.format(SELECT_DISTINCT_UNION_META, graphUri, sparqlCondition1, sparqlCondition2, sparqlCondition3);
	}
	
	public static String deleteTriples(String graphUri, String subject, String predicate, String object) {
		return String.format(DELETE_TRIPLES, graphUri, subject, predicate, object);
	}
	
	public static String selectObject(String graphUri, String subject, String predicate) {
		return String.format(SELECT_OBJECT, graphUri, subject, predicate);
	}
}
