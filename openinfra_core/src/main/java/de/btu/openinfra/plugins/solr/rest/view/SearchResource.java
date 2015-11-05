package de.btu.openinfra.plugins.solr.rest.view;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.mvc.Template;

import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;




/**
 * This class represents and implements the Search resource.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */

@Path("/v1/searchresult")
@Produces(MediaType.TEXT_HTML +
        OpenInfraResponseBuilder.UTF8_CHARSET +
        OpenInfraResponseBuilder.HTML_PRIORITY)
public class SearchResource {

    @GET
    @Template(name="/views/SearchResult.jsp")
    public String getView(
            @QueryParam("language") String locale,
            @QueryParam("query") String query) {
        return query;
    }
}

