package de.btu.openinfra.backend.rest.meta;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.meta.LogDao;
import de.btu.openinfra.backend.db.pojos.meta.LogPojo;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path(OpenInfraResponseBuilder.REST_URI_METADATA + "/logs")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
    MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET})
public class LogResource {

    @GET
    public List<LogPojo> get(
            @QueryParam("offset") int offset,
            @QueryParam("size") int size) {
        return new LogDao(
                OpenInfraSchemas.META_DATA).read(null, offset, size);
    }

    @GET
    @Path("{logId}")
    public LogPojo get(
            @PathParam("logId") UUID logId) {
        return new LogDao(
                OpenInfraSchemas.META_DATA).read(
                        null ,
                        logId);
    }

    @GET
	@Path("count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getCount() {
		return new LogDao(
				OpenInfraSchemas.META_DATA).getCount();
	}
    
    @POST
    public Response create(LogPojo pojo) {
        UUID id = new LogDao(OpenInfraSchemas.META_DATA).createOrUpdate(
                        pojo);
        return OpenInfraResponseBuilder.postResponse(id);
    }
    
    @DELETE
    @Path("{logId}")
    public Response delete(@PathParam("logId") UUID logId) {
        return OpenInfraResponseBuilder.deleteResponse(
                new LogDao(OpenInfraSchemas.META_DATA).delete(
                        logId),
                logId);
    }
    
    @GET
    @Path("/new")
    public LogPojo newLog() {
        return new LogDao(OpenInfraSchemas.META_DATA).newLog();
    }

}
