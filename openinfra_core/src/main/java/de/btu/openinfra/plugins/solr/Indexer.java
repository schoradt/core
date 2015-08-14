package de.btu.openinfra.plugins.solr;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.TopicInstanceDao;
import de.btu.openinfra.backend.db.jpa.model.AttributeValueValue;
import de.btu.openinfra.backend.db.jpa.model.LocalizedCharacterString;
import de.btu.openinfra.backend.db.jpa.model.TopicInstance;


/**
 * This class responsible for the Solr index. It provides functions to add,
 * update and delete documents to the index. It also provides a complete index
 * build function.
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class Indexer {
    
    // TODO setting projectId and language only for testing 
    private String projectId;
    private final String language = "de-DE";
    private HttpSolrServer solr = null;

    /**
     * Constructor for passing a server connection.
     * 
     * @param solr
     */
    public Indexer(HttpSolrServer solr) {
        setSolr(solr);
    }
    
    /**
     * Constructor, if no server connection was passed.
     */
    public Indexer() {
        // connect to the Solr server
        setSolr(new SolrServer("http://localhost:8983/solr")
                                    .connectToServer());
        // connection failed
        if (getSolr() == null) {
            // TODO do something!
        }
    }

    
    /**
     * This function starts the indexing process.
     */
    public boolean start() {
        
        // is the Solr server alive?
        if (getSolr() == null) {
            return false;
        }
                
        System.out.println("clear index ...");
        
        // TODO delete old index only for testing
        if (!deleteAllDocuments()) {
            return false;
        }
        
        // TODO check the index for entries
        // TODO if no entry exists, start an initial indexing
        
        System.out.println("add documents ...");
        
        // TODO read projects from meta database
        // Baalbek
        setProjectId("fd27a347-4e33-4ed7-aebc-eeff6dbf1054");
        indexAll();
        // Palatin
        setProjectId("7d431941-eece-48ac-bce5-3062d8d32e76");
        indexAll();
        
        return true;
    }
    
    
    /**
     * TODO implement the update of a specific topic instance
     */
    public void update() {
        
    }
    
    
    /**
     * This function will index every configured source. 
     */
    private boolean indexAll() {
        
        // locale object for the given language
        // TODO 
        //Locale locale = PtLocaleDao.forLanguageTag(language);
        
        System.out.println("retrieving topic instances ... ");
        
        // get all topic instances
        // TODO read() only return 10 results
        List<TopicInstance> tiList = new TopicInstanceDao(
                                    UUID.fromString(getProjectId()), 
                                    OpenInfraSchemas.PROJECTS).read();
        
        System.out.println("Adding values to index ... ");
        
        // all topic instances
        for (TopicInstance ti : tiList) {
            // abort indexing if something went wrong
            if (!indexTopic(ti)) {
                return false;
            }
        }
        
        System.out.println("Indexing complete");
        return true;
    }
    
    /**
     * Will add a specific topic instance to the index.
     *  
     * @param ti  Topic Instance model
     */
    public boolean indexTopic(TopicInstance ti) {
        SolrInputDocument doc = new SolrInputDocument();
        
        // add the topic instance id
        doc.addField("id", ti.getId());
        
        // add the project id
        doc.addField("projectId", getProjectId());
        
        // add the topic characteristic id
        doc.addField("topicCharacteristicId",
                     ti.getTopicCharacteristic().getId());
        
        // TODO don't forget attribute_value_domain!
        // run through all attribute values
        for (AttributeValueValue avv : ti.getAttributeValueValues()) {
            // all translations
            for (LocalizedCharacterString lcs : 
                avv.getPtFreeText().getLocalizedCharacterStrings()) {
                // add the attribute values
                // TODO what about multilinguism?
                doc.addField("attributeValue", lcs.getFreeText());
            }
            /*
            // add the attribute type
            doc.addField("attributeType",
                    avv.getAttributeTypeToAttributeTypeGroup()
                       .getAttributeType()
                       .getPtFreeText2()
                       .getLocalizedCharacterStrings());
                       */
        }
        
        try {
            // add the documents to solr
            solr.add(doc);
            // commit the changes to the server
            solr.commit();
        } catch (SolrServerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    
    /**
     * Will delete all documents in the index.
     */
    private boolean deleteAllDocuments() {
        try {
            solr.deleteByQuery("*:*");
            solr.commit();
        } catch (SolrServerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    private String getProjectId() {
        return projectId;
    }

    private void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    
    private HttpSolrServer getSolr() {
        return solr;
    }

    private void setSolr(HttpSolrServer solr) {
        this.solr = solr;
    }
}
