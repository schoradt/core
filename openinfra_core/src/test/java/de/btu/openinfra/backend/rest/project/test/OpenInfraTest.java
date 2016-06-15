package de.btu.openinfra.backend.rest.project.test;

import java.util.UUID;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Before;

public abstract class OpenInfraTest {

	protected Cookie cookie = null;
	protected WebTarget target = null;
	protected UUID pId = null;
	protected static final String BASE_URI = "http://localhost:8080/";
	protected static final String REST_PATH = "openinfra_core/rest/v1/";
	protected static final String BAALBEK =
			"fd27a347-4e33-4ed7-aebc-eeff6dbf1054";
	protected static final String TEST = "e7d42bff-4e40-4f43-9d1b-1dc5a190cd75";
	protected static final String BAALBEK_PROJECT_PATH = "projects/" + BAALBEK;
	protected static final String TEST_PROJECT_PATH = "projects/" + TEST;
	protected static final String SYSTEM_PATH = "system/";
	protected static final String PROJECT_PRIMER = TEST_PROJECT_PATH
	        + "/primer?language=de-DE&pojoClass=";
	protected static final String SYSTEM_PRIMER = "system/primer?pojoClass=";

	/**
	 * Performs the login
	 *
	 * @param username
	 * @param password
	 */
	protected void login(String username, String password) {
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

	/**
	 * Constructs a project path.
	 *
	 * @param project the project UUID as string
	 * @param slash trailing slash
	 * @return a project path
	 */
	protected String projectPath(String project, boolean slash) {
		return slash ? "projects/" + project + "/" : "projects/" + project;
	}

	/**
	 * Creates a builder by path and media type.
	 *
	 * @param path the path of the resource
	 * @param mediaType the media type of the request
	 * @return a builder which can be used to execute an HTTP method on the
	 * 	specified path
	 */
	protected Builder build(String path, String mediaType) {
		return target.path(REST_PATH + path).request(mediaType).cookie(cookie);
	}

	/**
	 * Creates a JSON builder by path.
	 *
	 * @param path the path of the resource
	 * @return a JSON builder
	 */
	protected Builder buildJson(String path) {
		return build(path, MediaType.APPLICATION_JSON);
	}

	/**
	 * Create an XML builder by path.
	 *
	 * @param path the path of the resource
	 * @return an XML builder
	 */
	protected Builder buildXml(String path) {
		return build(path, MediaType.APPLICATION_XML);
	}

	/**
	 * Create a builder by path, request parameter and media type.
	 *
	 * @param path the path of the resource
	 * @param mediaType the media type of the request
	 * @param qParamName the name of the request parameter
	 * @param qParamValue the value of the request parameter
	 * @return a builder which can be used to execute an HHTP method on the
	 * 	specified path
	 */
	protected Builder build(String path, String mediaType,
			String qParamName, String qParamValue) {
		return target.path(REST_PATH + path)
				.queryParam(qParamName, qParamValue)
				.request(mediaType).cookie(cookie);
	}

	/**
	 * This method performs a root login.
	 */
	protected void rootLogin() {
		Client client = ClientBuilder.newClient().register(
				JacksonFeature.class);
		target = client.target(BASE_URI);
		login("root", "root");
	}

	/**
	 * This method should be implemented by all test classes. This could contain
	 * a root login.
	 */
	@Before
	public abstract void beforeTest();

}
