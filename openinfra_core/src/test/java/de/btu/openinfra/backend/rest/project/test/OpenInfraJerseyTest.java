package de.btu.openinfra.backend.rest.project.test;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import de.btu.openinfra.backend.OpenInfraApplication;

public class OpenInfraJerseyTest extends JerseyTest {

	private static final String XML_FILE_PATH = "./xml/";
	private static final String JSON_FILE_PATH = "./json/";
	
	@Override
	public Application configure() {
		return new OpenInfraApplication();
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
