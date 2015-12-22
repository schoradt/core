package de.btu.openinfra.backend.solr;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.persistence.NoResultException;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import de.btu.openinfra.backend.OpenInfraProperties;
import de.btu.openinfra.backend.OpenInfraPropertyKeys;
import de.btu.openinfra.backend.OpenInfraPropertyValues;
import de.btu.openinfra.backend.OpenInfraTime;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.file.FileDao;
import de.btu.openinfra.backend.db.daos.meta.ProjectsDao;
import de.btu.openinfra.backend.db.daos.project.TopicInstanceDao;
import de.btu.openinfra.backend.db.jpa.model.AttributeValueDomain;
import de.btu.openinfra.backend.db.jpa.model.AttributeValueValue;
import de.btu.openinfra.backend.db.jpa.model.LocalizedCharacterString;
import de.btu.openinfra.backend.db.jpa.model.TopicInstance;
import de.btu.openinfra.backend.db.jpa.model.meta.Projects;
import de.btu.openinfra.backend.db.pojos.file.FilePojo;
import de.btu.openinfra.backend.db.pojos.solr.SolrIndexPojo;
import de.btu.openinfra.backend.exception.OpenInfraExceptionTypes;
import de.btu.openinfra.backend.exception.OpenInfraSolrException;
import de.btu.openinfra.backend.exception.OpenInfraWebException;
import de.btu.openinfra.backend.solr.enums.SolrDataTypeEnum;
import de.btu.openinfra.backend.solr.enums.SolrDocumentTypeEnum;
import de.btu.openinfra.backend.solr.enums.SolrIndexEnum;


