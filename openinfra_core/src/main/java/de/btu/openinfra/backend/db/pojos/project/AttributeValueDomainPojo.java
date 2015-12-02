package de.btu.openinfra.backend.db.pojos.project;

import java.util.UUID;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;
import de.btu.openinfra.backend.db.pojos.ValueListValuePojo;

public class AttributeValueDomainPojo extends OpenInfraPojo {

    private UUID topicInstanceId;
    private ValueListValuePojo domain;
    private UUID attributeTypeToAttributeTypeGroupId;

    /* Default constructor */
    public AttributeValueDomainPojo() {
    }

    /* Constructor that will set the id and trid automatically */
    public AttributeValueDomainPojo(OpenInfraModelObject modelObject) {
        super(modelObject);
    }

    public UUID getTopicInstanceId() {
        return topicInstanceId;
    }

    public void setTopicInstanceId(UUID topicInstanceId) {
        this.topicInstanceId = topicInstanceId;
    }

    public ValueListValuePojo getDomain() {
        return domain;
    }

    public void setDomain(ValueListValuePojo domain) {
        this.domain = domain;
    }

    public UUID getAttributeTypeToAttributeTypeGroupId() {
        return attributeTypeToAttributeTypeGroupId;
    }

    public void setAttributeTypeToAttributeTypeGroupId(UUID attributeTypeToAttributeTypeGroupId) {
        this.attributeTypeToAttributeTypeGroupId = attributeTypeToAttributeTypeGroupId;
    }

}
