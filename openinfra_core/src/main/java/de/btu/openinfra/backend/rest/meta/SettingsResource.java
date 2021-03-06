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
import de.btu.openinfra.backend.db.pojos.meta.SettingsPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;
import de.btu.openinfra.backend.db.rbac.meta.SettingsRbac;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path(OpenInfraResponseBuilder.REST_URI_METADATA + "/settings")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
    MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET})
public class SettingsResource {

    @GET
    public List<SettingsPojo> get(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @QueryParam("sortOrder") OpenInfraSortOrder sortOrder,
            @QueryParam("orderBy") OpenInfraOrderBy orderBy,
            @QueryParam("offset") int offset,
            @QueryParam("size") int size) {
        return new SettingsRbac().read(
                OpenInfraHttpMethod.valueOf(request.getMethod()),
                uriInfo,
                null,
                sortOrder,
                orderBy,
                offset,
                size);
    }

    @GET
    @Path("{settingsId}")
    public SettingsPojo get(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("settingsId") UUID settingsId) {
        return new SettingsRbac().read(
                OpenInfraHttpMethod.valueOf(request.getMethod()),
                uriInfo,
                null,
                settingsId);
    }

    @GET
	@Path("count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getCount(
	        @Context UriInfo uriInfo,
            @Context HttpServletRequest request) {
        return new SettingsRbac().getCount(
                OpenInfraHttpMethod.valueOf(request.getMethod()),
                uriInfo);
	}

    @POST
    public Response create(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            SettingsPojo pojo) {
        UUID id = new SettingsRbac().createOrUpdate(
                OpenInfraHttpMethod.valueOf(request.getMethod()),
                uriInfo,
                null,
                pojo);
        return OpenInfraResponseBuilder.postResponse(id);
    }

    @PUT
    @Path("{settingsId}")
    public Response update(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("settingsId") UUID settingsId,
            SettingsPojo pojo) {
        UUID id = new SettingsRbac().createOrUpdate(
                OpenInfraHttpMethod.valueOf(request.getMethod()),
                uriInfo,
                settingsId,
                pojo);
        return OpenInfraResponseBuilder.putResponse(id);
    }

    @DELETE
    @Path("{settingsId}")
    public Response delete(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("settingsId") UUID settingsId) {
        boolean deleteResult = new SettingsRbac().delete(
                OpenInfraHttpMethod.valueOf(request.getMethod()),
                uriInfo,
                settingsId);
        return OpenInfraResponseBuilder.deleteResponse(
                deleteResult,
                settingsId);
    }

}
