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
 * The persistent class for the value_list_values database table.
 *
 */
@Entity
@Table(name="value_list_values")
@NamedQueries({
	@NamedQuery(name="ValueListValue.findAll",
			query="SELECT v FROM ValueListValue v"),
	@NamedQuery(name="ValueListValue.findByValueList",
			query="SELECT v "
					+ "FROM ValueListValue v "
					+ "WHERE v.valueList = :value"),
	@NamedQuery(name="ValueListValue.countByValueList",
		query="SELECT COUNT(v) "
			+ "FROM ValueListValue v "
			+ "WHERE v.valueList = :value")
})
@NamedNativeQueries({
    @NamedNativeQuery(name="ValueListValue.findAllByLocaleAndOrder",
            query="SELECT vlv.*, vlv.xmin "
                  + "FROM value_list_values AS vlv "
                  + "LEFT OUTER JOIN ( "
                    + "SELECT * "
                    + "FROM value_list_values AS a, "
                    + "localized_character_string AS b "
                    + "WHERE a.%s = b.pt_free_text_id "
                    + "AND b.pt_locale_id = cast(? as uuid) ) AS sq "
                    + "ON (vlv.id = sq.id) "
                    + "WHERE vlv.belongs_to_value_list = ? "
                    + "ORDER BY free_text ",
            resultClass=ValueList.class)
})
public class ValueListValue extends OpenInfraModelObject
    implements Serializable {
	private static final long serialVersionUID = 1L;

	private Boolean visibility;

	//bi-directional many-to-one association to AttributeType
	@OneToMany(mappedBy="valueListValue1")
	private List<AttributeType> attributeTypes1;

	//bi-directional many-to-one association to AttributeType
	@OneToMany(mappedBy="valueListValue2")
	private List<AttributeType> attributeTypes2;

	//bi-directional many-to-one association to AttributeTypeToAttributeTypeGroup
	@OneToMany(mappedBy="valueListValue")
	private List<AttributeTypeToAttributeTypeGroup> attributeTypeToAttributeTypeGroups;

	//bi-directional many-to-one association to AttributeTypeXAttributeType
	@OneToMany(mappedBy="valueListValue")
	private List<AttributeTypeXAttributeType> attributeTypeXAttributeTypes;

	//bi-directional many-to-one association to AttributeValueDomain
	@OneToMany(mappedBy="valueListValue")
	private List<AttributeValueDomain> attributeValueDomains;

	//bi-directional many-to-one association to RelationshipType
	@OneToMany(mappedBy="valueListValue1")
	private List<RelationshipType> relationshipTypes1;

	//bi-directional many-to-one association to RelationshipType
	@OneToMany(mappedBy="valueListValue2")
	private List<RelationshipType> relationshipTypes2;

	//bi-directional many-to-one association to TopicCharacteristic
	@OneToMany(mappedBy="valueListValue")
	private List<TopicCharacteristic> topicCharacteristics;

	//bi-directional many-to-one association to PtFreeText
	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})
	@JoinColumn(name="description")
	private PtFreeText ptFreeText1;

	//bi-directional many-to-one association to PtFreeText
	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})
	@JoinColumn(name="name")
	private PtFreeText ptFreeText2;

	//bi-directional many-to-one association to ValueList
	@ManyToOne
	@JoinColumn(name="belongs_to_value_list")
	private ValueList valueList;

	//bi-directional many-to-one association to ValueListValuesXValueListValue
	@OneToMany(mappedBy="valueListValue1")
	private List<ValueListValuesXValueListValue> valueListValuesXValueListValues1;

	//bi-directional many-to-one association to ValueListValuesXValueListValue
	@OneToMany(mappedBy="valueListValue2")
	private List<ValueListValuesXValueListValue> valueListValuesXValueListValues2;

	//bi-directional many-to-one association to ValueListValuesXValueListValue
	@OneToMany(mappedBy="valueListValue3")
	private List<ValueListValuesXValueListValue> valueListValuesXValueListValues3;

	//bi-directional many-to-one association to ValueListXValueList
	@OneToMany(mappedBy="valueListValue")
	private List<ValueListXValueList> valueListXValueLists;

	public ValueListValue() {
	}

	public Boolean getVisibility() {
		return this.visibility;
	}

	public void setVisibility(Boolean visibility) {
		this.visibility = visibility;
	}

	public List<AttributeType> getAttributeTypes1() {
		return this.attributeTypes1;
	}

	public void setAttributeTypes1(List<AttributeType> attributeTypes1) {
		this.attributeTypes1 = attributeTypes1;
	}

	public AttributeType addAttributeTypes1(AttributeType attributeTypes1) {
		getAttributeTypes1().add(attributeTypes1);
		attributeTypes1.setValueListValue1(this);

		return attributeTypes1;
	}

	public AttributeType removeAttributeTypes1(AttributeType attributeTypes1) {
		getAttributeTypes1().remove(attributeTypes1);
		attributeTypes1.setValueListValue1(null);

		return attributeTypes1;
	}

	public List<AttributeType> getAttributeTypes2() {
		return this.attributeTypes2;
	}

	public void setAttributeTypes2(List<AttributeType> attributeTypes2) {
		this.attributeTypes2 = attributeTypes2;
	}

	public AttributeType addAttributeTypes2(AttributeType attributeTypes2) {
		getAttributeTypes2().add(attributeTypes2);
		attributeTypes2.setValueListValue2(this);

		return attributeTypes2;
	}

	public AttributeType removeAttributeTypes2(AttributeType attributeTypes2) {
		getAttributeTypes2().remove(attributeTypes2);
		attributeTypes2.setValueListValue2(null);

		return attributeTypes2;
	}

	public List<AttributeTypeToAttributeTypeGroup> getAttributeTypeToAttributeTypeGroups() {
		return this.attributeTypeToAttributeTypeGroups;
	}

	public void setAttributeTypeToAttributeTypeGroups(List<AttributeTypeToAttributeTypeGroup> attributeTypeToAttributeTypeGroups) {
		this.attributeTypeToAttributeTypeGroups = attributeTypeToAttributeTypeGroups;
	}

	public AttributeTypeToAttributeTypeGroup addAttributeTypeToAttributeTypeGroup(AttributeTypeToAttributeTypeGroup attributeTypeToAttributeTypeGroup) {
		getAttributeTypeToAttributeTypeGroups().add(attributeTypeToAttributeTypeGroup);
		attributeTypeToAttributeTypeGroup.setValueListValue(this);

		return attributeTypeToAttributeTypeGroup;
	}

	public AttributeTypeToAttributeTypeGroup removeAttributeTypeToAttributeTypeGroup(AttributeTypeToAttributeTypeGroup attributeTypeToAttributeTypeGroup) {
		getAttributeTypeToAttributeTypeGroups().remove(attributeTypeToAttributeTypeGroup);
		attributeTypeToAttributeTypeGroup.setValueListValue(null);

		return attributeTypeToAttributeTypeGroup;
	}

	public List<AttributeTypeXAttributeType> getAttributeTypeXAttributeTypes() {
		return this.attributeTypeXAttributeTypes;
	}

	public void setAttributeTypeXAttributeTypes(List<AttributeTypeXAttributeType> attributeTypeXAttributeTypes) {
		this.attributeTypeXAttributeTypes = attributeTypeXAttributeTypes;
	}

	public AttributeTypeXAttributeType addAttributeTypeXAttributeType(AttributeTypeXAttributeType attributeTypeXAttributeType) {
		getAttributeTypeXAttributeTypes().add(attributeTypeXAttributeType);
		attributeTypeXAttributeType.setValueListValue(this);

		return attributeTypeXAttributeType;
	}

	public AttributeTypeXAttributeType removeAttributeTypeXAttributeType(AttributeTypeXAttributeType attributeTypeXAttributeType) {
		getAttributeTypeXAttributeTypes().remove(attributeTypeXAttributeType);
		attributeTypeXAttributeType.setValueListValue(null);

		return attributeTypeXAttributeType;
	}

	public List<AttributeValueDomain> getAttributeValueDomains() {
		return this.attributeValueDomains;
	}

	public void setAttributeValueDomains(List<AttributeValueDomain> attributeValueDomains) {
		this.attributeValueDomains = attributeValueDomains;
	}

	public AttributeValueDomain addAttributeValueDomain(AttributeValueDomain attributeValueDomain) {
		getAttributeValueDomains().add(attributeValueDomain);
		attributeValueDomain.setValueListValue(this);

		return attributeValueDomain;
	}

	public AttributeValueDomain removeAttributeValueDomain(AttributeValueDomain attributeValueDomain) {
		getAttributeValueDomains().remove(attributeValueDomain);
		attributeValueDomain.setValueListValue(null);

		return attributeValueDomain;
	}

	public List<RelationshipType> getRelationshipTypes1() {
		return this.relationshipTypes1;
	}

	public void setRelationshipTypes1(List<RelationshipType> relationshipTypes1) {
		this.relationshipTypes1 = relationshipTypes1;
	}

	public RelationshipType addRelationshipTypes1(RelationshipType relationshipTypes1) {
		getRelationshipTypes1().add(relationshipTypes1);
		relationshipTypes1.setValueListValue1(this);

		return relationshipTypes1;
	}

	public RelationshipType removeRelationshipTypes1(RelationshipType relationshipTypes1) {
		getRelationshipTypes1().remove(relationshipTypes1);
		relationshipTypes1.setValueListValue1(null);

		return relationshipTypes1;
	}

	public List<RelationshipType> getRelationshipTypes2() {
		return this.relationshipTypes2;
	}

	public void setRelationshipTypes2(List<RelationshipType> relationshipTypes2) {
		this.relationshipTypes2 = relationshipTypes2;
	}

	public RelationshipType addRelationshipTypes2(RelationshipType relationshipTypes2) {
		getRelationshipTypes2().add(relationshipTypes2);
		relationshipTypes2.setValueListValue2(this);

		return relationshipTypes2;
	}

	public RelationshipType removeRelationshipTypes2(RelationshipType relationshipTypes2) {
		getRelationshipTypes2().remove(relationshipTypes2);
		relationshipTypes2.setValueListValue2(null);

		return relationshipTypes2;
	}

	public List<TopicCharacteristic> getTopicCharacteristics() {
		return this.topicCharacteristics;
	}

	public void setTopicCharacteristics(List<TopicCharacteristic> topicCharacteristics) {
		this.topicCharacteristics = topicCharacteristics;
	}

	public TopicCharacteristic addTopicCharacteristic(TopicCharacteristic topicCharacteristic) {
		getTopicCharacteristics().add(topicCharacteristic);
		topicCharacteristic.setValueListValue(this);

		return topicCharacteristic;
	}

	public TopicCharacteristic removeTopicCharacteristic(TopicCharacteristic topicCharacteristic) {
		getTopicCharacteristics().remove(topicCharacteristic);
		topicCharacteristic.setValueListValue(null);

		return topicCharacteristic;
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

	public ValueList getValueList() {
		return this.valueList;
	}

	public void setValueList(ValueList valueList) {
		this.valueList = valueList;
	}

	public List<ValueListValuesXValueListValue> getValueListValuesXValueListValues1() {
		return this.valueListValuesXValueListValues1;
	}

	public void setValueListValuesXValueListValues1(List<ValueListValuesXValueListValue> valueListValuesXValueListValues1) {
		this.valueListValuesXValueListValues1 = valueListValuesXValueListValues1;
	}

	public ValueListValuesXValueListValue addValueListValuesXValueListValues1(ValueListValuesXValueListValue valueListValuesXValueListValues1) {
		getValueListValuesXValueListValues1().add(valueListValuesXValueListValues1);
		valueListValuesXValueListValues1.setValueListValue1(this);

		return valueListValuesXValueListValues1;
	}

	public ValueListValuesXValueListValue removeValueListValuesXValueListValues1(ValueListValuesXValueListValue valueListValuesXValueListValues1) {
		getValueListValuesXValueListValues1().remove(valueListValuesXValueListValues1);
		valueListValuesXValueListValues1.setValueListValue1(null);

		return valueListValuesXValueListValues1;
	}

	public List<ValueListValuesXValueListValue> getValueListValuesXValueListValues2() {
		return this.valueListValuesXValueListValues2;
	}

	public void setValueListValuesXValueListValues2(List<ValueListValuesXValueListValue> valueListValuesXValueListValues2) {
		this.valueListValuesXValueListValues2 = valueListValuesXValueListValues2;
	}

	public ValueListValuesXValueListValue addValueListValuesXValueListValues2(ValueListValuesXValueListValue valueListValuesXValueListValues2) {
		getValueListValuesXValueListValues2().add(valueListValuesXValueListValues2);
		valueListValuesXValueListValues2.setValueListValue2(this);

		return valueListValuesXValueListValues2;
	}

	public ValueListValuesXValueListValue removeValueListValuesXValueListValues2(ValueListValuesXValueListValue valueListValuesXValueListValues2) {
		getValueListValuesXValueListValues2().remove(valueListValuesXValueListValues2);
		valueListValuesXValueListValues2.setValueListValue2(null);

		return valueListValuesXValueListValues2;
	}

	public List<ValueListValuesXValueListValue> getValueListValuesXValueListValues3() {
		return this.valueListValuesXValueListValues3;
	}

	public void setValueListValuesXValueListValues3(List<ValueListValuesXValueListValue> valueListValuesXValueListValues3) {
		this.valueListValuesXValueListValues3 = valueListValuesXValueListValues3;
	}

	public ValueListValuesXValueListValue addValueListValuesXValueListValues3(ValueListValuesXValueListValue valueListValuesXValueListValues3) {
		getValueListValuesXValueListValues3().add(valueListValuesXValueListValues3);
		valueListValuesXValueListValues3.setValueListValue3(this);

		return valueListValuesXValueListValues3;
	}

	public ValueListValuesXValueListValue removeValueListValuesXValueListValues3(ValueListValuesXValueListValue valueListValuesXValueListValues3) {
		getValueListValuesXValueListValues3().remove(valueListValuesXValueListValues3);
		valueListValuesXValueListValues3.setValueListValue3(null);

		return valueListValuesXValueListValues3;
	}

	public List<ValueListXValueList> getValueListXValueLists() {
		return this.valueListXValueLists;
	}

	public void setValueListXValueLists(List<ValueListXValueList> valueListXValueLists) {
		this.valueListXValueLists = valueListXValueLists;
	}

	public ValueListXValueList addValueListXValueList(ValueListXValueList valueListXValueList) {
		getValueListXValueLists().add(valueListXValueList);
		valueListXValueList.setValueListValue(this);

		return valueListXValueList;
	}

	public ValueListXValueList removeValueListXValueList(ValueListXValueList valueListXValueList) {
		getValueListXValueLists().remove(valueListXValueList);
		valueListXValueList.setValueListValue(null);

		return valueListXValueList;
	}

}