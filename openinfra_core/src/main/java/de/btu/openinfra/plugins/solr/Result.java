package de.btu.openinfra.plugins.solr;

import java.util.List;

import de.btu.openinfra.backend.db.pojos.TopicCharacteristicPojo;

/**
 * This class represents a result object that will be used by {@see Searcher}.
 * It holds all necessary attributes for representing a result. 
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 */
public class Result {

    private String projectId;
    private String topicInstanceId;
    private List<String> result;
    private TopicCharacteristicPojo topicCharacteristic;
    
    // @TODO find a way to set it dynamic from the Solr config 
    public final String PROJECT_ID_IDENTIFIER = "projectId";
    public final String TOPIC_INSTANCE_ID_IDENTIFIER = "id";
    public final String RESULT_IDENTIFIER = "attributeValue";
    public final String TOPIC_CHARACTERISTIC_ID_IDENTIFIER = 
            "topicCharacteristicId";
    
    public String getProjectId() {
        return projectId;
    }
    
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    
    public String getTopicInstanceId() {
        return topicInstanceId;
    }
    
    public void setTopicInstanceId(String topicInstanceId) {
        this.topicInstanceId = topicInstanceId;
    }
    
    public List<String> getResult() {
        return result;
    }
    
    public void setResult(List<String> result) {
        this.result = result;
    }
    
    public TopicCharacteristicPojo getTopicCharacteristic() {
        return topicCharacteristic;
    }

    public void setTopicCharacteristic(TopicCharacteristicPojo topicCharacteristic) {
        this.topicCharacteristic = topicCharacteristic;
    }
}
