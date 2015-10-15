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

@Path("/v1/projects")
public class OpenInfraFileUpload {
	
	@POST
	@Path("{projectId}/files/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadFile(FormDataMultiPart multiPart) {
		
		List<FormDataBodyPart> fields = multiPart.getFields("test");        
	    for(FormDataBodyPart field : fields){
	        System.out.println("--> " + 
	    field.getFormDataContentDisposition().getFileName() + " " + 
	        		field.getContentDisposition().getCreationDate() + " " +
	        		field.getMediaType());
	    }

	    return Response.ok().entity("uploaded").build();
	}

}
