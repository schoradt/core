package de.btu.openinfra.backend.rest.project.test;

import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
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
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.btu.openinfra.backend.db.pojos.AttributeTypeGroupPojo;
import de.btu.openinfra.backend.db.pojos.AttributeTypeGroupToAttributeTypePojo;
import de.btu.openinfra.backend.db.pojos.LocalizedString;
import de.btu.openinfra.backend.db.pojos.MultiplicityPojo;
import de.btu.openinfra.backend.db.pojos.PtFreeTextPojo;
import de.btu.openinfra.backend.db.pojos.PtLocalePojo;
import de.btu.openinfra.backend.db.pojos.project.ProjectPojo;
import de.btu.openinfra.backend.db.pojos.rbac.SubjectPojo;
import de.btu.openinfra.backend.db.pojos.webapp.WebappProjectPojo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OpenInfraJerseyTest {

	private Cookie cookie = null;
	private WebTarget target = null;
	private UUID pId = null;
	private final String BASE_URI = "http://localhost:8080/";
	private final String REST_PATH = "openinfra_core/rest/v1/";
	private final String BAALBEK = "fd27a347-4e33-4ed7-aebc-eeff6dbf1054";
	private final String TEST = "e7d42bff-4e40-4f43-9d1b-1dc5a190cd75";

	public static void main(String[] args) throws Exception {
		OpenInfraJerseyTest test = new OpenInfraJerseyTest();
		test.setUp();
		//test.test1addProject();
		//test.test2urlFileUpload();
		//test.test100webappProject();
		//test.test1000deleteProject();

		test.test1010postAttTypeToGroup();
		//test.deleteTopChar();
	}

	private String projectPath(String project) {
		return "projects/" + project;
	}

	@Before
	public void setUp() {
		Client client = ClientBuilder.newClient().register(
				JacksonFeature.class);
		target = client.target(BASE_URI);
		login("root", "root");
	}

	public Builder build(String path, String mediaType) {
		return target.path(REST_PATH + path).request(mediaType).cookie(cookie);
	}

	public Builder build(String path, String mediaType,
			String qParamName, String qParamValue) {
		return target.path(REST_PATH + path)
				.queryParam(qParamName, qParamValue)
				.request(mediaType).cookie(cookie);
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
	public void test1addProject() throws Exception {
		// Extract the UUID of PtLocale by language code 'de'.
		String locales = build("system/ptlocales",
				MediaType.APPLICATION_JSON).get(String.class);
		JSONArray jArray = (JSONArray)new JSONParser().parse(locales);
		UUID ptlId = null;
		for(int i = 0; i < jArray.size(); i++) {
			JSONObject obj = (JSONObject)new JSONParser().parse(
					jArray.get(i).toString());
			if(obj.get("languageCode").toString().equalsIgnoreCase("de")) {
				ptlId = UUID.fromString(obj.get("uuid").toString());
			}
		}

		PtLocalePojo ptl = new PtLocalePojo();
		ptl.setUuid(ptlId);

		List<LocalizedString> lsNameList = new LinkedList<LocalizedString>();
	    LocalizedString lsName = new LocalizedString();
		lsName.setCharacterString("JUnit Test Project");
		lsName.setLocale(ptl);
		lsNameList.add(lsName);

		List<LocalizedString> lsDescList = new LinkedList<LocalizedString>();
		LocalizedString lsDesc = new LocalizedString();
		lsDesc.setCharacterString("JUnit Test Description");
		lsDesc.setLocale(ptl);
		lsDescList.add(lsDesc);

		ProjectPojo p = new ProjectPojo();
		p.setDescriptions(new PtFreeTextPojo(lsDescList, null));
		p.setNames(new PtFreeTextPojo(lsNameList, null));

		String result = build("projects", MediaType.APPLICATION_JSON).post(
				Entity.json(p)).readEntity(String.class);
		JSONObject jRes = (JSONObject)new JSONParser().parse(result);
		pId = UUID.fromString(jRes.get("uuid").toString());
		System.out.println("Project created: " + result);
	}

	/**
	 * Baalbek related test case
	 */
	public void test1010postAttTypeToGroup() {
		AttributeTypeGroupPojo atg = new AttributeTypeGroupPojo();
		atg.setUuid(UUID.fromString("24e81d76-b7ea-4adf-bb46-4ee81b5b83ec"));
		MultiplicityPojo m = new MultiplicityPojo();
		m.setUuid(UUID.fromString("dd051168-b938-4b2f-9361-cfbc7fc5dce4"));

		AttributeTypeGroupToAttributeTypePojo atgtat =
				new AttributeTypeGroupToAttributeTypePojo();
		atgtat.setAttributeTypeGroup(atg);
		atgtat.setAttributeTypeId(
				UUID.fromString("1d6f37c8-cdb7-4b0a-9a76-c3ae2513a013"));
		atgtat.setOrder(0);
		atgtat.setMultiplicity(m);
		atgtat.setAttributeTypeGroupToTopicCharacteristicId(
				UUID.fromString("b92c8ce1-278b-4926-8490-24918ac1799a"));

		ObjectMapper om = new ObjectMapper();
		try {
			System.out.println(om.writeValueAsString(atgtat));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		Response res = build(projectPath(TEST) + "/attributetypes"
//				+ "/1d6f37c8-cdb7-4b0a-9a76-c3ae2513a013/"
//				+ "attributetypegroups", MediaType.APPLICATION_JSON).post(
//				Entity.json(atgtat));
//
//		System.out.println(res);
	}

	public void deleteTopChar() {
		Response res = build(projectPath(TEST) + "/topiccharacteristics/"
				+ "3aac556e-0ca7-4d0f-8f6a-1886174b00c7",
				MediaType.APPLICATION_JSON).delete();
		 System.out.println(res);
	}

	@Test
	public void test1000deleteProject() {
		String result = build("projects/" + pId, MediaType.APPLICATION_JSON)
				.delete().readEntity(String.class);
		System.out.println("Project deleted: " + result);
	}

	@Test
	public void listPjects() {
		Assert.assertEquals(Status.OK.getStatusCode(), build("projects",
				MediaType.APPLICATION_JSON).get().getStatus());
		Assert.assertEquals(Status.OK.getStatusCode(), build("projects",
				MediaType.APPLICATION_XML).get().getStatus());
	}

	@Test
	public void projects() {
		ProjectPojo pojo = target.path(REST_PATH + "projects/" + BAALBEK)
				.request(MediaType.APPLICATION_JSON).cookie(cookie).get()
				.readEntity(ProjectPojo.class);
		System.out.println(pojo);
		Assert.assertNotNull(pojo);
	}

	@Test
	public void test100webappProject() {
		UUID webappId =
				UUID.fromString("911fee71-efb8-4194-b383-a1e54b02e806");
		WebappProjectPojo wpp = new WebappProjectPojo();
		wpp.setData("hallo data");
		wpp.setProject(pId);
		wpp.setWebapp(webappId);

		String result = build("webapp/" + webappId.toString() + "/projects",
				MediaType.APPLICATION_JSON).post(Entity.json(wpp))
				.readEntity(String.class);
		System.out.println("Webapp data written: " + result);
	}

	@Test
	public void test2urlFileUpload() throws Exception {
		Response res = build("files/urlupload",
				MediaType.APPLICATION_JSON, "fileurl",
				URLEncoder.encode("http://www.newyorker.com/wp-content/uploads"
						+ "/2014/12/Batuman-Biggest-Stone-Block-1200.jpg",
						"UTF-8"))
				.post(null);
		System.out.println("--> " + res);
	}

}
