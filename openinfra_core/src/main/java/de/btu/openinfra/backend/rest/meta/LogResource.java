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
import de.btu.openinfra.backend.db.daos.meta.LogDao;
import de.btu.openinfra.backend.db.pojos.meta.LogPojo;

@Path("/metadata/logs")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class LogResource {

    @GET
    public List<LogPojo> get(
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
            @QueryParam("offset") int offset,
            @QueryParam("size") int size) {
        return new LogDao(
                OpenInfraSchemas.META_DATA).read(
                        null,
                        null,
                        null,
                        offset,
                        size);
    }

    @GET
    @Path("{logId}")
    public LogPojo get(
            @QueryParam("language") String language, 
            @PathParam("projectId") UUID projectId,
            @PathParam("logId") UUID logId) {
        return new LogDao(
                OpenInfraSchemas.META_DATA).read(
                        null , 
                        logId);
    }

}
