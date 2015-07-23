package de.btu.openinfra.backend.db.pojos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AttributeTypeAssociationPojo extends OpenInfraPojo {

	private AttributeTypePojo associatedAttributeType;
	private ValueListValuePojo relationship;
	
	public AttributeTypePojo getAssociatedAttributeType() {
		return associatedAttributeType;
	}

	public void setAssociatedAttributeType(
		AttributeTypePojo associatedAttributeType) {
		this.associatedAttributeType = associatedAttributeType;
	}

	public ValueListValuePojo getRelationship() {
		return relationship;
	}
	
	public void setRelationship(ValueListValuePojo relationship) {
		this.relationship = relationship;
	}
	
	
}
