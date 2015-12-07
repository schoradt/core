package de.btu.openinfra.backend.db.pojos;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;

@XmlRootElement
public class AttributeTypeToAttributeTypeGroupPojo extends OpenInfraPojo {

    private AttributeTypePojo attributeType;
    private UUID attributeTypeGroupId;
    private UUID attributeTypeGroupToTopicCharacteristicId;
    private MultiplicityPojo multiplicity;
    private ValueListValuePojo defaultValue;
    private Integer order;

    /* Default constructor */
    public AttributeTypeToAttributeTypeGroupPojo() {
    }

    /* Constructor that will set the id and trid automatically */
    public AttributeTypeToAttributeTypeGroupPojo(
            OpenInfraModelObject modelObject) {
        super(modelObject);
    }

    public AttributeTypePojo getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(AttributeTypePojo attributeType) {
        this.attributeType = attributeType;
    }

    public UUID getAttributeTypeGroupId() {
        return attributeTypeGroupId;
    }

    public void setAttributeTypeGroupId(UUID attributeTypeGroupId) {
        this.attributeTypeGroupId = attributeTypeGroupId;
    }

    public MultiplicityPojo getMultiplicity() {
        return multiplicity;
    }

    public void setMultiplicity(MultiplicityPojo multiplicity) {
        this.multiplicity = multiplicity;
    }

    public ValueListValuePojo getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(ValueListValuePojo defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public UUID getAttributeTypeGroupToTopicCharacteristicId() {
        return attributeTypeGroupToTopicCharacteristicId;
    }

    public void setAttributeTypeGroupToTopicCharacteristicId(
            UUID attributeTypeGroupToTopicCharacteristicId) {
        this.attributeTypeGroupToTopicCharacteristicId =
                attributeTypeGroupToTopicCharacteristicId;
    }

}
