package de.btu.openinfra.backend.db.jpa.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the pt_free_text database table.
 *
 */
@Entity
@Table(name="pt_free_text")
@NamedQuery(name="PtFreeText.findAll", query="SELECT p FROM PtFreeText p")
public class PtFreeText extends OpenInfraModelObject implements Serializable {
	private static final long serialVersionUID = 1L;

	//bi-directional many-to-one association to AttributeType
	@OneToMany(mappedBy="ptFreeText1")
	private List<AttributeType> attributeTypes1;

	//bi-directional many-to-one association to AttributeType
	@OneToMany(mappedBy="ptFreeText2")
	private List<AttributeType> attributeTypes2;

	//bi-directional many-to-one association to AttributeTypeGroup
	@OneToMany(mappedBy="ptFreeText1")
	private List<AttributeTypeGroup> attributeTypeGroups1;

	//bi-directional many-to-one association to AttributeTypeGroup
	@OneToMany(mappedBy="ptFreeText2")
	private List<AttributeTypeGroup> attributeTypeGroups2;

	//bi-directional many-to-one association to AttributeValueValue
	@OneToMany(mappedBy="ptFreeText")
	private List<AttributeValueValue> attributeValueValues;

	//bi-directional many-to-one association to LocalizedCharacterString
	@OneToMany(mappedBy="ptFreeText",
	           cascade={CascadeType.MERGE,CascadeType.REFRESH})
	private List<LocalizedCharacterString> localizedCharacterStrings;

	//bi-directional many-to-one association to Project
	@OneToMany(mappedBy="ptFreeText1")
	private List<Project> projects1;

	//bi-directional many-to-one association to Project
	@OneToMany(mappedBy="ptFreeText2")
	private List<Project> projects2;

	//bi-directional many-to-one association to TopicCharacteristic
	@OneToMany(mappedBy="ptFreeText")
	private List<TopicCharacteristic> topicCharacteristics;

	//bi-directional many-to-one association to ValueList
	@OneToMany(mappedBy="ptFreeText1")
	private List<ValueList> valueLists1;

	//bi-directional many-to-one association to ValueList
	@OneToMany(mappedBy="ptFreeText2")
	private List<ValueList> valueLists2;

	//bi-directional many-to-one association to ValueListValue
	@OneToMany(mappedBy="ptFreeText1")
	private List<ValueListValue> valueListValues1;

	//bi-directional many-to-one association to ValueListValue
	@OneToMany(mappedBy="ptFreeText2")
	private List<ValueListValue> valueListValues2;

	public PtFreeText() {
	}

	public List<AttributeType> getAttributeTypes1() {
		return this.attributeTypes1;
	}

	public void setAttributeTypes1(List<AttributeType> attributeTypes1) {
		this.attributeTypes1 = attributeTypes1;
	}

	public AttributeType addAttributeTypes1(AttributeType attributeTypes1) {
		getAttributeTypes1().add(attributeTypes1);
		attributeTypes1.setPtFreeText1(this);

		return attributeTypes1;
	}

	public AttributeType removeAttributeTypes1(AttributeType attributeTypes1) {
		getAttributeTypes1().remove(attributeTypes1);
		attributeTypes1.setPtFreeText1(null);

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
		attributeTypes2.setPtFreeText2(this);

		return attributeTypes2;
	}

	public AttributeType removeAttributeTypes2(AttributeType attributeTypes2) {
		getAttributeTypes2().remove(attributeTypes2);
		attributeTypes2.setPtFreeText2(null);

		return attributeTypes2;
	}

	public List<AttributeTypeGroup> getAttributeTypeGroups1() {
		return this.attributeTypeGroups1;
	}

	public void setAttributeTypeGroups1(List<AttributeTypeGroup> attributeTypeGroups1) {
		this.attributeTypeGroups1 = attributeTypeGroups1;
	}

	public AttributeTypeGroup addAttributeTypeGroups1(AttributeTypeGroup attributeTypeGroups1) {
		getAttributeTypeGroups1().add(attributeTypeGroups1);
		attributeTypeGroups1.setPtFreeText1(this);

		return attributeTypeGroups1;
	}

	public AttributeTypeGroup removeAttributeTypeGroups1(AttributeTypeGroup attributeTypeGroups1) {
		getAttributeTypeGroups1().remove(attributeTypeGroups1);
		attributeTypeGroups1.setPtFreeText1(null);

		return attributeTypeGroups1;
	}

	public List<AttributeTypeGroup> getAttributeTypeGroups2() {
		return this.attributeTypeGroups2;
	}

	public void setAttributeTypeGroups2(List<AttributeTypeGroup> attributeTypeGroups2) {
		this.attributeTypeGroups2 = attributeTypeGroups2;
	}

	public AttributeTypeGroup addAttributeTypeGroups2(AttributeTypeGroup attributeTypeGroups2) {
		getAttributeTypeGroups2().add(attributeTypeGroups2);
		attributeTypeGroups2.setPtFreeText2(this);

		return attributeTypeGroups2;
	}

	public AttributeTypeGroup removeAttributeTypeGroups2(AttributeTypeGroup attributeTypeGroups2) {
		getAttributeTypeGroups2().remove(attributeTypeGroups2);
		attributeTypeGroups2.setPtFreeText2(null);

		return attributeTypeGroups2;
	}

	public List<AttributeValueValue> getAttributeValueValues() {
		return this.attributeValueValues;
	}

	public void setAttributeValueValues(List<AttributeValueValue> attributeValueValues) {
		this.attributeValueValues = attributeValueValues;
	}

