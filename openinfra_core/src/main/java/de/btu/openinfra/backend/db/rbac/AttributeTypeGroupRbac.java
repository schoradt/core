package de.btu.openinfra.backend.db.rbac;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.AttributeTypeGroupDao;
import de.btu.openinfra.backend.db.jpa.model.AttributeTypeGroup;
import de.btu.openinfra.backend.db.pojos.AttributeTypeGroupPojo;

public class AttributeTypeGroupRbac extends OpenInfraRbac<
	AttributeTypeGroupPojo, AttributeTypeGroup, AttributeTypeGroupDao> {

	public AttributeTypeGroupRbac(
			UUID currentProjectId,
			OpenInfraSchemas schema) {
		super(currentProjectId, schema, AttributeTypeGroupDao.class);
	}
	
	public List<AttributeTypeGroupPojo> readSubGroups(
			Locale locale,
			UUID attributeTypeGroupId) {
		checkPermission();
		try {
			return dao.getDeclaredConstructor(constructorTypes).newInstance(
					currentProjectId, 
					schema).readSubGroups(locale, attributeTypeGroupId);
		} catch(Exception ex) {
			ex.printStackTrace();
			throw new WebApplicationException(
					ex.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

}
