package de.btu.openinfra.backend.db.pojos;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;

@XmlRootElement
public class TopicCharacteristicToRelationshipTypePojo extends OpenInfraPojo {

    private UUID relationshipTypeId;
    private MultiplicityPojo multiplicity;
    private TopicCharacteristicPojo topicCharacteristic;

    /* Default constructor */
    public TopicCharacteristicToRelationshipTypePojo() {
    }

    /* Constructor that will set the id and trid automatically */
    public TopicCharacteristicToRelationshipTypePojo(
            OpenInfraModelObject modelObject) {
        super(modelObject);
    }

    public UUID getRelationshipTypeId() {
        return relationshipTypeId;
    }

    public void setRelationshipTypeId(UUID relationshipTypeId) {
        this.relationshipTypeId = relationshipTypeId;
    }

    public MultiplicityPojo getMultiplicity() {
        return multiplicity;
    }

    public void setMultiplicity(MultiplicityPojo multiplicity) {
        this.multiplicity = multiplicity;
    }

    public TopicCharacteristicPojo getTopicCharacteristic() {
        return topicCharacteristic;
    }

    public void setTopicCharacteristic(
            TopicCharacteristicPojo topicCharacteristic) {
        this.topicCharacteristic = topicCharacteristic;
    }

}