	public AttributeValueValue addAttributeValueValue(AttributeValueValue attributeValueValue) {
		getAttributeValueValues().add(attributeValueValue);
		attributeValueValue.setPtFreeText(this);

		return attributeValueValue;
	}

	public AttributeValueValue removeAttributeValueValue(AttributeValueValue attributeValueValue) {
		getAttributeValueValues().remove(attributeValueValue);
		attributeValueValue.setPtFreeText(null);

		return attributeValueValue;
	}

	public List<LocalizedCharacterString> getLocalizedCharacterStrings() {
		return this.localizedCharacterStrings;
	}

	public void setLocalizedCharacterStrings(List<LocalizedCharacterString> localizedCharacterStrings) {
		this.localizedCharacterStrings = localizedCharacterStrings;
	}

	public LocalizedCharacterString addLocalizedCharacterString(LocalizedCharacterString localizedCharacterString) {
		getLocalizedCharacterStrings().add(localizedCharacterString);
		localizedCharacterString.setPtFreeText(this);

		return localizedCharacterString;
	}

	public LocalizedCharacterString removeLocalizedCharacterString(LocalizedCharacterString localizedCharacterString) {
		getLocalizedCharacterStrings().remove(localizedCharacterString);
		localizedCharacterString.setPtFreeText(null);

		return localizedCharacterString;
	}

	public List<Project> getProjects1() {
		return this.projects1;
	}

	public void setProjects1(List<Project> projects1) {
		this.projects1 = projects1;
	}

	public Project addProjects1(Project projects1) {
		getProjects1().add(projects1);
		projects1.setPtFreeText1(this);

		return projects1;
	}

	public Project removeProjects1(Project projects1) {
		getProjects1().remove(projects1);
		projects1.setPtFreeText1(null);

		return projects1;
	}

	public List<Project> getProjects2() {
		return this.projects2;
	}

	public void setProjects2(List<Project> projects2) {
		this.projects2 = projects2;
	}

	public Project addProjects2(Project projects2) {
		getProjects2().add(projects2);
		projects2.setPtFreeText2(this);

		return projects2;
	}

	public Project removeProjects2(Project projects2) {
		getProjects2().remove(projects2);
		projects2.setPtFreeText2(null);

		return projects2;
	}

	public List<TopicCharacteristic> getTopicCharacteristics() {
		return this.topicCharacteristics;
	}

	public void setTopicCharacteristics(List<TopicCharacteristic> topicCharacteristics) {
		this.topicCharacteristics = topicCharacteristics;
	}

	public TopicCharacteristic addTopicCharacteristic(TopicCharacteristic topicCharacteristic) {
		getTopicCharacteristics().add(topicCharacteristic);
		topicCharacteristic.setPtFreeText(this);

		return topicCharacteristic;
	}

	public TopicCharacteristic removeTopicCharacteristic(TopicCharacteristic topicCharacteristic) {
		getTopicCharacteristics().remove(topicCharacteristic);
		topicCharacteristic.setPtFreeText(null);

		return topicCharacteristic;
	}

	public List<ValueList> getValueLists1() {
		return this.valueLists1;
	}

	public void setValueLists1(List<ValueList> valueLists1) {
		this.valueLists1 = valueLists1;
	}

	public ValueList addValueLists1(ValueList valueLists1) {
		getValueLists1().add(valueLists1);
		valueLists1.setPtFreeText1(this);

		return valueLists1;
	}

	public ValueList removeValueLists1(ValueList valueLists1) {
		getValueLists1().remove(valueLists1);
		valueLists1.setPtFreeText1(null);

		return valueLists1;
	}

	public List<ValueList> getValueLists2() {
		return this.valueLists2;
	}

	public void setValueLists2(List<ValueList> valueLists2) {
		this.valueLists2 = valueLists2;
	}

	public ValueList addValueLists2(ValueList valueLists2) {
		getValueLists2().add(valueLists2);
		valueLists2.setPtFreeText2(this);

		return valueLists2;
	}

	public ValueList removeValueLists2(ValueList valueLists2) {
		getValueLists2().remove(valueLists2);
		valueLists2.setPtFreeText2(null);

		return valueLists2;
	}

	public List<ValueListValue> getValueListValues1() {
		return this.valueListValues1;
	}

	public void setValueListValues1(List<ValueListValue> valueListValues1) {
		this.valueListValues1 = valueListValues1;
	}

	public ValueListValue addValueListValues1(ValueListValue valueListValues1) {
		getValueListValues1().add(valueListValues1);
		valueListValues1.setPtFreeText1(this);

		return valueListValues1;
	}

	public ValueListValue removeValueListValues1(ValueListValue valueListValues1) {
		getValueListValues1().remove(valueListValues1);
		valueListValues1.setPtFreeText1(null);

		return valueListValues1;
	}

	public List<ValueListValue> getValueListValues2() {
		return this.valueListValues2;
	}

	public void setValueListValues2(List<ValueListValue> valueListValues2) {
		this.valueListValues2 = valueListValues2;
	}

	public ValueListValue addValueListValues2(ValueListValue valueListValues2) {
		getValueListValues2().add(valueListValues2);
		valueListValues2.setPtFreeText2(this);

		return valueListValues2;
	}

	public ValueListValue removeValueListValues2(ValueListValue valueListValues2) {
		getValueListValues2().remove(valueListValues2);
		valueListValues2.setPtFreeText2(null);

		return valueListValues2;
	}

}