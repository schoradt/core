package de.btu.openinfra.plugins.solr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;

import de.btu.openinfra.backend.OpenInfraTime;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.TopicInstanceDao;
import de.btu.openinfra.backend.db.daos.meta.ProjectsDao;
import de.btu.openinfra.backend.db.jpa.model.AttributeValueDomain;
import de.btu.openinfra.backend.db.jpa.model.AttributeValueValue;
import de.btu.openinfra.backend.db.jpa.model.LocalizedCharacterString;
import de.btu.openinfra.backend.db.jpa.model.TopicInstance;
import de.btu.openinfra.backend.db.jpa.model.meta.Projects;
import de.btu.openinfra.backend.exception.OpenInfraExceptionTypes;
import de.btu.openinfra.backend.exception.OpenInfraWebException;
import de.btu.openinfra.plugins.solr.db.pojos.SolrIndexPojo;
import de.btu.openinfra.plugins.solr.enums.DataTypeEnum;
import de.btu.openinfra.plugins.solr.enums.SolrIndexEnum;
import de.btu.openinfra.plugins.solr.exception.OpenInfraSolrException;


/**
 * This class is responsible for the Solr index. It provides functions to add,
 * update and delete documents to the index. It also provides a complete index
 * build function.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class SolrIndexer extends SolrServer {

    /**
     * Default constructor
     */
    public SolrIndexer() {
        // connect to the Solr server
        super();
    }


    /**
     *
     * This method starts the indexing process. It will accept a list of UUIDs
     * that represents the project IDs. If the List is empty, all projects that
     * can be found in the meta database will be indexed.
     *
     * @param projects A list of project UUIDs that should be indexed.
     * @return
     */
    public boolean indexProjects(SolrIndexPojo projectsPojo) {

        try {
            // TODO delete old index only for testing
            deleteAllDocuments();

            List<Projects> projectIndexList = new ArrayList<Projects>();

            // get all projects from the meta database
            List<Projects> metaProjects = new ProjectsDao(
                    null, OpenInfraSchemas.META_DATA).read();

            // check if the specified list contain entries
            if (projectsPojo != null && projectsPojo.getProjects().size() > 0) {
                // run through all projects from the meta database
                for (int i = 0; i < metaProjects.size(); i++) {
                    // if the project is no subproject and the ID is in the
                    // specified list
                    if (!metaProjects.get(i).getIsSubproject() &&
                            projectsPojo.getProjects().contains(
                                    metaProjects.get(i).getProjectId())) {
                        // then add the project to the index list
                        projectIndexList.add(metaProjects.get(i));
                    }
                }
            } else {
                // if no project list is specified, take all projects found in
                // the meta database
                projectIndexList = metaProjects;
            }

            // index all projects
            for (Projects project : projectIndexList) {
                // filter for main projects
                if (!project.getIsSubproject()) {
                    indexProject(project.getProjectId());
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            // TODO replace with SolrExceptionType
            throw new OpenInfraSolrException(
                    OpenInfraExceptionTypes.SOLR_INDEX_FAILED);
        }
    }

    /**
     * This function will index every topic instance of the passed project.
     *
     * @param projectId The id of the project that should be indexed.
     */
    private void indexProject(UUID projectId) {

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
            getSolr().add(docs);
            // commit the changes to the server
            getSolr().commit();
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

        UUID projectId = ti.getTopicCharacteristic().getProject().getId();
        SolrInputDocument doc = new SolrInputDocument();

        // add the topic instance id as document id
        doc.addField(SolrIndexEnum.TOPIC_INSTANCE_ID.getString(), ti.getId());

        // add the project id
        doc.addField(SolrIndexEnum.PROJECT_ID.getString(), projectId);

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
                    avv.getAttributeTypeToAttributeTypeGroup()
                        .getAttributeType().getValueListValue1()
                        .getPtFreeText2().getLocalizedCharacterStrings().get(0)
                        .getFreeText(),
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
                    avd.getAttributeTypeToAttributeTypeGroup()
                        .getAttributeType().getValueListValue1()
                        .getPtFreeText2().getLocalizedCharacterStrings().get(0)
                        .getFreeText(),
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
            getSolr().deleteById(ti.getId().toString());
            getSolr().commit();
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
            getSolr().deleteByQuery("*:*");
            getSolr().commit();
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
     * @param dataType
     * @param doc
     */
    private void addValuesToDoc(
            List<LocalizedCharacterString> type,
            List<LocalizedCharacterString> value,
            String dataType,
            SolrInputDocument doc
            ) {

        // check if the language of the attribute value is xx
        int x = containsAtPosition(value, "xx");
        if (x > -1) {
            String typeSuffix = "";
            Object specificValue = null;


            if (dataType.equals(DataTypeEnum.DATE.getString())) {
                typeSuffix = "_" + dataType;
                specificValue = castPtFreeTextToDataType(value.get(x)
                        .getFreeText(),
                        dataType);
            } else {
                specificValue = value.get(x).getFreeText();
            }

            // save the attribute value for every existing translation of the
            // attribute type
            for (int k = 0; k < type.size(); k++) {
                doc.addField(
                        SolrCharacterConverter.convert(
                                type.get(k).getFreeText()) + typeSuffix,
                                specificValue);
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
                e.printStackTrace();
                // continue indexing if an error occurred
                return;
            }
        }
    }

    /**
     * This method casts a specified string into a specified data type. If the
     * casting fails the string will be returned untouched.
     *
     * @param text
     * @param String
     * @return
     */
    private Object castPtFreeTextToDataType(String text, String dataType) {
        try {
            switch (DataTypeEnum.valueOf(dataType.toUpperCase())) {
                case DATE:
                    return OpenInfraTime.parse(text);
                case BOOLEAN:
                    return Boolean.parseBoolean(text);
                case INTEGER:
                    return Integer.parseInt(text);
                case NUMERIC:
                    return Double.parseDouble(text);
                default:
                    return text;
            }
        } catch (NumberFormatException e) {
            // return the string if something went wrong
            return text;
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
}
