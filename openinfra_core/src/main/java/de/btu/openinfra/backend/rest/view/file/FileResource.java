package de.btu.openinfra.backend.rest.view.file;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;

import de.btu.openinfra.backend.db.pojos.file.FilePojo;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path("/v1/files")
@Produces(MediaType.TEXT_HTML +
		OpenInfraResponseBuilder.UTF8_CHARSET +
		OpenInfraResponseBuilder.HTML_PRIORITY)
public class FileResource {

	/**
	 * Technical GUI. List files: Files.jsp
	 *
	 * @param uriInfo
	 * @param request
	 * @param offset
	 * @param size
	 * @return HTML
	 */
	@GET
	@Template(name="/views/list/Files.jsp")
    public List<FilePojo> filesByUser(
    		@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
        return new de.btu.openinfra.backend.rest.file.FileResource()
        .readFilesBySubject(uriInfo, request, offset, size);
    }

	/**
	 * Technical GUI. JSP view in order to upload a file: Upload.jsp
	 * @return HTML
	 */
	@GET
	@Path("upload")
	@Template(name="/views/Upload.jsp")
    public Response uploadFiles() {
        return Response.ok().entity("file upload").build();
    }

}
