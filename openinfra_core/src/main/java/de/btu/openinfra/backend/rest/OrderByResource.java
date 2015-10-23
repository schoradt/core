package de.btu.openinfra.backend.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.btu.openinfra.backend.db.daos.OrderByDao;
import de.btu.openinfra.backend.db.pojos.OrderByNamesPojo;
import de.btu.openinfra.backend.db.pojos.OrderByPojo;

/**
 * This class represents and implements the OrderBy resource.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@Path(OpenInfraResponseBuilder.REST_URI_ORDERBY)
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
	+ OpenInfraResponseBuilder.UTF8_CHARSET})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class OrderByResource {

	@GET
	public OrderByPojo get(
            @QueryParam("class") String classObject) {
        return OrderByDao.read(classObject);
    }

	@GET
    @Path("/names")
    public OrderByNamesPojo getNames() {
        return OrderByDao.getNames();
    }

}
