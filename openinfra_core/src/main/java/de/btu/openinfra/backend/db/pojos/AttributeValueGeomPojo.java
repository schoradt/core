package de.btu.openinfra.backend.db.pojos;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.daos.AttributeValueGeomType;
import de.btu.openinfra.backend.db.daos.MetaDataDao;
import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;

@XmlRootElement
public class AttributeValueGeomPojo extends OpenInfraMetaDataPojo {

    private UUID topicInstanceId;
    private String geom;
    private AttributeValueGeomType geomType;
    private UUID attributeTypeToAttributeTypeGroupId;

    /* Default constructor */
    public AttributeValueGeomPojo() {
    }

    /* Constructor that will set the id, trid and meta data automatically */
    public AttributeValueGeomPojo(OpenInfraModelObject modelObject, MetaDataDao mdDao) {
        super(modelObject, mdDao);
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

    @Override
    protected void makePrimerHelper() {
        topicInstanceId = null;
        geom = "";
        geomType = AttributeValueGeomType.GEOJSON;
        attributeTypeToAttributeTypeGroupId = null;
    }

}
