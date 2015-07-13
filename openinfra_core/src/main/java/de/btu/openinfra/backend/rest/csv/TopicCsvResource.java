package de.btu.openinfra.backend.rest.csv;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import de.btu.openinfra.backend.db.daos.AttributeValueGeomType;
import de.btu.openinfra.backend.rest.pdf.TopicPdfResource;

@Path("/projects/{projectId}/topicinstances")
public class TopicCsvResource {
	
	/**
	 * This variable defines the path to the XSL file.
	 */
	private String XSL_FILE = 
			"de/btu/openinfra/backend/xsl/TopicCsv.xsl"; 
	
	@GET
	@Produces({"text/csv","application/vnd.oasis.opendocument.spreadsheet"})
	@Path("{topicInstanceId}/topic.csv")
	public Response getTopicAsCsv(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("topicInstanceId") UUID topicInstanceId,
			@QueryParam("geomType") AttributeValueGeomType geomType,
			@Context UriInfo uriInfo,
			@Context HttpServletResponse servletResponse) {
		
		// Remove the CSV extension of the URL and add query parameters
		String path = uriInfo.getAbsolutePath().toString();
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(
				path.substring(0, path.lastIndexOf(".csv")) + 
				"?language=" + language);
		String xml = target.request(
				MediaType.APPLICATION_XML).get(String.class);
		
		Transformer xslTransformer;
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		StreamResult result = new StreamResult(stream);
		
		try {
			TransformerFactory transfact = TransformerFactory.newInstance();
			xslTransformer = 
					transfact.newTransformer(
							new StreamSource(
									new TopicPdfResource().getClass()
									.getClassLoader().getResourceAsStream(
											XSL_FILE)));
			xslTransformer.transform(
					new StreamSource(new StringReader(xml)), result);
		} catch(Exception ex) {
			ex.printStackTrace();
		} // end try catch
		
		return Response.ok().entity(stream.toByteArray()).build();
		
	}

}
