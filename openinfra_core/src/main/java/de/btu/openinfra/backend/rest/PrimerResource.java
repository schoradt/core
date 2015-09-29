package de.btu.openinfra.backend.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;
import de.btu.openinfra.backend.db.pojos.PojoPrimer;

@Path("/v1/primer")
@Produces({ MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY + OpenInfraResponseBuilder.UTF8_CHARSET,
        MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY + OpenInfraResponseBuilder.UTF8_CHARSET })
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class PrimerResource {

    @GET
    public OpenInfraPojo primePojo(@QueryParam("pojoName") String pojoName) {
        return PojoPrimer.primePojoStatically(pojoName);
    }
    
    @GET
    @Path("/names")
    public List<String> getPrimerNames() {
        return PojoPrimer.getPrimerNamesStatically();
    }
}
