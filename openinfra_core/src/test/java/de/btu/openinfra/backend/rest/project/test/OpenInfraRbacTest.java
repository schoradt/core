package de.btu.openinfra.backend.rest.project.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;

/**
 * This test class is used to test resources of the role-based access control
 * system.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class OpenInfraRbacTest extends OpenInfraTest {

	private static final String RBAC_PATH = "rbac";
	private static final String RBAC_OBJECT_PATH =
			RBAC_PATH + "/openinfraobjects";

	public static void main(String args[]) throws Exception {
		OpenInfraRbacTest test = new OpenInfraRbacTest();
		test.rootLogin();

		test.getAllOpenInfraObjects();
		test.getFirstOpeninfraObject();
	}

	@Override
	public void beforeTest() {
		rootLogin();
	}

	@Test
	public void getAllOpenInfraObjects() {
		Response res = buildJson(RBAC_OBJECT_PATH).get();
		System.out.println(res);
		assertEquals(Status.OK.getStatusCode(), res.getStatus());
	}

	@Test
	public void getFirstOpeninfraObject() throws Exception {
		String objects = buildJson(RBAC_OBJECT_PATH).get(String.class);
		JSONArray jArray = (JSONArray)new JSONParser().parse(objects);
		assertTrue(jArray.size() > 0);
		JSONObject jObject = (JSONObject)jArray.get(0);
		String ooPath = RBAC_OBJECT_PATH + "/" + jObject.get("uuid");
		Response res = buildXml(ooPath).get();
		System.out.print(res);
		assertEquals(Status.OK.getStatusCode(), res.getStatus());
	}

}
