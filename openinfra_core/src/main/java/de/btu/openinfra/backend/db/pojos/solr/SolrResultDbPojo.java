package de.btu.openinfra.backend.db.pojos.solr;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

/**
 * This POJO is a container for a database result of a Solr query.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 */
public class SolrResultDbPojo extends OpenInfraPojo {

    private UUID topicInstanceId;
    private UUID topicCharacteristicId;
    private UUID projectId;
    private Map<String, List<String>> highlight;

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public UUID getTopicInstanceId() {
        return topicInstanceId;
    }

    public void setTopicInstanceId(UUID topicInstanceId) {
        this.topicInstanceId = topicInstanceId;
    }

    public UUID getTopicCharacteristicId() {
        return topicCharacteristicId;
    }

    public void setTopicCharacteristicId(UUID topicCharacteristicId) {
        this.topicCharacteristicId = topicCharacteristicId;
    }

    public Map<String, List<String>> getHighlight() {
        return highlight;
    }

    public void setHighlight(Map<String, List<String>> highlight) {
        this.highlight = highlight;
    }
}
