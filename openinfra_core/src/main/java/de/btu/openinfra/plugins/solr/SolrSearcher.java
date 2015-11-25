package de.btu.openinfra.plugins.solr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient.RemoteSolrException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import de.btu.openinfra.backend.exception.OpenInfraWebException;
import de.btu.openinfra.plugins.PluginProperties;
import de.btu.openinfra.plugins.solr.db.pojos.SolrResultDbPojo;
import de.btu.openinfra.plugins.solr.db.pojos.SolrResultPojo;
import de.btu.openinfra.plugins.solr.db.pojos.SolrSearchPojo;
import de.btu.openinfra.plugins.solr.enums.SolrIndexEnum;


/**
 * This class is responsible for mapping a query request to Solr search query.
 * After mapping it will send the query to the Solr server and receive the
 * result. The result will be prepared and send back to the client.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class SolrSearcher extends SolrServer {

    /*
     * This variable defines the default start of the results and will be set if
     * no start variable was passed for the search method.
     * ATTENTION: Setting this variable greater than 0 will cut the results with
     * the highest relevance!
     */
    private final int RESULT_WINDOW_START = 0;

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
     * {@link SolrIndexEnum#LOOKUP_FIELD} for all languages will be removed from
     * the highlight map.
     *
     * @param searchPojo The SolrSearchPojo that contains the query.
     */
    public SolrResultPojo search(SolrSearchPojo searchPojo,
            int start, int rows) {

        // create the result object
        SolrResultPojo result = new SolrResultPojo();

        // create a list for the result objects
        List<SolrResultDbPojo> resultList = new ArrayList<SolrResultDbPojo>();

        // create a Solr query object
        SolrQuery query = new SolrQuery();

        // create a Solr parser object
        SolrQueryParser parser = new SolrQueryParser(getSolr());

        // parse the SolrSearchPojo to a Solr syntax query string
        String queryStr = parser.getSolrSyntaxQuery(searchPojo);

        // add the query string to the query object.
        query.setQuery(queryStr);

        // define the return value
        query.set("wt", "json");

        // if the start variable is negative, set it to the default
        if (start < 0) {
            start = RESULT_WINDOW_START;
        }

        // if the rows variable is negative or zero, set it to the default
        if (rows <= 0) {
            // TODO find a better way to retrieve the plugin name
            rows = Integer.parseInt(PluginProperties.getProperty(
                    SolrPropertyKeys.SOLR_DEFAULT_RESULTS_PER_PAGE.getKey(),
                    "Solr"));
        }

        // set the window of the results
        query.setStart(start);
        query.setRows(rows);

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

        try {
            // send the query to the Solr server
            QueryResponse response = getSolr().query(query);

            // get the result list from the Solr server
            SolrDocumentList solrResults = response.getResults();

            // save the elapsed time for retrieving the results
            result.setElapsedTime(response.getElapsedTime());

            // save the count of results
            result.setResultCount(response.getResults().getNumFound());

            // get the highlighting for the request
            Map<String, Map<String, List<String>>> hl =
                    response.getHighlighting();

            // regex pattern to retrieve the lookup fields
            Pattern lookupP = Pattern.compile(
                    "(" + SolrIndexEnum.LOOKUP_FIELD.getString() + "[a-z]{2})");

            // run through the results and add them to a list
            for (int i = 0; i < solrResults.size(); ++i) {
                SolrResultDbPojo rP = new SolrResultDbPojo();

                // save the topic instance id from the result object
                rP.setTopicInstanceId(UUID.fromString(
                        solrResults.get(i).getFieldValue(
                                SolrIndexEnum.TOPIC_INSTANCE_ID.getString())
                                .toString()));

                // save the topic characteristic id from the result object
                rP.setTopicCharacteristicId(UUID.fromString(
                        solrResults.get(i).getFieldValue(
                                SolrIndexEnum.TOPIC_CHARACTERISTIC_ID
                                .getString()).toString()));

                // save the project id from the result object
                rP.setProjectId(UUID.fromString(
                        solrResults.get(i).getFieldValue(
                                SolrIndexEnum.PROJECT_ID.getString())
                                .toString()));

                // only remove the default_search field if it is not the only
                // entry in the highlight field
                if (hl.get(rP.getTopicInstanceId().toString()).size() > 1 &&
                        !hl.get(rP.getTopicInstanceId().toString())
                        .containsKey(SolrIndexEnum.DEFAULT_SEARCH_FIELD
                                .getString())) {
                    // remove the default_search field from the highlighting map
                    // to avoid double entries
                    hl.get(rP.getTopicInstanceId().toString()).remove(
                            SolrIndexEnum.DEFAULT_SEARCH_FIELD.getString());
                }

                // search in the string representation of the hl map for a
                // specified lookup field
                Matcher m = lookupP.matcher(hl.get(rP.getTopicInstanceId()
                        .toString()).toString());

                while (m.find()) {
                    //  remove the matching fields to avoid double entries
                    hl.get(rP.getTopicInstanceId().toString()).remove(
                            m.group());
                }

                // add the highlighted fields
                rP.setHighlight(hl.get(rP.getTopicInstanceId().toString()));

                // add the current result to the result list
                resultList.add(rP);
            }
            result.setDatabaseResult(resultList);
            return result;
        } catch (SolrServerException | IOException | RemoteSolrException e) {
            throw new OpenInfraWebException(e);
        }
    }
}
