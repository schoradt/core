package de.btu.openinfra.backend.db.pojos;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.daos.MetaDataDao;
import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;

@XmlRootElement
public class AttributeTypeAssociationPojo extends OpenInfraMetaDataPojo {

    private UUID associationAttributeTypeId;
    private AttributeTypePojo associatedAttributeType;
    private ValueListValuePojo relationship;

    /* Default constructor */
    public AttributeTypeAssociationPojo() {
    }

    /* Constructor that will set the id, trid and meta data automatically */
    public AttributeTypeAssociationPojo(OpenInfraModelObject modelObject, MetaDataDao mdDao) {
        super(modelObject, mdDao);
    }

    public UUID getAssociationAttributeTypeId() {
        return associationAttributeTypeId;
    }

    public void setAssociationAttributeTypeId(UUID associationAttributeTypeId) {
        this.associationAttributeTypeId = associationAttributeTypeId;
    }

    public AttributeTypePojo getAssociatedAttributeType() {
        return associatedAttributeType;
    }

    public void setAssociatedAttributeType(AttributeTypePojo associatedAttributeType) {
        this.associatedAttributeType = associatedAttributeType;
    }

    public ValueListValuePojo getRelationship() {
        return relationship;
    }

    public void setRelationship(ValueListValuePojo relationship) {
        this.relationship = relationship;
    }

    @Override
    protected void makePrimerHelper() {
        associationAttributeTypeId = null;
        associatedAttributeType = new AttributeTypePojo();
        associatedAttributeType.makePrimer();
        relationship = new ValueListValuePojo();
        relationship.makePrimer();
    }

}
