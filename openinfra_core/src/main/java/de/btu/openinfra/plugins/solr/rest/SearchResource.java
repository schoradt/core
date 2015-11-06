package de.btu.openinfra.plugins.solr.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;
import de.btu.openinfra.plugins.solr.SolrIndexer;
import de.btu.openinfra.plugins.solr.SolrSearcher;
import de.btu.openinfra.plugins.solr.db.pojos.SolrIndexPojo;
import de.btu.openinfra.plugins.solr.db.pojos.SolrResultPojo;
import de.btu.openinfra.plugins.solr.db.pojos.SolrSearchPojo;

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
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class SearchResource {

    @POST
    public List<SolrResultPojo> get(
            @QueryParam("language") String locale,
            @QueryParam("start") int start,
            @QueryParam("rows") int rows,
            SolrSearchPojo searchPojo) {
        return new SolrSearcher().search(searchPojo, start, rows);
    }

    @POST
    @Path("/index")
    public boolean index(
            SolrIndexPojo projects) {
        SolrIndexer indexer = new SolrIndexer();
        return indexer.indexProjects(projects);
    }
}
