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

import de.btu.openinfra.backend.db.pojos.meta.SettingKeysPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;
import de.btu.openinfra.backend.db.rbac.meta.SettingKeysRbac;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path(OpenInfraResponseBuilder.REST_URI_METADATA + "/settingkeys")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
    MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET})
public class SettingKeysResource {

    @GET
    public List<SettingKeysPojo> get(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @QueryParam("offset") int offset,
            @QueryParam("size") int size) {
        return new SettingKeysRbac().read(
                OpenInfraHttpMethod.valueOf(request.getMethod()),
                uriInfo,
                null,
                offset, size);
    }

    @GET
    @Path("{settingKeysId}")
    public SettingKeysPojo get(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("settingKeysId") UUID settingKeysId) {
        return new SettingKeysRbac().read(
                OpenInfraHttpMethod.valueOf(request.getMethod()),
                uriInfo,
                null,
                settingKeysId);
    }

    @GET
    @Path("count")
    @Produces({MediaType.TEXT_PLAIN})
    public long getCount(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request) {
        return new SettingKeysRbac().getCount(
                OpenInfraHttpMethod.valueOf(request.getMethod()),
                uriInfo);
    }

    @POST
    public Response create(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            SettingKeysPojo pojo) {
        UUID id = new SettingKeysRbac().createOrUpdate(
                OpenInfraHttpMethod.valueOf(request.getMethod()),
                uriInfo,
                null,
                pojo);
        return OpenInfraResponseBuilder.postResponse(id);
    }

    @PUT
    @Path("{settingKeysId}")
    public Response update(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("settingKeysId") UUID settingKeysId,
            SettingKeysPojo pojo) {
        UUID id = new SettingKeysRbac().createOrUpdate(
                OpenInfraHttpMethod.valueOf(request.getMethod()),
                uriInfo,
                settingKeysId,
                pojo);
        return OpenInfraResponseBuilder.putResponse(id);
    }

    @DELETE
    @Path("{settingKeysId}")
    public Response delete(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("settingKeysId") UUID settingKeysId) {
        boolean deleteResult = new SettingKeysRbac().delete(
                OpenInfraHttpMethod.valueOf(request.getMethod()),
                uriInfo,
                settingKeysId);
        return OpenInfraResponseBuilder.deleteResponse(
                deleteResult,
                settingKeysId);
    }

}
