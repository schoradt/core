package de.btu.openinfra.backend.rest.project.test;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;

import de.btu.openinfra.backend.db.pojos.solr.SolrIndexPojo;

/**
 * This JUnit class is used to test classes related to the project schema.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class OpenInfraSearchTest extends OpenInfraTest {

    public static final String SEARCH_PATH = "search";
    public static final String INDEX_PATH = SEARCH_PATH + "/index";

    @Override
    public void beforeTest() {
        // TODO Auto-generated method stub
        rootLogin();
    }

    @Test
    public void postAttributeValueValue() {
        // get a SolrIndexer pojo
        SolrIndexPojo s = new SolrIndexPojo();

        // send the empty pojo to index all projects
        Response res = build(
                INDEX_PATH, MediaType.APPLICATION_JSON, "clean", "true")
                .post(Entity.json(s));
        System.out.println(res);
        // test the result
        assertEquals(Status.OK.getStatusCode(), res.getStatus());
    }
}
