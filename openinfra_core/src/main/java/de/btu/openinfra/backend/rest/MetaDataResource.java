package de.btu.openinfra.backend.rest;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.btu.openinfra.backend.db.daos.MetaDataDao;
import de.btu.openinfra.backend.db.daos.OpenInfraOrderByEnum;
import de.btu.openinfra.backend.db.daos.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraSortOrder;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.pojos.MetaDataPojo;

@Path(OpenInfraResponseBuilder.REST_URI + "/metadata")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
	+ OpenInfraResponseBuilder.UTF8_CHARSET})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class MetaDataResource {

    @GET
    public List<MetaDataPojo> get(
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
			@QueryParam("sortOrder") OpenInfraSortOrder sortOrder,
			@QueryParam("orderBy") OpenInfraOrderByEnum orderBy,
            @QueryParam("offset") int offset,
            @QueryParam("size") int size) {
        return new MetaDataDao(
                projectId,
                OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
                        PtLocaleDao.forLanguageTag(language),
						sortOrder,
						orderBy,
                        offset,
                        size);
    }

	@GET
	@Path("count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getCount(
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema) {
		return new MetaDataDao(
                projectId,
                OpenInfraSchemas.valueOf(schema.toUpperCase())).getCount();
	}

    @GET
    @Path("{metaDataId}")
    public MetaDataPojo get(
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("metaDataId") UUID metaDataId) {
        return new MetaDataDao(
                projectId,
                OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
                        PtLocaleDao.forLanguageTag(language),
                        metaDataId);
    }

}
