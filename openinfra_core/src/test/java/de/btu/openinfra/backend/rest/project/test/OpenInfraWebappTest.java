package de.btu.openinfra.backend.rest.project.test;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;

import de.btu.openinfra.backend.db.pojos.webapp.WebappPojo;
import de.btu.openinfra.backend.db.pojos.webapp.WebappProjectPojo;
import de.btu.openinfra.backend.db.pojos.webapp.WebappSubjectPojo;
import de.btu.openinfra.backend.db.pojos.webapp.WebappSystemPojo;

/**
 * This class is used to test classes related to the web-application schema.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class OpenInfraWebappTest extends OpenInfraTest {

	// user: root
	private static final String SUBJECT_ROOT =
			"10886546-8af3-4bb1-b74e-c4d14cd47451";
	private static final String WEBAPP = "809a2e7d-d52b-43e0-9733-04399953fa01";
	private static final String WEBAPP_PATH = "webapp/" + WEBAPP;
	private static final String WEBAPP_PROJECT_PATH =
			WEBAPP_PATH + "/" + "projects/" + BAALBEK;
	private static final String WEBAPP_SUBJECT_PATH =
			WEBAPP_PATH + "/" + "subjects/" + SUBJECT_ROOT;
	private static final String WEBAPP_SYSTEM_PATH = WEBAPP_PATH + "/system";

	/**
	 * This main method is used for detailed single test. However, it's not
	 * required for testing while a Test Framework is utilized.
	 *
	 * @param args
	 */
	public static void main(String args[]) {
		OpenInfraWebappTest test = new OpenInfraWebappTest();
		test.rootLogin();

		test.getWebapp();
		test.putWebapp();

		test.getWebappSystem();
		test.putWebappSystem();

		test.putWebappProject();
		test.putWebappProject();
		test.getWebappProject();
		test.deleteWebappProject();

		test.putWebappSubject();
		test.putWebappSubject();
		test.getWebappSubject();
		test.deleteWebappSubject();
	}


	@Override
	public void beforeTest() {
		rootLogin();
	}

	// Webapp only

	@Test
	public void getWebapp() {
		Response resJson = buildJson(WEBAPP_PATH).get();
		System.out.println(resJson);
		Response res = build(WEBAPP_PATH, MediaType.APPLICATION_XML).get();
		//WebappPojo wp = build(WEBAPP_PATH, MediaType.APPLICATION_XML)
		//		.get(WebappPojo.class);
		System.out.println(res);
		assertEquals(Status.OK.getStatusCode(), resJson.getStatus());
	}

	@Test
	public void putWebapp() {
		WebappPojo wp = buildXml(WEBAPP_PATH).get(WebappPojo.class);
		wp.setData("Some random data: " + UUID.randomUUID());
		Response res = buildJson(WEBAPP_PATH).put(Entity.json(wp));
		System.out.println(res);
		assertEquals(Status.OK.getStatusCode(), res.getStatus());
	}

	// Webapp system

	@Test
	public void getWebappSystem() {
		Response res = buildJson(WEBAPP_SYSTEM_PATH).get();
		System.out.println(res);
		assertEquals(Status.OK.getStatusCode(), res.getStatus());
	}

	@Test
	public void putWebappSystem() {
		WebappSystemPojo wsp =
				buildXml(WEBAPP_SYSTEM_PATH).get(WebappSystemPojo.class);
		wsp.setData("Some random data: " + UUID.randomUUID());
		Response res = buildJson(WEBAPP_SYSTEM_PATH).put(Entity.json(wsp));
		System.out.println(res);
		assertEquals(Status.OK.getStatusCode(), res.getStatus());
	}

	// Webapp project

	@Test
	public void getWebappProject() {
		Response res = buildJson(WEBAPP_PROJECT_PATH).get();
		System.out.println(res);
		assertEquals(Status.OK.getStatusCode(), res.getStatus());
	}

	@Test
	public void putWebappProject() {
		WebappProjectPojo wpp = new WebappProjectPojo();
		wpp.setData("Some random data: " + UUID.randomUUID());
		Response res = buildJson(WEBAPP_PROJECT_PATH).put(Entity.json(wpp));
		System.out.println(res);
		assertEquals(Status.OK.getStatusCode(), res.getStatus());
	}

	@Test
	public void deleteWebappProject() {
		Response res = buildJson(WEBAPP_PROJECT_PATH).delete();
		System.out.println(res);
		assertEquals(Status.OK.getStatusCode(), res.getStatus());
	}

	// Webapp subject

	@Test
	public void getWebappSubject() {
		Response res = buildJson(WEBAPP_SUBJECT_PATH).get();
		System.out.println(res);
		assertEquals(Status.OK.getStatusCode(), res.getStatus());
	}

	@Test
	public void putWebappSubject() {
		WebappSubjectPojo wsp = new WebappSubjectPojo();
		wsp.setData("Some random data: " + UUID.randomUUID());
		Response res = buildJson(WEBAPP_SUBJECT_PATH).put(Entity.json(wsp));
		System.out.println(res);
		assertEquals(Status.OK.getStatusCode(), res.getStatus());
	}

	@Test
	public void deleteWebappSubject() {
		Response res = buildJson(WEBAPP_SUBJECT_PATH).delete();
		System.out.println(res);
		assertEquals(Status.OK.getStatusCode(), res.getStatus());
	}

}
