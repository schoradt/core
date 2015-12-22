package de.btu.openinfra.backend.solr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient.RemoteSolrException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import de.btu.openinfra.backend.OpenInfraProperties;
import de.btu.openinfra.backend.OpenInfraPropertyKeys;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.MetaDataDao;
import de.btu.openinfra.backend.db.pojos.solr.SolrResultDbPojo;
import de.btu.openinfra.backend.db.pojos.solr.SolrResultDocPojo;
import de.btu.openinfra.backend.db.pojos.solr.SolrResultPojo;
import de.btu.openinfra.backend.db.pojos.solr.SolrSearchPojo;
import de.btu.openinfra.backend.exception.OpenInfraWebException;
import de.btu.openinfra.backend.solr.enums.SolrDocumentTypeEnum;
import de.btu.openinfra.backend.solr.enums.SolrIndexEnum;
import de.btu.openinfra.backend.solr.enums.SolrMandatoryEnum;


/**
 * This class is responsible for mapping a query request to Solr search query.
 * After mapping it will send the query to the Solr server and receive the
 * result. The result will be prepared and send back to the client.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class SolrSearcher extends SolrServer {

    /**
     * Default constructor
     */
    public SolrSearcher() {
        // connect to the Solr server
        super();
    }

    /**
     * This will start the search with the passed search term and return the
     * result. Before returning the result it will be prepared in a special way.
     * To avoid doublet the properties of
     * {@link SolrIndexEnum#DEFAULT_SEARCH_FIELD} and
     * {@link SolrIndexEnum#LOOKUP_FIELD} will be removed from the highlight
     * map.
     *
     * @param searchPojo The SolrSearchPojo that contains the query.
     */
    public SolrResultPojo search(SolrSearchPojo searchPojo,
            int start, int rows) {

        // create a Solr query object
        SolrQuery query = new SolrQuery();

        // create a Solr parser object
        SolrQueryParser parser = new SolrQueryParser(getSolr());

        // parse the SolrSearchPojo to a Solr syntax query string
        String queryStr = parser.getSolrSyntaxQuery(searchPojo);

        // add the query string to the query object.
        query.setQuery(queryStr);

        // add filter
        addFilter(searchPojo, query);

        // define the facets
        addFacets(query);

        // define the return value
        query.set("wt", "json");

        // if the start variable is negative, set it to zero
        if (start < 0) {
            start = 0;
        }

        // if the rows variable is negative or zero, set it to the default
        if (rows <= 0) {
            rows = Integer.parseInt(OpenInfraProperties.getProperty(
                    OpenInfraPropertyKeys.SOLR_DEFAULT_RESULTS_PER_PAGE
                    .getKey()));
        }

        // set the window of the results
        query.setStart(start);
        query.setRows(rows);

        // add highlighting
        addHighlighting(query, parser, queryStr);

        try {
            // extract the results
            return mapResult(getSolr().query(query));
        } catch (SolrServerException | IOException | RemoteSolrException e) {
            throw new OpenInfraWebException(e);
        }
    }

    /**
     * This method adds filter to the search query. It will extract the filter
     * from the corresponding fields in the SolrSearchPojo. It will always
     * prefer the positive filter. Only if the positive filter is null the
     * negative filter list will be used.
     *
     * @param searchPojo The SearchPojo that contains the filter.
     * @param query      The SolrQuery that will be used to execute the search.
     */
    private void addFilter(SolrSearchPojo searchPojo, SolrQuery query) {

        if (searchPojo.getPositiveProjectFilter() != null) {
            // set the positive project filter
            query.setFilterQueries(SolrIndexEnum.PROJECT_ID.getString() + ":"
                    + searchPojo.getPositiveProjectFilter());
        } else {
            // set the negative project filter
            if (searchPojo.getNegativeProjectFilter() != null) {
                for(UUID project : searchPojo.getNegativeProjectFilter()) {
                    query.setFilterQueries(
                            SolrMandatoryEnum.MUST_NOT.getString()
                            + SolrIndexEnum.PROJECT_ID.getString() + ":"
                            + project);
                }
            }
        }

        if (searchPojo.getPositiveTopicCharacteristicFilter() != null) {
            // set the positive topic characteristic filter
            query.setFilterQueries(
                    SolrIndexEnum.TOPIC_CHARACTERISTIC_ID.getString() + ":"
                    + searchPojo.getPositiveTopicCharacteristicFilter());
        } else {
            // set the negative topic characteristic filter
            if (searchPojo.getNegativeProjectFilter() != null) {
                for(UUID ti :
                        searchPojo.getNegativeTopicCharacteristicFilter()) {
                    query.setFilterQueries(
                            SolrMandatoryEnum.MUST_NOT.getString()
                            + SolrIndexEnum.TOPIC_CHARACTERISTIC_ID.getString()
                            + ":" + ti);
                }
            }
        }
    }

    /**
     * This method defines facets for the search result. It will only facet on
     * topic characteristics. There will only be entries in the facet that has
     * at least one match in the search result. The facets will be ordered by
     * there count.
     *
     * @param query The SolrQuery that will be used to execute the search.
     */
    private void addFacets(SolrQuery query) {
        // activates faceting
        query.setFacet(true);
        // only include results that has at least one entry
        query.setFacetMinCount(1);
        // return unlimited count of facets
        query.setFacetLimit(-1);
        // sort the facets descending for the count
        query.setFacetSort("count");
        // define the field for the facets
        query.set("facet.field",
                SolrIndexEnum.TOPIC_CHARACTERISTIC_ID.getString());
    }

    /**
     * This method will add highlights to the query.
     *
     * @param query
     * @param parser
     * @param queryStr
     */
    private void addHighlighting(SolrQuery query, SolrQueryParser parser,
            String queryStr) {
        // enable highlighting
        query.setHighlight(true);

        String hlFields = "*";

        List<String> lst = parser.extractFields(queryStr);

        // enable highlighting for every field that is requested in the query
        for (int i = 0; i < lst.size(); i++) {
            if (i == 0) {
                hlFields = lst.get(i);
            } else {
                hlFields += "," + lst.get(i);
            }
        }

        query.set("hl.fl", hlFields);

        // set the enclosing highlight tags
        // TODO add this to properties?
        query.setHighlightSimplePre("<b>");
        query.setHighlightSimplePost("</b>");
    }

    /**
     * This method maps the Solr result to a SolrResultPojo.
     *
     * @param response The QueryResponse from the Solr request.
     * @return         The SolrResultPojo that contains all necessary
     *                 informations from the QueryResponse.
     */
    private SolrResultPojo mapResult(QueryResponse response) {
        // get the result list from the Solr server
        SolrDocumentList solrResults = response.getResults();

        // create lists for the result objects
        List<SolrResultDbPojo> dbResultList = new ArrayList<SolrResultDbPojo>();
        List<SolrResultDocPojo> docResultList =
                new ArrayList<SolrResultDocPojo>();

        // create the result object
        SolrResultPojo result = new SolrResultPojo();

        // run through the results and add them to a list
        for (int i = 0; i < solrResults.size(); ++i) {
            // if our result is a database document
            if (solrResults.get(i).getFieldValue(
                    SolrIndexEnum.DOC_TYPE.getString()).toString()
                    .equals(SolrDocumentTypeEnum.DATABASE.toString())) {
                // map it to a SolrResultDbPojo
                dbResultList.add(mapDatabase(
                        solrResults.get(i), response.getHighlighting()));
            }

            // if our result is a file document
            if (solrResults.get(i).getFieldValue(
                    SolrIndexEnum.DOC_TYPE.getString()).toString()
                    .equals(SolrDocumentTypeEnum.FILE.toString())) {
                // map it to a SolrResultDocumentPojo
                docResultList.add(mapFile(
                        solrResults.get(i), response.getHighlighting()));
            }
        }

        // add the mapped lists to the result
        result.setDatabaseResult(dbResultList);
        result.setDocumentResult(docResultList);

        // save the elapsed time for retrieving the results
        result.setElapsedTime(response.getElapsedTime());

        // save the count of results
        result.setResultCount(response.getResults().getNumFound());

        // save the facets
        Map<String, Long> facets = new LinkedHashMap<String, Long>();
        for(FacetField ff : response.getFacetFields()) {
            for (FacetField.Count ffCount : ff.getValues()) {
                facets.put(ffCount.getName(), ffCount.getCount());
            }
        }

        // add the facets to the result
        result.setFacets(facets);

        return result;
    }

    /**
     * This method maps a database result to a SolrResultDbPojo.
     *
     * @param solrDocument The document that should be mapped.
     * @param hl           The highlighted part.
     * @return             The mapped SolrResultDbPojo.
     */
    private SolrResultDbPojo mapDatabase(
            SolrDocument solrDocument,
            Map<String, Map<String, List<String>>> hl) {
        SolrResultDbPojo rP = new SolrResultDbPojo();

        // save the topic instance id from the result object
        rP.setTopicInstanceId(UUID.fromString(
                solrDocument.getFieldValue(
                        SolrIndexEnum.ID.getString())
                        .toString()));

        // save the topic characteristic id from the result object
        rP.setTopicCharacteristicId(UUID.fromString(
                solrDocument.getFieldValue(
                        SolrIndexEnum.TOPIC_CHARACTERISTIC_ID
                        .getString()).toString()));

        // save the project id from the result object
        rP.setProjectId(UUID.fromString(
                solrDocument.getFieldValue(
                        SolrIndexEnum.PROJECT_ID.getString())
                        .toString()));

        // extract meta data of the result object
        MetaDataDao mdDao = new MetaDataDao(
                rP.getProjectId(), OpenInfraSchemas.PROJECTS);

        // read meta data for the topic instance
        rP.setTopicInstanceMetaData(
                mdDao.read(rP.getTopicInstanceId()));

        // read meta data for the topic characteristic
        rP.setTopicCharacteristicMetaData(
                mdDao.read(rP.getTopicCharacteristicId()));

        // only remove the default_search field if it is not the only
        // entry in the highlight field
        if (hl.get(rP.getTopicInstanceId().toString()).size() > 1 &&
                hl.get(rP.getTopicInstanceId().toString())
                .containsKey(SolrIndexEnum.DEFAULT_SEARCH_FIELD
                        .getString())) {
            // remove the default_search field from the highlighting map
            // to avoid double entries
            hl.get(rP.getTopicInstanceId().toString()).remove(
                    SolrIndexEnum.DEFAULT_SEARCH_FIELD.getString());
        }

        // remove the lookup field from the highlighting map to avoid double
        // entries
        hl.get(rP.getTopicInstanceId().toString()).remove(
                SolrIndexEnum.LOOKUP_FIELD.getString());

        // add the highlighted fields
        rP.setHighlight(hl.get(rP.getTopicInstanceId().toString()));

        // return the result
        return rP;
    }

    /**
     * This method maps a database result to a SolrResultDocPojo.
     *
     * @param solrDocument The document that should be mapped.
     * @param hl           The highlighted part.
     * @return             The mapped SolrResultDocPojo.
     */
    private SolrResultDocPojo mapFile(
            SolrDocument solrDocument,
            Map<String, Map<String, List<String>>> hl) {

        SolrResultDocPojo rP = new SolrResultDocPojo();

        // save the id of the file
        rP.setId(UUID.fromString(
                solrDocument.getFieldValue(
                        SolrIndexEnum.ID.getString())
                        .toString()));

        // save the hash code of the file
        rP.setHashCode(solrDocument.getFieldValue(
                SolrIndexEnum.FILE_HASH.getString()).toString());

        // only remove the default_search field if it is not the only
        // entry in the highlight field
        if (hl.get(rP.getId().toString()).size() > 1 &&
                hl.get(rP.getId().toString())
                .containsKey(SolrIndexEnum.DEFAULT_SEARCH_FIELD
                        .getString())) {
            // remove the default_search field from the highlighting map
            // to avoid double entries
            hl.get(rP.getId().toString()).remove(
                    SolrIndexEnum.DEFAULT_SEARCH_FIELD.getString());
        }

        // remove the lookup field from the highlighting map to avoid double
        // entries
        hl.get(rP.getId().toString()).remove(
                SolrIndexEnum.LOOKUP_FIELD.getString());

        // add the highlighted fields
        rP.setHighlight(hl.get(rP.getId().toString()));

        // return the result
        return rP;
    }


}
