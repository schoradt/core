package de.btu.openinfra.backend.rest.project.files;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path("/v1/projects")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
    MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET})
public class OpenInfraFileUpload {
	
	@POST
	@Path("{projectId}/files/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(FormDataMultiPart multiPart) {
		
		List<FormDataBodyPart> fields = multiPart.getFields("files");        
	    for(FormDataBodyPart field : fields){
	        System.out.println("--> " + 
	    field.getFormDataContentDisposition().getFileName() + " " + 
	        		field.getContentDisposition().getCreationDate() + " " +
	        		field.getMediaType());
	    }

	    return Response.ok().entity("uploaded").build();
	}

}
