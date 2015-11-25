package de.btu.openinfra.backend.db.pojos;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;

@XmlRootElement
public class RelationshipTypeToTopicCharacteristicPojo extends OpenInfraPojo {

    private UUID topicCharacteristicId;
    private MultiplicityPojo multiplicity;
    private RelationshipTypePojo relationshipType;

    /* Default constructor */
    public RelationshipTypeToTopicCharacteristicPojo() {
    }

    /* Constructor that will set the id and trid automatically */
    public RelationshipTypeToTopicCharacteristicPojo(OpenInfraModelObject modelObject) {
        super(modelObject);
    }

    public UUID getTopicCharacteristicId() {
        return topicCharacteristicId;
    }

    public void setTopicCharacteristicId(UUID topicCharacteristicId) {
        this.topicCharacteristicId = topicCharacteristicId;
    }

    public MultiplicityPojo getMultiplicity() {
        return multiplicity;
    }

    public void setMultiplicity(MultiplicityPojo multiplicity) {
        this.multiplicity = multiplicity;
    }

    public RelationshipTypePojo getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(RelationshipTypePojo relationshipType) {
        this.relationshipType = relationshipType;
    }

}
