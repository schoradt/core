package de.btu.openinfra.backend.rest;

import java.util.List;
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

import de.btu.openinfra.backend.db.OpenInfraOrderBy;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.OpenInfraSortOrder;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.pojos.ValueListAssociationPojo;
import de.btu.openinfra.backend.db.pojos.ValueListPojo;
import de.btu.openinfra.backend.db.pojos.ValueListValuePojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;
import de.btu.openinfra.backend.db.rbac.ValueListAssociationRbac;
import de.btu.openinfra.backend.db.rbac.ValueListRbac;
import de.btu.openinfra.backend.db.rbac.ValueListValueRbac;

/**
 * This class represents and implements the resource for value lists in the
 * project and system schema.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@Path(OpenInfraResponseBuilder.REST_URI_DEFAULT + "/valuelists")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
	+ OpenInfraResponseBuilder.UTF8_CHARSET})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ValueListResource {

    /**
     * This resource provides the count of ValueListPojos's in the specified
     * project or system schema.
     * <br><br>
     * <b>Example:</b>
     * <ul>
     *   <li>{@code rest/v1/projects/[uuid]/valuelists/count}</li>
     *   <li>{@code rest/v1/system/valuelists/count}</li>
     * </ul>
     *
     * @param uriInfo
     * @param request
     * @param projectId The id of the project. Can be null for system schema.
     * @param schema    The schema name the request should be processed at.
     * @return          The count of ValueListPojo's for the requested schema.
     *
     * @response.representation.200.qname The count of ValueListPojo's as
     *                                    long.
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
	@Path("count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getValueListsCount(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema) {
		return new ValueListRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).getCount(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo);
	}

	/**
     * This resource provides a list of all ValueListPojo's in the specified
     * project or system schema. This resource supports sorting and pagination
     * of the list.
     * <br><br>
     * <b>Example:</b>
     * <ul>
     *   <li>{@code rest/v1/projects/[uuid]/valuelists/[uuid]?language=de-DE&orderBy=NAME&sortOrder=ASC&offset=0&size=10}</li>
     *   <li>{@code rest/v1/system/valuelists/[uuid]?language=de-DE&orderBy=NAME&sortOrder=ASC&offset=0&size=10}</li>
     * </ul>
     *
     * @param uriInfo
     * @param request
     * @param language  The language the localized content of the object.
     * @param projectId The id of the project. Can be null for system schema.
     * @param schema    The schema name the request should be processed at.
     * @param sortOrder The sort order for the list.
     * @param orderBy   The element the list should be ordered by.
     * @param offset    The offset parameter for the elements of the list.
     * @param size      The count of elements the list should contain.
     * @return          A list of ValueListPojo's for the requested schema.
     *
     * @response.representation.200.qname A list of ValueListPojo's.
     * @response.representation.200.doc   This is the representation returned by
     *                                    default.
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
	@GET
	public List<ValueListPojo> get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@QueryParam("sortOrder") OpenInfraSortOrder sortOrder,
			@QueryParam("orderBy") OpenInfraOrderBy orderBy,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new ValueListRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						sortOrder,
						orderBy,
						offset,
						size);
	}

	/**
	 * This resource provides a list of ValueListAssociationPojo's in the
	 * specified project or system schema for the specified value list id. The
	 * ValueListAssociationPojo contains id of the requesting value list, the
	 * associated ValueListPojo and the relationship to the requesting
	 * ValueListPojo. This resource supports pagination of the list.
	 * <br><br>
     * <b>Example:</b>
     * <ul>
     *   <li>{@code rest/v1/projects/[uuid]/valuelists/[uuid]/associations?language=de-DE&offset=0&size=10}</li>
     *   <li>{@code rest/v1/system/valuelists/[uuid]/associations?language=de-DE&offset=0&size=10}</li>
     * </ul>
	 *
	 * @param uriInfo
	 * @param request
	 * @param language    The language the localized content of the object.
     * @param projectId   The id of the project. Can be null for system schema.
     * @param schema      The schema name the request should be processed at.
	 * @param valueListId The id of the requesting value list.
	 * @param offset      The offset parameter for the elements of the list.
     * @param size        The count of elements the list should contain.
	 * @return            A list of ValueListAssociationPojo's for the requested
	 *                    schema.
	 *
	 * @response.representation.200.qname A list of ValueListAssociationPojo's.
     * @response.representation.200.doc   This is the representation returned by
     *                                    default.
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
	@GET
	@Path("{valueListId}/associations")
	public List<ValueListAssociationPojo> getAssociations(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("valueListId") UUID valueListId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new ValueListAssociationRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						valueListId,
						offset,
						size);
	}

	/**
	 * This resource creates a new value list association object. The specified
     * ValueListAssociationPojo must contain the UUID of the requesting value
     * list, the associated ValueListPojo and the relationship. The
     * parameter UUID and TRID of the ValueListAssociationPojo must not be set.
     * <br><br>
     * <b>Example:</b>
     * <ul>
     *   <li>{@code rest/v1/projects/[uuid]/valuelists/[uuid]/associations}</li>
     *   <li>{@code rest/v1/system/valuelists/[uuid]/associations}</li>
     * </ul>
     *
	 * @param uriInfo
	 * @param request
	 * @param projectId   The id of the project. Can be null for system schema.
	 * @param schema      The schema name the request should be processed at.
	 * @param valueListId The id of the value list.
	 * @param pojo        The ValueListAssociationPojo that represents the new
     *                    object.
	 * @return            A Response with the UUID of the new created object or
	 *                    the status code 204.
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
    @Path("{valueListId}/associations")
    public Response create(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("valueListId") UUID valueListId,
            ValueListAssociationPojo pojo) {
        return OpenInfraResponseBuilder.postResponse(
                new ValueListAssociationRbac(
                        projectId,
                        OpenInfraSchemas.valueOf(schema.toUpperCase())
                        ).createOrUpdate(
                                OpenInfraHttpMethod.valueOf(
                                        request.getMethod()),
                                uriInfo,
                                pojo,
                                valueListId,
                                pojo.getAssociationValueListId(),
                                null,
                                null));
	}

	/**
	 * This resource provides the count of ValueListAssociationPojo's in the
	 * specified project or system schema.
     *
	 * @param uriInfo
	 * @param request
	 * @param projectId   The id of the project. Can be null for system schema.
     * @param schema      The schema name the request should be processed at.
     * @param valueListId The id of the requesting value list.
	 * @return            The count of ValueListAssociationPojo's for the
     *                    requested schema.
     *
     * @response.representation.200.qname The count of ValueListAssociationPojo's
     *                                    as long.
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
    @Path("{valueListId}/associations/count")
	@Produces({MediaType.TEXT_PLAIN})
    public long getAssociationsCount(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("valueListId") UUID valueListId) {
        return new ValueListAssociationRbac(
                projectId,
                OpenInfraSchemas.valueOf(schema.toUpperCase())).getCount(
                		OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
                        valueListId);
    }

	@GET
	@Path("{valueListId}/associations/{associatedValueListId}")
	public List<ValueListAssociationPojo> getAssociations(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("valueListId") UUID valueListId,
			@PathParam("associatedValueListId") UUID associatedValueListId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new ValueListAssociationRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						valueListId,
						associatedValueListId,
						offset,
						size);
	}

	@PUT
	@Path("{valueListId}/associations/{associatedValueListId}")
    public Response update(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("valueListId") UUID valueListId,
            @PathParam("associatedValueListId") UUID associatedValueListId,
            ValueListAssociationPojo pojo) {
        return OpenInfraResponseBuilder.putResponse(
                new ValueListAssociationRbac(
                        projectId,
                        OpenInfraSchemas.valueOf(schema.toUpperCase())
                        ).createOrUpdate(
                                OpenInfraHttpMethod.valueOf(
                                        request.getMethod()),
                                uriInfo,
                                pojo,
                                valueListId,
                                pojo.getAssociationValueListId(),
                                associatedValueListId,
                                pojo.getAssociatedValueList().getUuid()));
    }

    @DELETE
    @Path("{valueListId}/associations/{associatedValueListId}")
    public Response delete(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("valueListId") UUID valueListId,
            @PathParam("associatedValueListId") UUID associatedValueListId) {
        return OpenInfraResponseBuilder.deleteResponse(
                new ValueListAssociationRbac(
                        projectId,
                        OpenInfraSchemas.valueOf(schema.toUpperCase())).delete(
                                OpenInfraHttpMethod.valueOf(
                                      request.getMethod()),
                                uriInfo,
                                valueListId,
                                associatedValueListId));
    }

	@POST
	public Response createValueList(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
	        @PathParam("projectId") UUID projectId,
	        @PathParam("schema") String schema,
	        ValueListPojo pojo) {
	    // call the create or update method for the DAO and return the uuid
	    return OpenInfraResponseBuilder.postResponse(
	                new ValueListRbac(
	                        projectId,
	                        OpenInfraSchemas.valueOf(schema.toUpperCase()))
	                        .createOrUpdate(OpenInfraHttpMethod.valueOf(
	                        		request.getMethod()),
	        						uriInfo,
	        						null,
	        						pojo));
	}

	@GET
	@Path("{valueListId}")
	public ValueListPojo get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("valueListId") UUID valueListId) {
		return new ValueListRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						valueListId);
	}

	@DELETE
	@Path("{valueListId}")
	public Response delete(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
	        @PathParam("projectId") UUID projectId,
	        @PathParam("valueListId") UUID valueListId,
            @PathParam("schema") String schema) {
	    return OpenInfraResponseBuilder.deleteResponse(
	            new ValueListRbac(
	                    projectId,
	                    OpenInfraSchemas.valueOf(schema.toUpperCase()))
	                .delete(
	                		OpenInfraHttpMethod.valueOf(request.getMethod()),
    						uriInfo,
    						valueListId),
	                valueListId);
	}

	@PUT
    @Path("{valueListId}")
    public Response update(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("valueListId") UUID valueListId,
            ValueListPojo pojo) {
	    UUID uuid = new ValueListRbac(
                projectId,
                OpenInfraSchemas.valueOf(schema.toUpperCase())).createOrUpdate(
                		OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
                        valueListId,
                        pojo);
        return OpenInfraResponseBuilder.putResponse(uuid);
    }

	@GET
	@Path("{valueListId}/valuelistvalues")
	public List<ValueListValuePojo> getValueListValues(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("valueListId") UUID valueListId,
			@QueryParam("sortOrder") OpenInfraSortOrder sortOrder,
            @QueryParam("orderBy") OpenInfraOrderBy orderBy,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new ValueListValueRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						valueListId,
						sortOrder,
						orderBy,
						offset,
						size);
	}

	@POST
    @Path("{valueListId}/valuelistvalues")
	public Response createValueListValue(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("valueListId") UUID valueListId,
            @PathParam("schema") String schema,
            ValueListValuePojo pojo) {
        // call the create or update method for the DAO and return the uuid
        return OpenInfraResponseBuilder.postResponse(
                    new ValueListValueRbac(
                            projectId,
                            OpenInfraSchemas.valueOf(schema.toUpperCase()))
                            .createOrUpdate(
                            		OpenInfraHttpMethod.valueOf(
                            				request.getMethod()),
            						uriInfo,
            						pojo,
            						valueListId,
            						pojo.getBelongsToValueList()));
	}

	@GET
	@Path("{valueListId}/valuelistvalues/count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getValueListValuesCount(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("valueListId") UUID valueListId) {
		return new ValueListValueRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).getCount(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						valueListId);
	}

}
