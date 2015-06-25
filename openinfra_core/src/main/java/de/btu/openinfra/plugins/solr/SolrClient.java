package de.btu.openinfra.plugins.solr;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.apache.solr.client.solrj.impl.HttpSolrServer;

/**
 * Test class for Solr Client. It creates a connection to the SOLR server and
 * starts the Indexer.
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class SolrClient extends HttpServlet {
    
    private static final long serialVersionUID = -7917976359435682750L;
    
    @Override
    public void service(ServletRequest request, ServletResponse response)
            throws ServletException, IOException {
        // write something to the browser
        response.getWriter().write("<html><body>Solr Test Client</body></html>");
        
        
        // connect to the Solr server
        HttpSolrServer solr = new SolrServer("http://localhost:8983/solr")
                                    .connectToServer();
        
        
        // test the connection
        if (solr != null) {
            // start indexing
            Indexer indexer = new Indexer(solr);
            indexer.start();
        }
    }
    
   
    
    
}
