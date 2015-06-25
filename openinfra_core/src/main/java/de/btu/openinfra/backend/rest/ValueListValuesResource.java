package de.btu.openinfra.backend.rest;

import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.btu.openinfra.backend.db.daos.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.daos.ValueListValueDao;
import de.btu.openinfra.backend.db.pojos.ValueListValuePojo;

@Path(OpenInfraResponseBuilder.REST_URI + "/valuelistvalues")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY, 
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY})
public class ValueListValuesResource {
	
	@GET
	@Path("{valueListValueId}")
	public ValueListValuePojo get(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("valueListValueId") UUID valueListValueId) {
		return new ValueListValueDao(
				projectId, 
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						PtLocaleDao.forLanguageTag(language),
						valueListValueId);
	}

}
