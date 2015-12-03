package de.btu.openinfra.backend.rest;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.pojos.PtFreeTextPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;
import de.btu.openinfra.backend.db.rbac.PtFreeTextRbac;

/**
 * This class represents and implements the resource for PtFreeText in the
 * project and system schema.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@Path(OpenInfraResponseBuilder.REST_URI_DEFAULT + "/ptfreetext")
@Produces({ MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
        MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
        + OpenInfraResponseBuilder.UTF8_CHARSET })
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class PtFreeTextResource {

    /**
     * This resource creates a new PtFreeText object. The parameter UUID and
     * TRID of the ValueListAssociationPojo must not be set. In the normal case
     * the PtFreeText will be generated automatically. But there are some cases
     * where the database constraint checks fail if the PtFreeText is not in the
     * database before JPA persists the object itself. So the client must first
     * create the PtFreeText.
     * <ul>
     *   <li>rest/v1/projects/[uuid]/ptfreetext</li>
     *   <li>rest/v1/system/ptfreetext</li>
     * </ul>
     *
     * @param uriInfo
     * @param request
     * @param projectId   The id of the project. Can be null for system schema.
     * @param schema      The schema name the request should be processed at.
     * @param pojo        The PtFreeTextPojo that represents the new object.
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
    public Response create(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            PtFreeTextPojo pojo) {
        UUID id = new PtFreeTextRbac(
                projectId,
                OpenInfraSchemas.valueOf(schema.toUpperCase())).createOrUpdate(
                        OpenInfraHttpMethod.valueOf(request.getMethod()),
                        uriInfo,
                        null,
                        pojo);
        return OpenInfraResponseBuilder.postResponse(id);
    }
}
