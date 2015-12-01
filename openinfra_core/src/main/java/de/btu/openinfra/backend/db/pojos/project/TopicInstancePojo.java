package de.btu.openinfra.backend.db.pojos.project;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;
import de.btu.openinfra.backend.db.pojos.TopicCharacteristicPojo;

@XmlRootElement
public class TopicInstancePojo extends OpenInfraPojo {

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
    public TopicInstancePojo() {
    }

    /* Constructor that will set the id and trid automatically */
    public TopicInstancePojo(OpenInfraModelObject modelObject) {
        super(modelObject);
    }

    public TopicCharacteristicPojo getTopicCharacteristic() {
        return topicCharacteristic;
    }

    public void setTopicCharacteristic(
    		TopicCharacteristicPojo topicCharacteristic) {
        this.topicCharacteristic = topicCharacteristic;
    }

    public List<AttributeValuePojo> getValues() {
        return values;
    }

    public void setValues(List<AttributeValuePojo> values) {
        this.values = values;
    }

}
