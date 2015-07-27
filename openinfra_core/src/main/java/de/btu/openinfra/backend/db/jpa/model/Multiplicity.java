package de.btu.openinfra.backend.db.jpa.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;


/**
 * The persistent class for the multiplicity database table.
 * 
 */
@Entity

@NamedQueries({
    @NamedQuery(name="Multiplicity.count",
        query="SELECT COUNT(m) FROM Multiplicity m "),
    @NamedQuery(name="Multiplicity.findAll",
        query="SELECT m FROM Multiplicity m")
})
public class Multiplicity implements Serializable, OpenInfraModelObject {
	private static final long serialVersionUID = 1L;

	@Id
	private UUID id;

	@Column(name="max_value")
	private Integer maxValue;

	@Column(name="min_value")
	private Integer minValue;

	//bi-directional many-to-one association to AttributeTypeGroupToTopicCharacteristic
	@OneToMany(mappedBy="multiplicityBean")
	private List<AttributeTypeGroupToTopicCharacteristic> attributeTypeGroupToTopicCharacteristics;

	//bi-directional many-to-one association to AttributeTypeToAttributeTypeGroup
	@OneToMany(mappedBy="multiplicityBean")
	private List<AttributeTypeToAttributeTypeGroup> attributeTypeToAttributeTypeGroups;

	//bi-directional many-to-one association to RelationshipTypeToTopicCharacteristic
	@OneToMany(mappedBy="multiplicityBean")
	private List<RelationshipTypeToTopicCharacteristic> relationshipTypeToTopicCharacteristics;

	public Multiplicity() {
	}

	@Override
    public UUID getId() {
		return this.id;
	}

	@Override
	public void setId(UUID id) {
		this.id = id;
	}

	public Integer getMaxValue() {
		return this.maxValue;
	}

	public void setMaxValue(Integer maxValue) {
		this.maxValue = maxValue;
	}

	public Integer getMinValue() {
		return this.minValue;
	}

	public void setMinValue(Integer minValue) {
		this.minValue = minValue;
	}

	public List<AttributeTypeGroupToTopicCharacteristic> getAttributeTypeGroupToTopicCharacteristics() {
		return this.attributeTypeGroupToTopicCharacteristics;
	}

	public void setAttributeTypeGroupToTopicCharacteristics(List<AttributeTypeGroupToTopicCharacteristic> attributeTypeGroupToTopicCharacteristics) {
		this.attributeTypeGroupToTopicCharacteristics = attributeTypeGroupToTopicCharacteristics;
	}

	public AttributeTypeGroupToTopicCharacteristic addAttributeTypeGroupToTopicCharacteristic(AttributeTypeGroupToTopicCharacteristic attributeTypeGroupToTopicCharacteristic) {
		getAttributeTypeGroupToTopicCharacteristics().add(attributeTypeGroupToTopicCharacteristic);
		attributeTypeGroupToTopicCharacteristic.setMultiplicityBean(this);

		return attributeTypeGroupToTopicCharacteristic;
	}

	public AttributeTypeGroupToTopicCharacteristic removeAttributeTypeGroupToTopicCharacteristic(AttributeTypeGroupToTopicCharacteristic attributeTypeGroupToTopicCharacteristic) {
		getAttributeTypeGroupToTopicCharacteristics().remove(attributeTypeGroupToTopicCharacteristic);
		attributeTypeGroupToTopicCharacteristic.setMultiplicityBean(null);

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
		attributeTypeToAttributeTypeGroup.setMultiplicityBean(this);

		return attributeTypeToAttributeTypeGroup;
	}

	public AttributeTypeToAttributeTypeGroup removeAttributeTypeToAttributeTypeGroup(AttributeTypeToAttributeTypeGroup attributeTypeToAttributeTypeGroup) {
		getAttributeTypeToAttributeTypeGroups().remove(attributeTypeToAttributeTypeGroup);
		attributeTypeToAttributeTypeGroup.setMultiplicityBean(null);

		return attributeTypeToAttributeTypeGroup;
	}

	public List<RelationshipTypeToTopicCharacteristic> getRelationshipTypeToTopicCharacteristics() {
		return this.relationshipTypeToTopicCharacteristics;
	}

	public void setRelationshipTypeToTopicCharacteristics(List<RelationshipTypeToTopicCharacteristic> relationshipTypeToTopicCharacteristics) {
		this.relationshipTypeToTopicCharacteristics = relationshipTypeToTopicCharacteristics;
	}

	public RelationshipTypeToTopicCharacteristic addRelationshipTypeToTopicCharacteristic(RelationshipTypeToTopicCharacteristic relationshipTypeToTopicCharacteristic) {
		getRelationshipTypeToTopicCharacteristics().add(relationshipTypeToTopicCharacteristic);
		relationshipTypeToTopicCharacteristic.setMultiplicityBean(this);

		return relationshipTypeToTopicCharacteristic;
	}

	public RelationshipTypeToTopicCharacteristic removeRelationshipTypeToTopicCharacteristic(RelationshipTypeToTopicCharacteristic relationshipTypeToTopicCharacteristic) {
		getRelationshipTypeToTopicCharacteristics().remove(relationshipTypeToTopicCharacteristic);
		relationshipTypeToTopicCharacteristic.setMultiplicityBean(null);

		return relationshipTypeToTopicCharacteristic;
	}

}