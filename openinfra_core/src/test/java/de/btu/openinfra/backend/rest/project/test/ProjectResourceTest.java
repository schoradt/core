package de.btu.openinfra.backend.rest.project.test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * !!!!!!!!!!!!!!!! Caution !!!!!!!!!!!!!!!!!
 *  
 *  Don't use this anymore! Use the integrated Jersey test framework instead!
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class ProjectResourceTest {
	
	private static final String BASE_URI = 
			"http://localhost:8080/openinfra_backend/rest/";
	private static final String PATH = "projects"
			+ "/fd27a347-4e33-4ed7-aebc-eeff6dbf1054/multiplicities";
	
	private static final String XML_FILE_PATH = "./xml/";
	private static final String JSON_FILE_PATH = "./json/";
	
	private WebTarget target;
	
	@Before
	public void setUp() {
		Client client = ClientBuilder.newClient();
	    target = client.target(BASE_URI);
	}
	
	@Test
	public void testProject() throws Exception {
		System.out.println("############## Project Test ##############");

		// 1. Read files (JSON & XML)
		String projectXml = new String(Files.readAllBytes(
				Paths.get(getClass().getResource(
						XML_FILE_PATH + "Project.xml").toURI())));
		String projectJSON = new String(Files.readAllBytes(
				Paths.get(getClass().getResource(
						JSON_FILE_PATH + "Project.json").toURI())));
		// 2. Execute post request
		System.out.println("\n++++ Post XML Request ++++");
		Response response = target.path(PATH).request(
				MediaType.APPLICATION_XML).post(
						Entity.entity(
								projectXml, 
								MediaType.APPLICATION_XML));
		UUID id = UUID.fromString(response.readEntity(String.class));
		System.out.println(id + " " + response);
		Assert.assertTrue(response.getStatus() == 200);

		

		
	}
	
	@Test
	public void testMultiplicity() throws Exception {
		
		System.out.println("############## Multiplicity Test ##############");
		
		// 1. Read files (JSON & XML)
		String multiplicityXml = new String(Files.readAllBytes(
				Paths.get(getClass().getResource(
						XML_FILE_PATH + "Multiplicity.xml").toURI())));
		String multiplicityJSON = new String(Files.readAllBytes(
				Paths.get(getClass().getResource(
						JSON_FILE_PATH + "Multiplicity.json").toURI())));
		// 2. Execute post request
		System.out.println("\n++++ Post XML Request ++++");
		Response response = target.path(PATH).request(
				MediaType.APPLICATION_XML).post(
						Entity.entity(
								multiplicityXml, 
								MediaType.APPLICATION_XML));
		UUID id = UUID.fromString(response.readEntity(String.class));
		System.out.println(id + " " + response);
		Assert.assertTrue(response.getStatus() == 200);
		
		// 3. Execute get requests
		System.out.println("\n++++ Get JSON Request ++++");
	    String result = target.path(PATH + "/" + id).request(
	    		MediaType.APPLICATION_JSON).get(String.class);
	    System.out.println(result);
	    Assert.assertTrue(result != null);
		
		// 4. Execute put request
	    System.out.println("\n++++ Put JSON Request ++++");
	    String putPath = PATH + "/" + id;
	    //System.out.println("putPath: " + putPath);
	    multiplicityJSON = String.format(multiplicityJSON, "" + id);
	    //System.out.println(multiplicityJSON);
		response = target.path(putPath).request(
				MediaType.APPLICATION_JSON).put(
						Entity.entity(
								multiplicityJSON, 
								MediaType.APPLICATION_JSON));
		System.out.println(id + " " + response);
		Assert.assertTrue(response.getStatus() == 200);
		
		// 5. Execute get request
	    System.out.println("\n++++ Get XML Request ++++");
	    result = target.path(PATH).request(
	    		MediaType.APPLICATION_XML).get(String.class);
	    System.out.println(result);
	    Assert.assertTrue(result != null);
		
		// 6. Execute delete request
	    System.out.println("\n++++ Delete Request ++++");
	    response = target.path(PATH + "/" + id).request().delete();
	    System.out.println(response.readEntity(String.class) + " " + response);
		
	}
	
	

}
