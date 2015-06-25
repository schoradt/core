package de.btu.openinfra.plugins.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

/**
 * This class provides the connection to the Solr server.
 * 
 *  @author <a href="http://www.b-tu.de">BTU</a> DBIS
 */
public class SolrServer {
    
    private static HttpSolrServer solr;
    private static String url;
    
    /**
     * The constructor saves the URL to the Solr server.
     * 
     * @param url  the URL string for the connection to the Solr server
     */
    public SolrServer(String url) {
        // set the URL parameter
        setUrl(url);
    }
    
    /**
     * Creates a connection to the Solr server and returns the connection
     * object. If the connection can not be established, NULL will be returned.
     * 
     * @return  HttpSolrServer  connection object to Solr server or NULL
     */
    public HttpSolrServer connectToServer() {
        // create the Solr object
        setSolr(new HttpSolrServer(getUrl()));
        
        // test the connection and return the object
        if (testConnection()) {
            return getSolr();
        } else {
            return null;
        }
    }
    
    /**
     * This function pings the Solr server. False will be returned if the
     * server is not reachable.
     * 
     * @return  boolean  false if no connection is possible, else true
     */
    private boolean testConnection() {
        try {
            // just ping the server to test the connection
            solr.ping().getElapsedTime();
        } catch (SolrServerException e) {
            // TODO Debug message should not be here
            System.out.println("The Solr server on " + url + " is not reachable."
                                + " Please be sure that the Solr server is"
                                + " running.");
            return false;
        } catch (IOException e) {
            // what ever will lead to this point
            e.printStackTrace();
            return false;
        }
        // Solr server seems to be alive, no connection error occurred
        return true;
    }
    
    private static HttpSolrServer getSolr() {
        return solr;
    }
    
    private static void setSolr(HttpSolrServer solr) {
        SolrServer.solr = solr;
    }
    
    private String getUrl() {
        return url;
    }

    private void setUrl(String url) {
        SolrServer.url = url;
    }
}
