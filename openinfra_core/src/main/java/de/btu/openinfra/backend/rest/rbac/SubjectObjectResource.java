package de.btu.openinfra.backend.rest.rbac;

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

import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.pojos.rbac.SubjectObjectPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;
import de.btu.openinfra.backend.db.rbac.rbac.SubjectObjectRbac;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path(OpenInfraResponseBuilder.REST_URI_RBAC + "/subjectobjects")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
	+ OpenInfraResponseBuilder.UTF8_CHARSET})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class SubjectObjectResource {
	
	@GET
	public List<SubjectObjectPojo> get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new SubjectObjectRbac().read(
				OpenInfraHttpMethod.valueOf(request.getMethod()), 
				uriInfo,
				PtLocaleDao.forLanguageTag(language),  
				offset, 
				size);
	}
	
	@POST
	public Response create(
			@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
    		SubjectObjectPojo pojo) {	
		return OpenInfraResponseBuilder.postResponse(
				new SubjectObjectRbac().createOrUpdate(
						OpenInfraHttpMethod.valueOf(request.getMethod()), 
						uriInfo, null, pojo));		
	}
	
	@GET
	@Path("{id}")
	public SubjectObjectPojo get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("id") UUID uuid) {
		return new SubjectObjectRbac().read(
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
			SubjectObjectPojo pojo) {
		return OpenInfraResponseBuilder.putResponse(
				new SubjectObjectRbac().createOrUpdate(
						OpenInfraHttpMethod.valueOf(request.getMethod()), 
						uriInfo, uuid, pojo));		
	}
	
	@DELETE
	@Path("{id}")
	public Response delete(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("id") UUID uuid) {
		return OpenInfraResponseBuilder.deleteResponse(
				new SubjectObjectRbac().delete(
						OpenInfraHttpMethod.valueOf(request.getMethod()), 
						uriInfo, uuid), uuid);
	}

	@GET
	@Path("count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getCount(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request) {
		return new SubjectObjectRbac().getCount(
				OpenInfraHttpMethod.valueOf(request.getMethod()), uriInfo);
	}

}
