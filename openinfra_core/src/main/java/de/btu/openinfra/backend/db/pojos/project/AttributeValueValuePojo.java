package de.btu.openinfra.backend.db.pojos.project;

import java.util.UUID;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;
import de.btu.openinfra.backend.db.pojos.PtFreeTextPojo;

public class AttributeValueValuePojo extends OpenInfraPojo {

    private UUID topicInstanceId;
    private PtFreeTextPojo value;
    private UUID attributeTypeToAttributeTypeGroupId;

    /* Default constructor */
    public AttributeValueValuePojo() {
    }

    /* Constructor that will set the id and trid automatically */
    public AttributeValueValuePojo(OpenInfraModelObject modelObject) {
        super(modelObject);
    }

    public UUID getTopicInstanceId() {
        return topicInstanceId;
    }

    public void setTopicInstanceId(UUID topicInstanceId) {
        this.topicInstanceId = topicInstanceId;
    }

    public PtFreeTextPojo getValue() {
        return value;
    }

    public void setValue(PtFreeTextPojo value) {
        this.value = value;
    }

    public UUID getAttributeTypeToAttributeTypeGroupId() {
        return attributeTypeToAttributeTypeGroupId;
    }

    public void setAttributeTypeToAttributeTypeGroupId(UUID attributeTypeToAttributeTypeGroupId) {
        this.attributeTypeToAttributeTypeGroupId = attributeTypeToAttributeTypeGroupId;
    }

}
