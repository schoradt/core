package de.btu.openinfra.backend.db.pojos.project;

import java.util.List;

import de.btu.openinfra.backend.db.pojos.AttributeTypeGroupPojo;

/**
 * This is a very special data container used in the TopicPojo class.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
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
