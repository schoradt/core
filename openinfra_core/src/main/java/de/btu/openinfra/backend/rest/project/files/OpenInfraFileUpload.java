package de.btu.openinfra.backend.rest.project.files;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path(OpenInfraResponseBuilder.REST_URI_PROJECTS + "/files")
public class OpenInfraFileUpload {
	
	@POST
	@Path("upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile() {
		
		
		return Response.ok("").build();
	}

}
