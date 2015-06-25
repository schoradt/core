package de.btu.openinfra.backend.helper;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;



/**
 * This class is used to retrieve images from the BTU server.
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class ImgUrlHelper {
	
	/**
	 * This method returns a URL of a specific image string.
	 * 
	 * @param img  the image name
	 * @param size the size
	 * @return     the URL of this image
	 */
	public static String getImgUrl(String img, ImgSize size) {
		
		String imgUrl = "";
		// Replace white spaces -- the Java UrlEncoder replaces a '+' instead of
		// %20 as required here
		img =  img.replaceAll(" ", "%20");

		// 2. Iterate over all registerd urls
		for(ImgUrls url : Arrays.asList(ImgUrls.values())) {
			
			switch (size) {
			case MINI:
				imgUrl = url + "mini/" + img;
				break;
			case SMALL:
				imgUrl = url + "small/" + img;
				break;
			case BIG:
				imgUrl = url + "big/" + img;
				break;
			default:
				imgUrl = url + img;
				break;
			}
			
			try {
				HttpURLConnection.setFollowRedirects(false);
				HttpURLConnection conn = 
						(HttpURLConnection) new URL(imgUrl).openConnection();
				conn.setRequestMethod("HEAD");
				if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					break;
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			} // end try catch
		} // end for
				
		return imgUrl;
	}

}
