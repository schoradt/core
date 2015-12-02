package de.btu.openinfra.backend.db.rbac;

import java.util.Locale;
import java.util.UUID;

import javax.ws.rs.core.UriInfo;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.project.AttributeValueGeomType;
import de.btu.openinfra.backend.db.daos.project.TopicDao;
import de.btu.openinfra.backend.db.pojos.project.TopicPojo;

public class TopicRbac {
	
	/**
	 * The UUID of the current project.
	 */
	private UUID currentProjectId;
	/**
	 * The currently used schema.
	 */
	private OpenInfraSchemas schema;
	
	public TopicRbac(UUID currentProjectId, OpenInfraSchemas schema) {
		this.currentProjectId = currentProjectId;
		this.schema = schema;
	}
	
	public TopicPojo read(
			OpenInfraHttpMethod httpMethod, 
			UriInfo uriInfo,
			Locale locale, 
			UUID topicInstanceId, 
			AttributeValueGeomType geomType) {
		// Since this Class is not a rbac class, we use a closely related class 
		// to check the permission.
		new TopicInstanceRbac(
				currentProjectId, schema).checkPermission(
						httpMethod, 
						uriInfo);
		return new TopicDao(currentProjectId, schema).read(
				locale, topicInstanceId, geomType);
	}
}
