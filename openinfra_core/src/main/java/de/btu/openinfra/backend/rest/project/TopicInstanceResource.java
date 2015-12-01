package de.btu.openinfra.backend.rest.project;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
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

import de.btu.openinfra.backend.OpenInfraProperties;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.AttributeValueGeomType;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.pojos.TopicCharacteristicPojo;
import de.btu.openinfra.backend.db.pojos.TopicPojo;
import de.btu.openinfra.backend.db.pojos.project.AttributeValuePojo;
import de.btu.openinfra.backend.db.pojos.project.TopicInstanceAssociationPojo;
import de.btu.openinfra.backend.db.pojos.project.TopicInstancePojo;
import de.btu.openinfra.backend.db.rbac.AttributeValueRbac;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;
import de.btu.openinfra.backend.db.rbac.TopicCharacteristicRbac;
import de.btu.openinfra.backend.db.rbac.TopicInstanceAssociationRbac;
import de.btu.openinfra.backend.db.rbac.TopicInstanceRbac;
import de.btu.openinfra.backend.db.rbac.TopicRbac;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path(OpenInfraResponseBuilder.REST_URI_PROJECTS + "/topicinstances")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
    MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET})
public class TopicInstanceResource {

	@GET
	@Path("count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getTopicInstanceCount(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("projectId") UUID projectId) {
		return new TopicInstanceRbac(
				projectId,
				OpenInfraSchemas.PROJECTS).getCount(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo);
	}

