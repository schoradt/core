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

import de.btu.openinfra.backend.db.pojos.AttributeTypeGroupToAttributeTypePojo;
import de.btu.openinfra.backend.db.pojos.AttributeTypePojo;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path(OpenInfraResponseBuilder.REST_URI + "/attributetypes")
@Produces(MediaType.TEXT_HTML +
		OpenInfraResponseBuilder.UTF8_CHARSET +
		OpenInfraResponseBuilder.HTML_PRIORITY)
public class AttributeTypeResource {

	@GET
	@Template(name="/views/list/AttributeTypes.jsp")
	public List<AttributeTypePojo> get(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new de.btu.openinfra.backend.rest.AttributeTypeResource()
			.get(language, projectId, schema, offset, size);
	}

	@GET
	@Path("{attributeTypeId}")
	@Template(name="/views/detail/AttributeTypes.jsp")
	public AttributeTypePojo getView(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("attributeTypeId") UUID attributeTypeId) {
		return new de.btu.openinfra.backend.rest.AttributeTypeResource()
			.get(language, projectId, schema, attributeTypeId);
	}

	@GET
	@Template(name="/views/list/AttributeTypeGroupToAttributeType.jsp")
	@Path("{attributeTypeId}/attributetypegroups")
	public List<AttributeTypeGroupToAttributeTypePojo> getView(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("attributeTypeId") UUID attributeTypeId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new de.btu.openinfra.backend.rest.AttributeTypeResource()
			.get(language, projectId, schema, attributeTypeId, offset, size);
	}

}
