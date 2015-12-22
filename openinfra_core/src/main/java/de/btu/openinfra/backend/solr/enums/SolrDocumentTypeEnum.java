package de.btu.openinfra.backend.solr.enums;

/**
 * This enum maps the possible Solr document types.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 */
public enum SolrDocumentTypeEnum {

    DATABASE("database"),
    FILE("file");

    private String value;

    private SolrDocumentTypeEnum(String value) {
        this.value = value;
    }

    public String getString() {
        return this.value;
    }
}
