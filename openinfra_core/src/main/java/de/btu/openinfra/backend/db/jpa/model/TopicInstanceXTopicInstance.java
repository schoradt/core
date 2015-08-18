package de.btu.openinfra.backend.db.jpa.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the topic_instance_x_topic_instance database table.
 * 
 */
@Entity
@Table(name="topic_instance_x_topic_instance")
@NamedQueries({
	@NamedQuery(name="TopicInstanceXTopicInstance.count", 
			query="SELECT COUNT(t) FROM TopicInstanceXTopicInstance t "),
	@NamedQuery(name="TopicInstanceXTopicInstance.countByTopicInstance", 
			query="SELECT COUNT(t) "
					+ "FROM TopicInstanceXTopicInstance t "
					+ "WHERE t.topicInstance1Bean = :value"),
	@NamedQuery(name="TopicInstanceXTopicInstance.findAll", 
			query="SELECT t FROM TopicInstanceXTopicInstance t"),
	@NamedQuery(name="TopicInstanceXTopicInstance.findByTopicInstance",
			query="SELECT t "
					+ "FROM TopicInstanceXTopicInstance t "
					+ "WHERE t.topicInstance1Bean = :value"),
	@NamedQuery(
		name="TopicInstanceXTopicInstance.findByTopicInstanceAndTopicInstance",
		query="SELECT t "
			+ "FROM TopicInstanceXTopicInstance t "
			+ "WHERE t.topicInstance1Bean = :value AND "
			+ "t.topicInstance2Bean = :value2"),
	@NamedQuery(name="TopicInstanceXTopicInstance.findParent", 
			query="SELECT t "
					+ "FROM TopicInstanceXTopicInstance t "
					+ "WHERE t.topicInstance2Bean = :self")
})
public class TopicInstanceXTopicInstance extends OpenInfraModelObject
    implements Serializable {
	private static final long serialVersionUID = 1L;

	//bi-directional many-to-one association to RelationshipType
	@ManyToOne
	@JoinColumn(name="relationship_type_id")
	private RelationshipType relationshipType;

	//bi-directional many-to-one association to TopicInstance
	@ManyToOne
	@JoinColumn(name="topic_instance_1")
	private TopicInstance topicInstance1Bean;

	//bi-directional many-to-one association to TopicInstance
	@ManyToOne
	@JoinColumn(name="topic_instance_2")
	private TopicInstance topicInstance2Bean;

	public TopicInstanceXTopicInstance() {
	}

	public RelationshipType getRelationshipType() {
		return this.relationshipType;
	}

	public void setRelationshipType(RelationshipType relationshipType) {
		this.relationshipType = relationshipType;
	}

	public TopicInstance getTopicInstance1Bean() {
		return this.topicInstance1Bean;
	}

	public void setTopicInstance1Bean(TopicInstance topicInstance1Bean) {
		this.topicInstance1Bean = topicInstance1Bean;
	}

	public TopicInstance getTopicInstance2Bean() {
		return this.topicInstance2Bean;
	}

	public void setTopicInstance2Bean(TopicInstance topicInstance2Bean) {
		this.topicInstance2Bean = topicInstance2Bean;
	}

}