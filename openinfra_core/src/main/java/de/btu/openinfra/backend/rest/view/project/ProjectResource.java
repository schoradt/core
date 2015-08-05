package de.btu.openinfra.backend.rest.view.project;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.glassfish.jersey.server.mvc.Template;

import de.btu.openinfra.backend.db.daos.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.ProjectDao;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.pojos.ProjectPojo;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

/**
 * This class represents the project resource of the REST API. You can access
 * an overview of all available HTTP methods by calling the following link:
 * <a href="http://localhost:8080/openinfra_backend/rest/application.wadl">
 * http://localhost:8080/openinfra_backend/rest/application.wadl</a>
 *
 * The project resource is available by calling the following link:
 * <a href="http://localhost:8080/openinfra_backend/rest/projects">
 * http://localhost:8080/openinfra_backend/rest/projects</a>
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@Path("/projects")
@Produces(MediaType.TEXT_HTML +
		OpenInfraResponseBuilder.UTF8_CHARSET +
		OpenInfraResponseBuilder.HTML_PRIORITY)
public class ProjectResource {

	@GET
	@Template(name="/views/list/Projects.jsp")
	public Response get(
			@QueryParam("language") String language,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		Subject user = SecurityUtils.getSubject();
		if(user.isPermitted("/projects:get")) {
			List<ProjectPojo> list = new ProjectDao(
					null,
					OpenInfraSchemas.META_DATA).getMainProjects(
							PtLocaleDao.forLanguageTag(language));
			
			Iterator<ProjectPojo> it = list.iterator();
			while(it.hasNext()) {
				if(!user.isPermitted("/projects/{id}:get:" + it.next().getUuid())) {
					it.remove();
				}
			}
			return Response.ok(list).build();			
		} else {
			return Response.status(403).build();
		}
	}

	@GET
	@Path("{projectId}")
	@Template(name="/views/detail/Projects.jsp")
	public Response getView(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId) {
		if(SecurityUtils.getSubject().isPermitted("/projects/{id}:get:" + projectId)) {
			return OpenInfraResponseBuilder.getResponse(
					new ProjectDao(
							projectId,
							OpenInfraSchemas.PROJECTS).read(
									PtLocaleDao.forLanguageTag(language),
									projectId));			
		} else {
			return Response.status(403).build();
		}
	}

	@GET
	@Path("{projectId}/subprojects")
	@Template(name="/views/list/Projects.jsp")
	public Response getSubProjectsView(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return OpenInfraResponseBuilder.getResponse(
					new ProjectDao(
							projectId,
							OpenInfraSchemas.PROJECTS).readSubProjects(
									PtLocaleDao.forLanguageTag(language),
									offset,
									size));
	}

	@GET
	@Path("/maps")
	@Template(name="/views/Map.jsp")
	public Response getMaps(
			@QueryParam("language") String language,
			@PathParam("tc") UUID projectId) {
		return Response.ok().entity("maps").build();
	}

	@GET
    @Path("/3d")
	@Template(name="/views/3dWebGIS.jsp")
    public Response index() {
        return Response.ok().entity("3dWebgis").build();
    }
}
