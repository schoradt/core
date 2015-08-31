package de.btu.openinfra.backend.rest.rbac;

import java.util.List;
import java.util.UUID;

import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.daos.rbac.PasswordBlacklistDao;
import de.btu.openinfra.backend.db.pojos.rbac.PasswordBlacklistPojo;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path(OpenInfraResponseBuilder.REST_URI_RBAC + "/passwordblacklist")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
	+ OpenInfraResponseBuilder.UTF8_CHARSET})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class PasswordBlacklistResource {

	@GET
	public List<PasswordBlacklistPojo> get(
			@QueryParam("language") String language,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new PasswordBlacklistDao().read(
						PtLocaleDao.forLanguageTag(language),
						offset,
						size);
	}
	
	@GET
	@Path("{id}")
	public PasswordBlacklistPojo get(
			@QueryParam("language") String language,
			@PathParam("id") UUID uuid) {
		return new PasswordBlacklistDao().read(
				PtLocaleDao.forLanguageTag(language),
				uuid);
	}

}