package de.btu.openinfra.backend.db.pojos.project;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;
import de.btu.openinfra.backend.db.pojos.RelationshipTypePojo;

@XmlRootElement
public class TopicInstanceAssociationFromPojo extends OpenInfraPojo {

    private TopicInstancePojo associationInstance;
    private UUID associatedInstanceId;
    private RelationshipTypePojo relationshipType;

    /* Default constructor */
    public TopicInstanceAssociationFromPojo() {
    }

    /* Constructor that will set the id, trid and meta data automatically */
    public TopicInstanceAssociationFromPojo(OpenInfraModelObject modelObject) {
        super(modelObject);
    }

    // TODO check what happens with the meta data?
    public TopicInstanceAssociationFromPojo(
            UUID uuid,
            TopicInstancePojo assoTip,
            UUID tipId,
            RelationshipTypePojo rtp) {
        setUuid(uuid);
        associationInstance = assoTip;
        associatedInstanceId = tipId;
        relationshipType = rtp;
    }

    public TopicInstancePojo getAssociationInstance() {
        return associationInstance;
    }

    public void setAssociationInstance(TopicInstancePojo associationInstance) {
        this.associationInstance = associationInstance;
    }

    public RelationshipTypePojo getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(RelationshipTypePojo relationshipType) {
        this.relationshipType = relationshipType;
    }

    public UUID getAssociatedInstanceId() {
        return associatedInstanceId;
    }

    public void setAssociatedInstanceId(UUID associatedInstanceId) {
        this.associatedInstanceId = associatedInstanceId;
    }

}
