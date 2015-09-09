package de.btu.openinfra.backend.db.pojos;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.daos.MetaDataDao;
import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;

@XmlRootElement
public class TopicInstancePojo extends OpenInfraMetaDataPojo {

	/**
	 * This variable defines the corresponding topic characteristic this topic
	 * instance belongs to.
	 */
	private TopicCharacteristicPojo topicCharacteristic;
	/**
	 * This variable defines the specific values defined by the settings of the
	 * corresponding topic characteristic.
	 */
	private List<AttributeValuePojo> values;

	/* Default constructor */
    public TopicInstancePojo() {}

    /* Constructor that will set the id, trid and meta data automatically */
    public TopicInstancePojo(
            OpenInfraModelObject modelObject, MetaDataDao mdDao) {
        super(modelObject, mdDao);
    }

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
