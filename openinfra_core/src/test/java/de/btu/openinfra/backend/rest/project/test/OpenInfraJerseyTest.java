package de.btu.openinfra.backend.rest.project.test;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientProperties;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.btu.openinfra.backend.db.pojos.project.ProjectPojo;
import de.btu.openinfra.backend.db.pojos.rbac.SubjectPojo;

public class OpenInfraJerseyTest {

	private Cookie cookie = null;
	private WebTarget target = null;
	private final String BASE_URI = "http://localhost:8080/";
	private final String REST_PATH = "openinfra_core/rest/v1/";
	private final String BAALBEK = "fd27a347-4e33-4ed7-aebc-eeff6dbf1054";

	@Before
	public void setUp() {
		Client client = ClientBuilder.newClient();
		target = client.target(BASE_URI);
		login("root", "root");
	}

	public void login(String username, String password) {
		Form form = new Form();
		form.param("username", username);
		form.param("password", password);

		Client loginClient = ClientBuilder.newClient();
		loginClient.property(ClientProperties.FOLLOW_REDIRECTS, false);

		Response res = loginClient.target(BASE_URI).path(
				"openinfra_core/login.jsp").request().post(Entity.entity(
						form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
		cookie = res.getCookies().get("JSESSIONID");
	}

	@Test
	@SuppressWarnings("unchecked")
	public void listUsers() {
		List<SubjectPojo> pojos = target.path(REST_PATH + "rbac/subjects")
				.request(MediaType.APPLICATION_JSON).cookie(cookie).get()
				.readEntity(List.class);
		System.out.println(pojos);
		Assert.assertNotNull(pojos);
	}

	@Test
	public void projects() {
		ProjectPojo pojo = target.path(REST_PATH + "projects/" + BAALBEK)
				.request(MediaType.APPLICATION_JSON).cookie(cookie).get()
				.readEntity(ProjectPojo.class);
		System.out.println(pojo);
		Assert.assertNotNull(pojo);
	}

}