/**
 * This class is responsible for the Solr index. It provides functions to add,
 * update and delete documents to the index. It also provides a complete index
 * build function.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class SolrIndexer extends SolrServer {

    /*
     * This variable will define the maximum window size of the document list
     * that will be committed to the server at the indexing process at once.
     */
    private final int DEFAULT_WINDOW_SIZE = 1000;

    /**
     * Default constructor
     */
    public SolrIndexer() {
        // connect to the Solr server
        super();
    }

    /**
     *
     * @param clean    Determines if the index should be completely cleared
     *                 before starting the index process.
     * @return
     */
    public boolean indexAll(boolean clean) {
        // clear index if necessary
        if (clean) {
            deleteAllDocuments();
        }

        indexProjects(null);
        indexFiles();

        return false;
    }

    /**
     * This method starts the indexing process for database entries. It will
     * accept a list of UUIDs that represents the project IDs. If the List is
     * empty, all projects that can be found in the meta database will be
     * indexed.
     *
     * @param projects A list of project UUIDs that should be indexed.
     * @return         True if indexing was successful.
     * @throws OpenInfraSolrException if something goes wrong
     */
    public boolean indexProjects(SolrIndexPojo projectsPojo) {

        try {
            List<Projects> projectIndexList = new ArrayList<Projects>();

            // get all projects from the meta database
            List<Projects> metaProjects = new ProjectsDao(
                    null, OpenInfraSchemas.META_DATA).read(
                            0, Integer.MAX_VALUE);

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
                    System.out.println("starting indexing of project '"
                            + project.getProjectId() + "' ... ");
                    indexProject(project.getProjectId());
                    System.out.println("finished indexing of project '"
                            + project.getProjectId() + "'");
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
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

        TopicInstanceDao tiDao = new TopicInstanceDao(
                projectId, OpenInfraSchemas.PROJECTS);

        // TODO if the the topic instances of a project exceed 2.147.483.647 we
        // will get a problem here.
        double amount = tiDao.getCount();
        int start = 0;
        int windowSize = Integer.parseInt(OpenInfraProperties.getProperty(
                OpenInfraPropertyKeys.SOLR_INDEX_WINDOW.getKey()));

        // set a default size if the configured window size is zero or negative
        if (windowSize < 1) {
            windowSize = DEFAULT_WINDOW_SIZE;
        }

        // set the first window size
        int size = windowSize;

        // set the amount of runs we need to index all documents
        double indexRuns = Math.ceil(amount/windowSize);

        for (int i = 1; i <= (indexRuns); i++) {
            // get the defined amount of topic instances
            List<TopicInstance> tiList = tiDao.read(start, windowSize);

            Collection<SolrInputDocument> docs =
                    new ArrayList<SolrInputDocument>();

            System.out.println("adding TopicInstances to Solr documents");

            // run through all topic instances
            for (TopicInstance ti : tiList) {
                // create a new document
                docs.add(createOrUpdateDocument(ti));
            }

            System.out.println("sending Solr documents to Solr server");

            // send the documents to the solr server
            writeToIndex(docs);

            // set the amount variable for the next run
            size = windowSize * i;
            start = size;
        }
    }

    /**
     * This method starts the indexing process for files.
     *
     * @return True if indexing was successful.
     * @throws OpenInfraSolrException if something goes wrong
     */
    public boolean indexFiles() {
        try {
        // construct path to documents
        String filePath = OpenInfraPropertyValues.UPLOAD_PATH.getValue();

        // get a list of all files in the upload folder
        List<File> files = readFilesInDir(filePath);

        List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();

        // run through all files and create a SolrInputDocument
        for (File file : files) {
            if (file.isFile() && !file.isHidden()) {
                System.out.println("indexing document " + file.getName());
                docs.add(indexFile(file));
            }
        }

        // index the files
        if (docs.size() > 0) {
            return writeToIndex(docs);
        } else {
            return false;
        }

        } catch (Exception e) {
            e.printStackTrace();
            throw new OpenInfraSolrException(
                    OpenInfraExceptionTypes.SOLR_INDEX_FAILED);
        }
    }

    /**
     * This method will index a single file that was specified and return the
     * SolrInputDocument.
     *
     * @param file The file that should be indexed.
     * @return     The SolrInputDocument for the specified file.
     */
    private SolrInputDocument indexFile(File file) {
        String fileName = FilenameUtils.getBaseName(file.getName());
        FilePojo filePojo;

        // get the entry from the file database for the file
        try {
            filePojo = new FileDao().readBySignature(fileName);
        } catch (NoResultException e) {
            return null;
        }

        // create objects for parsing process
        ContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();
        InputStream input;
        SolrInputDocument doc = new SolrInputDocument();

        try {
            input = new FileInputStream(file);

            // let Tika detect the correct parser for the file
            AutoDetectParser autoParser = new AutoDetectParser();

            // parse the file
            autoParser.parse(input, handler, metadata, context);

            // add the file uuid
            doc.addField(
                    SolrIndexEnum.ID.getString(),
                    filePojo.getUuid());

            // add the signature as document id
            doc.addField(
                    SolrIndexEnum.FILE_HASH.getString(),
                    fileName);

            // add the original file name
            doc.addField(
                    SolrIndexEnum.DEFAULT_SEARCH_FIELD.getString(),
                    handler);

            // add the document type
            doc.addField(
                    SolrIndexEnum.DOC_TYPE.getString(),
                    SolrDocumentTypeEnum.FILE);

            // close the file stream
            input.close();

        } catch (IOException | SAXException | TikaException e) {
            e.printStackTrace();
        }
        return doc;
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
    private SolrInputDocument createOrUpdateDocument(TopicInstance ti) {

        UUID projectId = ti.getTopicCharacteristic().getProject().getId();
        SolrInputDocument doc = new SolrInputDocument();

        // add the topic instance id as document id
        doc.addField(SolrIndexEnum.ID.getString(), ti.getId());
        addDefaultSearchField(
                ti.getId().toString(),
                doc);

        // add the project id
        doc.addField(SolrIndexEnum.PROJECT_ID.getString(), projectId);
        addDefaultSearchField(
                projectId.toString(),
                doc);

        // add the topic characteristic id
        doc.addField(SolrIndexEnum.TOPIC_CHARACTERISTIC_ID.getString(),
                     ti.getTopicCharacteristic().getId());
        addDefaultSearchField(
                ti.getTopicCharacteristic().getId().toString(),
                doc);

        // add the document type
        doc.addField(
                SolrIndexEnum.DOC_TYPE.getString(),
                SolrDocumentTypeEnum.DATABASE);

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
     * This method will be the access point to index a single topic instance.
     * This method is thread-safe.
     *
     * @param projectId       The project id the topic instance contains to.
     * @param topicInstanceId The topic instance id that was updated.
     * @return                True if everything works fine, else false.
     */
    protected synchronized boolean createOrUpdateDocument(
            UUID projectId,
            UUID topicInstanceId) {

        if (getAlive()) {
            // delete the document first because it seems that updating is not
            // always performing the expected way
            deleteDocument(topicInstanceId);

            Collection<SolrInputDocument> docs =
                    new ArrayList<SolrInputDocument>();
            System.out.println("Updating index for topic instance '"
                    + topicInstanceId + "'");
            // Get the topic instance model object for the specified topic
            // instance id and create the document
            docs.add(createOrUpdateDocument(
                    new TopicInstanceDao(projectId, OpenInfraSchemas.PROJECTS)
                    .read(topicInstanceId)));

            // send the document to the Solr server
            return writeToIndex(docs);
        } else {
            return false;
        }
     }

    /**
     * This method deletes a specific document from the Solr index. This method
     * is thread-safe.
     *
     * @param id The id of the document that should be deleted.
     */
    protected synchronized boolean deleteDocument(UUID id) {
        try {
            System.out.println("Deleting document '" + id + "' from index.");
            getSolr().deleteById(id.toString());
            if (getSolr().commit().getStatus() == Status.OK.getStatusCode()) {
                return true;
            } else {
                return false;
            }
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method deletes all documents from the Solr index.
     */
    private void deleteAllDocuments() {
        try {
            System.out.println("Deleting all documents from index");
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
     * @param type     A list of attribute types as LocalizedCharacterString.
     * @param value    A list of values as LocalizedCharacterString.
     * @param dataType The data type of the value as string.
     * @param doc      The document the fields should be added to.
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


            if (dataType.equals(SolrDataTypeEnum.DATE.getString())) {
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

            // save the value in a specific field that is used for lookup
            doc.addField(SolrCharacterConverter.convert(
                    SolrIndexEnum.LOOKUP_FIELD.getString()),
                    value.get(x).getFreeText());

            // save the value in a default search field
            addDefaultSearchField(
                    value.get(x).getFreeText(),
                    doc);

            // Abort here because the value should only exists in one language.
            return;
        }

        // Run through every other translation of the attribute value.
        for (int i = 0; i < value.size(); i++) {
            try {

                // saves the language code
                String languageCode = value.get(i).getPtLocale()
                        .getLanguageCode().getLanguageCode();

                // Retrieve the position of the attribute type with the language
                // of the attribute value.
                int z = containsAtPosition(type, languageCode);

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
                            SolrIndexEnum.NO_TRANSLATION_FIELD.getString()),
                            value.get(i).getFreeText());
                }

                // save the value in a specific field that is used for lookup
                doc.addField(SolrCharacterConverter.convert(
                        SolrIndexEnum.LOOKUP_FIELD.getString()),
                        value.get(i).getFreeText());

                // save all values in a default search field
                addDefaultSearchField(
                        value.get(i).getFreeText(),
                        doc);

            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
                // continue indexing if an error occurred
                return;
            }
        }
    }

    /**
     * This method adds all values to a default search field that will be used
     * for global queries. We will filter the list to avoid doublet.
     *
     * @param value The value of the default field as string.
     * @param doc   The document the fields should be added to.
     */
    private void addDefaultSearchField(String value, SolrInputDocument doc) {
        if (!doc.containsValue(value)) {
            doc.addField(SolrCharacterConverter.convert(
                    SolrIndexEnum.DEFAULT_SEARCH_FIELD.getString()),
                    value);
        }

    }

    /**
     * This method casts a specified string into a specified data type. If the
     * casting fails the string will be returned untouched.
     *
     * @param text     The text that should be casted as data type.
     * @param dataType The data type the text should be casted at.
     * @return         The casted data type or the input string if the casting
     *                 fails.
     */
    private Object castPtFreeTextToDataType(String text, String dataType) {
        try {
            switch (SolrDataTypeEnum.valueOf(dataType.toUpperCase())) {
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

    /**
     * This method sends the Solr documents to the Server.
     *
     * @param docs A collection of SolrInputDocuments that should be written to
     *             the Solr index.
     * @return     True if the index was written, else false.
     */
    private boolean writeToIndex(Collection<SolrInputDocument> docs) {
        // send the documents to the solr server
        try {
            // add the documents to solr
            getSolr().add(docs);
            // commit the changes to the server
            getSolr().commit();
            return true;
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method read all files from a specified directory and return them as
     * a file list. There will only files included that fulfill the filter
     * restriction from the properties file. Further it will ignore sub
     * directories.
     *
     * @param dir The directory path as string
     * @return    A list of files that were found in the specified directory.
     */
    private List<File> readFilesInDir(String dir) {
        // get all files from the specified directory that will match the
        // filter from the properties file
        List<File> fileList = (List<File>) FileUtils.listFiles(
                new File(dir),
                OpenInfraProperties.getProperty(
                        OpenInfraPropertyKeys.SOLR_INDEXABLE_DOCUMENTS
                        .getKey()).split(","),
                false);
        return fileList;
    }
}
