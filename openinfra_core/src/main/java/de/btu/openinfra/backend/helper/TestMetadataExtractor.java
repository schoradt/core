package de.btu.openinfra.backend.helper;

import java.io.File;
import java.io.IOException;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class TestMetadataExtractor {

	public static void main(String[] args) {
		File file = new File("C:\\Users\\tino\\Downloads\\openinfra_img\\exif_sample.jpg");
		Metadata md = null;
		try {
			md = ImageMetadataReader.readMetadata(file);
		} catch (ImageProcessingException | IOException e) {
			e.printStackTrace();
		}

		for(Directory dir : md.getDirectories()) {
			for(Tag tag : dir.getTags()) {
				System.out.println("-->" + tag.getTagName() +
						" : " + tag.getDescription());
			}
		}

	}

}
