package de.btu.openinfra.backend.db.jpa.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the attribute_type_to_attribute_type_group database table.
 * 
 */
@Entity
@Table(name="attribute_type_to_attribute_type_group")
@NamedQueries({
	@NamedQuery(name="AttributeTypeToAttributeTypeGroup.findAll", 
			query="SELECT a FROM AttributeTypeToAttributeTypeGroup a"),
	@NamedQuery(name="AttributeTypeToAttributeTypeGroup.count", 
			query="SELECT COUNT(a) FROM AttributeTypeToAttributeTypeGroup a"),			
	@NamedQuery(
			name="AttributeTypeToAttributeTypeGroup.findByAttributeTypeGroup",
			query="SELECT a "
					+ "FROM AttributeTypeToAttributeTypeGroup a "
					+ "WHERE a.attributeTypeGroup = :value"),
	@NamedQuery(name="AttributeTypeToAttributeTypeGroup.findByAttributeType",
			query="SELECT a "
					+ "FROM AttributeTypeToAttributeTypeGroup a "
					+ "WHERE a.attributeType = :value"),
	@NamedQuery(name="AttributeTypeToAttributeTypeGroup.countByAttributeType",
		query="SELECT COUNT(a) "
				+ "FROM AttributeTypeToAttributeTypeGroup a "
				+ "WHERE a.attributeType = :value")
})
public class AttributeTypeToAttributeTypeGroup implements 
	Serializable, OpenInfraModelObject {
	private static final long serialVersionUID = 1L;

	@Id
	private UUID id;

	@Column(name="\"order\"")
	private Integer order;

	//bi-directional many-to-one association to AttributeType
	@ManyToOne
	@JoinColumn(name="attribute_type_id")
	private AttributeType attributeType;

	//bi-directional many-to-one association to AttributeTypeGroup
	@ManyToOne
	@JoinColumn(name="attribute_type_group_id")
	private AttributeTypeGroup attributeTypeGroup;

	//bi-directional many-to-one association to AttributeTypeGroupToTopicCharacteristic
	@ManyToOne
	@JoinColumn(name="attribute_type_group_to_topic_characteristic_id")
	private AttributeTypeGroupToTopicCharacteristic attributeTypeGroupToTopicCharacteristic;

	//bi-directional many-to-one association to Multiplicity
	@ManyToOne
	@JoinColumn(name="multiplicity")
	private Multiplicity multiplicityBean;

	//bi-directional many-to-one association to ValueListValue
	@ManyToOne
	@JoinColumn(name="default_value")
	private ValueListValue valueListValue;

	//bi-directional many-to-one association to AttributeValue
	@OneToMany(mappedBy="attributeTypeToAttributeTypeGroup")
	private List<AttributeValue> attributeValues;

	//bi-directional many-to-one association to AttributeValueDomain
	@OneToMany(mappedBy="attributeTypeToAttributeTypeGroup")
	private List<AttributeValueDomain> attributeValueDomains;

	//bi-directional many-to-one association to AttributeValueGeom
	@OneToMany(mappedBy="attributeTypeToAttributeTypeGroup")
	private List<AttributeValueGeom> attributeValueGeoms;

	//bi-directional many-to-one association to AttributeValueGeomz
	@OneToMany(mappedBy="attributeTypeToAttributeTypeGroup")
	private List<AttributeValueGeomz> attributeValueGeomzs;

	//bi-directional many-to-one association to AttributeValueValue
	@OneToMany(mappedBy="attributeTypeToAttributeTypeGroup")
	private List<AttributeValueValue> attributeValueValues;

	public AttributeTypeToAttributeTypeGroup() {
	}

	public UUID getId() {
		return this.id;
	}

	@Override
	public void setId(UUID id) {
		this.id = id;
	}

	public Integer getOrder() {
		return this.order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public AttributeType getAttributeType() {
		return this.attributeType;
	}

	public void setAttributeType(AttributeType attributeType) {
		this.attributeType = attributeType;
	}

	public AttributeTypeGroup getAttributeTypeGroup() {
		return this.attributeTypeGroup;
	}

	public void setAttributeTypeGroup(AttributeTypeGroup attributeTypeGroup) {
		this.attributeTypeGroup = attributeTypeGroup;
	}

	public AttributeTypeGroupToTopicCharacteristic getAttributeTypeGroupToTopicCharacteristic() {
		return this.attributeTypeGroupToTopicCharacteristic;
	}

	public void setAttributeTypeGroupToTopicCharacteristic(AttributeTypeGroupToTopicCharacteristic attributeTypeGroupToTopicCharacteristic) {
		this.attributeTypeGroupToTopicCharacteristic = attributeTypeGroupToTopicCharacteristic;
	}

	public Multiplicity getMultiplicityBean() {
		return this.multiplicityBean;
	}

	public void setMultiplicityBean(Multiplicity multiplicityBean) {
		this.multiplicityBean = multiplicityBean;
	}

	public ValueListValue getValueListValue() {
		return this.valueListValue;
	}

	public void setValueListValue(ValueListValue valueListValue) {
		this.valueListValue = valueListValue;
	}

	public List<AttributeValue> getAttributeValues() {
		return this.attributeValues;
	}

	public void setAttributeValues(List<AttributeValue> attributeValues) {
		this.attributeValues = attributeValues;
	}

	public AttributeValue addAttributeValue(AttributeValue attributeValue) {
		getAttributeValues().add(attributeValue);
		attributeValue.setAttributeTypeToAttributeTypeGroup(this);

		return attributeValue;
	}

	public AttributeValue removeAttributeValue(AttributeValue attributeValue) {
		getAttributeValues().remove(attributeValue);
		attributeValue.setAttributeTypeToAttributeTypeGroup(null);

		return attributeValue;
	}

	public List<AttributeValueDomain> getAttributeValueDomains() {
		return this.attributeValueDomains;
	}

	public void setAttributeValueDomains(List<AttributeValueDomain> attributeValueDomains) {
		this.attributeValueDomains = attributeValueDomains;
	}

	public AttributeValueDomain addAttributeValueDomain(AttributeValueDomain attributeValueDomain) {
		getAttributeValueDomains().add(attributeValueDomain);
		attributeValueDomain.setAttributeTypeToAttributeTypeGroup(this);

		return attributeValueDomain;
	}

	public AttributeValueDomain removeAttributeValueDomain(AttributeValueDomain attributeValueDomain) {
		getAttributeValueDomains().remove(attributeValueDomain);
		attributeValueDomain.setAttributeTypeToAttributeTypeGroup(null);

		return attributeValueDomain;
	}

	public List<AttributeValueGeom> getAttributeValueGeoms() {
		return this.attributeValueGeoms;
	}

	public void setAttributeValueGeoms(List<AttributeValueGeom> attributeValueGeoms) {
		this.attributeValueGeoms = attributeValueGeoms;
	}

	public AttributeValueGeom addAttributeValueGeom(AttributeValueGeom attributeValueGeom) {
		getAttributeValueGeoms().add(attributeValueGeom);
		attributeValueGeom.setAttributeTypeToAttributeTypeGroup(this);

		return attributeValueGeom;
	}

	public AttributeValueGeom removeAttributeValueGeom(AttributeValueGeom attributeValueGeom) {
		getAttributeValueGeoms().remove(attributeValueGeom);
		attributeValueGeom.setAttributeTypeToAttributeTypeGroup(null);

		return attributeValueGeom;
	}

	public List<AttributeValueGeomz> getAttributeValueGeomzs() {
		return this.attributeValueGeomzs;
	}

	public void setAttributeValueGeomzs(List<AttributeValueGeomz> attributeValueGeomzs) {
		this.attributeValueGeomzs = attributeValueGeomzs;
	}

	public AttributeValueGeomz addAttributeValueGeomz(AttributeValueGeomz attributeValueGeomz) {
		getAttributeValueGeomzs().add(attributeValueGeomz);
		attributeValueGeomz.setAttributeTypeToAttributeTypeGroup(this);

		return attributeValueGeomz;
	}

	public AttributeValueGeomz removeAttributeValueGeomz(AttributeValueGeomz attributeValueGeomz) {
		getAttributeValueGeomzs().remove(attributeValueGeomz);
		attributeValueGeomz.setAttributeTypeToAttributeTypeGroup(null);

		return attributeValueGeomz;
	}

	public List<AttributeValueValue> getAttributeValueValues() {
		return this.attributeValueValues;
	}

	public void setAttributeValueValues(List<AttributeValueValue> attributeValueValues) {
		this.attributeValueValues = attributeValueValues;
	}

	public AttributeValueValue addAttributeValueValue(AttributeValueValue attributeValueValue) {
		getAttributeValueValues().add(attributeValueValue);
		attributeValueValue.setAttributeTypeToAttributeTypeGroup(this);

		return attributeValueValue;
	}

	public AttributeValueValue removeAttributeValueValue(AttributeValueValue attributeValueValue) {
		getAttributeValueValues().remove(attributeValueValue);
		attributeValueValue.setAttributeTypeToAttributeTypeGroup(null);

		return attributeValueValue;
	}

}