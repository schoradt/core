package de.btu.openinfra.backend.helper;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * This class is a simple implementation for retrieving the current time
 * as a timestamp.
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class OpenInfraTime {
	
	public static String format(Date date) {
		if(date != null) {
	        // TODO this must be placed somewhere in the properties
	        String format = "yyyy-MM-dd'T'HH:mm:ssZ";
	        DateFormat df = new SimpleDateFormat(format);
	        return df.format(date);
		} else {
			return null;
		}
	}
	
	public static Timestamp now() {
		return new Timestamp(
				Calendar.getInstance(
						TimeZone.getTimeZone("UTC")).getTime().getTime());
	}
}