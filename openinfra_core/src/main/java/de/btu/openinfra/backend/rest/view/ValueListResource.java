package de.btu.openinfra.backend.rest.view;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.mvc.Template;

import de.btu.openinfra.backend.db.pojos.ValueListPojo;
import de.btu.openinfra.backend.db.pojos.ValueListValuePojo;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path(OpenInfraResponseBuilder.REST_URI + "/valuelists")
@Produces(MediaType.TEXT_HTML +
		OpenInfraResponseBuilder.UTF8_CHARSET +
		OpenInfraResponseBuilder.HTML_PRIORITY)
public class ValueListResource {

	@GET
	@Template(name="/views/list/ValueLists.jsp")
	public List<ValueListPojo> getView(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new de.btu.openinfra.backend.rest.ValueListResource()
				.get(language, projectId, schema, offset, size);
	}

	@GET
	@Path("{valueListId}")
	@Template(name="/views/detail/ValueLists.jsp")
	public ValueListPojo getView(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("valueListId") UUID valueListId) {
		return new de.btu.openinfra.backend.rest.ValueListResource()
			.get(language, projectId, schema, valueListId);
	}

	@GET
	@Path("{valueListId}/valuelistvalues")
	@Template(name="/views/list/ValueListsValues.jsp")
	public List<ValueListValuePojo> getValueListValues(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("valueListId") UUID valueListId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new de.btu.openinfra.backend.rest.ValueListResource()
			.getValueListValues(language, projectId, schema,
					valueListId, offset, size);
	}

}
