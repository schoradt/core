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
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

/**
 * This class represents and implements the resources for pojo primer in the
 * project schema. For the other schemas use the class
 * 'de.btu.openinfra.backend.rest/PrimerResource' instead. The
 * reason therefore lay in the fact that the regex expression
 * '/v1/{schema:(projects|system|rbac|metadata)}{optional:(/?)}
 * {projectId:([0-9a-f]{8}-([0-9a-f]{4}-){3}[0-9a-f]{12})?}'
 * does not work for the schema projects.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@Path(OpenInfraResponseBuilder.REST_URI_PROJECTS + "/primer")
@Produces({ MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
        MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
        + OpenInfraResponseBuilder.UTF8_CHARSET })
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class PrimerResource {

    /**
     * This resource provides a primer Pojo for the requested class. The primer
     * is an empty image of the requested class Pojo. This can be used as a base
     * for POST requests. Further the resource awaits a language definition to
     * fill in the correct language informations whenever they are required.
     * <ul>
     *   <li>rest/v1/projects/[uuid]/primer?language=de-DE&pojoClass=attributeType</li>
     * </ul>
     *
     * @response.representation.200.qname The specified Pojo class.
     * @response.representation.200.doc   This is the representation returned by
     *                                    default.
     *
     * @response.representation.500.qname OpenInfraWebException
     * @response.representation.500.doc   An internal error occurs if the
     *                                    backend runs into an unexpected
     *                                    exception.
     *
     * @param schema    The schema name the request should be processed at.
     * @param projectId The id of the project.
     * @param pojoClass The name of the class a Pojo should be created for.
     * @param language  The language of the localized objects.
     * @return
     */
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
                language,
                pojoClass);
    }

    /**
     * This resource provides a list of class names that can be used as value
     * for the <i>pojoClass</i> parameter at the primer resource.
     * <ul>
     *   <li>rest/v1/projects/[uuid]/primer?language=de-DE&pojoClass=attributeType</li>
     * </ul>
     *
     * @param schema The schema name the request should be processed at.
     * @return       A list of class names as string.
     *
     * @response.representation.200.qname A list of class names.
     * @response.representation.200.doc   This is the representation returned by
     *                                    default.
     *
     * @response.representation.500.qname OpenInfraWebException
     * @response.representation.500.doc   An internal error occurs if the
     *                                    backend runs into an unexpected
     *                                    exception.
     */
    @GET
    @Path("/names")
    public List<String> getPrimerNames(@PathParam("schema") String schema) {
        return PojoPrimer.getPrimerNamesStatically(
                (schema.toUpperCase().equals("METADATA"))
                ? OpenInfraSchemas.META_DATA
                : OpenInfraSchemas.valueOf(schema.toUpperCase()));
    }
}
