package de.btu.openinfra.backend.db.pojos;

import java.util.UUID;

import de.btu.openinfra.backend.db.daos.MetaDataDao;
import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;

public class AttributeValueValuePojo extends OpenInfraMetaDataPojo {

    private UUID topicInstanceId;
    private PtFreeTextPojo value;
    private UUID attributeTypeToAttributeTypeGroupId;

    /* Default constructor */
    public AttributeValueValuePojo() {
    }

    /* Constructor that will set the id, trid and meta data automatically */
    public AttributeValueValuePojo(OpenInfraModelObject modelObject, MetaDataDao mdDao) {
        super(modelObject, mdDao);
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

    @Override
    public void makePrimer() {
        topicInstanceId = null;
        value = new PtFreeTextPojo();
        value.makePrimer();
        attributeTypeToAttributeTypeGroupId = null;
    }

}
