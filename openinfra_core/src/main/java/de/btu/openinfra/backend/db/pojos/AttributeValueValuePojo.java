package de.btu.openinfra.backend.db.pojos;

import java.util.UUID;


public class AttributeValueValuePojo extends OpenInfraPojo {

	private UUID topicInstanceId;
	private ValuePojo value;
	private UUID attributeTypeToAttributeTypeGroupId;

	public UUID getTopicInstanceId() {
		return topicInstanceId;
	}

	public void setTopicInstanceId(UUID topicInstanceId) {
		this.topicInstanceId = topicInstanceId;
	}
	
	public ValuePojo getValue() {
		return value;
	}

	public void setValue(ValuePojo value) {
		this.value = value;
	}
	
    public UUID getAttributeTypeToAttributeTypeGroupId() {
        return attributeTypeToAttributeTypeGroupId;
    }

    public void setAttributeTypeToAttributeTypeGroupId(
            UUID attributeTypeToAttributeTypeGroupId) {
        this.attributeTypeToAttributeTypeGroupId = 
                attributeTypeToAttributeTypeGroupId;
    }
	
}
