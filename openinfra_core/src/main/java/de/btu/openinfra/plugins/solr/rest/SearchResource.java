package de.btu.openinfra.plugins.solr.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.btu.openinfra.plugins.solr.Indexer;
import de.btu.openinfra.plugins.solr.Result;
import de.btu.openinfra.plugins.solr.Searcher;

/**
 * This class represents and implements the Search resource.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@Path("/search")
@Produces({MediaType.APPLICATION_JSON})
public class SearchResource {

    @GET
    public List<Result> get(
            @QueryParam("query") String query,
            @QueryParam("language") String locale) {
        return new Searcher().search(query, locale);
    }

    @GET
    @Path("/index")
    @Produces({MediaType.TEXT_PLAIN})
    public boolean index() {
        Indexer indexer = new Indexer();
        return indexer.start();
    }
}
