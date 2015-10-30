package de.btu.openinfra.backend.rest.meta;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import de.btu.openinfra.backend.db.pojos.meta.LogPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;
import de.btu.openinfra.backend.db.rbac.meta.LogRbac;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path(OpenInfraResponseBuilder.REST_URI_METADATA + "/logs")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
    MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET})
public class LogResource {

    @GET
    public List<LogPojo> get(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @QueryParam("offset") int offset,
            @QueryParam("size") int size) {
        return new LogRbac().read(
                OpenInfraHttpMethod.valueOf(request.getMethod()),
                uriInfo,
                null,
                offset, size);
    }

    @GET
    @Path("{logId}")
    public LogPojo get(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("logId") UUID logId) {
        return new LogRbac().read(
                OpenInfraHttpMethod.valueOf(request.getMethod()),
                uriInfo,
                null,
                logId);
    }

    @GET
	@Path("count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getCount(
	        @Context UriInfo uriInfo,
            @Context HttpServletRequest request) {
        return new LogRbac().getCount(
                OpenInfraHttpMethod.valueOf(request.getMethod()),
                uriInfo);
	}

    @POST
    public Response create(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            LogPojo pojo) {
        UUID id = new LogRbac().createOrUpdate(
                OpenInfraHttpMethod.valueOf(request.getMethod()),
                uriInfo,
                null,
                pojo);
        return OpenInfraResponseBuilder.postResponse(id);
    }

    @DELETE
    @Path("{logId}")
    public Response delete(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("logId") UUID logId) {
        boolean deleteResult = new LogRbac().delete(
                OpenInfraHttpMethod.valueOf(request.getMethod()),
                uriInfo,
                logId);
        return OpenInfraResponseBuilder.deleteResponse(deleteResult, logId);
    }

}
