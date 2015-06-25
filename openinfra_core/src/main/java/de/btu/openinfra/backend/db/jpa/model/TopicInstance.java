package de.btu.openinfra.backend.db.jpa.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the topic_instance database table.
 * 
 */
@Entity
@Table(name="topic_instance")
@NamedQueries({
	@NamedQuery(name="TopicInstance.count", 
			query="SELECT COUNT(t) FROM TopicInstance t "),
	@NamedQuery(name="TopicInstance.countByTopicCharacteristic", 
			query="SELECT COUNT(t) "
					+ "FROM TopicInstance t "
					+ "WHERE t.topicCharacteristic = :value"),
	@NamedQuery(name="TopicInstance.findAll", 
			query="SELECT t FROM TopicInstance t"),
	@NamedQuery(name="TopicInstance.findByTopicCharacteristic", 
			query="SELECT t "
					+ "FROM TopicInstance t "
					+ "WHERE t.topicCharacteristic = :value "),
	@NamedQuery(name="TopicInstance.filterLikeAttributeValueValue",
			query="SELECT t "
					+ "FROM TopicInstance t "
					+ "WHERE t.topicCharacteristic = :tc "
					+ "AND t.id = ANY ( "
						+ "SELECT a.topicInstance.id "
						+ "FROM AttributeValueValue a "
						+ "WHERE a.ptFreeText.id = ANY ( "
								+ "SELECT l.ptFreeText.id "
								+ "FROM LocalizedCharacterString l "
								+ "WHERE l.ptLocale = :ptl "
								+ "AND l.freeText LIKE :filter) )"),
    @NamedQuery(name="TopicInstance.findByTopicCharacteristicWithGeomz",
            query="SELECT t "
                    + "FROM AttributeValueGeomz a INNER JOIN a.topicInstance t "
                    + "WHERE t.topicCharacteristic = :value"),
    @NamedQuery(name="TopicInstance.countByTopicGeomz", 
            query="SELECT COUNT(t) "
                    + "FROM AttributeValueGeomz a INNER JOIN a.topicInstance t "
                    + "WHERE t.topicCharacteristic = :value")
})
public class TopicInstance implements Serializable, OpenInfraModelObject {
	private static final long serialVersionUID = 1L;

	@Id
	private UUID id;

	//bi-directional many-to-one association to AttributeValue
	@OneToMany(mappedBy="topicInstance")
	private List<AttributeValue> attributeValues;

	//bi-directional many-to-one association to AttributeValueDomain
	@OneToMany(mappedBy="topicInstance")
	private List<AttributeValueDomain> attributeValueDomains;

	//bi-directional many-to-one association to AttributeValueGeom
	@OneToMany(mappedBy="topicInstance")
	private List<AttributeValueGeom> attributeValueGeoms;

	//bi-directional many-to-one association to AttributeValueGeomz
	@OneToMany(mappedBy="topicInstance")
	private List<AttributeValueGeomz> attributeValueGeomzs;

	//bi-directional many-to-one association to AttributeValueValue
	@OneToMany(mappedBy="topicInstance", cascade=CascadeType.MERGE)
	private List<AttributeValueValue> attributeValueValues;

	//bi-directional many-to-one association to TopicCharacteristic
	@ManyToOne
	@JoinColumn(name="topic_characteristic_id")
	private TopicCharacteristic topicCharacteristic;

	//bi-directional many-to-one association to TopicInstanceXTopicInstance
	@OneToMany(mappedBy="topicInstance1Bean")
	private List<TopicInstanceXTopicInstance> topicInstanceXTopicInstances1;

	//bi-directional many-to-one association to TopicInstanceXTopicInstance
	@OneToMany(mappedBy="topicInstance2Bean")
	private List<TopicInstanceXTopicInstance> topicInstanceXTopicInstances2;

	public TopicInstance() {
	}

	public UUID getId() {
		return this.id;
	}

	@Override
	public void setId(UUID id) {
		this.id = id;
	}

	public List<AttributeValue> getAttributeValues() {
		return this.attributeValues;
	}

	public void setAttributeValues(List<AttributeValue> attributeValues) {
		this.attributeValues = attributeValues;
	}

