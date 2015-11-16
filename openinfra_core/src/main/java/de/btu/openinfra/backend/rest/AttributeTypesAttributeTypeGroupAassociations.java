package de.btu.openinfra.backend.rest;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
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
import de.btu.openinfra.backend.db.pojos.AttributeTypeGroupToAttributeTypePojo;
import de.btu.openinfra.backend.db.rbac.AttributeTypeGroupToAttributeTypeRbac;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;

@Path(OpenInfraResponseBuilder.REST_URI_DEFAULT +
        "/attributetypesattributetypegroupsassociations")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
    MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class AttributeTypesAttributeTypeGroupAassociations {

    @GET
    @Path("{associationId}")
    public AttributeTypeGroupToAttributeTypePojo get(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("associationId") UUID associationId) {
        return new AttributeTypeGroupToAttributeTypeRbac(
                projectId,
                OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
                        OpenInfraHttpMethod.valueOf(request.getMethod()),
                        uriInfo,
                        PtLocaleDao.forLanguageTag(language),
                        associationId);
    }

    @PUT
    @Path("{associationId}")
    public Response update(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("associationId") UUID associationId,
            AttributeTypeGroupToAttributeTypePojo pojo) {
        return OpenInfraResponseBuilder.putResponse(
                new AttributeTypeGroupToAttributeTypeRbac(
                        projectId,
                        OpenInfraSchemas.valueOf(schema.toUpperCase())
                        ).createOrUpdate(
                                OpenInfraHttpMethod.valueOf(
                                        request.getMethod()),
                                uriInfo,
                                pojo,
                                associationId,
                                pojo.getUuid(),
                                pojo.getMetaData()));
    }

    @DELETE
    @Path("{associationId}")
    public Response delete(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("associationId") UUID associationId) {
        return OpenInfraResponseBuilder.deleteResponse(
                new AttributeTypeGroupToAttributeTypeRbac(
                        projectId,
                        OpenInfraSchemas.valueOf(schema.toUpperCase())).delete(
                                OpenInfraHttpMethod.valueOf(
                                      request.getMethod()),
                                uriInfo,
                                associationId),
                        associationId);
    }

}
