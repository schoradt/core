package de.btu.openinfra.backend.db.pojos.solr;

import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

/**
 * This POJO is a container for the search request from the client. It contains
 * either a simple query in Solr syntax (input from simple search) or a list of
 * complex query parts (input from extended search). The lists of projects and
 * topic characteristics will be used to filter the results.
 *
 * TODO The POJOs must extend OpenInfraPojo to be accessible for the primer
 *      class. The UUID and TRID that is provided by the OpenInfraPojo will
 *      never be used.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@XmlRootElement
public class SolrSearchPojo extends OpenInfraPojo {

    /*
     * Contains a raw Solr query in Solr syntax.
     */
    private String rawSolrQuery;

    /*
     * This is a list of complex query parts that will be parsed into Solr
     * syntax.
     */
    private List<SolrComplexQueryPartPojo> complexQueryPart;

    /*
     * This project id defines a positive filter for the results. This means
     * that all results must belong to this project.
     */
    private UUID positiveProjectFilter;

    /*
     * This topic characteristic id defines a positive filter for the results.
     * This means that all results must belong to this topic characteristic.
     */
    private UUID positiveTopicCharacteristicFilter;

    /*
     * This list of project ids defines a negative filter list for the results.
     * This means that all results do not belong to these projects.
     */
    private List<UUID> negativeProjectFilter;

    /*
     * This list of topic characteristic ids defines a negative filter list for
     * the results. This means that all results do not belong to these topic
     * characteristics.
     */
    private List<UUID> negativeTopicCharacteristicFilter;

    public String getRawSolrQuery() {
        return rawSolrQuery;
    }

    public void setRawSolrQuery(String rawSolrQuery) {
        this.rawSolrQuery = rawSolrQuery;
    }

    public List<SolrComplexQueryPartPojo> getComplexQueryPart() {
        return complexQueryPart;
    }

    public void setComplexQueryPart(
            List<SolrComplexQueryPartPojo> complexSolrQuery) {
        this.complexQueryPart = complexSolrQuery;
    }

    public UUID getPositiveProjectFilter() {
        return positiveProjectFilter;
    }

    public void setPositiveProjectFilter(UUID positiveProjectFilter) {
        this.positiveProjectFilter = positiveProjectFilter;
    }

    public UUID getPositiveTopicCharacteristicFilter() {
        return positiveTopicCharacteristicFilter;
    }

    public void setPositiveTopicCharacteristicFilter(
            UUID positiveTopicCharacteristicFilter) {
        this.positiveTopicCharacteristicFilter =
                positiveTopicCharacteristicFilter;
    }

    public List<UUID> getNegativeProjectFilter() {
        return negativeProjectFilter;
    }

    public void setNegativeProjectFilter(List<UUID> negativeProjectFilter) {
        this.negativeProjectFilter = negativeProjectFilter;
    }

    public List<UUID> getNegativeTopicCharacteristicFilter() {
        return negativeTopicCharacteristicFilter;
    }

    public void setNegativeTopicCharacteristicFilter(
            List<UUID> negativeTopicCharacteristicFilter) {
        this.negativeTopicCharacteristicFilter =
                negativeTopicCharacteristicFilter;
    }
}
