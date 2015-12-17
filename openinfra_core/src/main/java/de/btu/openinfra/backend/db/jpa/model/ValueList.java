package de.btu.openinfra.backend.db.jpa.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the value_list database table.
 *
 */
@Entity
@Table(name="value_list")
@NamedQueries({
	@NamedQuery(name="ValueList.count",
		query="SELECT COUNT(v) FROM ValueList v "),
	@NamedQuery(name="ValueList.findAll",
		query="SELECT v FROM ValueList v ORDER BY v.id")
})
@NamedNativeQueries({
	@NamedNativeQuery(name="ValueList.findAllByLocaleAndOrder",
			query="select vl.*, vl.xmin "
				  + "from value_list as vl "
			      + "LEFT OUTER JOIN ( "
			      	+ "select * "
			      	+ "from value_list as a, localized_character_string as b "
			      	+ "where a.%s = b.pt_free_text_id "
			        + "and b.pt_locale_id = cast(? as uuid) ) as sq "
			        + "on (vl.id = sq.id) "
			        + "order by free_text ",
			resultClass=ValueList.class)
})
public class ValueList extends OpenInfraModelObject implements Serializable {
	private static final long serialVersionUID = 1L;

	//bi-directional many-to-one association to AttributeType
	@OneToMany(mappedBy="valueList")
	private List<AttributeType> attributeTypes;

	//bi-directional many-to-one association to PtFreeText
	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})
	@JoinColumn(name="description")
	private PtFreeText ptFreeText1;

	//bi-directional many-to-one association to PtFreeText
	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})
	@JoinColumn(name="name")
	private PtFreeText ptFreeText2;

	//bi-directional many-to-one association to ValueListValue
	@OneToMany(mappedBy="valueList")
	private List<ValueListValue> valueListValues;

	//bi-directional many-to-one association to ValueListXValueList
	@OneToMany(mappedBy="valueList1Bean")
	private List<ValueListXValueList> valueListXValueLists1;

	//bi-directional many-to-one association to ValueListXValueList
	@OneToMany(mappedBy="valueList2Bean")
	private List<ValueListXValueList> valueListXValueLists2;

	public ValueList() {
	}

	public List<AttributeType> getAttributeTypes() {
		return this.attributeTypes;
	}

	public void setAttributeTypes(List<AttributeType> attributeTypes) {
		this.attributeTypes = attributeTypes;
	}

	public AttributeType addAttributeType(AttributeType attributeType) {
		getAttributeTypes().add(attributeType);
		attributeType.setValueList(this);

		return attributeType;
	}

	public AttributeType removeAttributeType(AttributeType attributeType) {
		getAttributeTypes().remove(attributeType);
		attributeType.setValueList(null);

		return attributeType;
	}

	public PtFreeText getPtFreeText1() {
		return this.ptFreeText1;
	}

	public void setPtFreeText1(PtFreeText ptFreeText1) {
		this.ptFreeText1 = ptFreeText1;
	}

	public PtFreeText getPtFreeText2() {
		return this.ptFreeText2;
	}

	public void setPtFreeText2(PtFreeText ptFreeText2) {
		this.ptFreeText2 = ptFreeText2;
	}

	public List<ValueListValue> getValueListValues() {
		return this.valueListValues;
	}

	public void setValueListValues(List<ValueListValue> valueListValues) {
		this.valueListValues = valueListValues;
	}

	public ValueListValue addValueListValue(ValueListValue valueListValue) {
		getValueListValues().add(valueListValue);
		valueListValue.setValueList(this);

		return valueListValue;
	}

	public ValueListValue removeValueListValue(ValueListValue valueListValue) {
		getValueListValues().remove(valueListValue);
		valueListValue.setValueList(null);

		return valueListValue;
	}

	public List<ValueListXValueList> getValueListXValueLists1() {
		return this.valueListXValueLists1;
	}

	public void setValueListXValueLists1(List<ValueListXValueList> valueListXValueLists1) {
		this.valueListXValueLists1 = valueListXValueLists1;
	}

	public ValueListXValueList addValueListXValueLists1(ValueListXValueList valueListXValueLists1) {
		getValueListXValueLists1().add(valueListXValueLists1);
		valueListXValueLists1.setValueList1Bean(this);

		return valueListXValueLists1;
	}

	public ValueListXValueList removeValueListXValueLists1(ValueListXValueList valueListXValueLists1) {
		getValueListXValueLists1().remove(valueListXValueLists1);
		valueListXValueLists1.setValueList1Bean(null);

		return valueListXValueLists1;
	}

	public List<ValueListXValueList> getValueListXValueLists2() {
		return this.valueListXValueLists2;
	}

	public void setValueListXValueLists2(List<ValueListXValueList> valueListXValueLists2) {
		this.valueListXValueLists2 = valueListXValueLists2;
	}

	public ValueListXValueList addValueListXValueLists2(ValueListXValueList valueListXValueLists2) {
		getValueListXValueLists2().add(valueListXValueLists2);
		valueListXValueLists2.setValueList2Bean(this);

		return valueListXValueLists2;
	}

	public ValueListXValueList removeValueListXValueLists2(ValueListXValueList valueListXValueLists2) {
		getValueListXValueLists2().remove(valueListXValueLists2);
		valueListXValueLists2.setValueList2Bean(null);

		return valueListXValueLists2;
	}

}