package de.btu.openinfra.backend.db.pojos;

import java.util.List;

import de.btu.openinfra.backend.db.pojos.project.AttributeValuePojo;

public class AttributeTypeToValue {

	private AttributeTypePojo attributeType;
	private List<AttributeValuePojo> attributeValue;

	public AttributeTypeToValue() {}

	public AttributeTypeToValue(
			AttributeTypePojo attributeType,
			List<AttributeValuePojo> attributeValue) {
		this.attributeType = attributeType;
		this.attributeValue = attributeValue;
	}

	public AttributeTypePojo getAttributeType() {
		return attributeType;
	}

	public void setAttributeType(AttributeTypePojo attributeType) {
		this.attributeType = attributeType;
	}

	public List<AttributeValuePojo> getAttributeValues() {
		return attributeValue;
	}

	public void setAttributeValues(List<AttributeValuePojo> attributeValues) {
		this.attributeValue = attributeValue;
	}

}
