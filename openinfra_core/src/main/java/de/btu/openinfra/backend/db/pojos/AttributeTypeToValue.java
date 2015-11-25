package de.btu.openinfra.backend.db.pojos;

import de.btu.openinfra.backend.db.pojos.project.AttributeValuePojo;

public class AttributeTypeToValue {
	
	private AttributeTypePojo attributeType;
	private AttributeValuePojo attributeValue;
	
	public AttributeTypeToValue() {}
	
	public AttributeTypeToValue(
			AttributeTypePojo attributeType, 
			AttributeValuePojo attributeValue) {
		this.attributeType = attributeType;
		this.attributeValue = attributeValue;
	}
	
	public AttributeTypePojo getAttributeType() {
		return attributeType;
	}
	
	public void setAttributeType(AttributeTypePojo attributeType) {
		this.attributeType = attributeType;
	}
	
	public AttributeValuePojo getAttributeValue() {
		return attributeValue;
	}
	
	public void setAttributeValue(AttributeValuePojo attributeValue) {
		this.attributeValue = attributeValue;
	}

}
