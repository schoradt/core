package de.btu.openinfra.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is responsible for retrieving informations about the plugins in
 * OpenInfRA.
 * TODO this class is just a quick workaround to provide a method that check if
 *      a plugin exists as package
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class OpenInfraPluginRegister {

    /**
     * The path to the plugin package
     */
    private static final String PLUGIN_PACKAGE = "de.btu.openinfra.plugins";

    /**
     * This method retrieves all first level package names that can be found
     * under the plugin package path.
     *
     * @return A list of strings that represent the installed plugins
     */
    private static List<String> findPlugins() {
        List<String> result = new ArrayList<>();
        // retrieve all packages from the ClassLoader instance
        for(Package p : Package.getPackages()) {
            if (p.getName().startsWith(PLUGIN_PACKAGE)) {
                // retrieve the name of the package
                String pN = p.getName();
                // create a regex pattern to match the first package under the
                // plugin package path
                Pattern pattern = Pattern.compile(PLUGIN_PACKAGE + ".([a-z]*)");
                Matcher matcher = pattern.matcher(pN);
                if (matcher.find()) {
                    // add the name to the result list
                    result.add(matcher.group(1).toLowerCase());
                }
            }
        }
        return result;
    }

    /**
     * This method simply checks if the passed name is a plugin package.
     *
     * @param name The name of the plugin we should look for.
     * @return     True if the plugin was found, else false.
     */
    public static boolean exists(String name) {
        for (String plugin : findPlugins()) {
            if (plugin.equals(name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
