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
import de.btu.openinfra.plugins.solr.enums.SolrIndexEnum;


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
        deleteAllDocuments();

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
            docs.add(createOrUpdateDocument(ti));
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
     * This method will create or update a Solr document from the passed topic
     * instance model. The document always have a unique id that is represented
     * by the topic instance id. Every Solr document will hold the information
     * about the project id, the topic characteristic id and all attribute types
     * and values that belongs to the topic instance.
     *
     * The attribute types will be used as field name. The attribute values will
     * be used as field values. Every field will be saved in each language it is
     * translated to. If only the attribute type or the attribute value is
     * translated, the whole tuple will not be indexed. It should be avoided to
     * mix the different languages in the index.
     *
     * This method does not commit the document to Solr, because it is
     * performing more effectively to commit all docs at once.
     *
     * @param ti  The Topic Instance model that should be indexed.
     * @return SolrInputDocument the Solr document
     */
    public SolrInputDocument createOrUpdateDocument(TopicInstance ti) {
        SolrInputDocument doc = new SolrInputDocument();

        // TODO use same finals as in SolrSearcher!
        // add the topic instance id as document id
        doc.addField(SolrIndexEnum.TOPIC_INSTANCE_ID.getString(), ti.getId());

        // add the project id
        doc.addField(SolrIndexEnum.PROJECT_ID.getString(),
                ti.getTopicCharacteristic().getProject().getId());

        // add the topic characteristic id
        doc.addField(SolrIndexEnum.TOPIC_CHARACTERISTIC_ID.getString(),
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
     * It will add the types and values for every language separately. Values
     * that has no translated type will be stored in a global field.
     * Values that has an undefined locale (xx) will be saved into different
     * fields, depending on the type translation.
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

        // check if the language of the attribute value is xx
        int x = containsAtPosition(value, "xx");
        if (x > -1) {
            // save the attribute value for every existing translation of the
            // attribute type
            for (int k = 0; k < type.size(); k++) {
                doc.addField(
                        SolrCharacterConverter.convert(
                                type.get(k)
                                .getFreeText()),
                        value.get(x).getFreeText());
            }
            // Abort here because the value should only exists in one language.
            return;
        }

        // Run through every other translation of the attribute value.
        for (int i = 0; i < value.size(); i++) {
            try {

                // Retrieve the position of the attribute type with the language
                // of the attribute value.
                int z = containsAtPosition(type, value.get(i).getPtLocale()
                        .getLanguageCode().getLanguageCode());

                if (z > -1) {
                    // Add the type and the value with the same language.
                    doc.addField(
                            SolrCharacterConverter.convert(
                                    type.get(z)
                                    .getFreeText()),
                                    value.get(i).getFreeText());
                } else {
                    // No translation for the attribute type was specified. To
                    // avoid information loss we will save all this values
                    // together in a special field.
                    doc.addField(SolrCharacterConverter.convert(
                            SolrIndexEnum.NO_TRANSLATION_FIELD.toString()),
                            value.get(i).getFreeText());
                }

            } catch (ArrayIndexOutOfBoundsException e) {
                throw new OpenInfraWebException(e);
            }
        }
    }

    /**
     * This method search in the passed list for an entry with the specified
     * language code and return its position in the list.
     *
     * @param lst  A list with LocalizedCharacterStrings where the language code
     *             should be searched at.
     * @param lang The language code
     * @return     The position in the passed list where the specified language
     *             was found at or -1 for no match.
     */
    private int containsAtPosition(
            List<LocalizedCharacterString> lst, String lang) {
        for (int i = 0; i < lst.size(); i++) {
            if (lst.get(i).getPtLocale().getLanguageCode().getLanguageCode()
                    .equals(lang)) {
                return i;
            }
        }
        return -1;
    }

    private SolrServer getSolrConnection() {
        return this.solrConnection;
    }

    private void setSolrConnection(SolrServer solrConnection) {
        this.solrConnection = solrConnection;
    }
}
