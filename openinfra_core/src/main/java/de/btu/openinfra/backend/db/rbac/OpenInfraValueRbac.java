package de.btu.openinfra.backend.db.rbac;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.json.simple.JSONObject;

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
			new Class[] {UUID.class, OpenInfraSchemas.class};

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
			OpenInfraHttpMethod httpMethod,
			UriInfo uriInfo,
			Locale locale,
			UUID valueId,
			int offset,
			int size) {
		checkPermission(httpMethod, uriInfo);
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

	/**
     * This is a generic method which is provided by all RBAC classes.
     *
     * @param pojo
     * @param firstAssociationId
     * @param firstAssociationIdFromPojo
     * @return
     * @throws RuntimeException
     */
    public UUID createOrUpdate(
            OpenInfraHttpMethod httpMethod,
            UriInfo uriInfo,
            TypePojo pojo,
            UUID firstAssociationId,
            UUID firstAssociationIdFromPojo,
            JSONObject json)
            throws RuntimeException {
        checkPermission(httpMethod, uriInfo);
        try {
            return dao.getDeclaredConstructor(constructorTypes).newInstance(
                    currentProjectId,
                    schema).createOrUpdate(
                            pojo,
                            firstAssociationId,
                            firstAssociationIdFromPojo,
                            json);
        } catch(Exception ex) {
            ex.printStackTrace();
            throw new WebApplicationException(
                    ex.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

	public List<TypePojo> read(
			OpenInfraHttpMethod httpMethod,
			UriInfo uriInfo,
            Locale locale,
            UUID valueId,
            OpenInfraSortOrder order,
            OpenInfraOrderBy column,
            int offset,
            int size) {
		checkPermission(httpMethod, uriInfo);
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

	public Long getCount(
			OpenInfraHttpMethod httpMethod,
			UriInfo uriInfo, UUID valueId) {
		checkPermission(httpMethod, uriInfo);
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
