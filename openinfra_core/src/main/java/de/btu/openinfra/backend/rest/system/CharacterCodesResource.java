package de.btu.openinfra.backend.rest.system;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.btu.openinfra.backend.db.daos.CharacterCodeDao;
import de.btu.openinfra.backend.db.daos.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.pojos.CharacterCodePojo;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

/**
 * This class represents the PtLocal resource of the REST API. 
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@Path("/system/charactercodes")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY, 
    MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY})
public class CharacterCodesResource {
	
	@GET
	public List<CharacterCodePojo> get(
			@QueryParam("language") String language,
			@QueryParam("offset") int offset, 
			@QueryParam("size") int size) {
		return new CharacterCodeDao(
				null, 
				OpenInfraSchemas.SYSTEM).read(
						PtLocaleDao.forLanguageTag(language),
						offset, 
						size);
	}
	
	@GET
	@Path("{characterCodeId}")	
	public CharacterCodePojo get(
			@QueryParam("language") String language,
			@PathParam("characterCodeId") UUID characterCodeId) {
		return new CharacterCodeDao(
				null, 
				OpenInfraSchemas.SYSTEM).read(
						PtLocaleDao.forLanguageTag(language),
						characterCodeId);
	}

	
}
