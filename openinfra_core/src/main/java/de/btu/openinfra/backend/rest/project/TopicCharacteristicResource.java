package de.btu.openinfra.backend.rest.project;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import de.btu.openinfra.backend.OpenInfraProperties;
import de.btu.openinfra.backend.db.OpenInfraOrderBy;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.OpenInfraSortOrder;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.daos.project.AttributeValueGeomType;
import de.btu.openinfra.backend.db.pojos.project.TopicGeomzPojo;
import de.btu.openinfra.backend.db.pojos.project.TopicInstancePojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;
import de.btu.openinfra.backend.db.rbac.TopicGeomzRbac;
import de.btu.openinfra.backend.db.rbac.TopicInstanceRbac;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path(OpenInfraResponseBuilder.REST_URI_PROJECTS + "/topiccharacteristics/"
		+ "{topicCharacteristicId}/topicinstances/")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
    MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET})
public class TopicCharacteristicResource {

	/**
	 * This method will read a list of topic instances for a specific topic
	 * characteristic id. Additional to the standard query parameter it will
	 * accept a filter parameter and orderBy parameter. It is possible to set
	 * both orderBy parameter simultaneously.
	 * <br/>
	 * In order to avoid heavy load, there exists default size and offset values
	 * when not specified.
	 * <ul>
     *   <li>rest/v1/projects/[uuid]/topiccharacteristics/[uuid]/topicinstances?language=de-DE&orderBy=UUID&sortOrder=ASC&offset=0&size=10</li>
     *   <li>rest/v1/projects/[uuid]/topiccharacteristics/[uuid]/topicinstances?language=de-DE&filter=abc</li>
     * </ul>
	 *
	 * @param language              The language of the requested object.
	 * @param projectId             The id of the project the requested topic
	 *                              instances belongs to.
	 * @param topicCharacteristicId The id of the topic characteristic the
	 *                              requested topic instances belongs to.
	 * @param filter                A string that filters on the attribute
	 *                              values that belong to the topic instances.
	 * @param sortOrder             The sort order for the list.
     * @param orderBy               The element the list should be ordered by.
	 * @param offset                The offset parameter for the elements of the
	 *                              list.
     * @param size                  The count of elements the list should
     *                              contain.
	 * @return                      A list of TopicInstancePojo's
	 *
	 * @response.representation.200.qname A list of TopicInstancePojo's.
     * @response.representation.200.doc   This is the representation returned by
     *                                    default.
     *
     * @response.representation.403.qname WebApplicationException
     * @response.representation.403.doc   This error occurs if you do not have
     *                                    the permission to access this
     *                                    resource.
     *
     * @response.representation.409.qname OpenInfraEntityException
     * @response.representation.409.doc   This error occurs if the parameters
     *                                    are not configured as expected.
     *
     * @response.representation.500.qname OpenInfraWebException
     * @response.representation.500.doc   An internal error occurs if the
     *                                    backend runs into an unexpected
     *                                    exception.
	 */
	@GET
	public List<TopicInstancePojo> getTopicInstances(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("topicCharacteristicId") UUID topicCharacteristicId,
			@QueryParam("filter") String filter,
			@QueryParam("sortOrder") OpenInfraSortOrder sortOrder,
            @QueryParam("orderBy") OpenInfraOrderBy orderBy,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		// Define the specific parameters when not specified correctly
		if(size == 0) {
			offset = OpenInfraProperties.DEFAULT_OFFSET;
			size = OpenInfraProperties.DEFAULT_SIZE;
		} // end if

		if(filter != null && filter.length() > 0) {
			return new TopicInstanceRbac(
					projectId,
					OpenInfraSchemas.PROJECTS).read(
							OpenInfraHttpMethod.valueOf(request.getMethod()),
							uriInfo,
							PtLocaleDao.forLanguageTag(language),
							topicCharacteristicId,
							filter,
							offset,
							size);
		} else {
			return new TopicInstanceRbac(
					projectId,
					OpenInfraSchemas.PROJECTS).read(
							OpenInfraHttpMethod.valueOf(request.getMethod()),
							uriInfo,
					PtLocaleDao.forLanguageTag(language),
					topicCharacteristicId,
					sortOrder,
					orderBy,
					offset,
					size);
		} // end if else
	}

