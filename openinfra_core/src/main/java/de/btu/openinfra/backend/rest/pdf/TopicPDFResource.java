package de.btu.openinfra.backend.rest.pdf;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.util.UUID;

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
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import de.btu.openinfra.backend.db.pojos.AttributeValueGeomType;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Produces({"application/pdf" + OpenInfraResponseBuilder.PDF_PRIORITY})
@Path("/projects/{projectId}/topicinstances")
public class TopicPDFResource {
	
	/**
	 * This variable defines the path to the XSL file.
	 */
	private static final String XSL_FILE = 
			"de/btu/openinfra/backend/xsl/fopStyle.xsl"; 
	
	/**
	 * http://www.e-zest.net/blog/integrating-apache-fop-with-java-project-to-generate-pdf-files/
	 * 
	 * @param language
	 * @param projectId
	 * @param topicInstanceId
	 * @param geomType
	 * @param uriInfo
	 * @return
	 */
	@GET
	@Path("{topicInstanceId}/topic")
	public Response getTopicAsPDF(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("topicInstanceId") UUID topicInstanceId,
			@QueryParam("geomType") AttributeValueGeomType geomType,
			@Context UriInfo uriInfo) {
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(uriInfo.getAbsolutePath());
		String xml = target.request(
				MediaType.APPLICATION_XML).get(String.class);		
		
		Fop fop;
		FopFactory fopf = FopFactory.newInstance();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		Transformer xslfoTransformer;
		
		try {
			fop = fopf.newFop(
					MimeConstants.MIME_PDF, 
					fopf.newFOUserAgent(), 
					stream);
			
			Result res = new SAXResult(fop.getDefaultHandler());
			
			TransformerFactory transfact = TransformerFactory.newInstance();
			xslfoTransformer = 
					transfact.newTransformer(
							new StreamSource(
									new TopicPDFResource().getClass()
									.getClassLoader().getResourceAsStream(
											XSL_FILE)));
			xslfoTransformer.transform(
					new StreamSource(new StringReader(xml)), res);
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return Response.ok().entity(stream.toByteArray()).build();
	}

}
