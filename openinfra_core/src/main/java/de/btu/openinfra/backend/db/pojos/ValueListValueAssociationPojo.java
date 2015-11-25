package de.btu.openinfra.backend.db.pojos;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;

@XmlRootElement
public class ValueListValueAssociationPojo extends OpenInfraPojo {

    private UUID associationValueListValueId;
    private ValueListValuePojo associatedValueListValue;
    private ValueListValuePojo relationship;

    /* Default constructor */
    public ValueListValueAssociationPojo() {
    }

    /* Constructor that will set the id and trid automatically */
    public ValueListValueAssociationPojo(OpenInfraModelObject modelObject) {
        super(modelObject);
    }

    public UUID getAssociationValueListValueId() {
        return associationValueListValueId;
    }

    public void setAssociationValueListValueId(UUID associationValueListValueId) {
        this.associationValueListValueId = associationValueListValueId;
    }

    public ValueListValuePojo getAssociatedValueListValue() {
        return associatedValueListValue;
    }

    public void setAssociatedValueListValue(ValueListValuePojo associatedValueListValue) {
        this.associatedValueListValue = associatedValueListValue;
    }

    public ValueListValuePojo getRelationship() {
        return relationship;
    }

    public void setRelationship(ValueListValuePojo relationship) {
        this.relationship = relationship;
    }

}
