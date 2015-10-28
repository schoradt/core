package de.btu.openinfra.plugins.solr.rest.view;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;




/**
 * This class represents and implements the Search resource.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */

@Path(OpenInfraResponseBuilder.REST_URI_SEARCH)
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
    MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET})
public class SearchResource {

//    @GET
//    @Template(name="/views/SearchResult.jsp")
//    public List<Result> get(
//            @QueryParam("query") String query,
//            @QueryParam("language") String locale) {
//        return new de.btu.openinfra.plugins.solr.rest.SearchResource()
//            .get(query, locale);
//    }
}

