package de.btu.openinfra.backend.db.pojos.project;

import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TopicGeomzPojo {

    private UUID topicInstanceId;
    private List<AttributeValueGeomzPojo> attributeValuesGeomz;
    
    public UUID getTopicInstanceId() {
        return topicInstanceId;
    }
    
    public void setTopicInstanceId(UUID topicInstanceId) {
        this.topicInstanceId = topicInstanceId;
    }
    
    public List<AttributeValueGeomzPojo> getAttributeValuesGeomz() {
        return attributeValuesGeomz;
    }
    
    public void setAttributeValuesGeomz(
            List<AttributeValueGeomzPojo> attributeValues) {
        this.attributeValuesGeomz = attributeValues;
    }
}
