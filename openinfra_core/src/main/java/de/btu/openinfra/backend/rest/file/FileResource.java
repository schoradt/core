package de.btu.openinfra.backend.rest.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.core.Info;
import org.json.simple.JSONObject;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import de.btu.openinfra.backend.OpenInfraProperties;
import de.btu.openinfra.backend.OpenInfraPropertyKeys;
import de.btu.openinfra.backend.OpenInfraPropertyValues;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.daos.file.FileDao;
import de.btu.openinfra.backend.db.daos.file.FilesProjectDao;
import de.btu.openinfra.backend.db.pojos.file.FilePojo;
import de.btu.openinfra.backend.db.pojos.file.FilesProjectPojo;
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

	private static final String EXTENSION = ".png";

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

	@GET
	@Path("{fileId:([0-9a-f]{8}-([0-9a-f]{4}-){3}[0-9a-f]{12})}")
	public FilePojo getFile(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("fileId") UUID fileId) {
		// Return the file, when the current user is owner of this file
		FilePojo pojo = new FileDao().read(null, fileId);
		if(new SubjectResource().self().getUuid().equals(pojo.getSubject())) {
			return pojo;
		}

		// Set the required permission
		String requiredPermission = "/projects/{id}:" +
			OpenInfraHttpMethod.valueOf(request.getMethod()).getAccess() + ":";
		// Get the list of projects where this file belongs to
		List<FilesProjectPojo> fpPojos =
				new FilesProjectDao().readByFileId(fileId);
		for(FilesProjectPojo fpp : fpPojos) {
			if(SecurityUtils.getSubject().isPermitted(
					requiredPermission + fpp.getProject())) {
				return pojo;
			}
		}
		throw new WebApplicationException(Status.FORBIDDEN);
	}

	@DELETE
	@Path("{fileId:([0-9a-f]{8}-([0-9a-f]{4}-){3}[0-9a-f]{12})}")
	public Response delete(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("fileId") UUID fileId) {

		if(!SecurityUtils.getSubject().isPermitted("/files:w")) {
			throw new WebApplicationException(Status.FORBIDDEN);
		}

		FileDao dao = new FileDao();
		FilePojo fp = dao.read(null, fileId);
		boolean del = new FileDao().delete(
				fileId, new SubjectResource().self().getUuid());
		if(del && dao.countBySignature(fp.getSignature()) == 0) {
			deleteFile(fp);
		}
		return OpenInfraResponseBuilder.deleteResponse(del, fileId);
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
		String fileExtension = ".png";
		if(dim.equalsIgnoreCase("origin")) {
			filePath = OpenInfraPropertyValues.UPLOAD_PATH.getValue();
			fileExtension = "." + FilenameUtils.getExtension(
					pojo.getOriginFileName());
		} else if(dim.equalsIgnoreCase("thumbnail")) {
			filePath = OpenInfraPropertyValues.IMAGE_THUMBNAIL_PATH.getValue();
		} else if(dim.equalsIgnoreCase("middle")) {
			filePath = OpenInfraPropertyValues.IMAGE_MIDDLE_PATH.getValue();
		} else if(dim.equalsIgnoreCase("popup")) {
			filePath = OpenInfraPropertyValues.IMAGE_POPUP_PATH.getValue();
		}
		String fileName = filePath + pojo.getSignature() + fileExtension;
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
			throw new WebApplicationException(Status.NOT_FOUND);
		}
		return Response.ok(document, contentType)
				.header("content-disposition","attachment; filename = " +
						FilenameUtils.removeExtension(
								pojo.getOriginFileName()) +
								fileExtension).build();
	}

	@POST
	@Path("/upload/{originFileName}")
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	public FilePojo uploadFile(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
    		@PathParam("originFileName") String originFileName,
    		InputStream is) {

		if(!SecurityUtils.getSubject().isPermitted("/files:w")) {
			throw new WebApplicationException(Status.FORBIDDEN);
		}

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
		if(signatureFile.exists()) {
			currentFile.delete();
		} else {
			currentFile.renameTo(signatureFile);
		}

		pojo.setSignature(signature);
		pojo = resizeDimensions(signatureFile.getAbsolutePath(), pojo);
		try {
			pojo.setExifData(extractMetadata(signatureFile));
		} catch (Exception e) {
			System.err.append("Metadata Extractor: " + e.getMessage());
		}
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

		if(!SecurityUtils.getSubject().isPermitted("/files:w")) {
			throw new WebApplicationException(Status.FORBIDDEN);
		}

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
    		if(signatureFile.exists()) {
    			currentFile.delete();
    		} else {
    			currentFile.renameTo(signatureFile);
    		}

    		pojo.setMimeType(field.getMediaType().toString());
    		pojo.setSignature(signature);
    		try {
    			pojo.setExifData(extractMetadata(signatureFile));
    		} catch (Exception e) {
    			System.err.append("Metadata Extractor: " + e.getMessage());
    		}

    		pojo = resizeDimensions(signatureFile.getAbsolutePath(), pojo);
	    	pojo.setOriginFileName(originFileName);
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
	 * by the upload path variable. The file name is used to extract the file
	 * extension. A UUID is generated in order to store the file into the file
	 * system.
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

	/**
	 * This method deletes all files concerning the pojo.
	 *
	 * @param pojo the corresponding pojo object
	 */
	private void deleteFile(FilePojo pojo) {
		File origin = new File(
				OpenInfraPropertyValues.UPLOAD_PATH.getValue() +
				pojo.getSignature() + "." +
						FilenameUtils.getExtension(pojo.getOriginFileName()));
		if(origin.exists()) {
			origin.delete();
		}

		File thumbnail = new File(
				OpenInfraPropertyValues.IMAGE_THUMBNAIL_PATH.getValue() +
				pojo.getSignature() + EXTENSION);
		if(thumbnail.exists()) {
			thumbnail.delete();
		}

		File middle = new File(
				OpenInfraPropertyValues.IMAGE_MIDDLE_PATH.getValue() +
				pojo.getSignature() + EXTENSION);
		if(middle.exists()) {
			middle.delete();
		}

		File popup = new File(
				OpenInfraPropertyValues.IMAGE_POPUP_PATH.getValue() +
				pojo.getSignature() + EXTENSION);
		if(popup.exists()) {
			popup.delete();
		}
	}

	/**
	 * This method is used to create a set of resized images of the uploaded
	 * file. Therefore, ImageMagick is used. It will create the corresponding
	 * resized images when it is possible.
	 *
	 * @param file the path to the file
	 * @param pojo the corresponding pojo object
	 * @return
	 */
	private FilePojo resizeDimensions(String file, FilePojo pojo) {

		ConvertCmd cmd = new ConvertCmd();

		// Thumbnail
		String[] thumbDim = OpenInfraProperties.getProperty(
				OpenInfraPropertyKeys.IMG_THUMBNAIL_DIMENSION.getKey())
				.split("x");
		String thumbPath =
				OpenInfraPropertyValues.IMAGE_THUMBNAIL_PATH.getValue() +
				pojo.getSignature() + EXTENSION;
		File thumbFile = new File(thumbPath);

		// Middle sized image
		String[] middleDim = OpenInfraProperties.getProperty(
				OpenInfraPropertyKeys.IMG_MIDDLE_DIMENSION.getKey())
				.split("x");
		String middlePath =
				OpenInfraPropertyValues.IMAGE_MIDDLE_PATH.getValue() +
				pojo.getSignature() + EXTENSION;
		File middleFile = new File(middlePath);

		// Popup sized image
		String[] popupDim = OpenInfraProperties.getProperty(
				OpenInfraPropertyKeys.IMG_POPUP_DIMENSION.getKey())
				.split("x");
		String popupPath =
				OpenInfraPropertyValues.IMAGE_POPUP_PATH.getValue() +
				pojo.getSignature() + EXTENSION;
		File popupFile = new File(popupPath);

		try {
			String originGeom = new Info(file, true).getImageGeometry();
			pojo.setOriginDimension(
					originGeom.substring(0, originGeom.indexOf("+")));

			if(!thumbFile.exists()) {
				cmd.run(opBuilder(thumbDim, file,
						pojo.getMimeType(), thumbPath, true));
			}
			if(thumbFile.exists()) {
				String thumbGeom = new Info(thumbPath, true).getImageGeometry();
				pojo.setThumbnailDimension(
						thumbGeom.substring(0, thumbGeom.indexOf("+")));
			}

			if(!middleFile.exists()) {
				cmd.run(opBuilder(middleDim, file,
						pojo.getMimeType(), middlePath, false));
			}
			if(middleFile.exists()) {
				String middleGeom =
						new Info(middlePath, true).getImageGeometry();
				pojo.setMiddleDimension(
						middleGeom.substring(0, middleGeom.indexOf("+")));
			}

			if(!popupFile.exists()) {
				cmd.run(opBuilder(popupDim, file,
						pojo.getMimeType(), popupPath, false));
			}
			if(popupFile.exists()) {
				String popupGeom = new Info(popupPath, true).getImageGeometry();
				pojo.setPopupDimension(
						popupGeom.substring(0, popupGeom.indexOf("+")));
			}
		} catch (IOException | InterruptedException | IM4JavaException e) {
			System.err.append("ImageMagick: " + e.getMessage() + "\n");
		}
		return pojo;
	}

	/**
	 * This method is a simple ImageMagick operation builder.
	 *
	 * @param dimensions
	 * @param filePath
	 * @param mimeType
	 * @param destination
	 * @param isThumbnail
	 * @return
	 */
	private IMOperation opBuilder(
			String[] dimensions, String filePath,
			String mimeType, String destination, boolean isThumbnail) {
		IMOperation op = new IMOperation();
		if(mimeType.contains("pdf")) {
			if(isThumbnail) {
				op.thumbnail(Integer.valueOf(dimensions[0]),
						Integer.valueOf(dimensions[1]),"!");
			} else {
				op.thumbnail(Integer.valueOf(dimensions[0]),
						Integer.valueOf(dimensions[1]));
			}
			op.background("white");
			op.alpha("remove");
			op.addImage(filePath + " [0]");
		} else {
			op.addImage(filePath);
			if(isThumbnail) {
				op.resize(Integer.valueOf(dimensions[0]),
						Integer.valueOf(dimensions[1]),"!");
			} else {
				op.resize(Integer.valueOf(dimensions[0]),
						Integer.valueOf(dimensions[1]));
			}
		}
		op.addImage(destination);
		return op;
	}

	@SuppressWarnings("unchecked")
	private String extractMetadata(File file) throws Exception {
		Metadata md = null;
		md = ImageMetadataReader.readMetadata(file);

		JSONObject json = new JSONObject();
		Map<String, Map<String, String>> dirMap =
				new HashMap<String, Map<String, String>>();
		for(Directory dir : md.getDirectories()) {
			Map<String, String> tags = new HashMap<String, String>();
			for(Tag tag : dir.getTags()) {
				tags.put(tag.getTagName(), tag.getDescription());
			}
			dirMap.put(dir.getName(), tags);
		}
		json.putAll(dirMap);
		return json.toJSONString();
	}

}
