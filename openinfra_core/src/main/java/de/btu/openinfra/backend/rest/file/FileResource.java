package de.btu.openinfra.backend.rest.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
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
import javax.ws.rs.core.UriInfo;

import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

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
	public List<FilePojo> uploadFile(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
			FormDataMultiPart multiPart) {

		// Get the current user id and create a list to hold uploaded files
		UUID subject = new SubjectResource().self().getUuid();
		List<FilePojo> files = new LinkedList<FilePojo>();

		List<FormDataBodyPart> fields = multiPart.getFields("files");
	    for(FormDataBodyPart field : fields) {
	    	FilePojo pojo = new FilePojo();
	    	// Store the file and rename it
	    	String originFileName =
	    			field.getFormDataContentDisposition().getFileName();
	    	InputStream fileStream = field.getEntityAs(InputStream.class);
	    	String fileName = saveFile(fileStream, originFileName);
	    	// Take the first image to generate the signature
	    	String signature = "";
    		try {
    			signature = new Sha256Hash(
    					Files.readAllBytes(Paths.get(fileName))).toString();
    		} catch(Exception ex) {
    			throw new OpenInfraWebException(ex);
    		}

//	    	for(File f : savedFiles) {
//	    		f.renameTo(new File(
//	    				f.getParentFile().getAbsolutePath() + signature));
//	    	}

	    	pojo.setSignature(signature);
	    	pojo.setOriginFileName(originFileName);
	    	pojo.setMimeType(field.getMediaType().toString());
	    	pojo.setSubject(subject);
	    	FileRbac rbac = new FileRbac();
	    	UUID result = rbac.createOrUpdate(
	    			OpenInfraHttpMethod.valueOf(request.getMethod()),
	    			uriInfo, null, pojo);
	    	files.add(rbac.read(
	    			OpenInfraHttpMethod.valueOf(request.getMethod()),
	    			uriInfo, null, result));

	    }

	    // Delete the signatures
	    for(FilePojo fp : files) {
	    	fp.setSignature(null);
	    }
	    return files;
	}

	private String saveFile(InputStream fileStream, String fileName) {
		String filePath = OpenInfraPropertyValues.UPLOAD_PATH.getValue() +
				UUID.randomUUID() + "." + FilenameUtils.getExtension(fileName);
		try {
			File oFile = new File(filePath);
			OutputStream os = new FileOutputStream(oFile);
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

		return filePath;
	}

}
