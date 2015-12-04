package de.btu.openinfra.backend.rest.project.test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.btu.openinfra.backend.db.pojos.webapp.WebappProjectPojo;
import de.btu.openinfra.backend.db.pojos.webapp.WebappSubjectPojo;

public class OpenInfraWebappTest extends OpenInfraTest {

	private String projectPath = "webapp/809a2e7d-d52b-43e0-9733-04399953fa01/"
			+ "projects/" + BAALBEK;
	/**
	 * user: tino
	 */
	private String subjectPath = "webapp/809a2e7d-d52b-43e0-9733-04399953fa01/"
			+ "subjects/" + "7f08131c-a6d9-4db1-8841-8a80a5066b6a";

	public static void main(String args[]) {
		OpenInfraWebappTest test = new OpenInfraWebappTest();
		test.rootLogin();

		test.putProject();
		test.getProject();
		test.deleteProject();

		test.putSubject();
		test.getSubject();
		test.deleteSubject();


	}

	public void putProject() {
		WebappProjectPojo wpp = new WebappProjectPojo();
		wpp.setData("hier sind daten!");
		Response res = build(projectPath,
				MediaType.APPLICATION_JSON).put(Entity.json(wpp));
		System.out.println(res);
	}

	public void getProject() {
		System.out.println(build(projectPath, MediaType.APPLICATION_JSON).get());
	}

	public void deleteProject() {
		System.out.println(build(projectPath, MediaType.APPLICATION_JSON).delete());
	}

	public void putSubject() {
		WebappSubjectPojo wsp = new WebappSubjectPojo();
		wsp.setData("hier sind subject daten!");
		Response res = build(subjectPath,
				MediaType.APPLICATION_JSON).put(Entity.json(wsp));
		System.out.println(res);
	}

	public void getSubject() {
		System.out.println(build(subjectPath, MediaType.APPLICATION_JSON).get());
	}

	public void deleteSubject() {
		System.out.println(build(subjectPath, MediaType.APPLICATION_JSON).delete());
	}

}
