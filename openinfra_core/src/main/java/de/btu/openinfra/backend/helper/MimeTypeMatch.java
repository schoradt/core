package de.btu.openinfra.backend.helper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.activation.MimeType;

import org.apache.tika.Tika;


public class MimeTypeMatch {

	public static void main(String[] args) throws Exception {
		Path filePath = Paths.get(args[0]);
		System.out.println("java: " + Files.probeContentType(filePath));
		String tikaMime = new Tika().detect(filePath);

		MimeType mimeType = new MimeType(tikaMime);
		System.out.println(mimeType.match("application/vnd.oasis.opendocument.*"));

	}

}
