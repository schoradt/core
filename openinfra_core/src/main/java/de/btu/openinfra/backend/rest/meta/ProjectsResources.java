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

@Path("/metadata/projects")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ProjectsResources {

    @GET
    public List<ProjectsPojo> get(
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
            @QueryParam("offset") int offset,
            @QueryParam("size") int size) {
        return new ProjectsDao(
                OpenInfraSchemas.META_DATA).read(
                        null,
                        offset,
                        size);
    }

    @GET
    @Path("{projectsId}")
    public ProjectsPojo get(
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
            @PathParam("projectsId") UUID projectsId) {
        return new ProjectsDao(
                OpenInfraSchemas.META_DATA).read(
                        null,
                        projectsId);
    }

}
