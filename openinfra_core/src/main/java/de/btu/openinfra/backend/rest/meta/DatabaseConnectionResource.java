package de.btu.openinfra.backend.rest.meta;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
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
import de.btu.openinfra.backend.db.OpenInfraSortOrder;
import de.btu.openinfra.backend.db.pojos.meta.DatabaseConnectionPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;
import de.btu.openinfra.backend.db.rbac.meta.DatabaseConnectionRbac;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

/**
 * This class represents and implements the resource for database connections in
 * the meta data schema. They are used to determine a database connection that
 * contains the server, the port, the database, the schema and the credentials.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@Path(OpenInfraResponseBuilder.REST_URI_METADATA + "/dbconnections")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
    MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET})
public class DatabaseConnectionResource {

    /**
     * This resource provides a list of all DatabaseConnectionPojo's. This
     * resource supports sorting and pagination of the list.
     * <ul>
     *   <li>rest/v1/metadata/dbconnections?orderBy=DATABASE&sortOrder=ASC&offset=0&size=10</li>
     * </ul>
     *
     * @param uriInfo
     * @param request
     * @param sortOrder The sort order for the list.
     * @param orderBy   The element the list should be ordered by.
     * @param offset    The offset parameter for the elements of the list.
     * @param size      The count of elements the list should contain.
     * @return          A list of DatabaseConnectionPojo's
     *
     * @response.representation.200.qname A list of DatabaseConnectionPojo's.
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
    public List<DatabaseConnectionPojo> get(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @QueryParam("sortOrder") OpenInfraSortOrder sortOrder,
            @QueryParam("orderBy") OpenInfraOrderBy orderBy,
            @QueryParam("offset") int offset,
            @QueryParam("size") int size) {
        return new DatabaseConnectionRbac().read(
        		OpenInfraHttpMethod.valueOf(request.getMethod()),
                uriInfo,
                null,
                sortOrder,
                orderBy,
                offset,
                size);
    }

    /**
     * This resource provides a DatabaseConnectionPojo for the specified UUID.
     * <ul>
     *   <li>rest/v1/metadata/dbconnections/[uuid]</li>
     * </ul>
     *
     * @param uriInfo
     * @param request
     * @param databaseConnectionId The UUID of the DatabaseConnectionPojo that
                                   should be retrieved.
     * @return                     The specified DatabaseConnectionPojo.
     *
     * @response.representation.200.qname A specified DatabaseConnectionPojo.
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
    @Path("{databaseConnectionId}")
    public DatabaseConnectionPojo get(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("databaseConnectionId") UUID databaseConnectionId) {
        return new DatabaseConnectionRbac().read(
                OpenInfraHttpMethod.valueOf(request.getMethod()),
                uriInfo,
                null,
                databaseConnectionId);
    }

    /**
     * This resource provides the count of DatabaseConnectionPojo's in the whole
     * system.
     * <ul>
     *   <li>rest/v1/metadata/dbconnections/count</li>
     * </ul>
     *
     * @param uriInfo
     * @param request
     * @return        The count of DatabaseConnectionPojo's.
     *
     * @response.representation.200.qname The count of DatabaseConnectionPojo's
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
	@Path("count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getCount(
	        @Context UriInfo uriInfo,
            @Context HttpServletRequest request) {
        return new DatabaseConnectionRbac().getCount(
                OpenInfraHttpMethod.valueOf(request.getMethod()),
                uriInfo);
	}

    /**
     * This resource creates a new Database Connection object. The specified
     * DatabaseConnectionPojo must contain the ServersPojo, the PortsPojo, the
     * DatabasesPojo, the SchemasPojo and the CredentialsPojo. The parameter
     * UUID and TRID of the DatabaseConnectionPojo must not be set.
     * <ul>
     *   <li>rest/v1/metadata/dbconnections</li>
     * </ul>
     *
     * @param uriInfo
     * @param request
     * @param pojo    The DatabaseConnectionPojo that represents the new object.
     * @return        A Response with the UUID of the new created object or the
     *                status code 204.
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
            DatabaseConnectionPojo pojo) {
        UUID id = new DatabaseConnectionRbac().createOrUpdate(
                OpenInfraHttpMethod.valueOf(request.getMethod()),
                uriInfo,
                null,
                pojo);
        return OpenInfraResponseBuilder.postResponse(id);
    }

    /**
     * This resource updates the Database Connection object with the specified
     * UUID. The specified DatabaseConnectionPojo must contain the UUID of the
     * object that should be updated, the TRID, the ServersPojo, the PortsPojo,
     * the DatabasesPojo, the SchemasPojo and the CredentialsPojo.
     * <ul>
     *   <li>rest/v1/metadata/dbconnections/[uuid]</li>
     * </ul>
     * <b>The object id in the DatabaseConnectionPojo and in the URI that
     * identifies the database connection must concur.</b>
     *
     * @param uriInfo
     * @param request
     * @param portsId The UUID of the PortsPojo that should be updated.
     * @param pojo    The PortsPojo that represents the updated object.
     * @return        A Response with the status code 200 for a successful
     *                update or 204 if the object could not be updated.
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
    @Path("{databaseConnectionId}")
    public Response update(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("databaseConnectionId") UUID databaseConnectionId,
            DatabaseConnectionPojo pojo) {
        UUID id = new DatabaseConnectionRbac().createOrUpdate(
                OpenInfraHttpMethod.valueOf(request.getMethod()),
                uriInfo,
                databaseConnectionId,
                pojo);
        return OpenInfraResponseBuilder.putResponse(id);
    }

    /**
     * This resource deletes the Database Connection object with the specified
     * UUID.
     * <ul>
     *   <li>rest/v1/metadata/dbconnections/[uuid]</li>
     * </ul>
     *
     * @param uriInfo
     * @param request
     * @param databaseConnectionId The UUID of the Database Connection object
                                   that should be deleted.
     * @return                     A Response with the status code 200 for a
     *                             successful deletion or 404 if the object was
     *                             not found.
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
    @Path("{databaseConnectionId}")
    public Response delete(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("databaseConnectionId") UUID databaseConnectionId) {
        boolean deleteResult = new DatabaseConnectionRbac().delete(
                OpenInfraHttpMethod.valueOf(request.getMethod()),
                uriInfo,
                databaseConnectionId);
        return OpenInfraResponseBuilder.deleteResponse(
                deleteResult,
                databaseConnectionId);
    }

}


