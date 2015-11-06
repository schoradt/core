package de.btu.openinfra.plugins.solr;

/**
 * This enumeration defines the used property keys. The values are defined in
 * another enumeration. {@see SolrProperties}
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public enum SolrPropertyKeys {

	/*
	 * This variable defines the URL to the Solr server.
	 */
	SOLR_URL("de.btu.openinfra.plugins.solr.url"),

	/*
	 * This variable defines the name of the Solr core.
	 */
	SOLR_CORE("de.btu.openinfra.plugins.solr.core"),

	/*
	 * This variable defines the default fuzzy parameter.
	 */
	SOLR_DEFUALT_FUZZY("de.btu.openinfra.plugins.solr.defaultFuzzy"),

	/*
     * This variable defines the default maximum number of results per request
     */
    SOLR_DEFUALT_RESULTS_PER_PAGE(
            "de.btu.openinfra.plugins.solr.defaultResultsPerPage");

	private String key;
	private SolrPropertyKeys(String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}

}
