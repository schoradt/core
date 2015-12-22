package de.btu.openinfra.backend.solr.enums;

import de.btu.openinfra.backend.solr.SolrCharacterConverter;

/**
 * This enum contains the standard Solr index fields.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 */
public enum SolrIndexEnum {

    /*
     * This variable represents the Solr field that holds the id of the
     * document.
     */
    ID("id"),

    /*
     * This variable represents the Solr field that holds the topic
     * characteristic id.
     */
    PROJECT_ID("_projectid_"),

    /*
     * This variable represents the Solr field that holds the project id.
     */
    TOPIC_CHARACTERISTIC_ID("_topiccharacteristicid_"),

    /*
     * This variable represents the Solr field that defines the default search
     * field. In this field all possible values of all other fields will be
     * added.
     */
    DEFAULT_SEARCH_FIELD("_defaultsearch_"),

    /*
     * This variable represents the Solr field that holds all attribute values
     * that have no translated attribute type names.
     */
    NO_TRANSLATION_FIELD("_notranslation_"),

    /*
     * This variable represents the Solr field for lookups. UUIDs will not be
     * added to this field.
     */
    LOOKUP_FIELD("_lookupfield_"),

    /*
     * This variable represents the Solr field for the file name as hash.
     */
    FILE_HASH("_filehash_"),

    /*
     * This variable represents the type of the indexed document e.g. database
     * or file.
     */
    DOC_TYPE("_documenttype_");

    private String value;

    private SolrIndexEnum(String value) {
        this.value = value;
    }

    public String getString() {
        return SolrCharacterConverter.convert(this.value);
    }
}
