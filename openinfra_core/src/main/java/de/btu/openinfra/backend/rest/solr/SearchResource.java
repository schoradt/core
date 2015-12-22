package de.btu.openinfra.backend.rest.solr;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.btu.openinfra.backend.db.pojos.solr.SolrIndexPojo;
import de.btu.openinfra.backend.db.pojos.solr.SolrResultPojo;
import de.btu.openinfra.backend.db.pojos.solr.SolrSearchPojo;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;
import de.btu.openinfra.backend.solr.SolrIndexer;
import de.btu.openinfra.backend.solr.SolrSearcher;
import de.btu.openinfra.backend.solr.SolrSuggester;

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

    /**
     * This resource awaits a SolrSearchPojo that will be the base for a search
     * request on the Solr index. There are two kinds of filters for projects
     * and topic characteristics. The first is a positive filter that will lead
     * to results that belong to the defined filter. The second is a negative
     * filter list that will lead to results that do not belong to the defined
     * filters. The parameter UUID and TRID of the SolrSearchPojo must not be
     * set.
     *
     * @param locale     The language of the search request as string.
     * @param start      The offset parameter for the results.
     * @param rows       The count of results that should be returned.
     * @param searchPojo The SearchPojo that contains the request.
     * @return           A SolrResultPojo that contains the result, a query
     *                   time, a count of the retrieved results, meta data for
     *                   the participated topic instances and topic
     *                   characteristics and facets for the topic
     *                   characteristics.
     *
     * @response.representation.200.qname SolrResultPojo
     * @response.representation.200.doc   This is the representation returned by
     *                                    default.
     *
     * @response.representation.409.qname OpenInfraEntityException
     * @response.representation.409.doc   This is the representation returned by
     *                                    default.
     *
     * @response.representation.500.qname OpenInfraWebException
     * @response.representation.500.doc   An internal error occurs if the
     *                                    connection to the Solr server is
     *                                    interrupted or the specified POJO is
     *                                    empty or faulty.
     */
    @POST
    public SolrResultPojo get(
            @QueryParam("language") String locale,
            @QueryParam("start") int start,
            @QueryParam("rows") int rows,
            SolrSearchPojo searchPojo) {
        return new SolrSearcher().search(searchPojo, start, rows);
    }

    /**
     * This resource starts the process that generates the Solr index for
     * files.
     *
     * @return         True if the process was successful.
     *
     * @response.representation.200.qname boolean
     * @response.representation.200.doc   True is the representation returned by
     *                                    default.
     *
     * @response.representation.500.qname OpenInfraSolrException
     * @response.representation.500.doc   An internal error occurs while
     *                                    indexing the database. This error can
     *                                    not be specified because every server
     *                                    side error will lead to this
     *                                    exception.
     */
    @GET
    @Path("/index/files")
    public boolean index() {
        return new SolrIndexer().indexFiles();
    }

    /**
     * This resource starts the process that generates the Solr index for
     * projects. The specified SolrIndexPojo can contain a list of project ids
     * the index should created for. If the list is empty all projects will be
     * indexed. The parameter UUID and TRID of the SolrSearchPojo must not be
     * set.
     *
     * @param projects The SolrIndexPojo that contains the list of project ids
     *                 that should be indexed.
     * @return         True if the process was successful.
     *
     * @response.representation.200.qname boolean
     * @response.representation.200.doc   True is the representation returned by
     *                                    default.
     *
     * @response.representation.500.qname OpenInfraSolrException
     * @response.representation.500.doc   An internal error occurs while
     *                                    indexing the database. This error can
     *                                    not be specified because every server
     *                                    side error will lead to this
     *                                    exception.
     */
    @POST
    @Path("/index/projects")
    public boolean index(
            SolrIndexPojo projects) {
        return new SolrIndexer().indexProjects(projects);
    }

    /**
     * This resource starts the process that generates the Solr index.
     *
     * @param clean    If true the index will be completely cleared before
     *                 indexing.
     * @return         True if the process was successful.
     *
     * @response.representation.200.qname boolean
     * @response.representation.200.doc   True is the representation returned by
     *                                    default.
     *
     * @response.representation.500.qname OpenInfraSolrException
     * @response.representation.500.doc   An internal error occurs while
     *                                    indexing the database. This error can
     *                                    not be specified because every server
     *                                    side error will lead to this
     *                                    exception.
     */
    @GET
    @Path("/index")
    public boolean index(
            @QueryParam("clean") boolean clean) {
        return new SolrIndexer().indexAll(clean);
    }

    /**
     * This resource provides a suggestion on the Solr index for a specified
     * query string.
     *
     * @response.representation.200.qname A list of strings.
     * @response.representation.200.doc   This is the representation returned by
     *                                    default.
     *
     * @response.representation.500.qname OpenInfraWebException
     * @response.representation.500.doc   An internal error occurs if the
     *                                    connection to the Solr server is
     *                                    interrupted.
     */
    @GET
    @Path("/suggest")
    public List<String> getSuggestion(
            @QueryParam("q") String qString) {
        return new SolrSuggester().getSuggestion(qString);
    }
}
