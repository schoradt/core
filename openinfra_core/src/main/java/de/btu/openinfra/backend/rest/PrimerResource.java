package de.btu.openinfra.backend.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.btu.openinfra.backend.db.PojoPrimer;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@Path("/v1/primer")
@Produces({ MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY + OpenInfraResponseBuilder.UTF8_CHARSET,
        MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY + OpenInfraResponseBuilder.UTF8_CHARSET })
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class PrimerResource {

    @GET
    public OpenInfraPojo primePojo(@QueryParam("pojoClass") String pojoClass) {
        return PojoPrimer.primePojoStatically(pojoClass);
    }
    
    @GET
    @Path("/names")
    public List<String> getPrimerNames() {
        return PojoPrimer.getPrimerNamesStatically();
    }
}
