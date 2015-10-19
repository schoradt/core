package de.btu.openinfra.backend.rest;

import java.util.List;

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

/**
 * This class supports only the schemas system, rbac or metadata. For the
 * schema projects use the class
 * 'de.btu.openinfra.backend.rest.project/PrimerResource' instead.
 * The reason therefore lay in the fact that the regex expression
 * '/v1/{schema:(projects|system|rbac|metadata)}{optional:(/?)}{projectId:([0-9a-f]{8}-([0-9a-f]{4}-){3}[0-9a-f]{12})?}'
 * does not work for the schema projects.
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@Path(OpenInfraResponseBuilder.REST_URI_PRIMER + "/primer")
@Produces({ MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
        MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
        + OpenInfraResponseBuilder.UTF8_CHARSET })
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class PrimerResource {

    @GET
    public OpenInfraPojo primePojo(
            @PathParam("schema") String schema,
            @QueryParam("pojoClass") String pojoClass,
            @QueryParam("language") String language) {
        return PojoPrimer.primePojoStatically(
                (schema.toUpperCase().equals("METADATA"))
                    ? OpenInfraSchemas.META_DATA
                    : OpenInfraSchemas.valueOf(schema.toUpperCase()),
                null,
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
