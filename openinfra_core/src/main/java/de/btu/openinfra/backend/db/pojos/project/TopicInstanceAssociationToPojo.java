package de.btu.openinfra.backend.db.pojos.project;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.daos.MetaDataDao;
import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraMetaDataPojo;
import de.btu.openinfra.backend.db.pojos.RelationshipTypePojo;

@XmlRootElement
public class TopicInstanceAssociationToPojo extends OpenInfraMetaDataPojo {

    private TopicInstancePojo associationInstance;
    private TopicInstancePojo associatedInstance;
    private RelationshipTypePojo relationshipType;

    /* Default constructor */
    public TopicInstanceAssociationToPojo() {
    }

    /* Constructor that will set the id, trid and meta data automatically */
    public TopicInstanceAssociationToPojo(
            OpenInfraModelObject modelObject,
            MetaDataDao mdDao) {
        super(modelObject, mdDao);
    }

    // TODO check what happens with the meta data?
    public TopicInstanceAssociationToPojo(
            UUID uuid,
            TopicInstancePojo assoTip,
            TopicInstancePojo tip,
            RelationshipTypePojo rtp) {
        setUuid(uuid);
        associationInstance = assoTip;
        associatedInstance = tip;
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

    public TopicInstancePojo getAssociatedInstance() {
        return associatedInstance;
    }

    public void setAssociatedInstance(TopicInstancePojo associatedInstance) {
        this.associatedInstance = associatedInstance;
    }

}
