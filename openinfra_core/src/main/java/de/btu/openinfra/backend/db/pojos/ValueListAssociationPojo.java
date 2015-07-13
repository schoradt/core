package de.btu.openinfra.backend.db.pojos;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ValueListAssociationPojo extends OpenInfraPojo {

	private UUID associatedId;
	private ValueListValuePojo relationship;

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
