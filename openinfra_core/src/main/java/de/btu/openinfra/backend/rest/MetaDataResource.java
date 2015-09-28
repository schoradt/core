package de.btu.openinfra.backend.rest;

import java.util.List;
import java.util.UUID;

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

import de.btu.openinfra.backend.db.OpenInfraOrderByEnum;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.OpenInfraSortOrder;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.pojos.MetaDataPojo;
import de.btu.openinfra.backend.db.rbac.MetaDataRbac;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;

@Path(OpenInfraResponseBuilder.REST_URI_DEFAULT + "/metadata")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
	+ OpenInfraResponseBuilder.UTF8_CHARSET})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class MetaDataResource {

    @GET
    public List<MetaDataPojo> get(
    		@Context UriInfo uriInfo,
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
			@QueryParam("sortOrder") OpenInfraSortOrder sortOrder,
			@QueryParam("orderBy") OpenInfraOrderByEnum orderBy,
            @QueryParam("offset") int offset,
            @QueryParam("size") int size) {
        return new MetaDataRbac(
                projectId,
                OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
                		OpenInfraHttpMethod.GET, 
						uriInfo,
                        PtLocaleDao.forLanguageTag(language),
						sortOrder,
						orderBy,
                        offset,
                        size);
    }

    @GET
    @Path("/new")
    public MetaDataPojo newMetaData(
    		@Context UriInfo uriInfo,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema) {
        return new MetaDataRbac(
                        projectId,
                        OpenInfraSchemas.valueOf(schema.toUpperCase()))
                    .newMetaData(
                    		OpenInfraHttpMethod.GET, 
    						uriInfo);
    }

    @POST
    public Response create(
    		@Context UriInfo uriInfo,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            MetaDataPojo pojo) {
        UUID id = new MetaDataRbac(
                projectId,
                OpenInfraSchemas.valueOf(schema.toUpperCase())).createOrUpdate(
                		OpenInfraHttpMethod.GET, 
						uriInfo,
                        pojo);
        return OpenInfraResponseBuilder.postResponse(id);
    }

    @PUT
    @Path("{metadataId}")
    public Response update(
    		@Context UriInfo uriInfo,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("metadataId") UUID metadataId,
            MetaDataPojo pojo) {
        return OpenInfraResponseBuilder.putResponse(
                new MetaDataRbac(
                        projectId,
                        OpenInfraSchemas.valueOf(schema.toUpperCase())
                        ).createOrUpdate(
                        		OpenInfraHttpMethod.GET, 
        						uriInfo, pojo, metadataId, null));
    }

    @DELETE
    @Path("{metadataId}")
    public Response delete(
    		@Context UriInfo uriInfo,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("metadataId") UUID metadataId) {
        return OpenInfraResponseBuilder.deleteResponse(
                new MetaDataRbac(
                        projectId,
                        OpenInfraSchemas.valueOf(schema.toUpperCase())).delete(
                        		OpenInfraHttpMethod.GET, 
        						uriInfo,
                                metadataId),
                metadataId);
    }

	@GET
	@Path("count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getCount(
			@Context UriInfo uriInfo,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema) {
		return new MetaDataRbac(
                projectId,
                OpenInfraSchemas.valueOf(schema.toUpperCase())).getCount(
                		OpenInfraHttpMethod.GET, 
						uriInfo);
	}

    @GET
    @Path("{metaDataId}")
    public MetaDataPojo get(
    		@Context UriInfo uriInfo,
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("metaDataId") UUID metaDataId) {
        return new MetaDataRbac(
                projectId,
                OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
                		OpenInfraHttpMethod.GET, 
						uriInfo,
                        PtLocaleDao.forLanguageTag(language),
                        metaDataId);
    }

}
