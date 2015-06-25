package de.btu.openinfra.plugins.solr;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import de.btu.openinfra.backend.db.daos.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.daos.TopicCharacteristicDao;

/**
 * This class responsible for sending search queries to the Solr server.
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class Searcher {
    
    private HttpSolrServer solr = null;
    private static final String SEARCH_FIELD = "attributeValue";
    
    
    /**
     * Constructor for passing a server connection.
     * 
     * @param solr
     */
    public Searcher(HttpSolrServer solr) {
        setSolr(solr);
    }
    
    /**
     * Constructor, if no server connection was passed.
     */
    public Searcher() {
        // connect to the Solr server
        setSolr(new SolrServer("http://localhost:8983/solr")
                                    .connectToServer());
        // connection failed
        if (getSolr() == null) {
            // TODO do something!
        }
    }
    
    /**
     * This will start the search with the passed search term. The field that
     * should be searched is always the same.
     * 
     * @param searchTerm the raw query string
     * @param locale     the locale where the query was past at
     */
    public List<Result> search(String searchTerm, String locale) {
        
        // create a list for the result objects
        
        List<Result> resultList = new LinkedList<Result>();
        // TODO use a Tokenizer to split the search Term into different tokens
        
        // is the Solr server alive?
        if (getSolr() == null) {
            // TODO: change this
            return null;
        }
        
        // create a Solr query object
        SolrQuery query = new SolrQuery();
        
        // add a proximity to the query if it is not empty
        // TODO make this configurable
        if (!searchTerm.isEmpty()) {
            searchTerm += "~0.8";
        }
        
        // add the search term
        query.setQuery(searchTerm);
        // define the return value
        query.set("wt", "xml");
        // set the default search field
        query.set("df", SEARCH_FIELD);
        // enable highlighting
        query.setHighlight(true);
        // set the field for highlighting
        query.set("hl.fl", SEARCH_FIELD);
        // set the enclosing highlight tags
        query.setHighlightSimplePre("<b>");
        query.setHighlightSimplePost("</b>");
        
        QueryResponse response = null;
        try {
            response = solr.query(query);
            
            SolrDocumentList solrResults = response.getResults();

            // run through the results and add them to a list 
            for (int i = 0; i < solrResults.size(); ++i) {
                // create a result object
                Result result = new Result();
                
                // add the projectId
                result.setProjectId(solrResults.get(i).getFieldValue(
                        result.PROJECT_ID_IDENTIFIER).toString());
                
                // add the topicInstanceId
                result.setTopicInstanceId(solrResults.get(i).getFieldValue(
                        result.TOPIC_INSTANCE_ID_IDENTIFIER).toString());
                
                // add the highlighted result out of the response map
                result.setResult(
                        response.getHighlighting()
                                .get(result.getTopicInstanceId())
                                .get(result.RESULT_IDENTIFIER));
                
                // TODO: it should also be possible, to find objects in system
                // add the topic characteristic object
                result.setTopicCharacteristic(
                        new TopicCharacteristicDao(
                                UUID.fromString(result.getProjectId()),
                                OpenInfraSchemas.PROJECTS)
                        .read(PtLocaleDao.forLanguageTag(locale),
                                UUID.fromString(
                                        solrResults
                                        .get(i)
                                        .getFieldValue(
                                                result
                                                .TOPIC_CHARACTERISTIC_ID_IDENTIFIER).toString())));
                // add the current result to the result list
                resultList.add(result);
            }
        } catch (SolrServerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // return the highlighted result list
        return resultList;
        
    }
    
    private HttpSolrServer getSolr() {
        return solr;
    }
    
    private void setSolr(HttpSolrServer solr) {
        this.solr = solr;
    }
    
}
