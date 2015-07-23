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
import de.btu.openinfra.backend.db.daos.meta.DatabaseConnectionDao;
import de.btu.openinfra.backend.db.pojos.meta.DatabaseConnectionPojo;

@Path("/metadata/dbconnections")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class DatabaseConnectionResource {

    @GET
    public List<DatabaseConnectionPojo> get(
            @QueryParam("offset") int offset,
            @QueryParam("size") int size) {
        return new DatabaseConnectionDao(
                OpenInfraSchemas.META_DATA).read(null, offset, size);
    }

    @GET
    @Path("{databaseConnectionId}")
    public DatabaseConnectionPojo get(
            @PathParam("databaseConnectionId") UUID databaseConnectionId) {
        return new DatabaseConnectionDao(
                OpenInfraSchemas.META_DATA).read(
                        null,
                        databaseConnectionId);
    }
    
    @GET
	@Path("count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getCount() {
		return new DatabaseConnectionDao(
				OpenInfraSchemas.META_DATA).getCount();
	}

}


