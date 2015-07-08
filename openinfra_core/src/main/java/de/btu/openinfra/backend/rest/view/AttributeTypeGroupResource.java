package de.btu.openinfra.backend.rest.view;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;

import de.btu.openinfra.backend.db.daos.OpenInfraOrderBy;
import de.btu.openinfra.backend.db.daos.OpenInfraSortOrder;
import de.btu.openinfra.backend.db.pojos.AttributeTypeGroupPojo;
import de.btu.openinfra.backend.db.pojos.AttributeTypeToAttributeTypeGroupPojo;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

/**
 * This class represents and implements the AttributeTypeGroupResource resource.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@Path(OpenInfraResponseBuilder.REST_URI + "/attributetypegroups")
@Produces(MediaType.TEXT_HTML +
		OpenInfraResponseBuilder.UTF8_CHARSET +
		OpenInfraResponseBuilder.HTML_PRIORITY)
public class AttributeTypeGroupResource {

	@GET
	@Template(name="/views/list/AttributeTypeGroups.jsp")
	public List<AttributeTypeGroupPojo> getView(
			@Context UriInfo uri,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@QueryParam("sortOrder") OpenInfraSortOrder sortOrder,
			@QueryParam("orderBy") OpenInfraOrderBy orderBy,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new de.btu.openinfra.backend.rest.AttributeTypeGroupResource()
			.get(language, projectId, schema, sortOrder, orderBy, offset, size);
	}

	@GET
	@Path("{attributeTypeGroupId}")
	@Template(name="/views/detail/AttributeTypeGroups.jsp")
	public AttributeTypeGroupPojo getView(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("attributeTypeGroupId") UUID attributeTypeGroupId) {
		return new de.btu.openinfra.backend.rest.AttributeTypeGroupResource()
			.get(language, projectId, schema, attributeTypeGroupId);
	}

	@GET
	@Path("{attributeTypeGroupId}/attributetypes")
	@Template(name="/views/list/AttributeTypeToAttributeTypeGroups.jsp")
	public List<AttributeTypeToAttributeTypeGroupPojo> getView(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("attributeTypeGroupId") UUID attributeTypeGroupId,
			@QueryParam("sortOrder") OpenInfraSortOrder sortOrder,
			@QueryParam("orderBy") OpenInfraOrderBy orderBy,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new de.btu.openinfra.backend.rest.AttributeTypeGroupResource()
			.get(language, projectId, schema,
					attributeTypeGroupId, sortOrder, orderBy, offset, size);
	}

}
