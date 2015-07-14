package de.btu.openinfra.backend.rest.system;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.btu.openinfra.backend.db.daos.CountryCodeDao;
import de.btu.openinfra.backend.db.daos.OpenInfraOrderByEnum;
import de.btu.openinfra.backend.db.daos.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraSortOrder;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.pojos.CountryCodePojo;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path("/system/countrycodes")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY, 
    MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY})
public class CountryCodesResource {
	
	@GET
	public List<CountryCodePojo> get(
			@QueryParam("language") String language,
			@QueryParam("sortOrder") OpenInfraSortOrder sortOrder,
			@QueryParam("orderBy") OpenInfraOrderByEnum orderBy,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new CountryCodeDao(
					null, 
					OpenInfraSchemas.SYSTEM).read(
							PtLocaleDao.forLanguageTag(language),
							sortOrder,
							orderBy,
							offset, 
							size);
	}
	
	@GET
	@Path("{countryCodeId}")	
	public CountryCodePojo get(
			@QueryParam("language") String language,
			@PathParam("countryCodeId") UUID countryCodeId) {
		return new CountryCodeDao(
				null, 
				OpenInfraSchemas.SYSTEM).read(
						PtLocaleDao.forLanguageTag(language),
						countryCodeId);
	}

}
