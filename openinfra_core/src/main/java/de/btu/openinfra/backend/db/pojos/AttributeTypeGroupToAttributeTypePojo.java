package de.btu.openinfra.backend.db.pojos;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;

@XmlRootElement
public class AttributeTypeGroupToAttributeTypePojo extends OpenInfraPojo {

    private AttributeTypeGroupPojo attributeTypeGroup;
    private UUID attributeTypeId;
    private UUID attributeTypeGroupToTopicCharacteristicId;
    private MultiplicityPojo multiplicity;
    private ValueListValuePojo defaultValue;
    private Integer order;

    /* Default constructor */
    public AttributeTypeGroupToAttributeTypePojo() {
    }

    /* Constructor that will set the id and trid automatically */
    public AttributeTypeGroupToAttributeTypePojo(
            OpenInfraModelObject modelObject) {
        super(modelObject);
    }

    public AttributeTypeGroupPojo getAttributeTypeGroup() {
        return attributeTypeGroup;
    }

    public void setAttributeTypeGroup(
            AttributeTypeGroupPojo attributeTypeGroup) {
        this.attributeTypeGroup = attributeTypeGroup;
    }

    public UUID getAttributeTypeId() {
        return attributeTypeId;
    }

    public void setAttributeTypeId(UUID attributeTypeId) {
        this.attributeTypeId = attributeTypeId;
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
