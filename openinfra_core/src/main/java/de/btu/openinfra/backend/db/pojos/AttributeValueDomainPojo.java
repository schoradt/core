package de.btu.openinfra.backend.db.pojos;

import java.util.UUID;

import de.btu.openinfra.backend.db.daos.MetaDataDao;
import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;

public class AttributeValueDomainPojo extends OpenInfraMetaDataPojo {

    private UUID topicInstanceId;
    private ValueListValuePojo domain;
    private UUID attributeTypeToAttributeTypeGroupId;

    /* Default constructor */
    public AttributeValueDomainPojo() {
    }

    /* Constructor that will set the id, trid and meta data automatically */
    public AttributeValueDomainPojo(OpenInfraModelObject modelObject, MetaDataDao mdDao) {
        super(modelObject, mdDao);
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

    @Override
    protected void makePrimerHelper() {
        topicInstanceId = null;
        domain = new ValueListValuePojo();
        domain.makePrimer();
        attributeTypeToAttributeTypeGroupId = null;
    }

}
