package de.btu.openinfra.backend.db.jpa.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the value_list_values_x_value_list_values database table.
 *
 */
@Entity
@Table(name="value_list_values_x_value_list_values")
@NamedQueries({
	@NamedQuery(name="ValueListValuesXValueListValue.findAll",
			query="SELECT v FROM ValueListValuesXValueListValue v"
			        + " ORDER BY v.id"),
	@NamedQuery(name="ValueListValuesXValueListValue.findByValueListValue",
			query="SELECT v FROM ValueListValuesXValueListValue v "
					+ "WHERE v.valueListValue2 = :value ORDER BY v.id"),
	@NamedQuery(name="ValueListValuesXValueListValue.countByValueListValue",
        query="SELECT count(v) FROM ValueListValuesXValueListValue v "
            + "WHERE v.valueListValue2 = :value "),
	@NamedQuery(
			name=
				"ValueListValuesXValueListValue."
				+ "findByValueListValueAndValueListValue",
			query="SELECT v FROM ValueListValuesXValueListValue v "
			+ "WHERE v.valueListValue2 = :value AND "
			+ "v.valueListValue3 = :value2 ORDER BY v.id"),
})
public class ValueListValuesXValueListValue extends OpenInfraModelObject
    implements Serializable {
	private static final long serialVersionUID = 1L;

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