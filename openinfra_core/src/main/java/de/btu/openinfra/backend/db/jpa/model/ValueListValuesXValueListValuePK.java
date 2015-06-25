package de.btu.openinfra.backend.db.jpa.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the value_list_values_x_value_list_values database table.
 * 
 */
@Embeddable
public class ValueListValuesXValueListValuePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="value_list_values_1", insertable=false, updatable=false)
	private String valueListValues1;

	@Column(name="value_list_values_2", insertable=false, updatable=false)
	private String valueListValues2;

	public ValueListValuesXValueListValuePK() {
	}
	public String getValueListValues1() {
		return this.valueListValues1;
	}
	public void setValueListValues1(String valueListValues1) {
		this.valueListValues1 = valueListValues1;
	}
	public String getValueListValues2() {
		return this.valueListValues2;
	}
	public void setValueListValues2(String valueListValues2) {
		this.valueListValues2 = valueListValues2;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ValueListValuesXValueListValuePK)) {
			return false;
		}
		ValueListValuesXValueListValuePK castOther = (ValueListValuesXValueListValuePK)other;
		return 
			this.valueListValues1.equals(castOther.valueListValues1)
			&& this.valueListValues2.equals(castOther.valueListValues2);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.valueListValues1.hashCode();
		hash = hash * prime + this.valueListValues2.hashCode();
		
		return hash;
	}
}