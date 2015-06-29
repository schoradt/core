package de.btu.openinfra.backend.rest;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.btu.openinfra.backend.db.daos.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.daos.ValueListDao;
import de.btu.openinfra.backend.db.daos.ValueListValueDao;
import de.btu.openinfra.backend.db.pojos.ValueListPojo;
import de.btu.openinfra.backend.db.pojos.ValueListValuePojo;

@Path(OpenInfraResponseBuilder.REST_URI + "/valuelists")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY,
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY})
public class ValueListResource {

	@GET
	@Path("count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getValueListsCount(
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema) {
		return new ValueListDao(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).getCount();
	}

	@GET
	public List<ValueListPojo> get(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new ValueListDao(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						PtLocaleDao.forLanguageTag(language),
						offset,
						size);
	}

	@POST
	public Response create(
	        @PathParam("projectId") UUID projectId,
	        @PathParam("schema") String schema,
	        ValueListPojo pojo) {
	    // call the create or update method for the DAO and return the uuid
	    return OpenInfraResponseBuilder.postResponse(
	                new ValueListDao(
	                        projectId,
	                        OpenInfraSchemas.valueOf(schema.toUpperCase()))
	                        .createOrUpdate(pojo));
	}

	@GET
	@Path("{valueListId}")
	public ValueListPojo get(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("valueListId") UUID valueListId) {
		return new ValueListDao(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						PtLocaleDao.forLanguageTag(language),
						valueListId);
	}

	@DELETE
	@Path("{valueListId}")
	public Response delete(
	        @PathParam("projectId") UUID projectId,
	        @PathParam("valueListId") UUID valueListId,
            @PathParam("schema") String schema) {
	    return OpenInfraResponseBuilder.deleteResponse(
	            new ValueListDao(
	                    projectId,
	                    OpenInfraSchemas.valueOf(schema.toUpperCase()))
	                .delete(valueListId),
	                valueListId);
	}

	@GET
    @Path("/hull")
    public ValueListPojo getEmptyShell(
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId) {
        return new ValueListDao(
                projectId,
                OpenInfraSchemas.PROJECTS).createEmptyShell(
                        PtLocaleDao.forLanguageTag(language));
    }

	@PUT
    @Path("{valueListId}")
    public Response update(
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("valueListId") UUID valueListId,
            ValueListPojo valueList) {
	    UUID uuid = new ValueListDao(
                projectId,
                OpenInfraSchemas.valueOf(schema.toUpperCase())).createOrUpdate(
                        valueList);
        return OpenInfraResponseBuilder.postResponse(uuid);
    }

	@GET
	@Path("{valueListId}/valuelistvalues")
	public List<ValueListValuePojo> getValueListValues(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("valueListId") UUID valueListId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new ValueListValueDao(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						PtLocaleDao.forLanguageTag(language),
						valueListId,
						offset,
						size);
	}

	@GET
	@Path("{valueListId}/valuelistvalues/count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getValueListValuesCount(
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("valueListId") UUID valueListId) {
		return new ValueListValueDao(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).getCount(
						valueListId);
	}

}
