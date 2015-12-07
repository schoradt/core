package de.btu.openinfra.backend.db.pojos;

import java.util.List;

import de.btu.openinfra.backend.db.pojos.project.AttributeValuePojo;

/**
 * Only use this class in combination with a attribute type group since the
 * attributeTypeToAttributeTypeGroupId relates to a specific attribute type
 * group.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class AttributeTypeToValue {

	private AttributeTypeGroupToAttributeTypePojo attributeTypeToAttributeTypeGroup;
	private AttributeTypePojo attributeType;
	private List<AttributeValuePojo> attributeValues;

	public AttributeTypeToValue() {}

	public AttributeTypeToValue(
			AttributeTypePojo attributeType,
			List<AttributeValuePojo> attributeValues,
			AttributeTypeGroupToAttributeTypePojo attributeTypeToAttributeTypeGroup) {
		this.attributeType = attributeType;
		this.attributeValues = attributeValues;
		this.attributeTypeToAttributeTypeGroup =
				attributeTypeToAttributeTypeGroup;
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

	public AttributeTypeGroupToAttributeTypePojo getAttributeTypeToAttributeTypeGroup() {
		return attributeTypeToAttributeTypeGroup;
	}

	public void setAttributeTypeToAttributeTypeGroup(
			AttributeTypeGroupToAttributeTypePojo attributeTypeToAttributeTypeGroup) {
		this.attributeTypeToAttributeTypeGroup = attributeTypeToAttributeTypeGroup;
	}

}
