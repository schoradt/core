package de.btu.openinfra.backend.rest;

import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.btu.openinfra.backend.db.daos.AttributeValueDao;
import de.btu.openinfra.backend.db.daos.AttributeValueDomainDao;
import de.btu.openinfra.backend.db.daos.AttributeValueGeomDao;
import de.btu.openinfra.backend.db.daos.AttributeValueGeomType;
import de.btu.openinfra.backend.db.daos.AttributeValueGeomzDao;
import de.btu.openinfra.backend.db.daos.AttributeValueValueDao;
import de.btu.openinfra.backend.db.daos.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.pojos.AttributeValuePojo;

@Path("/projects/{projectId}/attributevalues")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY, 
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY})
public class AttributeValueResource {
	
	@GET
	@Path("{attributeValueId}")
	public AttributeValuePojo get(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("attributeValueId") UUID attributeValueId,
			@QueryParam("geomType") AttributeValueGeomType geomType) {
		return new AttributeValueDao(
				projectId, 
				OpenInfraSchemas.PROJECTS).read(
						PtLocaleDao.forLanguageTag(language),
						attributeValueId, 
						geomType);
	}
	
	@PUT
	@Path("{attributeValueId}")
	public Response update(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("attributeValueId") UUID attributeValueId,
			AttributeValuePojo av) {
		UUID uuid = new AttributeValueDao(
    			projectId, 
    			OpenInfraSchemas.PROJECTS).createOrUpdate(av);
		return OpenInfraResponseBuilder.putResponse(uuid);
	}
	
	@GET
	@Path("geomtypes")
	public AttributeValueGeomType[] getGeomTypes() {
		return AttributeValueGeomType.values();
	}

	@GET
	@Path("/topicinstances/{topicInstanceId}/attributetypes/{attributeTypeId}/hull")
	public AttributeValuePojo getEmptyShell(
	        @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
            @PathParam("topicInstanceId") UUID topicInstanceId,
            @PathParam("attributeTypeId") UUID attributeTypeId,
            @QueryParam("geomType") AttributeValueGeomType geomType) {
	    return new AttributeValueDao(
                projectId, 
                OpenInfraSchemas.PROJECTS).createEmptyShell(
                        topicInstanceId,
                        attributeTypeId,
                        PtLocaleDao.forLanguageTag(language));
	}
	    
	@POST
    public Response createAttributeValue(
            @PathParam("projectId") UUID projectId,
            AttributeValuePojo pojo) {
        
        // It is not possible to write the AttributeValue directly because a
        // constraint. The appropriated AttributeValuePojo must be extracted
        // and written separately.
        UUID id = null;
        
        switch (pojo.getAttributeValueType()) {
        case ATTRIBUTE_VALUE_DOMAIN:
            id = new AttributeValueDomainDao(
                    projectId,
                    OpenInfraSchemas.PROJECTS).createOrUpdate(
                            pojo.getAttributeValueDomain());
            break;
        case ATTRIBUTE_VALUE_GEOM:
            id = new AttributeValueGeomDao(
                    projectId,
                    OpenInfraSchemas.PROJECTS).createOrUpdate(
                            pojo.getAttributeValueGeom());
            break;
        case ATTRIBUTE_VALUE_GEOMZ:
            id = new AttributeValueGeomzDao(
                    projectId,
                    OpenInfraSchemas.PROJECTS).createOrUpdate(
                            pojo.getAttributeValueGeomz());
            break;
        case ATTRIBUTE_VALUE_VALUE:
             id = new AttributeValueValueDao(
                    projectId,
                    OpenInfraSchemas.PROJECTS).createOrUpdate(
                            pojo.getAttributeValueValue());
            break;
        }
        
        return OpenInfraResponseBuilder.postResponse(id);
    }
}
