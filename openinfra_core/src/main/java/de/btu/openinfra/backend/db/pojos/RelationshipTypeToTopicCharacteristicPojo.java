package de.btu.openinfra.backend.db.pojos;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.daos.MetaDataDao;
import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;

@XmlRootElement
public class RelationshipTypeToTopicCharacteristicPojo extends OpenInfraMetaDataPojo {

    private UUID topicCharacteristicId;
    private MultiplicityPojo multiplicity;
    private RelationshipTypePojo relationshipType;

    /* Default constructor */
    public RelationshipTypeToTopicCharacteristicPojo() {
    }

    /* Constructor that will set the id, trid and meta data automatically */
    public RelationshipTypeToTopicCharacteristicPojo(OpenInfraModelObject modelObject, MetaDataDao mdDao) {
        super(modelObject, mdDao);
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
