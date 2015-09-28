package de.btu.openinfra.backend.rest.system;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import de.btu.openinfra.backend.db.OpenInfraOrderByEnum;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.OpenInfraSortOrder;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.pojos.LanguageCodePojo;
import de.btu.openinfra.backend.db.rbac.LanguageCodeRbac;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

/**
 * This class represents the LanguageCodes resource of the REST API.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@Path(OpenInfraResponseBuilder.REST_URI_SYSTEM + "/languagecodes")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
    MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET})
public class LanguageCodesResource {

	@GET
	public List<LanguageCodePojo> get(
			@Context UriInfo uriInfo,
			@QueryParam("language") String language,
			@QueryParam("sortOrder") OpenInfraSortOrder sortOrder,
			@QueryParam("orderBy") OpenInfraOrderByEnum orderBy,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new LanguageCodeRbac(
				null,
				OpenInfraSchemas.SYSTEM ).read(
						OpenInfraHttpMethod.GET, 
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						sortOrder,
						orderBy,
						offset,
						size);
	}

	@GET
	@Path("{languageCodeId}")
	public LanguageCodePojo get(
			@Context UriInfo uriInfo,
			@QueryParam("language") String language,
			@PathParam("languageCodeId") UUID languageCodeId) {
		return new LanguageCodeRbac(
				null,
				OpenInfraSchemas.SYSTEM ).read(
						OpenInfraHttpMethod.GET, 
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						languageCodeId);
	}

	@GET
    @Path("count")
    @Produces({MediaType.TEXT_PLAIN})
    public long getLanguageCodeCount(@Context UriInfo uriInfo) {
        return new LanguageCodeRbac(
                null,
                OpenInfraSchemas.SYSTEM).getCount(
                		OpenInfraHttpMethod.GET, 
						uriInfo);
    }

}
