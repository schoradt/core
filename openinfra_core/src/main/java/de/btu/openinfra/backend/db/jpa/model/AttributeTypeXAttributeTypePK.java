package de.btu.openinfra.backend.db.jpa.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the attribute_type_x_attribute_type database table.
 * 
 */
@Embeddable
public class AttributeTypeXAttributeTypePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="attribute_type_1", insertable=false, updatable=false)
	private String attributeType1;

	@Column(name="attribute_type_2", insertable=false, updatable=false)
	private String attributeType2;

	public AttributeTypeXAttributeTypePK() {
	}
	public String getAttributeType1() {
		return this.attributeType1;
	}
	public void setAttributeType1(String attributeType1) {
		this.attributeType1 = attributeType1;
	}
	public String getAttributeType2() {
		return this.attributeType2;
	}
	public void setAttributeType2(String attributeType2) {
		this.attributeType2 = attributeType2;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AttributeTypeXAttributeTypePK)) {
			return false;
		}
		AttributeTypeXAttributeTypePK castOther = (AttributeTypeXAttributeTypePK)other;
		return 
			this.attributeType1.equals(castOther.attributeType1)
			&& this.attributeType2.equals(castOther.attributeType2);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.attributeType1.hashCode();
		hash = hash * prime + this.attributeType2.hashCode();
		
		return hash;
	}
}