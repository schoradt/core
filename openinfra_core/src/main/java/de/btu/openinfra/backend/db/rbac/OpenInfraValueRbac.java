package de.btu.openinfra.backend.db.rbac;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import de.btu.openinfra.backend.db.OpenInfraOrderBy;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.OpenInfraSortOrder;
import de.btu.openinfra.backend.db.daos.OpenInfraValueDao;
import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

public abstract class OpenInfraValueRbac<
		TypePojo extends OpenInfraPojo,
		TypeModel extends OpenInfraModelObject,
		TypeModelValue,
		TypeDao extends OpenInfraValueDao<TypePojo, TypeModel, TypeModelValue>> 
			extends OpenInfraRbac<TypePojo, TypeModel, TypeDao> {
	
	protected Class<TypeModelValue> valueClass;
	
	/**
	 * This defines the constructor types in order to call the constructor in a 
	 * generic way via: 
	 */
	protected Class<?>[] constructorTypesValue =	
			new Class[] {UUID.class, OpenInfraSchemas.class, 
				OpenInfraModelObject.class, OpenInfraValueDao.class};
	
	/**
	 * Refers to the OpenInfraValueDao
	 * 
	 * @param currentProjectId
	 * @param schema
	 * @param valueClass
	 * @param dao
	 */
	protected OpenInfraValueRbac(
			UUID currentProjectId,
			OpenInfraSchemas schema, 
			Class<TypeModelValue> valueClass,
			Class<TypeDao> dao) {
		super(currentProjectId, schema, dao);
		this.valueClass = valueClass; 
	}

	public List<TypePojo> read(
			Locale locale,
			UUID valueId,
			int offset,
			int size) {
		checkPermission();
		try {
			return dao.getDeclaredConstructor(
					constructorTypesValue).newInstance(
							currentProjectId, 
							schema).read(locale, valueId, offset, size);
		} catch(Exception ex) {
			ex.printStackTrace();
			throw new WebApplicationException(
					ex.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}
	
	public List<TypePojo> read(
            Locale locale,
            UUID valueId,
            OpenInfraSortOrder order,
            OpenInfraOrderBy column,
            int offset,
            int size) {
		checkPermission();
		try {
			return dao.getDeclaredConstructor(
					constructorTypesValue).newInstance(
							currentProjectId, 
							schema).read(
									locale, 
									valueId, 
									order, 
									column, 
									offset, 
									size);
		} catch(Exception ex) {
			ex.printStackTrace();
			throw new WebApplicationException(
					ex.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}
	
	public Long getCount(UUID valueId) {
		checkPermission();
		try {
			return dao.getDeclaredConstructor(
					constructorTypesValue).newInstance(
							currentProjectId, 
							schema).getCount(valueId);
		} catch(Exception ex) {
			ex.printStackTrace();
			throw new WebApplicationException(
					ex.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}
}
