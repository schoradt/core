package de.btu.openinfra.backend.rest.csv;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import de.btu.openinfra.backend.db.daos.AttributeValueGeomType;
import de.btu.openinfra.backend.db.pojos.TopicPojo;
import de.btu.openinfra.backend.rest.pdf.TopicPdfResource;
import de.btu.openinfra.backend.rest.project.TopicInstanceResource;

/**
 * This class exports CSV files.
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@Path("/projects/{projectId}/topicinstances")
public class TopicCsvResource {
	
	/**
	 * This variable defines the path to the XSL file.
	 */
	private String XSL_FILE = 
			"de/btu/openinfra/backend/xsl/TopicCsv.xsl"; 
	
	/**
	 * This method executes the XSL transformation. 
	 * 
	 * @param language
	 * @param projectId
	 * @param topicInstanceId
	 * @param geomType
	 * @param uriInfo
	 * @param servletResponse
	 * @return
	 */
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
		
		// Don't care about accessing the RBAC system classes. Just use the 
		// resource class to retrieve the requested object. 
		TopicPojo pojo = new TopicInstanceResource().get(
				language, 
				projectId, 
				topicInstanceId, 
				geomType);
		
		String xml = "";
		StringWriter sw = new StringWriter();
		try {
			JAXBContext jaxb = JAXBContext.newInstance(TopicPojo.class);
			Marshaller m = jaxb.createMarshaller();
			m.marshal(pojo , sw);
			xml = sw.toString();			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		// Use a transformer and a specific XSL file in order to generate CSV
		// output.
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
