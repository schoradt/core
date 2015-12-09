package de.btu.openinfra.backend.rest.project;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.daos.project.AttributeValueGeomType;
import de.btu.openinfra.backend.db.pojos.project.AttributeValuePojo;
import de.btu.openinfra.backend.db.rbac.AttributeValueRbac;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path(OpenInfraResponseBuilder.REST_URI_PROJECTS + "/attributevalues")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
    MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class AttributeValueResource {

    /**
     * This resource provides a AttributeValuePojo for the specified UUID.
     * <ul>
     *   <li>rest/v1/projects/[uuid]/attributevalues/[uuid]</li>
     * </ul>
     *
     * @param uriInfo
     * @param request
     * @param language         The language of the requested object.
     * @param projectId        The id of the project the requested value belongs
     *                         to.
     * @param attributeValueId The UUID of the AttributeValuePojo that should be
                               retrieved.
     * @param geomType         The geometry type that is requested.
     * @return                 The specified AttributeValuePojo.
     *
     * @response.representation.200.qname A specified AttributeValuePojo.
     * @response.representation.200.doc   This is the representation returned by
     *                                    default.
     *
     * @response.representation.403.qname WebApplicationException
     * @response.representation.403.doc   This error occurs if you do not have
     *                                    the permission to access this
     *                                    resource.
     *
     * @response.representation.500.qname OpenInfraWebException
     * @response.representation.500.doc   An internal error occurs if the
     *                                    backend runs into an unexpected
     *                                    exception.
     */
	@GET
	@Path("{attributeValueId}")
	public AttributeValuePojo get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("attributeValueId") UUID attributeValueId,
			@QueryParam("geomType") AttributeValueGeomType geomType) {
		return new AttributeValueRbac(
				projectId,
				OpenInfraSchemas.PROJECTS).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						attributeValueId,
						geomType);
	}

	/**
	 * This resource updates the Attribute Value object with the specified UUID.
     * The specified AttributeValuePojo must contain the UUID of the
     * object that should be updated, the TRID, the attributeValueType and
     * depending on the attributeValueType an AttributeValueDomainPojo,
     * AttributeValueGeomPojo, AttributeValueGeomzPojo or
     * AttributeValueValuePojo.
     * <br><br>
     * The possible values for the attributeValueType can be the following:
     * <ul>
     *   <li>ATTRIBUTE_VALUE_VALUE</li>
     *   <li>ATTRIBUTE_VALUE_DOMAIN</li>
     *   <li>ATTRIBUTE_VALUE_GEOM</li>
     *   <li>ATTRIBUTE_VALUE_GEOMZ</li>
     * </ul>
     * <br>
     * <ul>
     *   <li>rest/v1/projects/[uuid]/attributevalues/[uuid]</li>
     * </ul>
     * <b>The object id in the AttributeValuePojo and in the URI that identifies
     * the database connection must concur.</b>
     *
	 * @param uriInfo
	 * @param request
	 * @param language         The language of the requested object.
     * @param projectId        The id of the project the requested value belongs
     *                         to.
     * @param attributeValueId The UUID of the AttributeValuePojo that should be
     *                         updated.
     * @param pojo             The AttributeValuePojo that represents the
     *                         updated object.
     * @return                 A Response with the status code 200 for a
     *                         successful update or 204 if the object could not
     *                         be updated.
	 *
	 * @response.representation.200.qname Response
     * @response.representation.200.doc   This is the representation returned by
     *                                    default.
     *
     * @response.representation.204.qname Response
     * @response.representation.204.doc   If the object could not be updated.
     *
     * @response.representation.403.qname WebApplicationException
     * @response.representation.403.doc   This error occurs if you do not have
     *                                    the permission to access this
     *                                    resource.
     *
     * @response.representation.409.qname OpenInfraEntityException
     * @response.representation.409.doc   This error occurs if the parameters
     *                                    are not configured as expected.
     *
     * @response.representation.500.qname OpenInfraWebException
     * @response.representation.500.doc   An internal error occurs if the
     *                                    backend runs into an unexpected
     *                                    exception.
	 */
	@PUT
	@Path("{attributeValueId}")
	public Response update(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("attributeValueId") UUID attributeValueId,
			AttributeValuePojo pojo) {
	    UUID id = new AttributeValueRbac(
                projectId,
                OpenInfraSchemas.PROJECTS).distributeTypes(
                		OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						pojo, projectId,
                        attributeValueId);
        return OpenInfraResponseBuilder.putResponse(id);
	}

	/**
	 * This resource provides a list of AttributeValueGeomType. It does not
	 * differ between 2d and 3d geometry types.
	 * <ul>
     *   <li>rest/v1/projects/[uuid]/attributevalues/geomtypes</li>
     * </ul>
     *
     * @return A list of AttributeValueGeomType.
     *
     * @response.representation.200.qname A list of AttributeValueGeomType's.
     * @response.representation.200.doc   This is the representation returned by
     *                                    default.
     *
     * @response.representation.403.qname WebApplicationException
     * @response.representation.403.doc   This error occurs if you do not have
     *                                    the permission to access this
     *                                    resource.
     *
     * @response.representation.500.qname OpenInfraWebException
     * @response.representation.500.doc   An internal error occurs if the
     *                                    backend runs into an unexpected
     *                                    exception.
	 */
	@GET
	@Path("geomtypes")
	public AttributeValueGeomType[] getGeomTypes() {
		return AttributeValueGeomType.values();
	}

	/**
	 * This resource creates a new Attribute Value object. The specified
     * AttributeValuePojo must contain the the attributeValueType and
     * depending on the attributeValueType an AttributeValueDomainPojo,
     * AttributeValueGeomPojo, AttributeValueGeomzPojo or
     * AttributeValueValuePojo. The parameter UUID and TRID of the
     * AttributeValuePojo must not be set.
     * <br><br>
     * The possible values for the attributeValueType can be the following:
     * <ul>
     *   <li>ATTRIBUTE_VALUE_VALUE</li>
     *   <li>ATTRIBUTE_VALUE_DOMAIN</li>
     *   <li>ATTRIBUTE_VALUE_GEOM</li>
     *   <li>ATTRIBUTE_VALUE_GEOMZ</li>
     * </ul>
     * <br>
     * <ul>
     *   <li>rest/v1/projects/[uuid]/attributevalues</li>
     * </ul>
     *
	 * @param uriInfo
	 * @param request
	 * @param projectId The id of the project the requested value belongs to.
     * @param pojo      The AttributeValuePojo that represents the new object.
	 * @return
	 *
	 * @response.representation.200.qname Response
     * @response.representation.200.doc   This is the representation returned by
     *                                    default.
     *
     * @response.representation.204.qname Response
     * @response.representation.204.doc   If the object could not be created.
     *
     * @response.representation.403.qname WebApplicationException
     * @response.representation.403.doc   This error occurs if you do not have
     *                                    the permission to access this
     *                                    resource.
     *
     * @response.representation.500.qname OpenInfraWebException
     * @response.representation.500.doc   An internal error occurs if the
     *                                    backend runs into an unexpected
     *                                    exception.
	 */
	@POST
    public Response create(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            AttributeValuePojo pojo) {
	    UUID id = new AttributeValueRbac(
                projectId,
                OpenInfraSchemas.PROJECTS).distributeTypes(
                		OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						pojo, projectId,
                        null);
        return OpenInfraResponseBuilder.postResponse(id);
    }

	/**
     * This resource deletes the Attribute Value object with the specified UUID.
     * <ul>
     *   <li>rest/v1/projects/[uuid]/attributevalues/[uuid]</li>
     * </ul>
     *
     * @param uriInfo
     * @param request
     * @param projectId        The id of the project the value that should be
     *                         deleted belongs to.
     * @param attributeValueId The UUID of the Attribute Value object that
     *                         should be deleted.
     * @return                 A Response with the status code 200 for a
     *                         successful deletion or 404 if the object was not
     *                         found.
     *
     * @response.representation.200.qname Response
     * @response.representation.200.doc   This is the representation returned by
     *                                    default.
     *
     * @response.representation.403.qname WebApplicationException
     * @response.representation.403.doc   This error occurs if you do not have
     *                                    the permission to access this
     *                                    resource.
     *
     * @response.representation.404.qname Response
     * @response.representation.404.doc   If the object could not be deleted
     *                                    because it was not found.
     *
     * @response.representation.500.qname OpenInfraWebException
     * @response.representation.500.doc   An internal error occurs if the
     *                                    backend runs into an unexpected
     *                                    exception.
     */
	@DELETE
	@Path("{attributeValueId}")
	public Response delete(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
    	    @PathParam("projectId") UUID projectId,
            @PathParam("attributeValueId") UUID attributeValueId) {
	    return OpenInfraResponseBuilder.deleteResponse(
	            new AttributeValueRbac(projectId, OpenInfraSchemas.PROJECTS)
	                .delete(OpenInfraHttpMethod.valueOf(request.getMethod()),
							uriInfo,
							attributeValueId),
                attributeValueId);
	}
}
