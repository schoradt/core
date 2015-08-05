package de.btu.openinfra.backend.rest.meta;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.btu.openinfra.backend.db.daos.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.meta.ProjectsDao;
import de.btu.openinfra.backend.db.pojos.meta.ProjectsPojo;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path("/metadata/projects")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
    MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET})
public class ProjectsResources {

    @GET
    public List<ProjectsPojo> get(
            @QueryParam("offset") int offset,
            @QueryParam("size") int size) {
        return new ProjectsDao(
                OpenInfraSchemas.META_DATA).read(null, offset, size);
    }

    @GET
    @Path("{projectsId}")
    public ProjectsPojo get(
            @PathParam("projectsId") UUID projectsId) {
        return new ProjectsDao(
                OpenInfraSchemas.META_DATA).read(
                        null,
                        projectsId);
    }

    @GET
	@Path("count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getCount() {
		return new ProjectsDao(
				OpenInfraSchemas.META_DATA).getCount();
	}

}
