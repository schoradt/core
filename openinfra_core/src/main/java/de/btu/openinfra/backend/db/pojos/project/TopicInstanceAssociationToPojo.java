package de.btu.openinfra.backend.db.pojos.project;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;
import de.btu.openinfra.backend.db.pojos.RelationshipTypePojo;

@XmlRootElement
public class TopicInstanceAssociationToPojo extends OpenInfraPojo {

    private UUID associationInstanceId;
    private TopicInstancePojo associatedInstance;
    private RelationshipTypePojo relationshipType;

    /* Default constructor */
    public TopicInstanceAssociationToPojo() {
    }

    /* Constructor that will set the id, trid and meta data automatically */
    public TopicInstanceAssociationToPojo(OpenInfraModelObject modelObject) {
        super(modelObject);
    }

    // TODO check what happens with the meta data?
    public TopicInstanceAssociationToPojo(
            UUID uuid,
            UUID assoId,
            TopicInstancePojo tip,
            RelationshipTypePojo rtp) {
        setUuid(uuid);
        associationInstanceId = assoId;
        associatedInstance = tip;
        relationshipType = rtp;
    }

    public UUID getAssociationInstanceId() {
        return associationInstanceId;
    }

    public void setAssociationInstanceId(UUID associationInstanceId) {
        this.associationInstanceId = associationInstanceId;
    }

    public RelationshipTypePojo getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(RelationshipTypePojo relationshipType) {
        this.relationshipType = relationshipType;
    }

    public TopicInstancePojo getAssociatedInstance() {
        return associatedInstance;
    }

    public void setAssociatedInstance(TopicInstancePojo associatedInstance) {
        this.associatedInstance = associatedInstance;
    }

}