	@GET
	@Path("{topicInstanceId}")
	public TopicInstancePojo get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("topicInstanceId") UUID topicInstanceId) {
		return new TopicInstanceRbac(
				projectId,
				OpenInfraSchemas.PROJECTS).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						topicInstanceId);
	}

	@PUT
    @Path("{topicInstanceId}")
    public Response update(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
            @PathParam("topicInstanceId") UUID topicInstanceId,
            TopicInstancePojo pojo) {
	    return OpenInfraResponseBuilder.putResponse(
	            new TopicInstanceRbac(
	                    projectId,
	                    OpenInfraSchemas.PROJECTS).createOrUpdate(
	                            OpenInfraHttpMethod.valueOf(
                                        request.getMethod()),
	                            uriInfo,
	                            pojo,
	                            topicInstanceId,
	                            pojo.getMetaData()));
    }

	@DELETE
	@Path("{topicInstanceId}")
	public Response delete(
	        @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
            @PathParam("topicInstanceId") UUID topicInstanceId) {
	    boolean deleteResult =
	            new TopicInstanceRbac(
                    projectId,
                    OpenInfraSchemas.PROJECTS).delete(
                            OpenInfraHttpMethod.valueOf(
                                    request.getMethod()),
                            uriInfo,
                            topicInstanceId);
	    return OpenInfraResponseBuilder.deleteResponse(
	            deleteResult,
	            topicInstanceId);
	}

	@GET
	@Path("{topicInstanceId}/associationsto")
	public List<TopicInstanceAssociationPojo> getAssociations(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("topicInstanceId") UUID topicInstanceId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		// Define the specific parameters when not specified correctly
		if(size == 0) {
			offset = OpenInfraProperties.DEFAULT_OFFSET;
			size = OpenInfraProperties.DEFAULT_SIZE;
		} // end if

		return new TopicInstanceAssociationRbac(
				projectId,
				OpenInfraSchemas.PROJECTS).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						topicInstanceId,
						offset,
						size);
	}

	@POST
    @Path("{topicInstanceId}/associationsto")
	public Response create(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("topicInstanceId") UUID topicInstanceId,
            TopicInstanceAssociationPojo pojo) {
	    return OpenInfraResponseBuilder.postResponse(
                new TopicInstanceAssociationRbac(
                        projectId,
                        OpenInfraSchemas.PROJECTS).createOrUpdate(
                                OpenInfraHttpMethod.valueOf(
                                        request.getMethod()),
                                uriInfo,
                                pojo,
                                topicInstanceId,
                                pojo.getAssociationInstanceId(),
                                null,
                                null,
                                pojo.getMetaData()));
    }

	@GET
	@Path("{topicInstanceId}/associationsto/topiccharacteristics")
	public List<TopicCharacteristicPojo> getTopicCharacteristicsTo(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("topicInstanceId") UUID topicInstanceId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		// Define the specific parameters when not specified correctly
		if(size == 0) {
			offset = OpenInfraProperties.DEFAULT_OFFSET;
			size = OpenInfraProperties.DEFAULT_SIZE;
		} // end if

		return new TopicCharacteristicRbac(
				projectId, OpenInfraSchemas.PROJECTS)
		.readByTopicInstanceAssociationTo(
				OpenInfraHttpMethod.valueOf(request.getMethod()),
				uriInfo, PtLocaleDao.forLanguageTag(language), topicInstanceId,
				offset, size);
	}

	@GET
	@Path("{topicInstanceId}/associationsto/topiccharacteristics/{topCharId}")
	public List<TopicInstanceAssociationPojo> getAssociationsToByTopchar(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("topicInstanceId") UUID topicInstanceId,
			@PathParam("topCharId") UUID topCharId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		// Define the specific parameters when not specified correctly
		if(size == 0) {
			offset = OpenInfraProperties.DEFAULT_OFFSET;
			size = OpenInfraProperties.DEFAULT_SIZE;
		} // end if

		return new TopicInstanceAssociationRbac(
				projectId, OpenInfraSchemas.PROJECTS)
			.readAssociationToByTopchar(OpenInfraHttpMethod.valueOf(
					request.getMethod()), uriInfo,
					PtLocaleDao.forLanguageTag(language),
					topicInstanceId, topCharId, offset, size);
	}

	@GET
	@Path("{topicInstanceId}/associationsfrom/topiccharacteristics/{topCharId}")
	public List<TopicInstanceAssociationPojo> getAssociationsFromByTopchar(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("topicInstanceId") UUID topicInstanceId,
			@PathParam("topCharId") UUID topCharId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		// Define the specific parameters when not specified correctly
		if(size == 0) {
			offset = OpenInfraProperties.DEFAULT_OFFSET;
			size = OpenInfraProperties.DEFAULT_SIZE;
		} // end if

		return new TopicInstanceAssociationRbac(
				projectId, OpenInfraSchemas.PROJECTS)
			.readAssociationFromByTopchar(OpenInfraHttpMethod.valueOf(
					request.getMethod()), uriInfo,
					PtLocaleDao.forLanguageTag(language),
					topicInstanceId, topCharId, offset, size);
	}

	@GET
	@Path("{topicInstanceId}/associationsfrom/topiccharacteristics")
	public List<TopicCharacteristicPojo> getTopicCharacteristicsFrom(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("topicInstanceId") UUID topicInstanceId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		// Define the specific parameters when not specified correctly
		if(size == 0) {
			offset = OpenInfraProperties.DEFAULT_OFFSET;
			size = OpenInfraProperties.DEFAULT_SIZE;
		} // end if

		return new TopicCharacteristicRbac(
				projectId, OpenInfraSchemas.PROJECTS)
		.readByTopicInstanceAssociationFrom(
				OpenInfraHttpMethod.valueOf(request.getMethod()),
				uriInfo, PtLocaleDao.forLanguageTag(language), topicInstanceId,
				offset, size);
	}


	@GET
	@Path("{topicInstanceId}/associationsto/count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getTopicInstanceAssociationCount(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("projectId") UUID projectId,
			@PathParam("topicInstanceId")
				UUID topicInstanceId) {
		return new TopicInstanceAssociationRbac(
				projectId,
				OpenInfraSchemas.PROJECTS).getCount(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						topicInstanceId);
	}

	@GET
	@Path("{topicInstanceId}/associationsto/{associatedTopicInstanceId}")
	public List<TopicInstanceAssociationPojo> getAssociations(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("topicInstanceId") UUID topicInstanceId,
			@PathParam("associatedTopicInstanceId")
				UUID associatedTopicInstanceId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		// Define the specific parameters when not specified correctly
		if(size == 0) {
			offset = OpenInfraProperties.DEFAULT_OFFSET;
			size = OpenInfraProperties.DEFAULT_SIZE;
		} // end if

		return new TopicInstanceAssociationRbac(
				projectId,
				OpenInfraSchemas.PROJECTS).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						topicInstanceId,
						associatedTopicInstanceId, offset, size);
	}

	@PUT
    @Path("{topicInstanceId}/associationsto/{associatedTopicInstanceId}")
	public Response update(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("topicInstanceId") UUID topicInstanceId,
            @PathParam("associatedTopicInstanceId")
                UUID associatedTopicInstanceId,
            TopicInstanceAssociationPojo pojo) {
	    return OpenInfraResponseBuilder.putResponse(
                new TopicInstanceAssociationRbac(
                        projectId,
                        OpenInfraSchemas.PROJECTS).createOrUpdate(
                                OpenInfraHttpMethod.valueOf(
                                        request.getMethod()),
                                uriInfo,
                                pojo,
                                topicInstanceId,
                                pojo.getAssociationInstanceId(),
                                associatedTopicInstanceId,
                                pojo.getAssociatedInstance().getUuid(),
                                pojo.getMetaData()));
    }

	@DELETE
    @Path("{topicInstanceId}/associationsto/{associatedTopicInstanceId}")
    public Response delete(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("topicInstanceId") UUID topicInstanceId,
            @PathParam("associatedTopicInstanceId")
                UUID associatedTopicInstanceId) {
	    return OpenInfraResponseBuilder.deleteResponse(
                new TopicInstanceAssociationRbac(
                        projectId,
                        OpenInfraSchemas.PROJECTS).delete(
                                OpenInfraHttpMethod.valueOf(
                                      request.getMethod()),
                                uriInfo,
                                topicInstanceId,
                                associatedTopicInstanceId));
	}

	@GET
    @Path("/{topicInstanceId}/attributetypes/{attributeTypeId}/attributevalues"
            + "/new")
    public AttributeValuePojo newAttributeValue(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
            @PathParam("topicInstanceId") UUID topicInstanceId,
            @PathParam("attributeTypeId") UUID attributeTypeId,
            @QueryParam("geomType") AttributeValueGeomType geomType) {
        return new AttributeValueRbac(
                projectId,
                OpenInfraSchemas.PROJECTS).newAttributeValue(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
                        topicInstanceId,
                        attributeTypeId,
                        PtLocaleDao.forLanguageTag(language));
    }

	@GET
    @Path("/{topicInstanceId}/attributetypes/{attributeTypeId}/attributevalues")
    public List<AttributeValuePojo> getAttributeValues(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
            @PathParam("topicInstanceId") UUID topicInstanceId,
            @PathParam("attributeTypeId") UUID attributeTypeId,
            @QueryParam("offset") int offset,
            @QueryParam("size") int size) {
		// Define the specific parameters when not specified correctly
		if(size == 0) {
			offset = OpenInfraProperties.DEFAULT_OFFSET;
			size = OpenInfraProperties.DEFAULT_SIZE;
		} // end if

        return new AttributeValueRbac(
                projectId,
                OpenInfraSchemas.PROJECTS).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
                        PtLocaleDao.forLanguageTag(language),
                        topicInstanceId,
                        attributeTypeId,
                        offset,
                        size);
    }

	@GET
    @Path("{topicInstanceId}/topic")
    public TopicPojo get(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
            @PathParam("topicInstanceId") UUID topicInstanceId,
            @QueryParam("geomType") AttributeValueGeomType geomType) {
        return new TopicRbac(
                projectId,
                OpenInfraSchemas.PROJECTS).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
                        PtLocaleDao.forLanguageTag(language),
                        topicInstanceId,
                        geomType);
    }
}
