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
import de.btu.openinfra.backend.db.OpenInfraOrderBy;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.OpenInfraSortOrder;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.daos.project.AttributeValueGeomType;
import de.btu.openinfra.backend.db.pojos.TopicCharacteristicPojo;
import de.btu.openinfra.backend.db.pojos.project.AttributeValuePojo;
import de.btu.openinfra.backend.db.pojos.project.TopicInstanceAssociationFromPojo;
import de.btu.openinfra.backend.db.pojos.project.TopicInstanceAssociationToPojo;
import de.btu.openinfra.backend.db.pojos.project.TopicInstancePojo;
import de.btu.openinfra.backend.db.pojos.project.TopicPojo;
import de.btu.openinfra.backend.db.rbac.AttributeValueRbac;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;
import de.btu.openinfra.backend.db.rbac.TopicCharacteristicRbac;
import de.btu.openinfra.backend.db.rbac.TopicInstanceAssociationFromRbac;
import de.btu.openinfra.backend.db.rbac.TopicInstanceAssociationToRbac;
import de.btu.openinfra.backend.db.rbac.TopicInstanceRbac;
import de.btu.openinfra.backend.db.rbac.TopicRbac;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

/**
 * This class represents and implements the resources for topic instances in the
 * project schema.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@Path(OpenInfraResponseBuilder.REST_URI_PROJECTS + "/topicinstances")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
    MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET})
public class TopicInstanceResource {

    /**
     * This resource provides the count of topic instances for a specific
     * project.
     * <ul>
     *   <li>rest/v1/projects/[uuid]/topicinstances/count</li>
     * </ul>
     * @param uriInfo
     * @param request
     * @param projectId The project id the topic instances belongs to.
     * @return          The count of topic instances for a project.
     */
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

	/**
	 * This method provides a TopicInstancePojo for a specific id.
     * <ul>
     *   <li>rest/v1/projects/[uuid]/topicinstances/[uuid]?language=de-DE</li>
     * </ul>
	 * @param uriInfo
	 * @param request
	 * @param language        The language of the localized objects.
	 * @param projectId       The project id the topic instances belongs to.
	 * @param topicInstanceId The id of the topic instance that is requested.
	 * @return                The requested TopicInstancePojo
	 */
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

	/**
	 * This method creates a new Topic Instance object. The specified
	 * TopicInstancePojo must contain a TopicCharacteristicPojo the instance
	 * belongs to and a list of AttributeValuePojo's that belongs to the
	 * instance. The parameter UUID and TRID of the TopicInstancePojo must not
	 * be set.
     * <ul>
     *   <li>rest/v1/projects/[uuid]/topicinstances?language=de-DE</li>
     * </ul>
	 * @param uriInfo
	 * @param request
	 * @param language  The language of the localized objects.
	 * @param projectId The id of the project the requested instance belongs to.
     * @param pojo      The TopicInstancePojo that represents the new object.
	 * @return          A Response with the UUID of the new created object or
	 *                  the status code 204.
	 */
	@POST
    public Response create(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
            TopicInstancePojo pojo) {
        UUID id = new TopicInstanceRbac(projectId,
                OpenInfraSchemas.PROJECTS).createOrUpdate(
                OpenInfraHttpMethod.valueOf(request.getMethod()),
                uriInfo,
                null,
                pojo);
        return OpenInfraResponseBuilder.postResponse(id);
    }

	/**
	 * This resource updates the Topic Instance object with the specified UUID.
     * The specified TopicInstancePojo must contain the UUID of the
     * object that should be updated, the TRID, the TopicCharacteristicPojo the
     * instance belongs to and a list of AttributeValuePojo's that belongs to
     * the instance.
     * <ul>
     *   <li>rest/v1/projects/[uuid]/topicinstances/[uuid]?language=de-DE</li>
     * </ul>
     * <b>The object id in the TopicInstancePojo and in the URI that identifies
     * the topic instance must concur.</b>
     *
	 * @param uriInfo
	 * @param request
	 * @param language        The language of the localized objects.
     * @param projectId       The id of the project the requested instance
     *                        belongs to.
	 * @param topicInstanceId The UUID of the TopicInstancePojo that should be
     *                        updated.
	 * @param pojo            The TopicInstancePojo that represents the new
	 *                        object.
	 * @return                A Response with the status code 200 for a
	 *                        successful update or 204 if the object could not
	 *                        be updated.
	 */
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
	                            topicInstanceId,
	                            pojo));
    }

	/**
	 * This resource deletes the Topic Instance object with the specified UUID.
     * <ul>
     *   <li>rest/v1/projects/[uuid]/topicinstances/[uuid]</li>
     * </ul>
     *
	 * @param uriInfo
	 * @param request
	 * @param language        The language of the localized objects.
	 * @param projectId       The id of the project the instance that should be
     *                        deleted belongs to.
	 * @param topicInstanceId The UUID of the TopicInstancePojo that should be
     *                        updated.
	 * @return                A Response with the status code 200 for a
	 *                        successful deletion or 404 if the object was not
	 *                        found.
	 */
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

	/**
	 * This resource provides a list of TopicInstanceAssociationToPojo's the
	 * specified topic instance is related to. This resource supports pagination
	 * of the list.
     * <ul>
     *   <li>rest/v1/projects/[uuid]/topicinstances/[uuid]/associationsto?language=de-DE&offset=0&size=10</li>
     * </ul>
     *
	 * @param uriInfo
	 * @param request
	 * @param language        The language of the localized objects.
     * @param projectId       The id of the project the requested associations
     *                        belongs to.
	 * @param topicInstanceId The UUID of the requesting topic instance.
	 * @param offset          The offset parameter for the elements of the list.
     * @param size            The count of elements the list should contain.
	 * @return                A list of TopicInstanceAssociationToPojo's the
	 *                        requested topic instance is related to.
	 */
	@GET
	@Path("{topicInstanceId}/associationsto")
	public List<TopicInstanceAssociationToPojo> getAssociations(
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

		return new TopicInstanceAssociationToRbac(
				projectId,
				OpenInfraSchemas.PROJECTS).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						topicInstanceId,
						offset,
						size);
	}

	/**
	 * This resource provides a list of TopicCharacteristicPojo's the specified
	 * topic instance is related to. This resource supports pagination of the
	 * list.
     * <ul>
     *   <li>rest/v1/projects/[uuid]/topicinstances/[uuid]/associationsto/topiccharacteristics?language=de-DE&offset=0&size=10</li>
     * </ul>
     *
	 * @param uriInfo
	 * @param request
	 * @param language        The language of the localized objects.
     * @param projectId       The id of the project the requested topic
     *                        characteristics belongs to.
     * @param topicInstanceId The UUID of the requesting topic instance.
     * @param offset          The offset parameter for the elements of the list.
     * @param size            The count of elements the list should contain.
     * @return                A list of TopicCharacteristicPojo's the topic
     *                        instance is related to.
	 */
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

	/**
	 * This resource provides a list of TopicInstanceAssociationToPojo's the
     * specified topic instance is related to and that points at the specified
     * topic characteristic. This resource supports sorting and pagination of
     * the list.
     * <ul>
     *   <li>rest/v1/projects/[uuid]/topicinstances/[uuid]/associationsto/topiccharacteristics/[uuid]?language=de-DE&orderBy=NAME&sortOrder=ASC&offset=0&size=10</li>
     * </ul>
     *
	 * @param uriInfo
	 * @param request
	 * @param language        The language of the localized objects.
     * @param projectId       The id of the project the requested topic
     *                        characteristics belongs to.
	 * @param projectId       The id of the project the requested associations
     *                        belongs to.
	 * @param topicInstanceId The UUID of the requesting topic instance.
	 * @param topCharId       The UUID of the requesting topic characteristic.
	 * @param sortOrder       The sort order for the list.
     * @param orderBy         The element the list should be ordered by.
     * @param offset          The offset parameter for the elements of the list.
     * @param size            The count of elements the list should contain.
     * @return                A list of TopicInstanceAssociationToPojo's the
     *                        specified topic instance is related to and that
     *                        points at the specified topic characteristic.
	 */
	@GET
	@Path("{topicInstanceId}/associationsto/topiccharacteristics/{topCharId}")
	public List<TopicInstanceAssociationToPojo> getAssociationsToByTopchar(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("topicInstanceId") UUID topicInstanceId,
			@PathParam("topCharId") UUID topCharId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size,
			@QueryParam("sortOrder") OpenInfraSortOrder sortOrder,
            @QueryParam("orderBy") OpenInfraOrderBy orderBy) {
		// Define the specific parameters when not specified correctly
		if(size == 0) {
			offset = OpenInfraProperties.DEFAULT_OFFSET;
			size = OpenInfraProperties.DEFAULT_SIZE;
		} // end if

		return new TopicInstanceAssociationToRbac(
				projectId, OpenInfraSchemas.PROJECTS)
			.readAssociationToByTopchar(OpenInfraHttpMethod.valueOf(
					request.getMethod()), uriInfo,
					PtLocaleDao.forLanguageTag(language),
					topicInstanceId, topCharId, offset, size, sortOrder,
					orderBy);
	}

	/**
	 * This resource provides a list of TopicInstanceAssociationFromPojo's that
	 * points at the specified topic instance and is related to the specified
	 * topic characteristic. This resource supports pagination of the list.
     * <ul>
     *   <li>rest/v1/projects/[uuid]/topicinstances/[uuid]/associationsfrom/topiccharacteristics/[uuid]?language=de-DE&offset=0&size=10</li>
     * </ul>
     *
	 * @param uriInfo
	 * @param request
	 * @param language        The language of the localized objects.
	 * @param projectId       The id of the project the requested associations
     *                        belongs to.
	 * @param topicInstanceId The UUID of the requesting topic instance.
     * @param topCharId       The UUID of the requesting topic characteristic.
	 * @param offset          The offset parameter for the elements of the list.
     * @param size            The count of elements the list should contain.
	 * @return                A list of TopicInstanceAssociationFromPojo's that
     *                        points at the specified topic instance and is
     *                        related to the specified topic characteristic.
	 */
	@GET
	@Path("{topicInstanceId}/associationsfrom/topiccharacteristics/{topCharId}")
	public List<TopicInstanceAssociationFromPojo> getAssociationsFromByTopchar(
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

		return new TopicInstanceAssociationFromRbac(
				projectId, OpenInfraSchemas.PROJECTS)
			.readAssociationFromByTopchar(OpenInfraHttpMethod.valueOf(
					request.getMethod()), uriInfo,
					PtLocaleDao.forLanguageTag(language),
					topicInstanceId, topCharId, offset, size);
	}

	/**
	 * This resource provides a list of TopicInstanceAssociationFromPojo's that
     * points at the specified topic instance. This resource supports pagination
     * of the list.
     * <ul>
     *   <li>rest/v1/projects/[uuid]/topicinstances/[uuid]/associationsfrom/topiccharacteristics?language=de-DE&offset=0&size=10</li>
     * </ul>
     *
	 * @param uriInfo
	 * @param request
	 * @param language        The language of the localized objects.
     * @param projectId       The id of the project the requested associations
     *                        belongs to.
     * @param topicInstanceId The UUID of the requesting topic instance.
	 * @param offset          The offset parameter for the elements of the list.
     * @param size            The count of elements the list should contain.
     * @return                A list of TopicInstanceAssociationFromPojo's that
     *                        points at the specified topic instance.
	 */
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

	/**
	 * This resource provides the count of topic instance associations the
	 * specified topic instance is related to.
	 * <ul>
     *   <li>rest/v1/projects/[uuid]/topicinstances/[uuid]/associationsto/count</li>
     * </ul>
     *
	 * @param uriInfo
	 * @param request
	 * @param projectId       The id of the project the requested associations
     *                        belongs to.
     * @param topicInstanceId The UUID of the requesting topic instance.
	 * @return                The count of associations the specified topic
	 *                        instance is related to.
	 */
	@GET
	@Path("{topicInstanceId}/associationsto/count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getTopicInstanceAssociationCount(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("projectId") UUID projectId,
			@PathParam("topicInstanceId")
				UUID topicInstanceId) {
		return new TopicInstanceAssociationToRbac(
				projectId,
				OpenInfraSchemas.PROJECTS).getCount(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						topicInstanceId);
	}

	/**
	 * This resource provides a list of TopicInstanceAssociationToPojo's the
     * specified topic instance is related to and that points at the second
     * specified topic instance. This resource supports sorting and pagination
     * of the list.
     * <ul>
     *   <li>rest/v1/projects/[uuid]/topicinstances/[uuid]/associationsto/[uuid]?language=de-DE&offset=0&size=10</li>
     * </ul>
     *
	 * @param uriInfo
	 * @param request
	 * @param language        The language of the localized objects.
	 * @param projectId       The id of the project the requested associations
     *                        belongs to.
	 * @param topicInstanceId The UUID of the requesting topic instance.
	 * @param associatedTopicInstanceId The UUID of the second requesting topic
	 *                                  instance.
     * @param offset          The offset parameter for the elements of the list.
     * @param size            The count of elements the list should contain.
	 * @return                A list of TopicInstanceAssociationFromPojo's the
     *                        specified topic instance is related to and that
     *                        points at the second topic instance.
	 */
	@GET
	@Path("{topicInstanceId}/associationsto/{associatedTopicInstanceId}")
	public List<TopicInstanceAssociationToPojo> getAssociations(
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

		return new TopicInstanceAssociationToRbac(
				projectId,
				OpenInfraSchemas.PROJECTS).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						topicInstanceId,
						associatedTopicInstanceId, offset, size);
	}

	/**
     * This resource creates a new Topic Instance association object. The
     * specified TopicInstanceAssociationToPojo contains the UUID of the
     * topic instance the relation assumes to, the associated TopicInstancePojo
     * and the relationship to the requesting TopicInstancePojo as
     * RelationshipTypePojo. The parameter UUID and TRID of the
     * TopicInstanceAssociationToPojo must not be set.
     * <ul>
     *   <li>rest/v1/projects/[uuid]/topicinstances/[uuid]/associationsto</li>
     * </ul>
     *
     * @param uriInfo
     * @param request
     * @param projectId       The id of the project the requested instances
     *                        belongs to.
     * @param topicInstanceId The UUID of the requesting topic instance.
     * @param pojo            The TopicInstanceAssociationToPojo that represents
     *                        the new object.
     * @return                A Response with the UUID of the new created object
     *                        or the status code 204.
     */
    @POST
    @Path("{topicInstanceId}/associationsto")
    public Response create(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("topicInstanceId") UUID topicInstanceId,
            TopicInstanceAssociationToPojo pojo) {
        return OpenInfraResponseBuilder.postResponse(
                new TopicInstanceAssociationToRbac(
                        projectId,
                        OpenInfraSchemas.PROJECTS).createOrUpdate(
                                OpenInfraHttpMethod.valueOf(
                                        request.getMethod()),
                                uriInfo,
                                pojo,
                                topicInstanceId,
                                pojo.getAssociationInstanceId(),
                                null,
                                null));
    }

	/**
	 * This resource updates the Topic Instance association object the topic
	 * instance is related to and that points at the second topic instance. The
     * specified TopicInstanceAssociationToPojo contains the UUID of the topic
     * instance the relation assumes to, the associated TopicInstancePojo
     * and the relationship to the requesting TopicInstancePojo as
     * <ul>
     *   <li>rest/v1/projects/[uuid]/topicinstances/[uuid]/associationsto/[uuid]?language=de-DE</li>
     * </ul>
     * <b>The object id in the TopicInstanceAssociationToPojo and in the URI
     * that identifies the topic instance association must concur.</b>
     *
	 * @param uriInfo
	 * @param request
	 * @param projectId       The id of the project the requested instances
     *                        belongs to.
	 * @param topicInstanceId The UUID of the requesting topic instance.
	 * @param associatedTopicInstanceId The UUID of the second requesting topic
     *                                  instance.
	 * @param pojo            The TopicInstanceAssociationToPojo that represents
     *                        the new object.
	 * @return                A Response with the status code 200 for a
	 *                        successful update or 204 if the object could not
	 *                        be updated.
	 */
	@PUT
    @Path("{topicInstanceId}/associationsto/{associatedTopicInstanceId}")
	public Response update(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("topicInstanceId") UUID topicInstanceId,
            @PathParam("associatedTopicInstanceId")
                UUID associatedTopicInstanceId,
            TopicInstanceAssociationToPojo pojo) {
	    return OpenInfraResponseBuilder.putResponse(
                new TopicInstanceAssociationToRbac(
                        projectId,
                        OpenInfraSchemas.PROJECTS).createOrUpdate(
                                OpenInfraHttpMethod.valueOf(
                                        request.getMethod()),
                                uriInfo,
                                pojo,
                                topicInstanceId,
                                pojo.getAssociationInstanceId(),
                                associatedTopicInstanceId,
                                pojo.getAssociatedInstance().getUuid()));
    }

	/**
	 * This resource deletes the Topic Instance association object the topic
     * instance is related to and that points at the second topic instance.
     * <ul>
     *   <li>rest/v1/projects/[uuid]/topicinstances/[uuid]/associationsto/[uuid]</li>
     * </ul>
     *
	 * @param uriInfo
	 * @param request
	 * @param projectId       The id of the project the instance that should be
     *                        deleted belongs to.
	 * @param topicInstanceId The UUID of the TopicInstancePojo that should be
     *                        updated.
	 * @param associatedTopicInstanceId The UUID of the second requesting topic
     *                                  instance.
	 * @return                A Response with the status code 200 for a
     *                        successful deletion or 404 if the object was not
     *                        found.
	 */
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
                new TopicInstanceAssociationToRbac(
                        projectId,
                        OpenInfraSchemas.PROJECTS).delete(
                                OpenInfraHttpMethod.valueOf(
                                      request.getMethod()),
                                uriInfo,
                                topicInstanceId,
                                associatedTopicInstanceId));
	}

	/**
	 * This resource creates a AttributeValuePojo shell that contains some
     * informations about the topicInstance, the attributeType and the locale.
     * In comparison to the primer object, this resource provides more
     * specialized informations depending on the topic instance and attribute
     * type.
     * <ul>
     *   <li>rest/v1/projects/[uuid]/topicinstances/[uuid]/attributetypes/[uuid]/attributevalues/new?language=de-DE</li>
     * </ul>
     *
	 * @param uriInfo
	 * @param request
	 * @param language        The language of the localized objects.
	 * @param projectId       The project id the value belongs to.
	 * @param topicInstanceId The id of the topic instance the value belongs to.
	 * @param attributeTypeId The id of the attribute type the value belongs to.
	 * @return                A partly filled AttributeValuePojo.
	 */
	@GET
    @Path("{topicInstanceId}/attributetypes/{attributeTypeId}/attributevalues"
            + "/new")
    public AttributeValuePojo newAttributeValue(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
            @PathParam("topicInstanceId") UUID topicInstanceId,
            @PathParam("attributeTypeId") UUID attributeTypeId) {
        return new AttributeValueRbac(
                projectId,
                OpenInfraSchemas.PROJECTS).newAttributeValue(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
                        topicInstanceId,
                        attributeTypeId,
                        PtLocaleDao.forLanguageTag(language));
    }

	/**
	 * This resource provides a list of AttributeValuePojo's that belongs to the
	 * specified topic instance and the specified attribute value. This resource
	 * supports pagination of the list.
	 * <ul>
     *   <li>rest/v1/projects/[uuid]/topicinstances/[uuid]/attributetypes/[uuid]/attributevalues?language=de-DE&offset=0&size=10</li>
     * </ul>
	 *
	 * @param uriInfo
	 * @param request
	 * @param language        The language of the localized objects.
	 * @param projectId       The project id the values belongs to.
	 * @param topicInstanceId The id of the topic instance the value belongs to.
     * @param attributeTypeId The id of the attribute type the value belongs to.
	 * @param offset          The offset parameter for the elements of the list.
     * @param size            The count of elements the list should contain.
	 * @return
	 */
	@GET
    @Path("{topicInstanceId}/attributetypes/{attributeTypeId}/attributevalues")
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

	/**
	 * This resource provides a TopicPojo. A TopicPojo is a business object that
	 * does not directly map to the database. It consists of different objects
	 * from the database.
	 * <ul>
     *   <li>rest/v1/projects/[uuid]/topicinstances/[uuid]/topic?language=de-DE&geomType=TEXT</li>
     * </ul>
	 *
	 * @param uriInfo
	 * @param request
	 * @param language        The language of the localized objects.
     * @param projectId       The project id the topic belongs to.
	 * @param topicInstanceId The topic instance the topic belongs to.
	 * @param geomType        Defines the representation of geometry data.
	 * @return
	 */
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
