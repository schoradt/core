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
 * The persistent class for the relationship_type database table.
 * 
 */
@Entity
@Table(name="relationship_type")
@NamedQueries({
	@NamedQuery(name="RelationshipType.findAll", 
			query="SELECT r FROM RelationshipType r"),
	@NamedQuery(name="RelationshipType.count", 
			query="SELECT COUNT(r) FROM RelationshipType r"),
	@NamedQuery(name="RelationshipType.findByTopicCharacteristic",
			query="SELECT rt "
					+ "FROM RelationshipType rt "
					+ "JOIN rt.relationshipTypeToTopicCharacteristics rttc "
					+ "WHERE rttc.id = ANY ( "
						+ "SELECT r.id "
						+ "FROM RelationshipTypeToTopicCharacteristic r "
						+ "WHERE r.topicCharacteristic = :value)"),
	@NamedQuery(name="RelationshipType.countByTopicCharacteristic",
	query="SELECT COUNT(rt) "
			+ "FROM RelationshipType rt "
			+ "JOIN rt.relationshipTypeToTopicCharacteristics rttc "
			+ "WHERE rttc.id = ANY ( "
				+ "SELECT r.id "
				+ "FROM RelationshipTypeToTopicCharacteristic r "
				+ "WHERE r.topicCharacteristic = :value)")
})

public class RelationshipType extends OpenInfraModelObject
    implements Serializable {
	private static final long serialVersionUID = 1L;

	//bi-directional many-to-one association to ValueListValue
	@ManyToOne
	@JoinColumn(name="description")
	private ValueListValue valueListValue1;

	//bi-directional many-to-one association to ValueListValue
	@ManyToOne
	@JoinColumn(name="reference_to")
	private ValueListValue valueListValue2;

	//bi-directional many-to-one association to RelationshipTypeToTopicCharacteristic
	@OneToMany(mappedBy="relationshipType")
	private List<RelationshipTypeToTopicCharacteristic> relationshipTypeToTopicCharacteristics;

	//bi-directional many-to-one association to TopicInstanceXTopicInstance
	@OneToMany(mappedBy="relationshipType")
	private List<TopicInstanceXTopicInstance> topicInstanceXTopicInstances;

	public RelationshipType() {
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

	public List<RelationshipTypeToTopicCharacteristic> getRelationshipTypeToTopicCharacteristics() {
		return this.relationshipTypeToTopicCharacteristics;
	}

	public void setRelationshipTypeToTopicCharacteristics(List<RelationshipTypeToTopicCharacteristic> relationshipTypeToTopicCharacteristics) {
		this.relationshipTypeToTopicCharacteristics = relationshipTypeToTopicCharacteristics;
	}

	public RelationshipTypeToTopicCharacteristic addRelationshipTypeToTopicCharacteristic(RelationshipTypeToTopicCharacteristic relationshipTypeToTopicCharacteristic) {
		getRelationshipTypeToTopicCharacteristics().add(relationshipTypeToTopicCharacteristic);
		relationshipTypeToTopicCharacteristic.setRelationshipType(this);

		return relationshipTypeToTopicCharacteristic;
	}

	public RelationshipTypeToTopicCharacteristic removeRelationshipTypeToTopicCharacteristic(RelationshipTypeToTopicCharacteristic relationshipTypeToTopicCharacteristic) {
		getRelationshipTypeToTopicCharacteristics().remove(relationshipTypeToTopicCharacteristic);
		relationshipTypeToTopicCharacteristic.setRelationshipType(null);

		return relationshipTypeToTopicCharacteristic;
	}

	public List<TopicInstanceXTopicInstance> getTopicInstanceXTopicInstances() {
		return this.topicInstanceXTopicInstances;
	}

	public void setTopicInstanceXTopicInstances(List<TopicInstanceXTopicInstance> topicInstanceXTopicInstances) {
		this.topicInstanceXTopicInstances = topicInstanceXTopicInstances;
	}

	public TopicInstanceXTopicInstance addTopicInstanceXTopicInstance(TopicInstanceXTopicInstance topicInstanceXTopicInstance) {
		getTopicInstanceXTopicInstances().add(topicInstanceXTopicInstance);
		topicInstanceXTopicInstance.setRelationshipType(this);

		return topicInstanceXTopicInstance;
	}

	public TopicInstanceXTopicInstance removeTopicInstanceXTopicInstance(TopicInstanceXTopicInstance topicInstanceXTopicInstance) {
		getTopicInstanceXTopicInstances().remove(topicInstanceXTopicInstance);
		topicInstanceXTopicInstance.setRelationshipType(null);

		return topicInstanceXTopicInstance;
	}

}