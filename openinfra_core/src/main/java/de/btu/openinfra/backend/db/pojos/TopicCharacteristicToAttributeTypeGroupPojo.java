package de.btu.openinfra.backend.db.pojos;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;

@XmlRootElement
public class TopicCharacteristicToAttributeTypeGroupPojo extends OpenInfraPojo {

    private TopicCharacteristicPojo topicCharacteristic;
    private UUID attributTypeGroupId;
    private MultiplicityPojo multiplicity;
    private int order;

    /* Default constructor */
    public TopicCharacteristicToAttributeTypeGroupPojo() {
    }

    /* Constructor that will set the id and trid automatically */
    public TopicCharacteristicToAttributeTypeGroupPojo(OpenInfraModelObject modelObject) {
        super(modelObject);
    }

    public TopicCharacteristicPojo getTopicCharacteristic() {
        return topicCharacteristic;
    }

    public void setTopicCharacteristic(TopicCharacteristicPojo topicCharacteristic) {
        this.topicCharacteristic = topicCharacteristic;
    }

    public UUID getAttributTypeGroupId() {
        return attributTypeGroupId;
    }

    public void setAttributTypeGroupId(UUID attributTypeGroupId) {
        this.attributTypeGroupId = attributTypeGroupId;
    }

    public MultiplicityPojo getMultiplicity() {
        return multiplicity;
    }

    public void setMultiplicity(MultiplicityPojo multiplicity) {
        this.multiplicity = multiplicity;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

}
