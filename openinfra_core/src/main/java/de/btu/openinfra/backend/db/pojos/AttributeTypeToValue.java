package de.btu.openinfra.backend.db.pojos;

import java.util.List;

import de.btu.openinfra.backend.db.pojos.project.AttributeValuePojo;

public class AttributeTypeToValue {

	private AttributeTypePojo attributeType;
	private List<AttributeValuePojo> attributeValues;

	public AttributeTypeToValue() {}

	public AttributeTypeToValue(
			AttributeTypePojo attributeType,
			List<AttributeValuePojo> attributeValues) {
		this.attributeType = attributeType;
		this.attributeValues = attributeValues;
	}

	public AttributeTypePojo getAttributeType() {
		return attributeType;
	}

	public void setAttributeType(AttributeTypePojo attributeType) {
		this.attributeType = attributeType;
	}

	public List<AttributeValuePojo> getAttributeValues() {
		return attributeValues;
	}

	public void setAttributeValues(List<AttributeValuePojo> attributeValues) {
		this.attributeValues = attributeValues;
	}

}
