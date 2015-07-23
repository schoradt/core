package de.btu.openinfra.backend.rest;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.btu.openinfra.backend.db.daos.AttributeTypeAssociationDao;
import de.btu.openinfra.backend.db.daos.AttributeTypeDao;
import de.btu.openinfra.backend.db.daos.AttributeTypeGroupToAttributeTypeDao;
import de.btu.openinfra.backend.db.daos.OpenInfraOrderByEnum;
import de.btu.openinfra.backend.db.daos.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraSortOrder;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.pojos.AttributeTypeAssociationPojo;
import de.btu.openinfra.backend.db.pojos.AttributeTypeGroupToAttributeTypePojo;
import de.btu.openinfra.backend.db.pojos.AttributeTypePojo;

@Path(OpenInfraResponseBuilder.REST_URI + "/attributetypes")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY, 
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY})
public class AttributeTypeResource {
	
	@GET
	public List<AttributeTypePojo> get(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@QueryParam("sortOrder") OpenInfraSortOrder sortOrder,
			@QueryParam("orderBy") OpenInfraOrderByEnum orderBy,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new AttributeTypeDao(
				projectId, 
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						PtLocaleDao.forLanguageTag(language),
						sortOrder,
						orderBy,
						offset, 
						size);
	}
	
	@GET
	@Path("{attributeTypeId}/associations")
	public List<AttributeTypeAssociationPojo> getAssociations(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("attributeTypeId") UUID attributeTypeId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		
		return new AttributeTypeAssociationDao(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						PtLocaleDao.forLanguageTag(language),
						attributeTypeId,
						offset,
						size);
	}
	
	@GET
	@Path("{attributeTypeId}/associations/{associatedAttributeTypeId}")
	public List<AttributeTypeAssociationPojo> getAssociations(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("attributeTypeId") UUID attributeTypeId,
			@PathParam("associatedAttributeTypeId")
				UUID associatedAttributeTypeId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		
		return new AttributeTypeAssociationDao(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						PtLocaleDao.forLanguageTag(language),
						attributeTypeId,
						associatedAttributeTypeId,
						offset,
						size);
	}
	
	@GET
	@Path("count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getCount(
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema) {
		return new AttributeTypeDao(
				projectId, 
				OpenInfraSchemas.valueOf(schema.toUpperCase())).getCount();
	}
	
	@GET
	@Path("{attributeTypeId}")
	public AttributeTypePojo get(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("attributeTypeId") UUID attributeTypeId) {
		return new AttributeTypeDao(
				projectId, 
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						PtLocaleDao.forLanguageTag(language),
						attributeTypeId);
	}
	
	@GET
	@Path("{attributeTypeId}/attributetypegroups")
	public List<AttributeTypeGroupToAttributeTypePojo> get(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("attributeTypeId") UUID attributeTypeId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new AttributeTypeGroupToAttributeTypeDao(
				projectId, 
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						PtLocaleDao.forLanguageTag(language),
						attributeTypeId, 
						offset, 
						size);
	}
	
	@GET
	@Path("{attributeTypeId}/attributetypegroups/count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getAttributeTypeGroupsCount(
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("attributeTypeId") UUID attributeTypeId) {
		return new AttributeTypeGroupToAttributeTypeDao(
				projectId, 
				OpenInfraSchemas.valueOf(schema.toUpperCase())).getCount(
						attributeTypeId);
	}
	
	@GET
	@Path("{attributeTypeId}/attributetypegroups/{attributeTypeGroupId}")
	public List<AttributeTypeGroupToAttributeTypePojo> get(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("attributeTypeId") UUID attributeTypeId,
			@PathParam("attributeTypeGroupId") UUID attributeTypeGroupId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new AttributeTypeGroupToAttributeTypeDao(
				projectId, 
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						PtLocaleDao.forLanguageTag(language),
						attributeTypeId,
						attributeTypeGroupId,
						offset,
						size);
	}

}
