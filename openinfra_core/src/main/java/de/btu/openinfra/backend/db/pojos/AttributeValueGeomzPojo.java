package de.btu.openinfra.backend.db.pojos;

import java.util.UUID;

import de.btu.openinfra.backend.db.daos.AttributeValueGeomType;
import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;

public class AttributeValueGeomzPojo extends OpenInfraPojo {

    private UUID topicInstanceId;
    private String geom;
    private AttributeValueGeomType geomType;
    private UUID attributeTypeToAttributeTypeGroupId;

    /* Default constructor */
    public AttributeValueGeomzPojo() {
    }

    /* Constructor that will set the id and trid automatically */
    public AttributeValueGeomzPojo(OpenInfraModelObject modelObject) {
        super(modelObject);
    }

    public UUID getTopicInstanceId() {
        return topicInstanceId;
    }

    public void setTopicInstanceId(UUID topicInstanceId) {
        this.topicInstanceId = topicInstanceId;
    }

    public String getGeom() {
        return geom;
    }

    public void setGeom(String geom) {
        this.geom = geom;
    }

    public AttributeValueGeomType getGeomType() {
        return geomType;
    }

    public void setGeomType(AttributeValueGeomType geomType) {
        this.geomType = geomType;
    }

    public UUID getAttributeTypeToAttributeTypeGroupId() {
        return attributeTypeToAttributeTypeGroupId;
    }

    public void setAttributeTypeToAttributeTypeGroupId(UUID attributeTypeToAttributeTypeGroupId) {
        this.attributeTypeToAttributeTypeGroupId = attributeTypeToAttributeTypeGroupId;
    }

}
