package de.btu.openinfra.backend.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;
import de.btu.openinfra.backend.db.pojos.PojoPrimer;

@Path(OpenInfraResponseBuilder.REST_URI_DEFAULT + "/primer")
@Produces({ MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY + OpenInfraResponseBuilder.UTF8_CHARSET,
        MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY + OpenInfraResponseBuilder.UTF8_CHARSET })
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class PrimerResource {

    @GET
    public OpenInfraPojo primePojo(@QueryParam("pojoName") String pojoName) {
        return PojoPrimer.primePojoStatically(pojoName);
    }
}