	/**
     * This resource provides the count of TopicInstancePojo's for a specific
     * topic characteristic in a specified project.
     * <ul>
     *   <li>rest/v1/projects/[uuid]/topiccharacteristics/[topicCharacteristicId]/topicinstances/count</li>
     * </ul>
     *
     * @param uriInfo
     * @param request
     * @param projectId             The id of the project where the topic
     *                              instances are part of.
     * @param topicCharacteristicId The id of the topic characteristic the
     *                              requested topic instances count belongs to.
     * @return                      The count of TopicInstancePojo's.
     *
     * @response.representation.200.qname The count of TopicInstancePojo's as
     *                                    long.
     * @response.representation.200.doc   This is the representation returned by
     *                                    default.
     *
     * @response.representation.403.qname WebApplicationException
     * @response.representation.403.doc   This error occurs if you do not have
     *                                    the permission to access this
     *                                    resource.
     *
     * @response.representation.500.qname OpenInfraWebException
     * @response.representation.500.doc   An internal error occurs if the
     *                                    backend runs into an unexpected
     *                                    exception.
     */
	@GET
	@Path("count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getTopicInstancesCount(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("projectId") UUID projectId,
			@PathParam("topicCharacteristicId") UUID topicCharacteristicId) {
		return new TopicInstanceRbac(
				projectId,
				OpenInfraSchemas.PROJECTS).getCount(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						topicCharacteristicId);
	}

	/**
	 * This is a special representation of the topic object. It delivers always
	 * a list of topic instances with corresponding 3D attribute values as X3D.
	 * This resource can cause heavy load on the server. For this reason the
     * maximum number of objects that will be returned is forced by a
     * configuration variable. If the window size is not determined, the max
     * number will be used. If the defined window size is greater than the
     * configuration the configuration value will be used. Further it will only
     * accept the geometry types TEXT or X3D. All other types are currently
     * discard. If the type differ from the mentioned types, TEXT will be used.
     * <ul>
     *   <li>rest/v1/projects/[uuid]/topiccharacteristics/[topicCharacteristicId]/topicinstances/geomz?language=de-DE&geomType=X3D&offset=0&size=10</li>
     * </ul>
	 *
	 * @param language              The language of the requested object.
	 * @param projectId             The id of the project the requested topics
	 *                              belongs to.
	 * @param topicCharacteristicId The id of the topic characteristic the
     *                              requested topics belongs to.
	 * @param geomType              The geometry type that is requested (must be
     *                              TEXT or X3D)
	 * @param offset                The offset parameter for the elements of the
     *                              list.
     * @param size                  The count of elements the list should
     *                              contain.
	 * @return                      A list of TopicGeomzPojo's.
	 *
	 * @response.representation.200.qname A list of TopicGeomzPojo's.
     * @response.representation.200.doc   This is the representation returned by
     *                                    default.
     *
     * @response.representation.403.qname WebApplicationException
     * @response.representation.403.doc   This error occurs if you do not have
     *                                    the permission to access this
     *                                    resource.
     *
     * @response.representation.409.qname OpenInfraEntityException
     * @response.representation.409.doc   This error occurs if the parameters
     *                                    are not configured as expected.
     *
     * @response.representation.500.qname OpenInfraWebException
     * @response.representation.500.doc   An internal error occurs if the
     *                                    backend runs into an unexpected
     *                                    exception.
     */
	@GET
    @Path("geomz")
    public List<TopicGeomzPojo> getTopicInstancesGeomz(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
            @PathParam("topicCharacteristicId") UUID topicCharacteristicId,
            @QueryParam("geomType") AttributeValueGeomType geomType,
            @QueryParam("offset") int offset,
            @QueryParam("size") int size) {
	    return new TopicGeomzRbac(
	            projectId,
	            OpenInfraSchemas.PROJECTS,
	            geomType).read(
	            		OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
	                    PtLocaleDao.forLanguageTag(language),
	                    topicCharacteristicId,
	                    offset,
	                    size);
    }

	/**
     * This is a special representation of the topic object. It delivers always
     * a list of topic instances with corresponding 3D attribute values as X3D.
     * <ul>
     *   <li>rest/v1/projects/[uuid]/topiccharacteristics/[topicCharacteristicId]/topicinstances/geomz/count</li>
     * </ul>
     *
     * @param uriInfo
     * @param request
     * @param projectId             The id of the project where the geom topic
     *                              instances are part of.
     * @param topicCharacteristicId The id of the topic characteristic the
     *                              requested geom topic instances count belongs
     *                              to.
     * @return                      The count of TopicGeomzPojo's.
     *
     * @response.representation.200.qname The count of TopicGeomzPojo's as long.
     * @response.representation.200.doc   This is the representation returned by
     *                                    default.
     *
     * @response.representation.403.qname WebApplicationException
     * @response.representation.403.doc   This error occurs if you do not have
     *                                    the permission to access this
     *                                    resource.
     *
     * @response.representation.500.qname OpenInfraWebException
     * @response.representation.500.doc   An internal error occurs if the
     *                                    backend runs into an unexpected
     *                                    exception.
     */
    @GET
    @Path("geomz/count")
    public long getTopicInstancesGeomzCount(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("topicCharacteristicId") UUID topicCharacteristicId) {
        return new TopicGeomzRbac(
                projectId,
                OpenInfraSchemas.PROJECTS,
                null).getCount(
                		OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						topicCharacteristicId);
    }
}
