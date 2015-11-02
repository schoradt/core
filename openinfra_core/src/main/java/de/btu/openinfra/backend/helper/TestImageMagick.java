package de.btu.openinfra.backend.helper;

import javax.ws.rs.core.MediaType;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.core.Info;
import org.im4java.process.ProcessStarter;

import de.btu.openinfra.backend.OpenInfraProperties;
import de.btu.openinfra.backend.OpenInfraPropertyKeys;

public class TestImageMagick {

	public static void main(String[] args) throws Exception {
		System.out.println("--> " + MediaType.APPLICATION_OCTET_STREAM);

		ProcessStarter.setGlobalSearchPath(
				OpenInfraProperties.getProperty(
						OpenInfraPropertyKeys.WIN_IMAGEMAGICK_PATH
						.getKey()));



		// create command
		ConvertCmd cmd = new ConvertCmd();

		// create the operation, add images and operators/options
		IMOperation op = new IMOperation();
		op.addImage("C:\\Users\\tino\\Downloads\\openinfra_img\\PM9UtbC.jpg");
		op.resize(80,80,"!");
		op.addImage("C:\\Users\\tino\\Downloads\\openinfra_img\\myimage_small.png");

		// execute the operation
		cmd.run(op);

		Info imageInfo = new Info("C:\\Users\\tino\\Downloads\\openinfra_img\\myimage_small.png",true);
		System.out.println("Format: " + imageInfo.getImageFormat());
		System.out.println("Width: " + imageInfo.getImageWidth());
		System.out.println("Height: " + imageInfo.getImageHeight());
		System.out.println("Geometry: " + imageInfo.getImageGeometry());
		System.out.println("Depth: " + imageInfo.getImageDepth());
		System.out.println("Class: " + imageInfo.getImageClass());

	}

}
