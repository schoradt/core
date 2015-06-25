package de.btu.openinfra.backend.db.jpa.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the relationship_type_to_topic_characteristic database table.
 * 
 */
@Embeddable
public class RelationshipTypeToTopicCharacteristicPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="relationship_type_id", insertable=false, updatable=false)
	private String relationshipTypeId;

	@Column(name="topic_characteristic_id", insertable=false, updatable=false)
	private String topicCharacteristicId;

	public RelationshipTypeToTopicCharacteristicPK() {
	}
	public String getRelationshipTypeId() {
		return this.relationshipTypeId;
	}
	public void setRelationshipTypeId(String relationshipTypeId) {
		this.relationshipTypeId = relationshipTypeId;
	}
	public String getTopicCharacteristicId() {
		return this.topicCharacteristicId;
	}
	public void setTopicCharacteristicId(String topicCharacteristicId) {
		this.topicCharacteristicId = topicCharacteristicId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RelationshipTypeToTopicCharacteristicPK)) {
			return false;
		}
		RelationshipTypeToTopicCharacteristicPK castOther = (RelationshipTypeToTopicCharacteristicPK)other;
		return 
			this.relationshipTypeId.equals(castOther.relationshipTypeId)
			&& this.topicCharacteristicId.equals(castOther.topicCharacteristicId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.relationshipTypeId.hashCode();
		hash = hash * prime + this.topicCharacteristicId.hashCode();
		
		return hash;
	}
}