package de.btu.openinfra.backend.rest.project;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
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
import de.btu.openinfra.backend.db.daos.OrderByDao;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.pojos.OrderByNamesPojo;
import de.btu.openinfra.backend.db.pojos.OrderByPojo;
import de.btu.openinfra.backend.db.pojos.file.FilePojo;
import de.btu.openinfra.backend.db.pojos.project.ProjectPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;
import de.btu.openinfra.backend.db.rbac.ProjectRbac;
import de.btu.openinfra.backend.db.rbac.file.FileRbac;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

/**
 * This class represents and implements the resource for projects.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@Path("/v1/projects")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
	+ OpenInfraResponseBuilder.UTF8_CHARSET})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ProjectResource {

	/**
	 * This method provides a list of all projects. The provided parameters are
	 * used for paging.
	 *
	 * @param uriInfo
     * @param request
     * @param language The language of the requested object.
	 * @param offset   the number where to start
	 * @param size     the size of items to provide
	 * @return         a list of projects &lt;= size (window within offset
	 *                 and size)
	 */
	@GET
	public List<ProjectPojo> get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new ProjectRbac(
				null,
				OpenInfraSchemas.META_DATA).readMainProjects(
						PtLocaleDao.forLanguageTag(language));
	}

	/**
	 * This resource provides the count of main projects.
     * <ul>
     *   <li>rest/v1/projects/[uuid]/count</li>
     * </ul>
	 *
	 * @param uriInfo
	 * @param request
	 * @return        The count of main projects.
	 */
	@GET
	@Path("count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getMainProjectsCount(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request) {
		return new ProjectRbac(
				null,
				OpenInfraSchemas.META_DATA).getMainProjectsCount();
	}

	/**
	 * This method provides a ProjectPojo for a specific id.
	 * <ul>
     *   <li>rest/v1/projects/[uuid]</li>
     * </ul>
     *
     * @param uriInfo
     * @param request
     * @param language  The language of the requested object.
	 * @param projectId the id of the requested project
	 * @return          the requested project
	 */
	@GET
	@Path("{projectId}")
	public Response get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId) {
		return OpenInfraResponseBuilder.getResponse(
				new ProjectRbac(
						projectId,
						OpenInfraSchemas.PROJECTS).read(
								OpenInfraHttpMethod.valueOf(
										request.getMethod()),
								uriInfo,
								PtLocaleDao.forLanguageTag(language),
								projectId));
	}

	/**
	 * This method delivers a list of sub projects specified by the current
	 * project id in only one hierarchy level.
	 * <ul>
     *   <li>rest/v1/projects/[uuid]/subprojects</li>
     * </ul>
	 *
	 * @param uriInfo
     * @param request
     * @param language  The language of the requested object.
	 * @param projectId the id of the current project
	 * @param offset    the number where to start
	 * @param size      the size of items to provide
	 * @return          a list of sub projects
	 */
	@GET
	@Path("{projectId}/subprojects")
	public List<ProjectPojo> getSubProjects(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new ProjectRbac(
				projectId,
				OpenInfraSchemas.PROJECTS).readSubProjects(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						offset,
						size);
	}

	/**
	 * This resource provides the count of sub projects.
     * <ul>
     *   <li>rest/v1/projects/[uuid]/subprojects/count</li>
     * </ul>
	 * @param uriInfo
	 * @param request
	 * @param projectId The id of the project the sub projects belongs to.
	 * @return          The count of sub projects
	 */
	@GET
	@Path("{projectId}/subprojects/count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getSubProjectsCount(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("projectId") UUID projectId) {
		return new ProjectRbac(
				projectId,
				OpenInfraSchemas.PROJECTS).getSubProjectsCount(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo);
	}

	/**
	 * This method creates a new project. To determine if we want a new main
	 * project or only a subproject we will check the specified ProjectPojo. If
	 * the subprojectOf id is specified, the new project will be a sub project
	 * of the project with the specified id. Otherwise it will be a main project
	 * that consists of an own database schema. It is also possible to create a
	 * sub project for a sub project.
	 * <ul>
	 *   <li>rest/v1/projects</li>
     *   <li>rest/v1/projects?createEmpty=true&loadIntitialData=false</li>
     *   <li>rest/v1/projects?createEmpty=true&loadIntitialData=truee</li>
     * </ul>
	 *
	 * @param uriInfo
     * @param request
	 * @param createEmpty The application creates an empty new schema when true.
	 *                    This is necessary by the external XML interface
	 *                    (XML import/export interface by HTW Dresden)
	 * @param loadInitialData This parameter prerequisites 'createEmpty=true'.
	 *                        There is no initial data stored in the new data
	 *                        base when false. This is required by the
	 *                        XML import/export interface.
	 * @param project The ProjectPojo that represents the new object.
	 * @return        A Response with the UUID of the new created object or the
     *                status code 204.
	 */
	@POST
	public Response createProject(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("createEmpty") boolean createEmpty,
			@QueryParam("loadInitialData") boolean loadInitialData,
			ProjectPojo project) {
	    // create the project
		UUID id = new ProjectRbac(
		        null, OpenInfraSchemas.PROJECTS).createProject(
		                project,
		                createEmpty,
		                loadInitialData,
		                OpenInfraHttpMethod.valueOf(
		                        request.getMethod()), uriInfo);
		return OpenInfraResponseBuilder.postResponse(id);
	}

	/**
	 * Currently, this method should update a project. In contrast to the
	 * definition of the HTTP PUT method, this method updates an existing
	 * resource and doesn't replace  it. This should be discussed/fixed in
	 * the future.
	 * <ul>
     *   <li>rest/v1/projects/[uuid]</li>
     * </ul>
	 *
	 * @param uriInfo
     * @param request
	 * @param projectId the id of the project which should be updated
	 * @param project   The ProjectPojo that represents the updated object.
	 * @return          A Response with the status code 200 for a successful
	 *                  update or 204 if the object could not be updated.
	 */
    @PUT
    @Path("{projectId}")
    public Response updateProject(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
    		@PathParam("projectId") UUID projectId,
    		ProjectPojo project) {
    	UUID uuid = new ProjectRbac(
    			projectId,
    			OpenInfraSchemas.PROJECTS).createOrUpdate(
    					OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						projectId,
						project);
    	return OpenInfraResponseBuilder.putResponse(uuid);
    }

	/**
	 * This method deletes a project.
	 * <ul>
     *   <li>rest/v1/projects/[uuid]</li>
     * </ul>
	 *
	 * @param uriInfo
     * @param request
	 * @param projectId The UUID of the Project object that should be deleted.
     * @return          A Response with the status code 200 for a successful
     *                  deletion or 404 if the object was not found.
	 */
	@DELETE
	@Path("{projectId}")
	public Response deleteProject(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
	        @PathParam("projectId") UUID projectId) {
	    return OpenInfraResponseBuilder.deleteResponse(
	            new ProjectRbac(
    	            projectId,
    	            OpenInfraSchemas.PROJECTS).deleteProject(
    	                    OpenInfraHttpMethod.valueOf(request.getMethod()),
    	                    uriInfo),
                projectId);
	}

	/**
	 * This resource provides a list of all parent ProjectPojo's for the
     * specified project id. The ProjectPojo for the specified id will also be
     * part of the list. The list will be sorted bottom/top.
     * <ul>
     *   <li>rest/v1/projects/[uuid]/parents</li>
     * </ul>
     *
	 * @param uriInfo
	 * @param request
	 * @param language  The language of the requested object.
     * @param projectId The id of the current project we want to retrieve the
     *                  parents.
	 * @return          A list of ProjectPojo's that represent the parents of
     *                  the requested project.
	 */
	@GET
	@Path("{projectId}/parents")
	public List<ProjectPojo> readParents(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId) {
		return new ProjectRbac(
				projectId,
				OpenInfraSchemas.PROJECTS).readParents(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language));
	}

	/**
	 * This resource provides a list of FilePojo's that are assigned to the
     * specified project.
     * <ul>
     *   <li>rest/v1/projects/[uuid]/files</li>
     * </ul>
     *
	 * @param uriInfo
	 * @param request
	 * @param projectId The id of the project the requested files belongs to.
	 * @return          A list of FilePojo's.
	 */
    @GET
	@Path("{projectId}/files")
	public List<FilePojo> readFilesByProject(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("projectId") UUID projectId) {
		return new FileRbac().readByProject(
				OpenInfraHttpMethod.valueOf(request.getMethod()),
				uriInfo, projectId);
	}

    /**
     * This resource provides a list of sorting types for a specified class
     * name. The sorting types can be used for the orderby parameter that
     * different resources support.
     * <ul>
     *   <li>rest/v1/projects/orderby?class=AttributeType</li>
     * </ul>
     *
     * @param schema      The schema name the sorting types should belong to.
     * @param classObject The name of the object the sorting types should belong
     *                    to.
     * @return            A OrderByPojo for the specified object in
     *                    specified schema.
     */
	@GET
    @Path("/orderby")
    public OrderByPojo getP(
            @PathParam("schema") OpenInfraSchemas schema,
            @QueryParam("class") String classObject) {
        return OrderByDao.read(OpenInfraSchemas.PROJECTS, classObject);
    }

	/**
	 * This resource provides a OrderByNamesPojo for the project schema. This
	 * Pojo contains a list of object names that support sorting.
	 * <ul>
     *   <li>rest/v1/projects/orderby/names</li>
     * </ul>
	 *
	 * @param schema The schema name the sorting list should be retrieved for.
	 * @return       The OrderByNamesPojo for the specified schema.
	 */
    @GET
    @Path("/orderby/names")
    public OrderByNamesPojo getNamesP(
            @PathParam("schema") OpenInfraSchemas schema) {
        return OrderByDao.getNames(OpenInfraSchemas.PROJECTS);
    }
}
