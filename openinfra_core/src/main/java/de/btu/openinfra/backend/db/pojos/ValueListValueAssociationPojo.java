package de.btu.openinfra.backend.db.pojos;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ValueListValueAssociationPojo extends OpenInfraPojo {
	
	private ValueListValuePojo valueListValue;
	private UUID associatedId;
	private ValueListValuePojo relationship;
	
	public ValueListValuePojo getValueListValue() {
		return valueListValue;
	}
	public void setValueListValue(ValueListValuePojo valueListValue) {
		this.valueListValue = valueListValue;
	}
	public UUID getAssociatedId() {
		return associatedId;
	}
	public void setAssociatedId(UUID associatedId) {
		this.associatedId = associatedId;
	}
	public ValueListValuePojo getRelationship() {
		return relationship;
	}
	public void setRelationship(ValueListValuePojo relationship) {
		this.relationship = relationship;
	}
	
	

}
