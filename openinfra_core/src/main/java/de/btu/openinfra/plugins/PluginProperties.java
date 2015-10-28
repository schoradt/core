package de.btu.openinfra.plugins;

import java.util.Properties;

import de.btu.openinfra.backend.OpenInfraPluginRegister;
import de.btu.openinfra.backend.exception.OpenInfraExceptionTypes;
import de.btu.openinfra.backend.exception.OpenInfraPluginException;

/**
 * OpenInfRA properties implemented as singleton.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class PluginProperties {

	/**
	 * The properties object.
	 */
	private static Properties prop;

	/**
	 * This variable defines the location of the OpenInfRA properties file.
	 */
	private static final String PROPERTIES_FILE =
			"de/btu/openinfra/plugins/%s/properties/%s.properties";

	/**
	 * This method retrieves the required information from the property file
	 * located in the resource folder.
	 *
	 * @param key    the key of the required value
	 * @param plugin the case sensitive name of the plugin
	 * @return       the value referenced by the key
	 */
	public static String getProperty(String key, String plugin) {
		if(prop == null) {
			prop = new Properties();
			// check if the plugin exists
			if (OpenInfraPluginRegister.exists(plugin)) {
        		try {
        		    // add the plugin name to the properties file path
        		    String url = String.format(
        		            PROPERTIES_FILE, plugin.toLowerCase(), plugin);
        		    // load the property file
        			prop.load(new PluginProperties().getClass().getClassLoader()
        					.getResourceAsStream(url));
        		} catch (Exception e) {
        			e.printStackTrace();
        		} // end try catch
			} else {
			    // If the requested plugin was not found in the ClassLoader
			    // instance throw an exception.
			    throw new OpenInfraPluginException(
			            OpenInfraExceptionTypes.PLUGIN_NOT_FOUND);
			}
		} // end if
		// return the requested key from the loaded property file
		return prop.getProperty(key);
	}
}
