package de.btu.openinfra.backend.db.jpa.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the attribute_value database table.
 * 
 */
@Entity
@Table(name="attribute_value")
@NamedQueries({
	@NamedQuery(name="AttributeValue.findAll", 
			query="SELECT a FROM AttributeValue a"),
	@NamedQuery(name="AttributeValue.findByTopicInstance",
			query="SELECT a "
					+ "FROM AttributeValue a "
					+ "WHERE a.topicInstance = :value"),
	@NamedQuery(name="AttributeValue.findByTopicInstanceAndAttributeType",
    query="SELECT a "
            + "FROM AttributeValue a, AttributeTypeToAttributeTypeGroup ag "
            + "WHERE a.topicInstance = :value AND "
            + "a.attributeTypeToAttributeTypeGroup = ag AND "
            + "ag.attributeType = :value2")
})
public class AttributeValue extends OpenInfraModelObject
    implements Serializable {
	private static final long serialVersionUID = 1L;

	//bi-directional many-to-one association to AttributeTypeToAttributeTypeGroup
	@ManyToOne
	@JoinColumn(name="attribute_type_to_attribute_type_group_id")
	private AttributeTypeToAttributeTypeGroup attributeTypeToAttributeTypeGroup;

	//bi-directional many-to-one association to TopicInstance
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="topic_instance_id")
	private TopicInstance topicInstance;

	public AttributeValue() {
	}

	public AttributeTypeToAttributeTypeGroup getAttributeTypeToAttributeTypeGroup() {
		return this.attributeTypeToAttributeTypeGroup;
	}

	public void setAttributeTypeToAttributeTypeGroup(AttributeTypeToAttributeTypeGroup attributeTypeToAttributeTypeGroup) {
		this.attributeTypeToAttributeTypeGroup = attributeTypeToAttributeTypeGroup;
	}

	public TopicInstance getTopicInstance() {
		return this.topicInstance;
	}

	public void setTopicInstance(TopicInstance topicInstance) {
		this.topicInstance = topicInstance;
	}

}