package de.btu.openinfra.backend.rest.view.system;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.mvc.Template;

import de.btu.openinfra.backend.db.daos.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.daos.ValueListDao;
import de.btu.openinfra.backend.db.pojos.ValueListPojo;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

//@Path("/system/valuelists")
@Produces(MediaType.TEXT_HTML + OpenInfraResponseBuilder.UTF8_CHARSET)
public class ValueListsResource {

	@GET
	@Template(name="/views/list/ValueLists.jsp")
	public List<ValueListPojo> get(
			@QueryParam("language") String language,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new ValueListDao(
				null,
				OpenInfraSchemas.SYSTEM).read(
						PtLocaleDao.forLanguageTag(language),
						offset,
						size);
	}

	@GET
	@Path("{valueListId}")
	@Template(name="/views/detail/ValueLists.jsp")
	public ValueListPojo get(
			@QueryParam("language") String language,
			@PathParam("valueListId") UUID valueListId) {
		return new ValueListDao(
				null,
				OpenInfraSchemas.SYSTEM).read(
						PtLocaleDao.forLanguageTag(language),
						valueListId);
	}

}
