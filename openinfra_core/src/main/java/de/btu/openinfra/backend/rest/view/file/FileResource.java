package de.btu.openinfra.backend.rest.view.file;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Template;

import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path("/v1/files")
@Produces(MediaType.TEXT_HTML +
		OpenInfraResponseBuilder.UTF8_CHARSET +
		OpenInfraResponseBuilder.HTML_PRIORITY)
public class FileResource {

	@GET
	@Template(name="/views/list/Files.jsp")
	@Produces(MediaType.TEXT_HTML +
			OpenInfraResponseBuilder.UTF8_CHARSET +
			OpenInfraResponseBuilder.HTML_PRIORITY)
    public Response files() {
        return Response.ok().entity("file upload").build();
    }


	@GET
	@Path("upload")
	@Template(name="/views/Upload.jsp")
	@Produces(MediaType.TEXT_HTML +
			OpenInfraResponseBuilder.UTF8_CHARSET +
			OpenInfraResponseBuilder.HTML_PRIORITY)
    public Response uploadFiles() {
        return Response.ok().entity("file upload").build();
    }

}
