package de.btu.openinfra.backend.db.jpa.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the value_list_values_x_value_list_values database table.
 * 
 */
@Entity
@Table(name="value_list_values_x_value_list_values")
@NamedQuery(name="ValueListValuesXValueListValue.findAll", query="SELECT v FROM ValueListValuesXValueListValue v")
public class ValueListValuesXValueListValue implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ValueListValuesXValueListValuePK id;

	//bi-directional many-to-one association to ValueListValue
	@ManyToOne
	@JoinColumn(name="relationship")
	private ValueListValue valueListValue1;

	//bi-directional many-to-one association to ValueListValue
	@ManyToOne
	@JoinColumn(name="value_list_values_1")
	private ValueListValue valueListValue2;

	//bi-directional many-to-one association to ValueListValue
	@ManyToOne
	@JoinColumn(name="value_list_values_2")
	private ValueListValue valueListValue3;

	public ValueListValuesXValueListValue() {
	}

	public ValueListValuesXValueListValuePK getId() {
		return this.id;
	}

	public void setId(ValueListValuesXValueListValuePK id) {
		this.id = id;
	}

	public ValueListValue getValueListValue1() {
		return this.valueListValue1;
	}

	public void setValueListValue1(ValueListValue valueListValue1) {
		this.valueListValue1 = valueListValue1;
	}

	public ValueListValue getValueListValue2() {
		return this.valueListValue2;
	}

	public void setValueListValue2(ValueListValue valueListValue2) {
		this.valueListValue2 = valueListValue2;
	}

	public ValueListValue getValueListValue3() {
		return this.valueListValue3;
	}

	public void setValueListValue3(ValueListValue valueListValue3) {
		this.valueListValue3 = valueListValue3;
	}

}