package de.btu.openinfra.backend.rest.project;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.PojoPrimer;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path(OpenInfraResponseBuilder.REST_URI_PROJECTS + "/primer")
@Produces({ MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
        MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
        + OpenInfraResponseBuilder.UTF8_CHARSET })
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class PrimerResource {

    @GET
    public OpenInfraPojo primePojo(
            @PathParam("schema") String schema,
            @PathParam("projectId") UUID projectId,
            @QueryParam("pojoClass") String pojoClass,
            @QueryParam("language") String language) {
        return PojoPrimer.primePojoStatically(
                (schema.toUpperCase().equals("METADATA"))
                    ? OpenInfraSchemas.META_DATA
                    : OpenInfraSchemas.valueOf(schema.toUpperCase()),
                projectId,
                PtLocaleDao.forLanguageTag(language.toUpperCase()),
                pojoClass);
    }

    @GET
    @Path("/names")
    public List<String> getPrimerNames(@PathParam("schema") String schema) {
        return PojoPrimer.getPrimerNamesStatically(
                (schema.toUpperCase().equals("METADATA"))
                ? OpenInfraSchemas.META_DATA
                : OpenInfraSchemas.valueOf(schema.toUpperCase()));
    }
}
