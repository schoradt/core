package de.btu.openinfra.backend.rest.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.io.FilenameUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import de.btu.openinfra.backend.OpenInfraPropertyValues;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.pojos.file.FilePojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;
import de.btu.openinfra.backend.db.rbac.file.FileRbac;
import de.btu.openinfra.backend.exception.OpenInfraWebException;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;
import de.btu.openinfra.backend.rest.rbac.SubjectResource;

@Path("/v1/files")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
    MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET})
public class FileResource {

	@GET
    @Path("count")
	@Produces({MediaType.TEXT_PLAIN})
    public long getFilesCount(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request) {
		return 0;
	}

	@GET
	public List<FilePojo> files(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new FileRbac().read(
				OpenInfraHttpMethod.valueOf(request.getMethod()),
				uriInfo,
				PtLocaleDao.forLanguageTag(language),
				offset,
				size);
	}

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
			FormDataMultiPart multiPart) {

		UUID subject = new SubjectResource().self().getUuid();

		List<FormDataBodyPart> fields = multiPart.getFields("files");
	    for(FormDataBodyPart field : fields) {
	    	InputStream fileStream = field.getEntityAs(InputStream.class);
	    	FilePojo pojo = new FilePojo();
	    	String fileName =
	    			field.getFormDataContentDisposition().getFileName();
	    	pojo.setOriginFileName(fileName);
	    	pojo.setMimeType(field.getMediaType().toString());
	    	pojo.setSubject(subject);
	    	UUID result = new FileRbac().createOrUpdate(
	    			OpenInfraHttpMethod.valueOf(request.getMethod()),
	    			uriInfo, null, pojo);
	    	if(result != null) {
	    		saveFile(fileStream, field.getMediaType().toString(),
	    				result, fileName);
	    	}
	    }
	    // TODO gesamte liste der infos zurückgeben
	    return Response.ok().entity("uploaded").build();
	}

	private void saveFile(InputStream fileStream,
			String mimeType, UUID file, String fileName) {
		String filePath = "";
		String newFileName =
				"/" + file + "." + FilenameUtils.getExtension(fileName);
		if(mimeType.startsWith("image")) {
			filePath = OpenInfraPropertyValues.IMAGE_PATH.getValue();
		} else {
			filePath = OpenInfraPropertyValues.FILE_PATH.getValue();
		}
		try {
			OutputStream os = new FileOutputStream(
					new File(filePath + newFileName));
			int read = 0;
			byte[] buffer = new byte[1024];
			while((read = fileStream.read(buffer)) != -1) {
				os.write(buffer, 0, read);
			}
			os.flush();
			os.close();
		} catch (Exception ex) {
			throw new OpenInfraWebException(ex);
		}

		// Create thumbnails
		// Thumbnail 60x60 fix
		// Middle    400x400 scaled (dimension in table)
		// Popup     800x800 scaled (dimension in table)
		if(mimeType.startsWith("image")) {
			// small & middle
			try {

				Metadata md = ImageMetadataReader.readMetadata(
						new File(filePath + newFileName));
				System.out.println("--> " + md.getDirectoryCount());

				for (Directory directory : md.getDirectories()) {
				    for (Tag tag : directory.getTags()) {
				        System.out.println(tag);
				    }
				}

				Thumbnails.of(filePath + newFileName).size(125, 125).toFile(
						OpenInfraPropertyValues.IMAGE_SMALL_PATH.getValue() +
						newFileName);
				Thumbnails.of(filePath + newFileName).size(400, 400).toFile(
						OpenInfraPropertyValues.IMAGE_MIDDLE_PATH.getValue() +
						newFileName);
			} catch (IOException | ImageProcessingException ex) {
				throw new OpenInfraWebException(ex);
			}
		}
	}

}
