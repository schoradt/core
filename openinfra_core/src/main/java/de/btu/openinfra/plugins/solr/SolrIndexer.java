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
import de.btu.openinfra.backend.db.jpa.model.LocalizedCharacterString;
import de.btu.openinfra.backend.db.jpa.model.TopicInstance;
import de.btu.openinfra.backend.exception.OpenInfraWebException;


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
     * TODO this is a temporary method that can be deleted in production mode.
     *
     * This function starts the indexing process.
     */
    public boolean start() {

        // TODO debug message, remove this
        System.out.println("clear index ...");

        // TODO delete old index only for testing
//        deleteAllDocuments();

        System.out.println("add documents ...");

        // TODO read projects from meta database
        // Baalbek
//        indexAll(UUID.fromString("fd27a347-4e33-4ed7-aebc-eeff6dbf1054"));
        // Palatin
//        indexAll(UUID.fromString("7d431941-eece-48ac-bce5-3062d8d32e76"));
        // Test
        refreshIndex(UUID.fromString("e7d42bff-4e40-4f43-9d1b-1dc5a190cd75"));

        return true;
    }

    /**
     * This function will index every topic instance of the passed project.
     *
     * @param projectId The id of the project that should be indexed.
     */
    public void refreshIndex(UUID projectId) {

        // get all topic instances
        List<TopicInstance> tiList = new TopicInstanceDao(
                projectId, OpenInfraSchemas.PROJECTS).read();

        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        // run through all topic instances
        for (TopicInstance ti : tiList) {
            // create a new document
            docs.add(createDocument(ti));
        }

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
    }

    /**
     * This method will create a Solr document from the passed topic instance
     * model. The document always have a unique id that is represented by the
     * topic instance id, the project id and topic characteristic id the topic
     * instance belongs to. Further all attribute types and values that belongs
     * to the topic instance will dynamically be added in every translated
     * language.
     *
     * This method avoid committing the document to Solr, because it is more
     * performant to commit all docs at once.
     *
     * @param ti  The Topic Instance model that should be indexed.
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

        // run through all attribute values
        for (AttributeValueValue avv : ti.getAttributeValueValues()) {
            // add the current attribute value value to the doc
            addValuesToDoc(
                    avv.getAttributeTypeToAttributeTypeGroup()
                        .getAttributeType().getPtFreeText2()
                        .getLocalizedCharacterStrings(),
                    avv.getPtFreeText().getLocalizedCharacterStrings(),
                    doc);
        }

        // run through all attribute value domains
        for (AttributeValueDomain avd : ti.getAttributeValueDomains()) {
            // add the current attribute value domain to the doc
            addValuesToDoc(
                    avd.getAttributeTypeToAttributeTypeGroup()
                        .getAttributeType().getPtFreeText2()
                        .getLocalizedCharacterStrings(),
                    avd.getValueListValue().getPtFreeText2()
                        .getLocalizedCharacterStrings(),
                    doc);
        }
        return doc;
    }

    /**
     * This method will update a specific topic instance in the Solr index. It
     * simply calls the method to create documents, because this will do the
     * update as well.
     *
     * @param ti The topic instance that should be updated.
     */
    public void updateDocument(TopicInstance ti) {
        Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        // call the create method to update the document
        docs.add(createDocument(ti));

        // send the updated document to the solr server
        try {
            // add the documents to solr
            getSolrConnection().getSolr().add(docs);
            // commit the changes to the server
            getSolrConnection().getSolr().commit();
        } catch (SolrServerException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * This method deletes a specific document from the Solr index.
     *
     * @param ti The Topic Instance model that should be indexed.
     */
    public void deleteDocument(TopicInstance ti) {
        try {
            getSolrConnection().getSolr().deleteById(ti.getId().toString());
            getSolrConnection().getSolr().commit();
        } catch (SolrServerException | IOException e) {
            throw new OpenInfraWebException(e);
        }
    }

    /**
     * This method deletes all documents from the Solr index.
     *
     * @return True if the deletion was successful, else false.
     */
    public void deleteAllDocuments() {
        try {
            getSolrConnection().getSolr().deleteByQuery("*:*");
            getSolrConnection().getSolr().commit();
        } catch (SolrServerException | IOException e) {
            throw new OpenInfraWebException(e);
        }
    }

    /**
     * This method adds the passed type and value to the passed Solr document.
     * It will add the types and values for every language separately. It will
     * also react on values that have a undefined locale (xx) and save them into
     * different fields, depending on the type translation.
     *
     * @param type
     * @param value
     * @param doc
     */
    private void addValuesToDoc(
            List<LocalizedCharacterString> type,
            List<LocalizedCharacterString> value,
            SolrInputDocument doc
            ) {
        // for every translation of the attribute type
        for (int i = 0; i < type.size(); i++) {
            // set the value locale to the counter
            int valueLocale = i;

            // check if the locale of the attribute value is xx
            if (value.get(0).getPtLocale().getLanguageCode().getLanguageCode()
                    .equals("xx")) {
                // set the value locale to 0, because their is only one
                valueLocale = 0;
            }

            // Add the name of the attribute type as field name. The
            // attribute value will be saved as object value.
            try {
                doc.addField(
                        SolrCharacterConverter.convert(
                                type.get(i)
                                .getFreeText()),
                        value.get(valueLocale).getFreeText());
            } catch (ArrayIndexOutOfBoundsException e) {
                // If we got this exception no translation for either the
                // attribute type or the attribute value exists. To avoid
                // mixed language forms in the index we will completely
                // ignore this attribute type or attribute value.
            }
        }
    }

    private SolrServer getSolrConnection() {
        return this.solrConnection;
    }

    private void setSolrConnection(SolrServer solrConnection) {
        this.solrConnection = solrConnection;
    }
}
