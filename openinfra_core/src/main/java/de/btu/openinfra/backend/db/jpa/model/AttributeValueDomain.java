package de.btu.openinfra.backend.db.jpa.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the attribute_value_domain database table.
 * 
 */
@Entity
@Table(name="attribute_value_domain")
@NamedQuery(name="AttributeValueDomain.findAll", query="SELECT a FROM AttributeValueDomain a")
public class AttributeValueDomain implements Serializable, OpenInfraModelObject {
	private static final long serialVersionUID = 1L;

	@Id
	private UUID id;
	
	private Integer xmin;

	//bi-directional many-to-one association to AttributeTypeToAttributeTypeGroup
	@ManyToOne
	@JoinColumn(name="attribute_type_to_attribute_type_group_id")
	private AttributeTypeToAttributeTypeGroup attributeTypeToAttributeTypeGroup;

	//bi-directional many-to-one association to TopicInstance
	@ManyToOne
	@JoinColumn(name="topic_instance_id")
	private TopicInstance topicInstance;

	//bi-directional many-to-one association to ValueListValue
	@ManyToOne
	@JoinColumn(name="domain")
	private ValueListValue valueListValue;

	public AttributeValueDomain() {
	}

	@Override
	public UUID getId() {
		return this.id;
	}

	@Override
	public void setId(UUID id) {
		this.id = id;
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

	public ValueListValue getValueListValue() {
		return this.valueListValue;
	}

	public void setValueListValue(ValueListValue valueListValue) {
		this.valueListValue = valueListValue;
	}
	
	@Override
	public Integer getXmin() {
		return xmin;
	}

}