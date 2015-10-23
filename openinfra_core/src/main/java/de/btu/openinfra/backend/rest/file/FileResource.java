package de.btu.openinfra.backend.rest.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.core.Info;

import de.btu.openinfra.backend.OpenInfraProperties;
import de.btu.openinfra.backend.OpenInfraPropertyKeys;
import de.btu.openinfra.backend.OpenInfraPropertyValues;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.daos.file.FileDao;
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
		// In this case we don't need the RBAC class since we only retrieve
		// data which belongs to the current subject (user).
		return new FileDao().countBySubject(
				new SubjectResource().self().getUuid());
	}

	@GET
	@Path("{fileId:([0-9a-f]{8}-([0-9a-f]{4}-){3}[0-9a-f]{12})}")
	public FilePojo getFile(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("fileId") UUID fileId) {
		return new FileRbac().read(
				OpenInfraHttpMethod.valueOf(request.getMethod()),
				uriInfo, null, fileId);
	}

	@GET
	@Path("{fileId:([0-9a-f]{8}-([0-9a-f]{4}-){3}[0-9a-f]{12})}/{dim}")
	public Response getFileContent(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("fileId") UUID fileId,
			@PathParam("dim") String dim) {
		FilePojo pojo = getFile(uriInfo, request, fileId);
		String filePath = "";
		String fileExtension = "png";
		if(dim.equalsIgnoreCase("origin")) {
			filePath = OpenInfraPropertyValues.UPLOAD_PATH.getValue();
			fileExtension = FilenameUtils.getExtension(
					pojo.getOriginFileName());
		} else if(dim.equalsIgnoreCase("thumbnail")) {
			filePath = OpenInfraPropertyValues.IMAGE_THUMBNAIL_PATH.getValue();
		} else if(dim.equalsIgnoreCase("middle")) {
			filePath = OpenInfraPropertyValues.IMAGE_MIDDLE_PATH.getValue();
		} else if(dim.equalsIgnoreCase("popup")) {
			filePath = OpenInfraPropertyValues.IMAGE_POPUP_PATH.getValue();
		}
		String fileName = filePath + pojo.getSignature() + "." + fileExtension;
		// Send not found status code when file desn't exists
		if(!new File(fileName).exists()) {
			Response.status(404).build();
		}
		java.nio.file.Path p = Paths.get(fileName);
		byte[] document = null;
		String contentType = "";
		try {
			document = Files.readAllBytes(p);
			contentType = Files.probeContentType(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.ok(document, contentType)
				.header("content-disposition","attachment; filename = " +
						pojo.getOriginFileName()).build();
	}

	@GET
	public List<FilePojo> files(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		// In this case we don't need the RBAC class since we only retrieve
		// data which belongs to the current subject (user).
		return new FileDao().readBySubject(
				PtLocaleDao.forLanguageTag(language),
				new SubjectResource().self().getUuid(),
				offset,
				size);
	}

	@POST
	@Path("/upload/{originFileName}")
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	public FilePojo uploadFile(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
    		@PathParam("originFileName") String originFileName,
    		InputStream is) {

		// Get the current user id and create a list to hold uploaded files
		UUID subject = new SubjectResource().self().getUuid();

		FilePojo pojo = new FilePojo();
    	String signature = "";
    	String fileName = "";
    	java.nio.file.Path filePath = null;
		try {
			fileName = saveFile(is, originFileName);
			filePath = Paths.get(fileName);
			pojo.setMimeType(Files.probeContentType(filePath));
			signature = new Sha256Hash(Files.readAllBytes(filePath)).toString();
		} catch(Exception ex) {
			throw new OpenInfraWebException(ex);
		}

		// rename file
		File currentFile = new File(fileName);
		File signatureFile = new File(
				OpenInfraPropertyValues.UPLOAD_PATH.getValue() +
				signature + "." + FilenameUtils.getExtension(fileName));
		currentFile.renameTo(signatureFile);

		pojo = resizeDimensions(signatureFile.getAbsolutePath(),
				signature, pojo);
    	pojo.setSignature(signature);
    	pojo.setOriginFileName(originFileName);
    	pojo.setSubject(subject);
    	FileRbac rbac = new FileRbac();
    	UUID result = rbac.createOrUpdate(
    			OpenInfraHttpMethod.valueOf(request.getMethod()),
    			uriInfo, null, pojo);
    	FilePojo fp = rbac.read(
    			OpenInfraHttpMethod.valueOf(request.getMethod()),
    			uriInfo, null, result);
		return fp;
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
	    	// Take the first image to generate the signature
	    	String signature = "";
	    	String fileName = "";
    		try {
    			fileName = saveFile(
    					field.getEntityAs(InputStream.class), originFileName);
    			signature = new Sha256Hash(
    					Files.readAllBytes(Paths.get(fileName))).toString();
    		} catch(Exception ex) {
    			throw new OpenInfraWebException(ex);
    		}

    		// rename file
    		File currentFile = new File(fileName);
    		File signatureFile = new File(
    				OpenInfraPropertyValues.UPLOAD_PATH.getValue() +
    				signature + "." + FilenameUtils.getExtension(fileName));
    		currentFile.renameTo(signatureFile);

    		pojo = resizeDimensions(signatureFile.getAbsolutePath(),
    				signature, pojo);
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
	    return files;
	}

	/**
	 * This method saves a file into the file system. The path is specified
	 * by the upload path variable.
	 *
	 * @param fileStream
	 * @param fileName
	 * @return
	 */
	private String saveFile(InputStream fileStream, String fileName)
			throws IOException {
		String filePath = OpenInfraPropertyValues.UPLOAD_PATH.getValue() +
				UUID.randomUUID() + "." + FilenameUtils.getExtension(fileName);
		try {
			OutputStream os = new FileOutputStream(new File(filePath));
			int read = 0;
			byte[] buffer = new byte[1024];
			while((read = fileStream.read(buffer)) != -1) {
				os.write(buffer, 0, read);
			}
			os.flush();
			os.close();
		} catch (Exception ex) {
			throw new OpenInfraWebException(ex);
		} finally {
			if(fileStream != null) {
				fileStream.close();
			}
		}
		return filePath;
	}

	private FilePojo resizeDimensions(String file,
			String signature, FilePojo pojo) {

		ConvertCmd cmd = new ConvertCmd();
		String extension = ".png";

		String[] thumbDim = OpenInfraProperties.getProperty(
				OpenInfraPropertyKeys.IMG_THUMBNAIL_DIMENSION.getKey())
				.split("x");
		String thumbPath =
				OpenInfraPropertyValues.IMAGE_THUMBNAIL_PATH.getValue() +
				signature + extension;
		IMOperation thumbnail = new IMOperation();
		thumbnail.addImage(file);
		thumbnail.resize(Integer.valueOf(thumbDim[0]),
				Integer.valueOf(thumbDim[1]),"!");
		thumbnail.addImage(thumbPath);

		String[] middleDim = OpenInfraProperties.getProperty(
				OpenInfraPropertyKeys.IMG_MIDDLE_DIMENSION.getKey())
				.split("x");
		String middlePath =
				OpenInfraPropertyValues.IMAGE_MIDDLE_PATH.getValue() +
				signature + extension;
		IMOperation middle = new IMOperation();
		middle.addImage(file);
		middle.resize(Integer.valueOf(middleDim[0]),
				Integer.valueOf(middleDim[1]));
		middle.addImage(middlePath);

		String[] popupDim = OpenInfraProperties.getProperty(
				OpenInfraPropertyKeys.IMG_POPUP_DIMENSION.getKey())
				.split("x");
		String popupPath =
				OpenInfraPropertyValues.IMAGE_POPUP_PATH.getValue() +
				signature + extension;
		IMOperation popup = new IMOperation();
		popup.addImage(file);
		popup.resize(Integer.valueOf(popupDim[0]),
				Integer.valueOf(popupDim[1]));
		popup.addImage(popupPath);

		try {
			String originGeom = new Info(file, true).getImageGeometry();
			pojo.setOriginDimension(
					originGeom.substring(0, originGeom.indexOf("+")));

			cmd.run(thumbnail);
			String thumbGeom = new Info(thumbPath, true).getImageGeometry();
			pojo.setThumbnailDimension(
					thumbGeom.substring(0, thumbGeom.indexOf("+")));

			cmd.run(middle);
			String middleGeom = new Info(middlePath, true).getImageGeometry();
			pojo.setMiddleDimension(
					middleGeom.substring(0, middleGeom.indexOf("+")));

			cmd.run(popup);
			String popupGeom = new Info(popupPath, true).getImageGeometry();
			pojo.setPopupDimension(
					popupGeom.substring(0, popupGeom.indexOf("+")));
		} catch (IOException | InterruptedException | IM4JavaException e) {
			throw new OpenInfraWebException(e);
		}
		return pojo;
	}

}
