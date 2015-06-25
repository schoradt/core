package de.btu.openinfra.backend.db.pojos;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TopicInstancePojo extends OpenInfraPojo {
	
	private TopicCharacteristicPojo topicCharacteristic;
	private List<AttributeValuePojo> values;
	
	public TopicCharacteristicPojo getTopicCharacteristic() {
		return topicCharacteristic;
	}

	public void setTopicCharacteristic(TopicCharacteristicPojo topicCharacteristic) {
		this.topicCharacteristic = topicCharacteristic;
	}

	public List<AttributeValuePojo> getValues() {
		return values;
	}
	
	public void setValues(List<AttributeValuePojo> values) {
		this.values = values;
	}
	
}
