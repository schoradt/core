package de.btu.openinfra.backend.db.jpa.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the attribute_value_geomz database table.
 *
 */
@Entity
@Table(name="attribute_value_geomz")
@NamedQueries({
    @NamedQuery(name="AttributeValueGeomz.findAll",
            query="SELECT a FROM AttributeValueGeomz a"),
    @NamedQuery(name="AttributeValueGeomz.findByTopicInstance",
            query="SELECT a "
                    + "FROM AttributeValueGeomz a "
                    + "WHERE a.topicInstance = :value")})
@NamedNativeQueries({
    @NamedNativeQuery(name="AttributeValueGeomz.select",
            query="SELECT %s "
                    + "FROM attribute_value_geomz "
                    + "WHERE id = cast(? as uuid)")
})

public class AttributeValueGeomz extends OpenInfraModelObject
    implements Serializable {
	private static final long serialVersionUID = 1L;

	private String geom;

	//bi-directional many-to-one association to AttributeTypeToAttributeTypeGroup
	@ManyToOne
	@JoinColumn(name="attribute_type_to_attribute_type_group_id")
	private AttributeTypeToAttributeTypeGroup attributeTypeToAttributeTypeGroup;

	//bi-directional many-to-one association to TopicInstance
	@ManyToOne
	@JoinColumn(name="topic_instance_id")
	private TopicInstance topicInstance;

	public AttributeValueGeomz() {
	}

	public String getGeom() {
		return this.geom;
	}

	public void setGeom(String geom) {
		this.geom = geom;
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