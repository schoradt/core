package de.btu.openinfra.backend.rest.project.test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import de.btu.openinfra.backend.OpenInfraApplication;

public class OpenInfraJerseyTest extends JerseyTest {

	Cookie cookie = null;

	@Override
	public Application configure() {
		//login("root", "root");
		return new OpenInfraApplication();
	}

	public void login(String username, String password) {
		Form form = new Form();
		form.param("username", username);
		form.param("password", password);

		final WebTarget targetRes01 = target("login.jsp");
		Response res01 = targetRes01.request().get();
		System.out.println("--> " + res01);


		Response res = target("openinfra_core/login.jsp").request(MediaType.APPLICATION_JSON_TYPE)
				    .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED_TYPE));

		System.out.println(res);

		Response res2 = target("v1/projects").request(MediaType.APPLICATION_JSON).get();
		System.out.println(res2);

		Assert.assertNotEquals(Status.NOT_FOUND, res.getStatus());

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
		Assert.assertNotNull(target("projects").request().get());
	}

}
