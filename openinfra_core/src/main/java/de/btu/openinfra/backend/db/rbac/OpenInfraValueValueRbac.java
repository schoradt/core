package de.btu.openinfra.backend.db.rbac;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraValueValueDao;
import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

public class OpenInfraValueValueRbac<
	TypePojo extends OpenInfraPojo,
	TypeModel extends OpenInfraModelObject, 
	TypeModelValue, 
	TypeModelValue2,
	TypeDao extends OpenInfraValueValueDao<TypePojo,
		TypeModel, TypeModelValue, TypeModelValue2>> 
	extends OpenInfraValueRbac<TypePojo, TypeModel, TypeModelValue, TypeDao> {
	
	protected Class<TypeModelValue2> valueClass2;
	
	/**
	 * Refers to the OpenInfraValueValueDao
	 * 
	 * @param currentProjectId
	 * @param schema
	 * @param valueClass
	 * @param valueClass2
	 * @param dao
	 */
	protected OpenInfraValueValueRbac(
			UUID currentProjectId,
			OpenInfraSchemas schema, 
			Class<TypeModelValue> valueClass,
			Class<TypeModelValue2> valueClass2,
			Class<TypeDao> dao) {
		super(currentProjectId, schema, valueClass, dao);
		this.valueClass2 = valueClass2;
	}	

	public List<TypePojo> read(
			OpenInfraHttpMethod httpMethod, 
			UriInfo uriInfo,
			Locale locales,
			UUID valueId,
			UUID valueId2,
			int offset,
			int size) {
		checkPermission(httpMethod, uriInfo);
		try {
			return dao.getDeclaredConstructor(
					constructorTypes).newInstance(
							currentProjectId,
							schema).read(
									locales, 
									valueId, 
									valueId2, 
									offset, 
									size);
		} catch(Exception ex) {
			ex.printStackTrace();
			throw new WebApplicationException(
					ex.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		}		
	}


}
