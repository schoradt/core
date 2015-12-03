package de.btu.openinfra.backend.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OrderByDao;
import de.btu.openinfra.backend.db.pojos.OrderByNamesPojo;
import de.btu.openinfra.backend.db.pojos.OrderByPojo;

/**
 * This class represents and implements the OrderBy resource.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@Path("v1")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
	+ OpenInfraResponseBuilder.UTF8_CHARSET})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class OrderByResource {

	@GET
    @Path("/system/orderby")
	public OrderByPojo getS(
	        @PathParam("schema") OpenInfraSchemas schema,
            @QueryParam("class") String classObject) {
        return OrderByDao.read(OpenInfraSchemas.SYSTEM, classObject);
    }

	@GET
    @Path("/system/orderby/names")
    public OrderByNamesPojo getNamesS(
            @PathParam("schema") OpenInfraSchemas schema) {
        return OrderByDao.getNames(OpenInfraSchemas.SYSTEM);
    }

	@GET
    @Path("/metadata/orderby")
    public OrderByPojo getM(
            @PathParam("schema") OpenInfraSchemas schema,
            @QueryParam("class") String classObject) {
        return OrderByDao.read(OpenInfraSchemas.META_DATA, classObject);
    }

    @GET
    @Path("/metadata/orderby/names")
    public OrderByNamesPojo getNamesM(
            @PathParam("schema") OpenInfraSchemas schema) {
        return OrderByDao.getNames(OpenInfraSchemas.META_DATA);
    }

    @GET
    @Path("rbac/orderby")
    public OrderByPojo getR(
            @PathParam("schema") OpenInfraSchemas schema,
            @QueryParam("class") String classObject) {
        return OrderByDao.read(OpenInfraSchemas.RBAC, classObject);
    }

    @GET
    @Path("rbac/orderby/names")
    public OrderByNamesPojo getNamesR(
            @PathParam("schema") OpenInfraSchemas schema) {
        return OrderByDao.getNames(OpenInfraSchemas.RBAC);
    }
}
