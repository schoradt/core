package de.btu.openinfra.backend.db.pojos;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;

@XmlRootElement
public class RelationshipTypePojo extends OpenInfraPojo {

    private ValueListValuePojo description;
    private ValueListValuePojo RelationshipType;

    /* Default constructor */
    public RelationshipTypePojo() {
    }

    /* Constructor that will set the id and trid automatically */
    public RelationshipTypePojo(OpenInfraModelObject modelObject) {
        super(modelObject);
    }

    public ValueListValuePojo getDescription() {
        return description;
    }

    public void setDescription(ValueListValuePojo description) {
        this.description = description;
    }

    public ValueListValuePojo getRelationshipType() {
        return RelationshipType;
    }

    public void setRelationshipType(ValueListValuePojo relationshipType) {
        RelationshipType = relationshipType;
    }

}
