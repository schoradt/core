package de.btu.openinfra.backend.db.daos;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.pojos.AttributeTypeGroupToAttributeTypes;
import de.btu.openinfra.backend.db.pojos.AttributeTypeGroupToTopicCharacteristicPojo;
import de.btu.openinfra.backend.db.pojos.AttributeTypeToAttributeTypeGroupPojo;
import de.btu.openinfra.backend.db.pojos.TopicCharacteristicDetailPojo;

/**
 * This is a very special data access class which is used to retrieve detailed
 * information about
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class TopicCharacteristicDetailDao {

	/**
	 * The UUID of the current project.
	 */
	private UUID currentProjectId;
	/**
	 * The currently used schema.
	 */
	private OpenInfraSchemas schema;

	/**
	 * This is the default constructor.
	 *
	 * @param currentProjectId the current project id
	 * @param schema           the current schema
	 */
	public TopicCharacteristicDetailDao(
			UUID currentProjectId, OpenInfraSchemas schema) {
		this.currentProjectId = currentProjectId;
		this.schema = schema;
	}

	/**
	 * Reads a specific detail POJO.
	 *
	 * @param locale
	 * @param topcharId the id of the required object
	 * @return a specific detail POJO
	 */
	public TopicCharacteristicDetailPojo read(
			Locale locale,
			UUID topcharId) {
		// Initialize the object which should be returned
		TopicCharacteristicDetailPojo tcdp =
				new TopicCharacteristicDetailPojo();

		List<AttributeTypeGroupToAttributeTypes> atgtatList =
				new LinkedList<AttributeTypeGroupToAttributeTypes>();

		// Retrieve all available attribute type groups which refer to the
		// requested topic characteristic.
		List<AttributeTypeGroupToTopicCharacteristicPojo> atgList =
				new AttributeTypeGroupToTopicCharacteristicDao(
						currentProjectId,
						schema).read(
								locale,
								topcharId,
								0,
								Integer.MAX_VALUE);

		// Iterate over the related attribute type groups
		for(AttributeTypeGroupToTopicCharacteristicPojo atgTopchar : atgList) {

			// Retrieve all attribute types belonging to this group
			List<AttributeTypeToAttributeTypeGroupPojo> atList =
					new AttributeTypeToAttributeTypeGroupDao(
							currentProjectId,
							schema).read(
									locale,
									atgTopchar.getAttributeTypeGroup()
										.getUuid(),
									0,
									Integer.MAX_VALUE);

			// Initialize the data container which holds the attribute types
			AttributeTypeGroupToAttributeTypes atgtat =
					new AttributeTypeGroupToAttributeTypes();
			atgtat.setAttributeTypeGroup(atgTopchar.getAttributeTypeGroup());
			atgtat.setAttributeTypeToAttributeTypeGroup(atList);
			atgtatList.add(atgtat);
		}

		tcdp.setAttributeTypeGroupToAttributeTypes(atgtatList);
		return tcdp;
	}


}