	public AttributeValue addAttributeValue(AttributeValue attributeValue) {
		getAttributeValues().add(attributeValue);
		attributeValue.setTopicInstance(this);

		return attributeValue;
	}

	public AttributeValue removeAttributeValue(AttributeValue attributeValue) {
		getAttributeValues().remove(attributeValue);
		attributeValue.setTopicInstance(null);

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
		attributeValueDomain.setTopicInstance(this);

		return attributeValueDomain;
	}

	public AttributeValueDomain removeAttributeValueDomain(AttributeValueDomain attributeValueDomain) {
		getAttributeValueDomains().remove(attributeValueDomain);
		attributeValueDomain.setTopicInstance(null);

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
		attributeValueGeom.setTopicInstance(this);

		return attributeValueGeom;
	}

	public AttributeValueGeom removeAttributeValueGeom(AttributeValueGeom attributeValueGeom) {
		getAttributeValueGeoms().remove(attributeValueGeom);
		attributeValueGeom.setTopicInstance(null);

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
		attributeValueGeomz.setTopicInstance(this);

		return attributeValueGeomz;
	}

	public AttributeValueGeomz removeAttributeValueGeomz(AttributeValueGeomz attributeValueGeomz) {
		getAttributeValueGeomzs().remove(attributeValueGeomz);
		attributeValueGeomz.setTopicInstance(null);

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
		attributeValueValue.setTopicInstance(this);

		return attributeValueValue;
	}

	public AttributeValueValue removeAttributeValueValue(AttributeValueValue attributeValueValue) {
		getAttributeValueValues().remove(attributeValueValue);
		attributeValueValue.setTopicInstance(null);

		return attributeValueValue;
	}

	public TopicCharacteristic getTopicCharacteristic() {
		return this.topicCharacteristic;
	}

	public void setTopicCharacteristic(TopicCharacteristic topicCharacteristic) {
		this.topicCharacteristic = topicCharacteristic;
	}

	public List<TopicInstanceXTopicInstance> getTopicInstanceXTopicInstances1() {
		return this.topicInstanceXTopicInstances1;
	}

	public void setTopicInstanceXTopicInstances1(List<TopicInstanceXTopicInstance> topicInstanceXTopicInstances1) {
		this.topicInstanceXTopicInstances1 = topicInstanceXTopicInstances1;
	}

	public TopicInstanceXTopicInstance addTopicInstanceXTopicInstances1(TopicInstanceXTopicInstance topicInstanceXTopicInstances1) {
		getTopicInstanceXTopicInstances1().add(topicInstanceXTopicInstances1);
		topicInstanceXTopicInstances1.setTopicInstance1Bean(this);

		return topicInstanceXTopicInstances1;
	}

	public TopicInstanceXTopicInstance removeTopicInstanceXTopicInstances1(TopicInstanceXTopicInstance topicInstanceXTopicInstances1) {
		getTopicInstanceXTopicInstances1().remove(topicInstanceXTopicInstances1);
		topicInstanceXTopicInstances1.setTopicInstance1Bean(null);

		return topicInstanceXTopicInstances1;
	}

	public List<TopicInstanceXTopicInstance> getTopicInstanceXTopicInstances2() {
		return this.topicInstanceXTopicInstances2;
	}

	public void setTopicInstanceXTopicInstances2(List<TopicInstanceXTopicInstance> topicInstanceXTopicInstances2) {
		this.topicInstanceXTopicInstances2 = topicInstanceXTopicInstances2;
	}

	public TopicInstanceXTopicInstance addTopicInstanceXTopicInstances2(TopicInstanceXTopicInstance topicInstanceXTopicInstances2) {
		getTopicInstanceXTopicInstances2().add(topicInstanceXTopicInstances2);
		topicInstanceXTopicInstances2.setTopicInstance2Bean(this);

		return topicInstanceXTopicInstances2;
	}

	public TopicInstanceXTopicInstance removeTopicInstanceXTopicInstances2(TopicInstanceXTopicInstance topicInstanceXTopicInstances2) {
		getTopicInstanceXTopicInstances2().remove(topicInstanceXTopicInstances2);
		topicInstanceXTopicInstances2.setTopicInstance2Bean(null);

		return topicInstanceXTopicInstances2;
	}

}