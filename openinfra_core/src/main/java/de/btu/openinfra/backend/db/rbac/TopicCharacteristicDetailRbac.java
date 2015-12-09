package de.btu.openinfra.backend.db.rbac;

import java.util.Locale;
import java.util.UUID;

import javax.ws.rs.core.UriInfo;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.TopicCharacteristicDetailDao;
import de.btu.openinfra.backend.db.pojos.TopicCharacteristicDetailPojo;

public class TopicCharacteristicDetailRbac {

	/**
	 * The UUID of the current project.
	 */
	private UUID currentProjectId;
	/**
	 * The currently used schema.
	 */
	private OpenInfraSchemas schema;

	public TopicCharacteristicDetailRbac(
			UUID currentProjectId, OpenInfraSchemas schema) {
		this.currentProjectId = currentProjectId;
		this.schema = schema;
	}

	public TopicCharacteristicDetailPojo read(
			OpenInfraHttpMethod httpMethod,
			UriInfo uriInfo,
			Locale locale,
			UUID topcharId) {
		// Since this Class is not a rbac class, we use a closely related class
		// to check the permission.
		new TopicCharacteristicRbac(
				currentProjectId, schema).checkPermission(
						httpMethod,
						uriInfo);
		return new TopicCharacteristicDetailDao(
				currentProjectId, schema).read(locale, topcharId);
	}

}
