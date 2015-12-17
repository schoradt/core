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
	@NamedQuery(name="TopicInstanceXTopicInstance"
			+ ".countAssociationToByTopicInstanceAndTopicCharacteristic",
					query="SELECT COUNT(t) "
							+ "FROM TopicInstanceXTopicInstance t "
							+ "WHERE t.topicInstance1Bean = :topicInstance "
							+ "AND t.topicInstance2Bean.topicCharacteristic = "
							+ ":topicCharacteristic"),
	@NamedQuery(name="TopicInstanceXTopicInstance"
			+ ".countAssociationFromByTopicInstanceAndTopicCharacteristic",
					query="SELECT COUNT(t) "
							+ "FROM TopicInstanceXTopicInstance t "
							+ "WHERE t.topicInstance2Bean = :topicInstance "
							+ "AND t.topicInstance1Bean.topicCharacteristic = "
							+ ":topicCharacteristic"),
	@NamedQuery(name="TopicInstanceXTopicInstance"
			+ ".findAssociationToByTopicInstanceAndTopicCharacteristic",
					query="SELECT t "
							+ "FROM TopicInstanceXTopicInstance t "
							+ "WHERE t.topicInstance1Bean = :topicInstance "
							+ "AND t.topicInstance2Bean.topicCharacteristic = "
							+ ":topicCharacteristic ORDER BY t.id"),
	@NamedQuery(name="TopicInstanceXTopicInstance"
			+ ".findAssociationFromByTopicInstanceAndTopicCharacteristic",
					query="SELECT t "
							+ "FROM TopicInstanceXTopicInstance t "
							+ "WHERE t.topicInstance2Bean = :topicInstance "
							+ "AND t.topicInstance1Bean.topicCharacteristic = "
							+ ":topicCharacteristic ORDER BY t.id"),
	@NamedQuery(name="TopicInstanceXTopicInstance.findAll",
			query="SELECT t FROM TopicInstanceXTopicInstance t ORDER BY t.id"),
	@NamedQuery(name="TopicInstanceXTopicInstance.findByTopicInstance",
			query="SELECT t "
					+ "FROM TopicInstanceXTopicInstance t "
					+ "WHERE t.topicInstance1Bean = :value ORDER BY t.id"),
	@NamedQuery(
		name="TopicInstanceXTopicInstance.findByTopicInstanceAndTopicInstance",
		query="SELECT t "
			+ "FROM TopicInstanceXTopicInstance t "
			+ "WHERE t.topicInstance1Bean = :value AND "
			+ "t.topicInstance2Bean = :value2 ORDER BY t.id")
})
@NamedNativeQueries({
    @NamedNativeQuery(
            name="TopicInstanceXTopicInstance.findAssociationToByTopicInstance"
                    + "AndTopicCharacteristicByLocaleAndOrderForValues",
            query="SELECT "
                    + "txt.*, txt.xmin "
                    + "FROM "
                        + "topic_instance_x_topic_instance txt, "
                        + "topic_instance ti1 "
                        + "LEFT JOIN ("
                            + "SELECT "
                                + "ti2.id, at.name, lcs.free_text "
                            + "FROM topic_instance ti2 "
                                + "JOIN attribute_value_value avv "
                                    + "ON avv.topic_instance_id = ti2.id "
                                + "JOIN attribute_type_to_attribute_type_group ata "
                                    + "ON ata.id = avv.attribute_type_to_attribute_type_group_id "
                                + "JOIN attribute_type at "
                                    + "ON at.id = ata.attribute_type_id "
                                + "JOIN localized_character_string lcs "
                                    + "ON lcs.pt_free_text_id = avv.value "
                            + "WHERE at.id = cast(? AS uuid) AND "
                                + "(lcs.pt_locale_id = cast(? AS uuid) OR "
                                + "lcs.pt_locale_id = cast(? AS uuid))) AS b "
                            + "ON b.id = ti1.id "
                    + "WHERE txt.topic_instance_1 = cast(? AS uuid) AND "
                        + "ti1.topic_characteristic_id = cast(? AS uuid) AND "
                        + "ti1.id = txt.topic_instance_2 "
                    + "ORDER BY b.free_text ",
                resultClass=TopicInstanceXTopicInstance.class),
    @NamedNativeQuery(
            name="TopicInstanceXTopicInstance.findAssociationFromByTopicInstance"
                    + "AndTopicCharacteristicByLocaleAndOrderForValues",
            query="SELECT "
                    + "txt.*, txt.xmin "
                    + "FROM "
                        + "topic_instance_x_topic_instance txt, "
                        + "topic_instance ti1 "
                        + "LEFT JOIN ("
                            + "SELECT "
                                + "ti2.id, at.name, lcs.free_text "
                            + "FROM topic_instance ti2 "
                                + "JOIN attribute_value_value avv "
                                    + "ON avv.topic_instance_id = ti2.id "
                                + "JOIN attribute_type_to_attribute_type_group ata "
                                    + "ON ata.id = avv.attribute_type_to_attribute_type_group_id "
                                + "JOIN attribute_type at "
                                    + "ON at.id = ata.attribute_type_id "
                                + "JOIN localized_character_string lcs "
                                    + "ON lcs.pt_free_text_id = avv.value "
                            + "WHERE at.id = cast(? AS uuid) AND "
                                + "(lcs.pt_locale_id = cast(? AS uuid) OR "
                                + "lcs.pt_locale_id = cast(? AS uuid))) AS b "
                            + "ON b.id = ti1.id "
                    + "WHERE txt.topic_instance_2 = cast(? AS uuid) AND "
                        + "ti1.topic_characteristic_id = cast(? AS uuid) AND "
                        + "ti1.id = txt.topic_instance_1 "
                    + "ORDER BY b.free_text ",
                resultClass=TopicInstanceXTopicInstance.class),
    @NamedNativeQuery(
            name="TopicInstanceXTopicInstance.findAssociationToByTopicInstance"
                    + "AndTopicCharacteristicByLocaleAndOrderForDomains",
            query="SELECT "
                    + "txt.*, txt.xmin "
                    + "FROM "
                        + "topic_instance_x_topic_instance txt, "
                        + "topic_instance ti1 "
                        + "LEFT JOIN ("
                            + "SELECT "
                                + "ti2.id, at.name, lcs.free_text "
                            + "FROM topic_instance ti2 "
                                + "JOIN attribute_value_domain avd "
                                    + "ON avd.topic_instance_id = ti2.id "
                                + "JOIN attribute_type_to_attribute_type_group ata "
                                    + "ON ata.id = avd.attribute_type_to_attribute_type_group_id "
                                + "JOIN attribute_type at "
                                    + "ON at.id = ata.attribute_type_id "
                                + "JOIN value_list_values vlv "
                                    + "ON vlv.id = avd.domain "
                                + "JOIN localized_character_string lcs "
                                    + "ON lcs.pt_free_text_id = vlv.name "
                            + "WHERE at.id = cast(? AS uuid) AND "
                                + "(lcs.pt_locale_id = cast(? AS uuid) OR "
                                + "lcs.pt_locale_id = cast(? AS uuid))) AS b "
                            + "ON b.id = ti1.id "
                    + "WHERE txt.topic_instance_1 = cast(? AS uuid) AND "
                        + "ti1.topic_characteristic_id = cast(? AS uuid) AND "
                        + "ti1.id = txt.topic_instance_2 "
                    + "ORDER BY b.free_text ",
                resultClass=TopicInstanceXTopicInstance.class),
    @NamedNativeQuery(
            name="TopicInstanceXTopicInstance.findAssociationFromByTopicInstance"
                    + "AndTopicCharacteristicByLocaleAndOrderForDomains",
            query="SELECT "
                    + "txt.*, txt.xmin "
                    + "FROM "
                        + "topic_instance_x_topic_instance txt, "
                        + "topic_instance ti1 "
                        + "LEFT JOIN ("
                            + "SELECT "
                                + "ti2.id, at.name, lcs.free_text "
                            + "FROM topic_instance ti2 "
                                + "JOIN attribute_value_domain avd "
                                    + "ON avd.topic_instance_id = ti2.id "
                                + "JOIN attribute_type_to_attribute_type_group ata "
                                    + "ON ata.id = avd.attribute_type_to_attribute_type_group_id "
                                + "JOIN attribute_type at "
                                    + "ON at.id = ata.attribute_type_id "
                                + "JOIN value_list_values vlv "
                                    + "ON vlv.id = avd.domain "
                                + "JOIN localized_character_string lcs "
                                    + "ON lcs.pt_free_text_id = vlv.name "
                            + "WHERE at.id = cast(? AS uuid) AND "
                                + "(lcs.pt_locale_id = cast(? AS uuid) OR "
                                + "lcs.pt_locale_id = cast(? AS uuid))) AS b "
                            + "ON b.id = ti1.id "
                    + "WHERE txt.topic_instance_2 = cast(? AS uuid) AND "
                        + "ti1.topic_characteristic_id = cast(? AS uuid) AND "
                        + "ti1.id = txt.topic_instance_1 "
                    + "ORDER BY b.free_text ",
                resultClass=TopicInstanceXTopicInstance.class),
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