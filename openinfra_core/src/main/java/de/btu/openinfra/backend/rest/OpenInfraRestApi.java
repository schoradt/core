package de.btu.openinfra.backend.rest;

import java.io.ByteArrayOutputStream;
import java.net.URL;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

@Path("/application.html")
@Singleton
@Produces({MediaType.TEXT_HTML})
public class OpenInfraRestApi {
	
	/**
	 * This variable defines the path to the XSL file path.
	 */
	private static final String XSL_FILE = 
			"de/btu/openinfra/backend/xsl/wadl.xsl";
	
	@GET
	public Response getRestApi(@Context UriInfo uriInfo) {
		
		String wadl = uriInfo.getBaseUri() + "application.wadl";
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		TransformerFactory tf = TransformerFactory.newInstance();
		StreamSource xslt = new StreamSource(
				new OpenInfraRestApi().getClass().getClassLoader()
					.getResourceAsStream(XSL_FILE));
		StreamResult result = new StreamResult(stream);
		Transformer tr;
		
		try {
			tr = tf.newTransformer(xslt);
			tr.transform(new StreamSource(new URL(wadl).openStream()), result);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return Response.ok().entity(stream.toByteArray()).build();
		
	}

}
