package de.btu.openinfra.backend.db.pojos;

import java.util.List;

public class AttributeTypeGroupToValues {

	private AttributeTypeGroupPojo attributeTypeGroup;
	private List<AttributeTypeToValue> attributeTypesToValues;

	public List<AttributeTypeToValue> getAttributeTypesToValues() {
		return attributeTypesToValues;
	}

	public void setAttributeTypesToValues(
			List<AttributeTypeToValue> attributeTypesToValues) {
		this.attributeTypesToValues = attributeTypesToValues;
	}

	public AttributeTypeGroupPojo getAttributeTypeGroup() {
		return attributeTypeGroup;
	}

	public void setAttributeTypeGroup(AttributeTypeGroupPojo attributeTypeGroup) {
		this.attributeTypeGroup = attributeTypeGroup;
	}

}
