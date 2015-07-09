package de.btu.openinfra.backend.rest;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.btu.openinfra.backend.db.daos.MetaDataDao;
import de.btu.openinfra.backend.db.daos.OpenInfraOrderBy;
import de.btu.openinfra.backend.db.daos.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraSortOrder;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.pojos.MetaDataPojo;

@Path("/projects/{projectId}/metadata")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY, 
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY})
public class MetaDataResource {

    @GET
    public List<MetaDataPojo> get(
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
			@QueryParam("sortOrder") OpenInfraSortOrder sortOrder,
			@QueryParam("orderBy") OpenInfraOrderBy orderBy,
            @QueryParam("offset") int offset,
            @QueryParam("size") int size) {
        return new MetaDataDao(
                projectId,
                OpenInfraSchemas.PROJECTS).read(
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
			@PathParam("projectId") UUID projectId) {
		return new MetaDataDao(
                projectId,
                OpenInfraSchemas.PROJECTS).getCount();
	}

    @GET
    @Path("{metaDataId}")
    public MetaDataPojo get(
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
            @PathParam("metaDataId") UUID metaDataId) {
        return new MetaDataDao(
                projectId,
                OpenInfraSchemas.PROJECTS).read(
                        PtLocaleDao.forLanguageTag(language), 
                        metaDataId);
    }

}
