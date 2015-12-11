package de.btu.openinfra.backend.solr;

import java.util.UUID;

import de.btu.openinfra.backend.OpenInfraProperties;
import de.btu.openinfra.backend.OpenInfraPropertyKeys;
import de.btu.openinfra.backend.solr.enums.SolrIndexOperationEnum;

/**
 * The class supports threaded indexing for single documents. This can be useful
 * for updating small parts of the index. To start the index process simply call
 * <code>start()</code> on the SolrThreadedIndexer object.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class SolrThreadedIndexer extends SolrIndexer {

    /*
     * This project id will be specified by a special constructor. It is mainly
     * formulated for the thread based single indexing process.
     */
    private UUID projectId = null;

    /*
     * This topic instance id will be specified by a special constructor. It is
     * mainly formulated for the thread based single indexing process.
     */
    private UUID topicInstanceId = null;

    /*
     * This operation will be specified by a special constructor. It is mainly
     * formulated for the thread based single indexing process.
     */
    private SolrIndexOperationEnum operation = null;

    /**
     * Constructor for dynamically indexing via threads. It supports only
     * indexing for single documents. Further you must explicit specify the
     * request you want to execute.
     *
     * @param projectId       The project id the topic instance belongs to.
     * @param topicInstanceId The topic instance id that should be processed.
     * @param operation       The operation of the type
     *                        {@link SolrIndexOperationEnum} that should be
     *                        executed on the index.
     */
    public SolrThreadedIndexer(UUID projectId, UUID topicInstanceId,
            SolrIndexOperationEnum operation) {
        // connect to the Solr server
        super();
        this.projectId = projectId;
        this.topicInstanceId = topicInstanceId;
        this.operation = operation;
    }

    /**
     * This method contains the main logic. It will update or delete a single
     * document from the Solr index depending on the specified operation in the
     * constructor. The thread will start delayed for at least 15 seconds or the
     * time that is configured in the properties file. This is necessary while
     * deleting objects that are no topic instances (e.g. attribute values). If
     * we delete the
     */
    @Override
    public void run() {
        try {
            // this will be the minimum delay time for index threads
            long defaultSleepTime = 15000;

            // get the delay time from the properties
            long propertySleepTime = Long.parseLong(
                    OpenInfraProperties.getProperty(
                            OpenInfraPropertyKeys.SOLR_INDEX_DELAY.getKey()));

            // only use the property delay time if it is greater than the
            // default delay time
            if (propertySleepTime > defaultSleepTime) {
                defaultSleepTime = propertySleepTime;
            }
            Thread.sleep(defaultSleepTime);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // execute different indexing methods depending on the requested
        // operation
        switch (operation) {
        case UPDATE:
            // check if the project id and topic instance id is not null
            if (projectId != null && topicInstanceId != null) {
                // update the specified document
                boolean result =
                        createOrUpdateDocument(projectId, topicInstanceId);
                if (!result) {
                    System.out.println("Updating enity in index failed.");
                }
            }
            break;
        case DELETE:
            // check if the topic instance id is not null
            if (topicInstanceId != null) {
                // delete the specified document
                boolean result = deleteDocument(topicInstanceId);
                if (!result) {
                    System.out.println("Deleting enity in index failed.");
                }
            }
            break;
        default:
            stopIndexing();
            break;
        }
    }

    /**
     * This method starts the indexing process with the specified parameters
     * from the constructor.
     */
    @Override
    public void start() {
        // start the thread
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * This method stops the currecnt indexing thread.
     */
    public void stopIndexing() {
        System.out.println("Indexing process aborted.");
        this.interrupt();
    }
}
