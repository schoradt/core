package de.btu.openinfra.backend.rest.rbac;

import java.util.LinkedList;
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

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.daos.rbac.SubjectDao;
import de.btu.openinfra.backend.db.pojos.rbac.SubjectPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;
import de.btu.openinfra.backend.db.rbac.OpenInfraRealmNames;
import de.btu.openinfra.backend.db.rbac.rbac.SubjectRbac;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;


@Path(OpenInfraResponseBuilder.REST_URI_RBAC + "/subjects")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
	+ OpenInfraResponseBuilder.UTF8_CHARSET})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class SubjectResource {

	@GET
	public List<SubjectPojo> get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new SubjectRbac().read(
				OpenInfraHttpMethod.valueOf(request.getMethod()), 
				uriInfo,
				PtLocaleDao.forLanguageTag(language),
				offset,
				size);
	}
	
	@GET
    @Path("count")
	@Produces({MediaType.TEXT_PLAIN})
    public long getSubjectsCount(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request) {
		return new SubjectRbac().getCount(
				OpenInfraHttpMethod.valueOf(request.getMethod()), uriInfo);
	}
    		
	
	@POST
	public Response create(
			@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
    		SubjectPojo pojo) {		
		return OpenInfraResponseBuilder.postResponse(	
				new SubjectRbac().createOrUpdate(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						null,
						pojo));		
	}
	
	@GET
	@Path("{id}")
	public SubjectPojo get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("id") UUID uuid) {
		return new SubjectRbac().read(
				OpenInfraHttpMethod.valueOf(request.getMethod()), 
				uriInfo,
				PtLocaleDao.forLanguageTag(language),
				uuid);
	}
	
	@PUT
	@Path("{id}")
	public Response put(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("id") UUID uuid,
			SubjectPojo pojo) {
		return OpenInfraResponseBuilder.putResponse(
				new SubjectRbac().createOrUpdate(
						OpenInfraHttpMethod.valueOf(request.getMethod()), 
						uriInfo,
						uuid,
						pojo));		
	}
	
	@DELETE
	@Path("{id}")
	public Response delete(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("id") UUID uuid) {
		return OpenInfraResponseBuilder.deleteResponse(
				new SubjectRbac().delete(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo, uuid), uuid);
	}
 
	
	@GET
	@Path("bylogin")
	public SubjectPojo getSubjectByLogin(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("login") String login) {		
		return new SubjectRbac().read(
				OpenInfraHttpMethod.valueOf(request.getMethod()), 
				uriInfo,
				login);
	}

	/**
	 * This method is not secured by the RBAC system since it retrieves the
	 * information of the current subject (user). Each subject should be able
	 * to view its own informations.
	 * 
	 * @return the current subject as pojo
	 */
	@GET
	@Path("self")
	public SubjectPojo self() {
		// Retrieve the login from principals (there might be multiple)
		Subject s = SecurityUtils.getSubject();	
		List<String> login = new LinkedList<String>();
		for(Object o : s.getPrincipals().fromRealm(
				OpenInfraRealmNames.LOGIN.name())) {
			login.add(o.toString());
		}
		return new SubjectDao().read(login.get(0));
	}
	
	

}