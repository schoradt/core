package de.btu.openinfra.backend.db.jpa.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the attribute_type_group_to_topic_characteristic database table.
 * 
 */
@Entity
@Table(name="attribute_type_group_to_topic_characteristic")
@NamedQueries({
	@NamedQuery(name="AttributeTypeGroupToTopicCharacteristic.findAll", 
			query="SELECT a FROM AttributeTypeGroupToTopicCharacteristic a"),
	@NamedQuery(name="AttributeTypeGroupToTopicCharacteristic"
			+ ".findByTopicCharacteristic",
			query="SELECT a "
					+ "FROM AttributeTypeGroupToTopicCharacteristic a "
					+ "WHERE a.topicCharacteristic = :value"),
	@NamedQuery(name="AttributeTypeGroupToTopicCharacteristic"
			+ ".findByTopicCharacteristicAndAttributeTypeGroup",
			query="SELECT a "
					+ "FROM AttributeTypeGroupToTopicCharacteristic a "
					+ "WHERE a.topicCharacteristic = :value AND "
					+ "a.attributeTypeGroup = :value2"),	
	@NamedQuery(name="AttributeTypeGroupToTopicCharacteristic"
			+ ".findByAttributeTypeGroup",
			query="SELECT a "
					+ "FROM AttributeTypeGroupToTopicCharacteristic a "
					+ "WHERE a.attributeTypeGroup = :value"),
	@NamedQuery(name="AttributeTypeGroupToTopicCharacteristic"
			+ ".findByAttributeTypeGroupAndTopicCharacteristic",
			query="SELECT a "
					+ "FROM AttributeTypeGroupToTopicCharacteristic a "
					+ "WHERE a.attributeTypeGroup = :value AND "
					+ "a.topicCharacteristic = :value2"),
	@NamedQuery(
	        name="AttributeTypeGroupToTopicCharacteristic."
	                + "countByAttributeTypeGroup",
	        query="SELECT COUNT(a) "
	                + "FROM AttributeTypeGroupToTopicCharacteristic a "
	                + "WHERE a.attributeTypeGroup = :value"),
	@NamedQuery(
            name="AttributeTypeGroupToTopicCharacteristic."
                    + "countByTopicCharacteristic",
            query="SELECT COUNT(a) "
                    + "FROM AttributeTypeGroupToTopicCharacteristic a "
                    + "WHERE a.topicCharacteristic = :value")
})

public class AttributeTypeGroupToTopicCharacteristic
    extends OpenInfraModelObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="\"order\"")
	private Integer order;

	//bi-directional many-to-one association to AttributeTypeGroup
	@ManyToOne
	@JoinColumn(name="attribute_type_group_id")
	private AttributeTypeGroup attributeTypeGroup;

	//bi-directional many-to-one association to Multiplicity
	@ManyToOne
	@JoinColumn(name="multiplicity")
	private Multiplicity multiplicityBean;

	//bi-directional many-to-one association to TopicCharacteristic
	@ManyToOne
	@JoinColumn(name="topic_characteristic_id")
	private TopicCharacteristic topicCharacteristic;

	//bi-directional many-to-one association to AttributeTypeToAttributeTypeGroup
	@OneToMany(mappedBy="attributeTypeGroupToTopicCharacteristic")
	private List<AttributeTypeToAttributeTypeGroup> attributeTypeToAttributeTypeGroups;

	public AttributeTypeGroupToTopicCharacteristic() {
	}

	public Integer getOrder() {
		return this.order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public AttributeTypeGroup getAttributeTypeGroup() {
		return this.attributeTypeGroup;
	}

	public void setAttributeTypeGroup(AttributeTypeGroup attributeTypeGroup) {
		this.attributeTypeGroup = attributeTypeGroup;
	}

	public Multiplicity getMultiplicityBean() {
		return this.multiplicityBean;
	}

	public void setMultiplicityBean(Multiplicity multiplicityBean) {
		this.multiplicityBean = multiplicityBean;
	}

	public TopicCharacteristic getTopicCharacteristic() {
		return this.topicCharacteristic;
	}

	public void setTopicCharacteristic(TopicCharacteristic topicCharacteristic) {
		this.topicCharacteristic = topicCharacteristic;
	}

	public List<AttributeTypeToAttributeTypeGroup> getAttributeTypeToAttributeTypeGroups() {
		return this.attributeTypeToAttributeTypeGroups;
	}

	public void setAttributeTypeToAttributeTypeGroups(List<AttributeTypeToAttributeTypeGroup> attributeTypeToAttributeTypeGroups) {
		this.attributeTypeToAttributeTypeGroups = attributeTypeToAttributeTypeGroups;
	}

	public AttributeTypeToAttributeTypeGroup addAttributeTypeToAttributeTypeGroup(AttributeTypeToAttributeTypeGroup attributeTypeToAttributeTypeGroup) {
		getAttributeTypeToAttributeTypeGroups().add(attributeTypeToAttributeTypeGroup);
		attributeTypeToAttributeTypeGroup.setAttributeTypeGroupToTopicCharacteristic(this);

		return attributeTypeToAttributeTypeGroup;
	}

	public AttributeTypeToAttributeTypeGroup removeAttributeTypeToAttributeTypeGroup(AttributeTypeToAttributeTypeGroup attributeTypeToAttributeTypeGroup) {
		getAttributeTypeToAttributeTypeGroups().remove(attributeTypeToAttributeTypeGroup);
		attributeTypeToAttributeTypeGroup.setAttributeTypeGroupToTopicCharacteristic(null);

		return attributeTypeToAttributeTypeGroup;
	}

}