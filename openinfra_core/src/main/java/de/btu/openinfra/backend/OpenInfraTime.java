package de.btu.openinfra.backend;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
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

    /**
     * This method formats a date into a string.
     *
     * @param date The date that should be parsed into a string.
     * @return     The string or null if the input date is null.
     */
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

	/**
	 * This method parses a string into a date. If the string could not be
	 * parsed null will be returned.
	 *
	 * @param string The string that should be parsed into a date.
	 * @return       The date or null if the string could not be parsed.
	 */
	public static Date parse(String string) {
	    if(string != null) {
	        /*
	         * The following date formats will be parsed. This is not part of
	         * the properties file because the date formats should be tested.
	         * The order of the dates is important.
	         */
            String[] dateFormats = {
                    "yyyy-MM-dd'T'HH:mm:ssZ", "yyyy-MM-dd HH:mm:ssZ"};

            for (String dateFormat : dateFormats) {
                try {
                    if (string.contains("+")) {
	                      string += "00";
	                  } else {
	                      string += "+0000";
	                  }
                    return new SimpleDateFormat(dateFormat).parse(string);
                } catch (ParseException ignore) { }
            }
        } else {
            return null;
        }
        return null;
	}

	/**
	 * This method returns the current timestamp.
	 *
	 * @return The current timestamp.
	 */
	public static Timestamp now() {
		return new Timestamp(
				Calendar.getInstance(
						TimeZone.getTimeZone("UTC")).getTime().getTime());
	}
}