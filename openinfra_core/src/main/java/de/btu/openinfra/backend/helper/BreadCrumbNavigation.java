package de.btu.openinfra.backend.helper;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.AttributeTypeDao;
import de.btu.openinfra.backend.db.daos.AttributeTypeGroupDao;
import de.btu.openinfra.backend.db.daos.ProjectDao;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.daos.TopicCharacteristicDao;
import de.btu.openinfra.backend.db.daos.TopicInstanceDao;
import de.btu.openinfra.backend.db.daos.ValueListDao;
import de.btu.openinfra.backend.db.pojos.ProjectPojo;
import de.btu.openinfra.backend.db.pojos.TopicCharacteristicPojo;

/**
 * This static class is responsible for generating a bread crumb navigation for
 * the front end.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class BreadCrumbNavigation {

    // the regex pattern to check for UUIDs
    private final static String uuidPattern =
            "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}";

    // this string marks a label as a label
    // TODO is there some way to make this available for jsp?
    public final static String labelMarker = ".#label#.";

    // standard label for missing translations
    private final static String noTranslation = "notranslation";

    // will determine the position when the URL parts get relevant
    // TODO not very dynamically!
    private final static int uselessCrumbsUntil = 3;


    /**
     * This functions creates the bread crumb navigation. It constructs the
     * crumbs by analyzing the passed URL. Because of the fact, that the
     * navigation history can not be drained from the URL, special treatments
     * are necessary. The returned crumbs will be in the order
     * [[URL], [CRUMB NAME], [URL], [CRUMB NAME], ...]
     *
     * TODO this method should not be here in this class!
     * TODO very hard coded method
     *
     * @param url
     * @param language
     * @param parameter
     * @return
     */
    public static Map<String, String> getBreadCrumb(
                                        String url,
                                        String language,
                                        Map<String, String[]> parameter) {

        // first split the URL at the slash
        String[] split = url.split("/");

        // get the locale for the passed language
        Locale locale = PtLocaleDao.forLanguageTag(language);

        // list of bread crumbs that will be returned (key = value, key = url)
        Map<String, String> breadCrumbs = new LinkedHashMap<String, String>();

        // placeholder for key and value of the hash map
        String key = "";
        String value = "";

        // will be used to count the runs through the URL parts
        int uselessCrumbCounter = 0;

        // placeholder for the project id
        UUID projectId = null;

        // container for URL parts that must be hard coded
        String urlAddon = "";

        // the URL parameter
        String param = getParameters(parameter);


        // only create bread crumbs if the URL has a specific length
        // TODO not very dynamic
        // TODO ignoring system is just a workaround
        if(split.length >= 5 && !split[3].equals("system")) {
            // set the project id, that will always appear at position four
        	try {
        		projectId = UUID.fromString(split[4]);
        	} catch(Exception ex) {
        		//TODO handle this
        	}


            // run through all parts of the URL
            for (int i = 0; i < split.length; i++) {
                // ignore useless URL parts
                if (uselessCrumbCounter >= uselessCrumbsUntil) {
                    // test for UUIDs via regex
                    if (split[i].matches(uuidPattern)) {
                        // check the last inserted key
                        switch (split[i-1]) {
                        case "projects":
                            // retrieve a list of all parent projects
                            List<ProjectPojo> projects =
                                ProjectDao.getParents(locale, url);

                            // iterate over all projects
                            for (int x = 0; x < projects.size(); x++) {
                                // get the name of the project
                                try {
                                    key = projects.get(x)
                                            .getNames()
                                            .getLocalizedStrings()
                                            .get(0)
                                            .getCharacterString();
                                } catch (IndexOutOfBoundsException e) {
                                    key = generateNoTranslationString();
                                }

                                // create URL right here
                                value = "projects/" + projects.get(x).getUuid()
                                        + "/subprojects";

                                // put the label and the URL into the bread crumbs
                                breadCrumbs.put(key, value);
                                key = "";
                            }
                            break;

                        case "topiccharacteristics":
                            // get the name of the topic characteristic
                            try {
                                key = new TopicCharacteristicDao(
                                        projectId,
                                        OpenInfraSchemas.PROJECTS)
                                            .read(locale,
                                                  UUID.fromString(split[i]))
                                                .getDescriptions()
                                                .getLocalizedStrings()
                                                .get(0)
                                                .getCharacterString();
                            } catch (IndexOutOfBoundsException e) {
                                key = generateNoTranslationString();
                            }

                            // create an addition for the URL
                            urlAddon = "/topiccharacteristics/" + split[i]
                                        + "/topicinstances";
                            break;

                        case "topicinstances":
                            TopicCharacteristicPojo tip = new TopicInstanceDao(
                                    projectId,
                                    OpenInfraSchemas.PROJECTS)
                                        .read(locale, UUID.fromString(split[i]))
                                        .getTopicCharacteristic();

                            try {
                                // get the name of the topic instance
                                key = tip.getDescriptions()
                                         .getLocalizedStrings()
                                         .get(0)
                                         .getCharacterString();
                            } catch (IndexOutOfBoundsException e) {
                                key = generateNoTranslationString();
                            }
                            // create an addition for the URL
                            urlAddon = "/topicinstances/" + split[i] + "/topic";

                            // at this point it is possible to write the correct
                            // URL for the label topicinstances, because here
                            // we can retrieve the topic characteristic id, that
                            // is not part of the URL
                            breadCrumbs.put(
                                    labelMarker + "topicinstances", split[3]
                                    + "/" + projectId + "/topiccharacteristics/"
                                    + tip.getUuid().toString()
                                    + "/topicinstances" + param);

                            break;

                        case "attributetypes":
                            // get the name of the attribute type
                            try {
                                key = new AttributeTypeDao(
                                        projectId,
                                        OpenInfraSchemas.PROJECTS)
                                            .read(locale,
                                                  UUID.fromString(split[i]))
                                            .getNames()
                                            .getLocalizedStrings()
                                            .get(0)
                                            .getCharacterString();
                            } catch (IndexOutOfBoundsException e) {
                                key = generateNoTranslationString();
                            }

                            // create an addition for the URL
                            urlAddon = "/attributetypes/" + split[i];
                            break;

                        case "attributetypegroups":
                            // get the name of the attribute type group
                            try {
                                key = new AttributeTypeGroupDao(
                                        projectId,
                                        OpenInfraSchemas.PROJECTS)
                                            .read(locale,
                                                  UUID.fromString(split[i]))
                                            .getNames()
                                            .getLocalizedStrings()
                                            .get(0)
                                            .getCharacterString();
                            } catch (IndexOutOfBoundsException e) {
                                key = generateNoTranslationString();
                            }

                            // create an addtion for the URL
                            urlAddon = "/attributetypegroups/" + split[i];
                            break;

                        case "valuelists":
                            // get the name of the value list
                            try {
                                key = new ValueListDao(
                                        projectId,
                                        OpenInfraSchemas.PROJECTS)
                                            .read(locale,
                                                  UUID.fromString(split[i]))
                                            .getNames()
                                            .getLocalizedStrings()
                                            .get(0)
                                            .getCharacterString();
                            } catch (IndexOutOfBoundsException e) {
                                key = generateNoTranslationString();
                            }

                            // create an addition for the URL
                            urlAddon = "/valuelists/" + split[i]
                                       + "/valuelistvalues";
                            break;
                        default:
                        }

                        // create the URL with the project string, UUID and the
                        // specific additions
                        value = split[3] + "/" + projectId + urlAddon + param;

                        // put the label and the URL into the bread crumbs
                        if (!key.equals("")) {
                            breadCrumbs.put(key, value);
                        }

                        // clear the URL addition for the next run
                        urlAddon = "";

                    // we got a label
                    } else {
                        // add a special string to validate it as a label
                        key = labelMarker + split[i];

                        // check if projects is the current crumb
                        if (split[i].equals(split[3])) {
                            // we do not need to write projects as part of the
                            // URL again
                            value = split[i];
                        } else {
                            switch (split[i]) {
                            case "topicinstances":
                                // if topicinstances apperars at the end of the
                                // URL it will be ignored as a label and URL
                                if (i == split.length-1) continue;

                                // it is not possible to retrieve the topic
                                // characteristic id at this point, so it
                                // doesn't matter what kind of URL will be set
                                // here
                                break;

                            case "valuelistvalues":
                                // if valuelistvalues appears at the end of the
                                // URL it will be ignored as a label and URL
                                // fall through
                            case "subprojects":
                                // if subprojects appears at the end of the URL
                                // it will be ignored as a label and URL
                                // fall through
                            case "topic":
                                // if topic appears at the end of the URL it
                                // will be ignored as a label and URL
                                // fall through
                            case "new":
                                // if new appears at the end of the URL it
                                // will be ignored as a label and URL
                                continue;

                            case "attributetypes":
                                // If attributetypes appears at the end of the
                                // URL and the URL split is larger than 6
                                // (matches only for attribute type groups) it
                                // will be ignored as a label and URL.
                                if (i == split.length-1 && split.length > 6) {
                                    continue;
                                }

                                // run through and add attributetypes to the URL

                            default:
                                // add only the relevant key word to the URL
                                urlAddon = "/" + split[i];
                                break;
                            }

                            // add the string "projects" and the project id from
                            // the URL as part of the new URL
                            value = split[3] + "/" + projectId + urlAddon + param;

                            // clear the URL addition for the next run
                            urlAddon = "";
                        }

                        // put the label and the URL into the bread crumbs
                        breadCrumbs.put(key, value);
                    }
                } else {
                    uselessCrumbCounter++;
                }
            }
        }

        // return the list with the bread crumbs
        return breadCrumbs;
    }


    /**
     * This function transform the parameters from the passed object into a
     * usable form for an URL. This complies to the form
     * ?param1=value&param2=value
     *
     * @param parameter the parameters of the URL as Map<String, String[]>
     * @return          the parameters as usable string for an URL
     */
    private static String getParameters(Map<String, String[]> parameter) {

        String param = "";

        // retrieve the parameters
        Iterator<Entry<String, String[]>> it = parameter.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String[]> pair = it.next();

            // ignore the filter parameter
            if (pair.getKey().equals("filter")) {
                continue;
            }

            // add the parameter
            param += pair.getKey() + "=";

            // cycle through parameter value array
            for ( String s : pair.getValue()) {
                // add the the parameter value
                param += s;
            }

            // add ampersand for connect different parameter
            param += "&";
        }

        // remove the trailing ampersand and add a question mark
        if (param.endsWith("&")) {
            param = "?" + param.substring(0, param.length()-1);
        }

        return param;
    }


    /**
     * This function creates a label for database objects that have no
     * translation. As the label must be unique, a random number will be added.
     *
     * @return   marked label
     */
    private static String generateNoTranslationString() {
        return Math.random() + labelMarker + noTranslation;
    }
}
