package de.btu.openinfra.plugins.solr.rest.view;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.mvc.Template;

import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;
import de.btu.openinfra.plugins.solr.Result;




/**
 * This class represents and implements the Search resource.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */

@Path(OpenInfraResponseBuilder.REST_URI_SEARCH)
@Produces(MediaType.TEXT_HTML +
        OpenInfraResponseBuilder.UTF8_CHARSET +
        OpenInfraResponseBuilder.HTML_PRIORITY)
public class SearchResource {

    @GET
    @Template(name="/views/SearchResult.jsp")
    public List<Result> get(
            @QueryParam("query") String query,
            @QueryParam("language") String locale) {
        return new de.btu.openinfra.plugins.solr.rest.SearchResource()
            .get(query, locale);
    }
}

