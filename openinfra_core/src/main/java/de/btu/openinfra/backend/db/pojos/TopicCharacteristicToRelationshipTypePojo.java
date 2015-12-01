package de.btu.openinfra.backend.db.pojos;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;

@XmlRootElement
public class TopicCharacteristicToRelationshipTypePojo extends OpenInfraPojo {

    private UUID relationshipType;
    private MultiplicityPojo multiplicity;
    private TopicCharacteristicPojo topicCharacteristic;

    /* Default constructor */
    public TopicCharacteristicToRelationshipTypePojo() {
    }

    /* Constructor that will set the id and trid automatically */
    public TopicCharacteristicToRelationshipTypePojo(OpenInfraModelObject modelObject) {
        super(modelObject);
    }

    public UUID getRelationshipe() {
        return relationshipType;
    }

    public void setRelationshipe(UUID relationShipe) {
        this.relationshipType = relationShipe;
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

    public void setTopicCharacteristic(TopicCharacteristicPojo topicCharacteristic) {
        this.topicCharacteristic = topicCharacteristic;
    }

}
