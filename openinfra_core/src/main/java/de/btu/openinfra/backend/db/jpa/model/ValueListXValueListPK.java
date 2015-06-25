package de.btu.openinfra.backend.db.jpa.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the value_list_x_value_list database table.
 * 
 */
@Embeddable
public class ValueListXValueListPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="value_list_1", insertable=false, updatable=false)
	private String valueList1;

	@Column(name="value_list_2", insertable=false, updatable=false)
	private String valueList2;

	public ValueListXValueListPK() {
	}
	public String getValueList1() {
		return this.valueList1;
	}
	public void setValueList1(String valueList1) {
		this.valueList1 = valueList1;
	}
	public String getValueList2() {
		return this.valueList2;
	}
	public void setValueList2(String valueList2) {
		this.valueList2 = valueList2;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ValueListXValueListPK)) {
			return false;
		}
		ValueListXValueListPK castOther = (ValueListXValueListPK)other;
		return 
			this.valueList1.equals(castOther.valueList1)
			&& this.valueList2.equals(castOther.valueList2);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.valueList1.hashCode();
		hash = hash * prime + this.valueList2.hashCode();
		
		return hash;
	}
}