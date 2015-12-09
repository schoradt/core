package de.btu.openinfra.backend.db.pojos.project;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TopicPojo {
	
	private TopicInstancePojo topicInstance;
	private List<AttributeTypeGroupToValues> attributeTypeGroupsToValues;

	public TopicInstancePojo getTopicInstance() {
		return topicInstance;
	}
	
	public void setTopicInstance(TopicInstancePojo topicInstance) {
		this.topicInstance = topicInstance;
	}
	
	public List<AttributeTypeGroupToValues> getAttributeTypeGroupsToValues() {
		return attributeTypeGroupsToValues;
	}
	
	public void setAttributeTypeGroupsToValues(
			List<AttributeTypeGroupToValues> attributeTypeGroupsToValues) {
		this.attributeTypeGroupsToValues = attributeTypeGroupsToValues;
	}
	
}
