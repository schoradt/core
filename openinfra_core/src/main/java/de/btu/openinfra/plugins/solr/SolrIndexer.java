package de.btu.openinfra.plugins.solr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.TopicInstanceDao;
import de.btu.openinfra.backend.db.jpa.model.AttributeValueDomain;
import de.btu.openinfra.backend.db.jpa.model.AttributeValueValue;
import de.btu.openinfra.backend.db.jpa.model.TopicInstance;


/**
 * This class is responsible for the Solr index. It provides functions to add,
 * update and delete documents to the index. It also provides a complete index
 * build function.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class SolrIndexer {

    private SolrServer solrConnection = null;

    /**
     * Default constructor
     */
    public SolrIndexer() {
        // connect to the Solr server
        setSolrConnection(new SolrServer());
    }


    /**
     * This function starts the indexing process.
     */
    public boolean start() {

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
//        indexAll(UUID.fromString("fd27a347-4e33-4ed7-aebc-eeff6dbf1054"));
        // Palatin
//        indexAll(UUID.fromString("7d431941-eece-48ac-bce5-3062d8d32e76"));
        // Test
        indexAll(UUID.fromString("e7d42bff-4e40-4f43-9d1b-1dc5a190cd75"));

        return true;
    }


    /**
     * TODO implement the update of a specific topic instance
     */
    public void update() {

    }


    /**
     * This function will index every configured source.
     *
     * @param projectId The id of the project that should be indexed.
     */
    private boolean indexAll(UUID projectId) {
        System.out.println("retrieving topic instances ... ");

        // get all topic instances
        List<TopicInstance> tiList = new TopicInstanceDao(
                projectId, OpenInfraSchemas.PROJECTS).read();

        System.out.println("Creating Documents ... ");

        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        // run through all topic instances
        for (TopicInstance ti : tiList) {
            // create a new document
            docs.add(createDocument(ti));
        }

        System.out.println("Adding values to index ... ");

        // send the documents to the solr server
        try {
            // add the documents to solr
            getSolrConnection().getSolr().add(docs);
            // commit the changes to the server
            getSolrConnection().getSolr().commit();
        } catch (SolrServerException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("Indexing complete");
        return true;
    }

    /**
     * This method will create a Solr document from the passed topic instance
     * model. The document always have a unique id that is represented by the
     * topic instance id, the project id and topic characteristic id the topic
     * instance belongs to. Further all attribute types and values that belongs
     * to the topic instance will dynamically be added in every translated
     * language.
     *
     * @param ti  the Topic Instance model that should be indexed
     * @return SolrInputDocument the Solr document
     */
    private SolrInputDocument createDocument(TopicInstance ti) {
        SolrInputDocument doc = new SolrInputDocument();

        // add the topic instance id
        // TODO ids can be duplicated in different projects (initial topic
        //      framework) but the data could differ
        doc.addField("id", ti.getId());

        // add the project id
        doc.addField("projectId",
                ti.getTopicCharacteristic().getProject().getId());

        // add the topic characteristic id
        doc.addField("topicCharacteristicId",
                     ti.getTopicCharacteristic().getId());

        // TODO don't forget attribute_value_domain!
        // run through all attribute values
        for (AttributeValueValue avv : ti.getAttributeValueValues()) {
            // for every translation of the value
            for (int i = 0; i < avv.getPtFreeText()
                    .getLocalizedCharacterStrings().size(); i++) {
                // Add the name of the attribute type as field name. The
                // attribute value will be saved as object value.
                try {
                    doc.addField(
                            SolrCharacterConverter.convert(
                                    avv.getAttributeTypeToAttributeTypeGroup()
                                    .getAttributeType().getPtFreeText2()
                                    .getLocalizedCharacterStrings().get(i)
                                    .getFreeText()),
                            avv.getPtFreeText().getLocalizedCharacterStrings()
                            .get(i).getFreeText());
                } catch (ArrayIndexOutOfBoundsException e) {
                    // If we got this exception no translation for either the
                    // attribute type or the attribute value exists. To avoid
                    // mixed language forms in the index we will completely
                    // ignore this attribute type or attribute value.
                }
            }
        }

        // run through all attribute value domains
        for (AttributeValueDomain avd : ti.getAttributeValueDomains()) {
            // for every translation of the domain value
            for (int i = 0; i < avd.getValueListValue().getPtFreeText2()
                    .getLocalizedCharacterStrings().size(); i++) {
                // Add the name of the attribute type as field name. The name of
                // the value list of the domain will be saved as object value.
                try {
                    doc.addField(
                            SolrCharacterConverter.convert(
                                    avd.getAttributeTypeToAttributeTypeGroup()
                                    .getAttributeType().getPtFreeText2()
                                    .getLocalizedCharacterStrings().get(i)
                                    .getFreeText()),
                            avd.getValueListValue().getPtFreeText2()
                            .getLocalizedCharacterStrings().get(i)
                            .getFreeText());
                } catch (ArrayIndexOutOfBoundsException e) {
                    // If we got this exception no translation for either the
                    // attribute type or the attribute value exists. To avoid
                    // mixed language forms in the index we will completely
                    // ignore this attribute type or attribute value.
                }
            }
        }
        return doc;
    }


    /**
     * This method deletes all documents from the Solr index.
     */
    private boolean deleteAllDocuments() {
        try {
            getSolrConnection().getSolr().deleteByQuery("*:*");
            getSolrConnection().getSolr().commit();
        } catch (SolrServerException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private SolrServer getSolrConnection() {
        return this.solrConnection;
    }

    private void setSolrConnection(SolrServer solrConnection) {
        this.solrConnection = solrConnection;
    }
}
