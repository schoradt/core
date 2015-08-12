package de.btu.openinfra.backend.rest;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.btu.openinfra.backend.db.OpenInfraOrderByEnum;
import de.btu.openinfra.backend.db.OpenInfraSortOrder;
import de.btu.openinfra.backend.db.daos.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.pojos.PtLocalePojo;

/**
 * This class represents the PtLocal resource of the REST API.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@Path(OpenInfraResponseBuilder.REST_URI + "/ptlocales")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
	+ OpenInfraResponseBuilder.UTF8_CHARSET})
public class PtLocaleResource {

	@GET
	public List<PtLocalePojo> get(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@QueryParam("sortOrder") OpenInfraSortOrder sortOrder,
			@QueryParam("orderBy") OpenInfraOrderByEnum orderBy,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new PtLocaleDao(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						PtLocaleDao.forLanguageTag(language),
						sortOrder,
						orderBy,
						offset,
						size);
	}

	@GET
	@Path("count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getCount(
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema) {
		return new PtLocaleDao(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).getCount();
	}

	@GET
	@Path("{ptLocaleId}")
	public PtLocalePojo get(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("ptLocaleId") UUID ptLocaleId) {
		return new PtLocaleDao(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						PtLocaleDao.forLanguageTag(language),
						ptLocaleId);
	}

}
