package de.btu.openinfra.backend.db.jpa.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the attribute_type_group database table.
 * 
 */
@Entity
@Table(name="attribute_type_group")
@NamedQueries({
	@NamedQuery(name="AttributeTypeGroup.findAll", 
		query="SELECT a FROM AttributeTypeGroup a"),
	@NamedQuery(name="AttributeTypeGroup.count",
		query="SELECT COUNT(a) FROM AttributeTypeGroup a")
})

public class AttributeTypeGroup extends OpenInfraModelObject
    implements Serializable {
	private static final long serialVersionUID = 1L;

	//bi-directional many-to-one association to AttributeTypeGroup
	@ManyToOne
	@JoinColumn(name="subgroup_of")
	private AttributeTypeGroup attributeTypeGroup;

	//bi-directional many-to-one association to AttributeTypeGroup
	@OneToMany(mappedBy="attributeTypeGroup")
	private List<AttributeTypeGroup> attributeTypeGroups;

	//bi-directional many-to-one association to PtFreeText
	@ManyToOne
	@JoinColumn(name="description")
	private PtFreeText ptFreeText1;

	//bi-directional many-to-one association to PtFreeText
	@ManyToOne
	@JoinColumn(name="name")
	private PtFreeText ptFreeText2;

	//bi-directional many-to-one association to AttributeTypeGroupToTopicCharacteristic
	@OneToMany(mappedBy="attributeTypeGroup")
	private List<AttributeTypeGroupToTopicCharacteristic> attributeTypeGroupToTopicCharacteristics;

	//bi-directional many-to-one association to AttributeTypeToAttributeTypeGroup
	@OneToMany(mappedBy="attributeTypeGroup")
	private List<AttributeTypeToAttributeTypeGroup> attributeTypeToAttributeTypeGroups;

	public AttributeTypeGroup() {
	}

	public AttributeTypeGroup getAttributeTypeGroup() {
		return this.attributeTypeGroup;
	}

	public void setAttributeTypeGroup(AttributeTypeGroup attributeTypeGroup) {
		this.attributeTypeGroup = attributeTypeGroup;
	}

	public List<AttributeTypeGroup> getAttributeTypeGroups() {
		return this.attributeTypeGroups;
	}

	public void setAttributeTypeGroups(List<AttributeTypeGroup> attributeTypeGroups) {
		this.attributeTypeGroups = attributeTypeGroups;
	}

	public AttributeTypeGroup addAttributeTypeGroup(AttributeTypeGroup attributeTypeGroup) {
		getAttributeTypeGroups().add(attributeTypeGroup);
		attributeTypeGroup.setAttributeTypeGroup(this);

		return attributeTypeGroup;
	}

	public AttributeTypeGroup removeAttributeTypeGroup(AttributeTypeGroup attributeTypeGroup) {
		getAttributeTypeGroups().remove(attributeTypeGroup);
		attributeTypeGroup.setAttributeTypeGroup(null);

		return attributeTypeGroup;
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

	public List<AttributeTypeGroupToTopicCharacteristic> getAttributeTypeGroupToTopicCharacteristics() {
		return this.attributeTypeGroupToTopicCharacteristics;
	}

	public void setAttributeTypeGroupToTopicCharacteristics(List<AttributeTypeGroupToTopicCharacteristic> attributeTypeGroupToTopicCharacteristics) {
		this.attributeTypeGroupToTopicCharacteristics = attributeTypeGroupToTopicCharacteristics;
	}

	public AttributeTypeGroupToTopicCharacteristic addAttributeTypeGroupToTopicCharacteristic(AttributeTypeGroupToTopicCharacteristic attributeTypeGroupToTopicCharacteristic) {
		getAttributeTypeGroupToTopicCharacteristics().add(attributeTypeGroupToTopicCharacteristic);
		attributeTypeGroupToTopicCharacteristic.setAttributeTypeGroup(this);

		return attributeTypeGroupToTopicCharacteristic;
	}

	public AttributeTypeGroupToTopicCharacteristic removeAttributeTypeGroupToTopicCharacteristic(AttributeTypeGroupToTopicCharacteristic attributeTypeGroupToTopicCharacteristic) {
		getAttributeTypeGroupToTopicCharacteristics().remove(attributeTypeGroupToTopicCharacteristic);
		attributeTypeGroupToTopicCharacteristic.setAttributeTypeGroup(null);

		return attributeTypeGroupToTopicCharacteristic;
	}

	public List<AttributeTypeToAttributeTypeGroup> getAttributeTypeToAttributeTypeGroups() {
		return this.attributeTypeToAttributeTypeGroups;
	}

	public void setAttributeTypeToAttributeTypeGroups(List<AttributeTypeToAttributeTypeGroup> attributeTypeToAttributeTypeGroups) {
		this.attributeTypeToAttributeTypeGroups = attributeTypeToAttributeTypeGroups;
	}

	public AttributeTypeToAttributeTypeGroup addAttributeTypeToAttributeTypeGroup(AttributeTypeToAttributeTypeGroup attributeTypeToAttributeTypeGroup) {
		getAttributeTypeToAttributeTypeGroups().add(attributeTypeToAttributeTypeGroup);
		attributeTypeToAttributeTypeGroup.setAttributeTypeGroup(this);

		return attributeTypeToAttributeTypeGroup;
	}

	public AttributeTypeToAttributeTypeGroup removeAttributeTypeToAttributeTypeGroup(AttributeTypeToAttributeTypeGroup attributeTypeToAttributeTypeGroup) {
		getAttributeTypeToAttributeTypeGroups().remove(attributeTypeToAttributeTypeGroup);
		attributeTypeToAttributeTypeGroup.setAttributeTypeGroup(null);

		return attributeTypeToAttributeTypeGroup;
	}

}