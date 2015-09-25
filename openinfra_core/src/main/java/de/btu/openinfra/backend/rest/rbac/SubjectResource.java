package de.btu.openinfra.backend.rest.rbac;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.daos.rbac.SubjectDao;
import de.btu.openinfra.backend.db.pojos.rbac.SubjectPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraRealmNames;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;


@Path(OpenInfraResponseBuilder.REST_URI_RBAC + "/subjects")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
	+ OpenInfraResponseBuilder.UTF8_CHARSET})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class SubjectResource {

	@GET
	public List<SubjectPojo> get(
			@QueryParam("language") String language,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new SubjectDao().read(
						PtLocaleDao.forLanguageTag(language),
						offset,
						size);
	}
	
	@GET
	@Path("{id}")
	public SubjectPojo get(
			@QueryParam("language") String language,
			@PathParam("id") UUID uuid) {
		return new SubjectDao().read(
				PtLocaleDao.forLanguageTag(language),
				uuid);
	}
	
	@GET
	@Path("bylogin")
	public SubjectPojo getSubjectByLogin(
			@QueryParam("login") String login) {
		return new SubjectDao().read(login);
	}

	@GET
	@Path("self")
	public SubjectPojo self() {
		// Retrieve the login from principals (there might be multiple)
		Subject s = SecurityUtils.getSubject();
		List<String> login = new LinkedList<String>();
		for(Object o : s.getPrincipals().fromRealm(
				OpenInfraRealmNames.LOGIN.name())) {
			login.add(o.toString());
		}
		return new SubjectDao().read(login.get(0));
	}

}