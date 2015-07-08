package de.btu.openinfra.backend;

import java.util.Locale;
import java.util.Properties;

import de.btu.openinfra.backend.db.daos.OpenInfraSortOrder;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;

/**
 * OpenInfRA properties implemented as singleton.
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class OpenInfraProperties {
	
	/**
	 * The properties object.
	 */
	private static Properties prop;
	
	/**
	 * This variable defines the location of the OpenInfRA properties file.
	 */
	private static final String PROPERTIES_FILE = 
			"de/btu/openinfra/backend/properties/OpenInfRA.properties";
	
	/**
	 * This variable defines the default offset for lists. This should be used
	 * when the offset parameter is not specified in order to avoid infinite
	 * loading times. 
	 */
	public static final int DEFAULT_OFFSET = Integer.parseInt(
            getProperty(OpenInfraPropertyKeys.DEFAULT_OFFSET.toString()));
	
	/**
	 * This variable defines the default size for lists. This should be used
	 * when the size parameter is not specified in order to avoid infinite
	 * loading times.  
	 */
	public static final int DEFAULT_SIZE = Integer.parseInt(
           getProperty(OpenInfraPropertyKeys.DEFAULT_SIZE.toString()));
	
	/**
     * This variable defines the max size for lists. This should be used
     * when the resource must be restricted due to heavy load to the server.  
     */
    public static final int MAX_SIZE = Integer.parseInt(
            getProperty(OpenInfraPropertyKeys.MAX_SIZE.toString()));
    
    /**
     * This variable defines the default language.
     */
    public static final Locale DEFAULT_LANGUAGE = PtLocaleDao.forLanguageTag(
    		getProperty(OpenInfraPropertyKeys.DEFAULT_LANGUAGE.toString()));
    
    /**
     * This variable defines the default sort order.
     */
    public static final OpenInfraSortOrder DEFAULT_ORDER = 
    		OpenInfraSortOrder.valueOf(getProperty(
    				OpenInfraPropertyKeys.DEFAULT_ORDER.toString()));
	
	/**
	 * This method retrieves the required information from the property file 
	 * located in the resource folder.
	 * 
	 * @param key the key of the required value
	 * @return    the value referenced by the key
	 */
	public static String getProperty(String key) {
		if(prop == null) {
			prop = new Properties();
			try {
				prop.load(new OpenInfraProperties().getClass().getClassLoader()
						.getResourceAsStream(PROPERTIES_FILE));
			} catch (Exception e) {
				e.printStackTrace();
			} // end try catch
		} // end if
		return prop.getProperty(key);
	}

}
