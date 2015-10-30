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

import de.btu.openinfra.backend.db.pojos.meta.ServersPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;
import de.btu.openinfra.backend.db.rbac.meta.ServersRbac;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path(OpenInfraResponseBuilder.REST_URI_METADATA + "/servers")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
    MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET})
public class ServersResource {

    @GET
    public List<ServersPojo> get(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @QueryParam("offset") int offset,
            @QueryParam("size") int size) {
        return new ServersRbac().read(
                OpenInfraHttpMethod.valueOf(request.getMethod()),
                uriInfo,
                null,
                offset, size);
    }

    @GET
    @Path("{serversId}")
    public ServersPojo get(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("serversId") UUID serversId) {
        return new ServersRbac().read(
                OpenInfraHttpMethod.valueOf(request.getMethod()),
                uriInfo,
                null,
                serversId);
    }

    @GET
    @Path("count")
    @Produces({MediaType.TEXT_PLAIN})
    public long getCount(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request) {
        return new ServersRbac().getCount(
                OpenInfraHttpMethod.valueOf(request.getMethod()),
                uriInfo);
    }

    @POST
    public Response create(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            ServersPojo pojo) {
        UUID id = new ServersRbac().createOrUpdate(
                OpenInfraHttpMethod.valueOf(request.getMethod()),
                uriInfo,
                null,
                pojo);
        return OpenInfraResponseBuilder.postResponse(id);
    }

    @PUT
    @Path("{serversId}")
    public Response update(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("serversId") UUID serversId,
            ServersPojo pojo) {
        UUID id = new ServersRbac().createOrUpdate(
                OpenInfraHttpMethod.valueOf(request.getMethod()),
                uriInfo,
                serversId,
                pojo);
        return OpenInfraResponseBuilder.putResponse(id);
    }

    @DELETE
    @Path("{serversId}")
    public Response delete(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("serversId") UUID serversId) {
        boolean deleteResult = new ServersRbac().delete(
                OpenInfraHttpMethod.valueOf(request.getMethod()),
                uriInfo,
                serversId);
        return OpenInfraResponseBuilder.deleteResponse(
                deleteResult,
                serversId);
    }

}
