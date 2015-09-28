package de.btu.openinfra.backend.rest.rbac;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.daos.rbac.SubjectProjectDao;
import de.btu.openinfra.backend.db.pojos.rbac.SubjectProjectPojo;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path(OpenInfraResponseBuilder.REST_URI_RBAC + "/subjectproject")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
	+ OpenInfraResponseBuilder.UTF8_CHARSET})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class SubjectProjectResource {
	
	@GET
	public List<SubjectProjectPojo> get(
			@QueryParam("language") String language,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new SubjectProjectDao().read(
				PtLocaleDao.forLanguageTag(language), offset, size);
	}
	
	@GET
	@Path("{id}")
	public SubjectProjectPojo get(
			@QueryParam("language") String language,
			@PathParam("id") UUID uuid) {
		return new SubjectProjectDao().read(
				PtLocaleDao.forLanguageTag(language), 
				uuid);
	}
		

}
