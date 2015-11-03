package de.btu.openinfra.plugins.solr.db.pojos;

import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This POJO is a container for the search request from the client. It contains
 * either a simple query in Solr syntax (input from simple search) or a list of
 * complex query parts (input from extended search).
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@XmlRootElement
public class SolrSearchPojo {

    private String rawSolrQuery;
    private List<SolrComplexQueryPartPojo> complexQueryPart;
    private List<UUID> projectId;
    private List<UUID> topicCharacteristicId;

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

    public List<UUID> getProjectId() {
        return projectId;
    }

    public void setProjectId(List<UUID> projectId) {
        this.projectId = projectId;
    }

    public List<UUID> getTopicCharacteristicId() {
        return topicCharacteristicId;
    }

    public void setTopicCharacteristicId(List<UUID> topicCharacteristicId) {
        this.topicCharacteristicId = topicCharacteristicId;
    }
}
