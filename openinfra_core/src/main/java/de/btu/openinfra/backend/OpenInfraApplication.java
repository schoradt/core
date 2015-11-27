package de.btu.openinfra.backend;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.ProcessStarter;

/**
 * This class is represents the main class of the OpenInfRA application. It is
 * used to configure application specific stuff such as the Jersey JAX-RS and
 * HTML template engines. This class is referenced in the web.xml file and will
 * be called during application start.
 *
 * Moreover, this class creates the necessary directory structure and tests
 * if the external packages such as ImageMagick are available. An exception is
 * thrown when something goes wrong. This exception should be written to tomcat
 * log files (e.g. catalina.out).
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class OpenInfraApplication extends ResourceConfig {

	/**
	 * This variable defines the used persistence context.
	 */
	public static final String PERSISTENCE_CONTEXT = "openinfra_core";

	/**
	 * This is the default non argument constructor which calls the super
	 * constructor and sets configuration specific arguments such as the
	 * resource packages and the used template engine.
	 */
	public OpenInfraApplication() {

		super();

		// Register the JSP template engine
		property(JspMvcFeature.TEMPLATE_BASE_PATH, "/jsp/");
		register(JspMvcFeature.class);

		// Register packages which include the URI mapping
		// This is done recursively
		packages("de.btu.openinfra.backend.rest");
		packages("de.btu.openinfra.plugins.solr.rest");

		checkAndCreateFileSystem();
		initImageMagick();
	}

	/**
	 * This method is used to identify if the OpenInfRA application runs on
	 * Windows or not.
	 *
	 * @return true if it runs on Windows
	 */
	public static boolean runsOnWindows() {
		return System.getProperty("os.name").startsWith("Windows");
	}

	/**
	 * This method checks the file system and creates the necessary directory
	 * structure.
	 */
	private void checkAndCreateFileSystem() {
		try {
			// Try to create the OpenInfRA file path when it doesn't exists.
			File dir = new File(OpenInfraProperties.FILE_PATH);
			if(!dir.exists()) {
				dir.mkdir();
			}

			// Try to write in the given directory.
			File f = new File(OpenInfraProperties.FILE_PATH + "test.txt");
			f.createNewFile();
			f.delete();

			// Try to create the project data path.
			File pdata = new File(
					OpenInfraPropertyValues.PROJECTDATA_PATH.getValue());
			if(!pdata.exists()) {
				pdata.mkdir();
			}

			// Try to create the upload path.
			File upload = new File(
					OpenInfraPropertyValues.UPLOAD_PATH.getValue());
			if(!upload.exists()) {
				upload.mkdir();
			}

			// Try to create the image paths.
			File images = new File(
					OpenInfraPropertyValues.IMAGE_THUMBNAIL_PATH.getValue());
			if(!images.exists()) {
				images.mkdir();
			}

			// Try to create the small images path.
			File samllImages = new File(
					OpenInfraPropertyValues.IMAGE_POPUP_PATH.getValue());
			if(!samllImages.exists()) {
				samllImages.mkdir();
			}

			// Try to create the middle images path.
			File middleImages = new File(
					OpenInfraPropertyValues.IMAGE_MIDDLE_PATH.getValue());
			if(!middleImages.exists()) {
				middleImages.mkdir();
			}

		} catch(Exception ex) {
			System.err.print("Couldn't write on file System! \n");
			ex.printStackTrace();
		}
	}

	/**
	 * This function tries to initialize ImageMagick.
	 */
	private void initImageMagick() {
		if(runsOnWindows()) {
			ProcessStarter.setGlobalSearchPath(
					OpenInfraProperties.getProperty(
							OpenInfraPropertyKeys.WIN_IMAGEMAGICK_PATH
							.getKey()));
		}

		// Create fake image
		String imgPath =
				OpenInfraPropertyValues.UPLOAD_PATH.getValue() + "test.jpg";
        BufferedImage img = new BufferedImage(256, 256,
        		BufferedImage.TYPE_INT_RGB);
        try {
			ImageIO.write(img, "jpg", new File(imgPath));
		} catch (IOException e) {
			e.printStackTrace();
		}

        // Try ImageMagick
		// create command
		ConvertCmd cmd = new ConvertCmd();

		// create the operation, add images and operators/options
		IMOperation op = new IMOperation();
		op.addImage(imgPath);
		op.resize(80,80,"!");
		String testImg =
				OpenInfraPropertyValues.UPLOAD_PATH.getValue() + "test.png";
		op.addImage(testImg);

		// execute the operation
		try {
			cmd.run(op);
		} catch (IOException | InterruptedException | IM4JavaException e) {
			e.printStackTrace();
		}

		new File(imgPath).delete();
		new File(testImg).delete();
	}

	/**
	 * This method delivers the OpenInfRA version provided by the Maven
	 * pom file. In order to change the version number change it in the
	 * pom.xml file.
	 *
	 * @param sc the current servlet context
	 * @return   the current version as String
	 */
	public static String getOpenInfraVersion(ServletContext sc) {
		Properties prop = new Properties();
		try {
			prop.load(sc.getResourceAsStream("META-INF/maven/"
					+ "de.btu.openinfra.core/openinfra_core/pom.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		} // end try catch
		return prop.getProperty("version");
	}
}
