package de.btu.openinfra.backend.rest.project;

import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.AttributeValueGeomType;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.pojos.AttributeValuePojo;
import de.btu.openinfra.backend.db.rbac.AttributeValueRbac;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path("/projects/{projectId}/attributevalues")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
    MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class AttributeValueResource {

	@GET
	@Path("{attributeValueId}")
	public AttributeValuePojo get(
			@Context UriInfo uriInfo,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("attributeValueId") UUID attributeValueId,
			@QueryParam("geomType") AttributeValueGeomType geomType) {
		return new AttributeValueRbac(
				projectId,
				OpenInfraSchemas.PROJECTS).read(
						OpenInfraHttpMethod.GET, 
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						attributeValueId,
						geomType);
	}

	@PUT
	@Path("{attributeValueId}")
	public Response update(
			@Context UriInfo uriInfo,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("attributeValueId") UUID attributeValueId,
			AttributeValuePojo pojo) {
	    UUID id = new AttributeValueRbac(
                projectId,
                OpenInfraSchemas.PROJECTS).distributeTypes(
                		OpenInfraHttpMethod.GET, 
						uriInfo,
						pojo, projectId,
                        attributeValueId);
        return OpenInfraResponseBuilder.putResponse(id);
	}

	@GET
	@Path("geomtypes")
	public AttributeValueGeomType[] getGeomTypes() {
		return AttributeValueGeomType.values();
	}

	@POST
    public Response create(
    		@Context UriInfo uriInfo,
            @PathParam("projectId") UUID projectId,
            AttributeValuePojo pojo) {
	    UUID id = new AttributeValueRbac(
                projectId,
                OpenInfraSchemas.PROJECTS).distributeTypes(
                		OpenInfraHttpMethod.GET, 
						uriInfo,
						pojo, projectId,
                        null);
        return OpenInfraResponseBuilder.postResponse(id);
    }

	@DELETE
	@Path("{attributeValueId}")
	public Response delete(
			@Context UriInfo uriInfo,
    	    @PathParam("projectId") UUID projectId,
            @PathParam("attributeValueId") UUID attributeValueId) {
	    return OpenInfraResponseBuilder.deleteResponse(
	            new AttributeValueRbac(projectId, OpenInfraSchemas.PROJECTS)
	                .delete(OpenInfraHttpMethod.GET, 
							uriInfo,
							attributeValueId),
                attributeValueId);
	}
}
