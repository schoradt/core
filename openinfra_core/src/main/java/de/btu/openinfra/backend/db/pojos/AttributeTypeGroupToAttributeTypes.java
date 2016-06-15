package de.btu.openinfra.backend.db.pojos;

import java.util.List;

public class AttributeTypeGroupToAttributeTypes {

	private AttributeTypeGroupPojo attributeTypeGroup;
	private List<AttributeTypeToAttributeTypeGroupPojo>
		attributeTypeToAttributeTypeGroup;

	public AttributeTypeGroupPojo getAttributeTypeGroup() {
		return attributeTypeGroup;
	}

	public void setAttributeTypeGroup(AttributeTypeGroupPojo attributeTypeGroup) {
		this.attributeTypeGroup = attributeTypeGroup;
	}

	public List<AttributeTypeToAttributeTypeGroupPojo> getAttributeTypeToAttributeTypeGroup() {
		return attributeTypeToAttributeTypeGroup;
	}

	public void setAttributeTypeToAttributeTypeGroup(
			List<AttributeTypeToAttributeTypeGroupPojo> attributeTypeToAttributeTypeGroup) {
		this.attributeTypeToAttributeTypeGroup = attributeTypeToAttributeTypeGroup;
	}
}
