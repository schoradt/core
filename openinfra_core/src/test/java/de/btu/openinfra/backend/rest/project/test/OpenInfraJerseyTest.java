package de.btu.openinfra.backend.rest.project.test;

import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

public class OpenInfraJerseyTest {

	private Map<String, NewCookie> cookies = null;
	private WebTarget target = null;
	private final String BASE_URI = "http://localhost:8080/";

	@Before
	public void setUp() {
		Client client = ClientBuilder.newClient();
		target = client.target(BASE_URI);
	}

	public void login(String username, String password) {
		Form form = new Form();
		form.param("username", username);
		form.param("password", password);

		//Response res = target("openinfra_core/login.jsp").request(MediaType.APPLICATION_JSON_TYPE)
		//		    .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		Response res = target.path("openinfra_core/login.jsp").request()
				.post(Entity.entity(
						form, MediaType.MULTIPART_FORM_DATA));

		System.out.println(res);

		cookies = res.getCookies();

		for(String s : cookies.keySet()) {
			System.out.println(s);
		}

		for(NewCookie c : cookies.values()) {
			System.out.println(c);
		}

		Response res2 = target.path("openinfra_core/rest/v1/projects").request(
				MediaType.APPLICATION_JSON).get();
		System.out.println(res2);

		//Assert.assertNotEquals(Status.NOT_FOUND, res.getStatus());

		//return target("http://localhost:8080/openinfra_core/login.jsp").request().post(Entity.entity(
		//		form, MediaType.APPLICATION_FORM_URLENCODED_TYPE)).getCookies();
	}

	@Test
	public void listUsers() {

		System.out.println("--> hier");
		//Map<String, NewCookie> res = login("root", "root");
		login("root", "root");
		//System.out.println("--> map: " + res);

//		for(NewCookie c : res.values()) {
//			System.out.println(c);
//		}

//		List<SubjectPojo> subjects =
//				target("/rest/v1/rbac/subjects").request().cookie(cookie) .get(List.class);
//		Assert.assertNotNull(subjects);
	}


//	@Test
//	public void testProject() throws Exception {
//		System.out.println("############## Project Test ##############");
//
//		// 1. Read files (JSON & XML)
//		String projectXml = new String(Files.readAllBytes(
//				Paths.get(getClass().getResource(
//						XML_FILE_PATH + "Project.xml").toURI())));
//		String projectJSON = new String(Files.readAllBytes(
//				Paths.get(getClass().getResource(
//						JSON_FILE_PATH + "Project.json").toURI())));
//		// 2. Execute post request
//		System.out.println("\n++++ Post XML Request ++++");
//		Response response = target("projects").request(
//				MediaType.APPLICATION_XML).post(
//						Entity.entity(
//								projectXml,
//								MediaType.APPLICATION_XML));
//		UUID id = UUID.fromString(response.readEntity(String.class));
//		System.out.println(id + " " + response);
//		Assert.assertTrue(response.getStatus() == 200);
//
//	}

	@Test
	public void testTheWest() {
		//Assert.assertNotNull(target("projects").request().get());
	}

}
