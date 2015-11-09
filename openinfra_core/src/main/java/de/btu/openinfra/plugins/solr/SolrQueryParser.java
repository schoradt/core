package de.btu.openinfra.plugins.solr;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.math.NumberUtils;

import de.btu.openinfra.backend.exception.OpenInfraExceptionTypes;
import de.btu.openinfra.plugins.PluginProperties;
import de.btu.openinfra.plugins.solr.db.pojos.SolrComplexQueryPartPojo;
import de.btu.openinfra.plugins.solr.db.pojos.SolrSearchPojo;
import de.btu.openinfra.plugins.solr.exception.OpenInfraSolrException;

public class SolrQueryParser {

    /**
     * This method returns a search query in Solr syntax. It will parse the
     * requested SolrSearchPojo and replace the field names for correct matching
     * to the Solr index.
     *
     * @return A String that represents the search query in Solr syntax.
     */
    public String getSolrSyntaxQuery(SolrSearchPojo pojo) {
        try {
            if (pojo != null) {
                // Determine which query is set.
                if (pojo.getRawSolrQuery() != null && !pojo.getRawSolrQuery()
                        .equals("")) {
                    // start the parse process for a simple Solr query
                    return parseRawSolrQuery(pojo.getRawSolrQuery());
                } else if (pojo.getComplexQueryPart().size() > 0) {
                    // start the parse process for a complex Solr query
                    return parseComplexSolrQuery(pojo.getComplexQueryPart());
                }
            }
        } catch (Exception e) {
            // TODO replace with SolrExceptionType
            throw new OpenInfraSolrException(
                    OpenInfraExceptionTypes.INTERNAL_SERVER_EXCEPTION);
        }
        // TODO replace with SolrExceptionType
        throw new OpenInfraSolrException(
                OpenInfraExceptionTypes.INTERNAL_SERVER_EXCEPTION);
    }

    /**
     * This method parses a raw Solr query. It only converts the field
     * identifier.
     *
     * @param rawQuery The raw query in Solr syntax that should be converted.
     * @return         The converted Solr query.
     */
    private String parseRawSolrQuery(String rawQuery) {

        for (String attributeTypeName : extractFields(rawQuery)) {
            // convert the type name and replace it in the raw query
            rawQuery = rawQuery.replaceAll(
                    attributeTypeName,
                    SolrCharacterConverter.convert(attributeTypeName));
        }
        return rawQuery;
    }

    /**
     * This method parses a complex Solr query into a Solr syntax string.
     *
     * @param complexQueryList A list of complex queries that should be parsed.
     * @return                 The Solr syntax string.
     */
    private String parseComplexSolrQuery(
            List<SolrComplexQueryPartPojo> complexQueryList) {
        String query = "";
        boolean firstRun = true;
        for (SolrComplexQueryPartPojo part : complexQueryList) {
            query += " " + parsePart(part, firstRun);
            firstRun = false;
        }
        // remove leading and trailing whitespaces
        return query.trim();
    }

    /**
     * This method parses a SolrComplexQueryPartPojo to Solr syntax. There are
     * some special definitions for the POJO properties.
     *
     * @param part     The part of the complex query that should be parsed.
     * @param firstRun Defines if it is the first part of the query.
     * @return         The complex query part as string in Solr syntax.
     */
    private String parsePart(SolrComplexQueryPartPojo part, boolean firstRun) {
        String query = "";
        // add the attribute type
        if (part.getAttributeType() != null) {
            query += SolrCharacterConverter.convert(part.getAttributeType());
        }

        // add the delimiter between attribute type and attribute value
        if (!query.equals("")) {
            query += ":";
        }

        // add the mandatory identifier
        if (part.getMandatory() != null) {
            query = part.getMandatory().getString() + query;
        }

        // add the attribute value in different ways depending on the relational
        // operator
        if (part.getAttributeValue() != null) {
            if (part.getRelationalOperator() != null) {
                switch (part.getRelationalOperator()) {
                case GREATER_THAN:
                case SMALLER_THAN:
                    // only possible for numeric values
                    if (NumberUtils.isNumber(part.getAttributeValue())) {
                        query += String.format(
                                part.getRelationalOperator().getString(),
                                part.getAttributeValue());
                    } else {
                        // TODO replace with SolrExceptionType
                        throw new OpenInfraSolrException(
                                OpenInfraExceptionTypes
                                .INTERNAL_SERVER_EXCEPTION);
                    }
                    break;
                case BETWEEN:
                    query += String.format(
                            part.getRelationalOperator().getString(),
                            part.getAttributeValue(),
                            part.getTillAttributeValue());
                    break;
                default:
                    query += maskString(part.getAttributeValue());
                    break;
                }
            } else {
                query += part.getAttributeValue();
            }
        } else {
            // return an empty string if no attribute value is passed
            return "";
        }

        // only add the logic operator if it is not the first part we match
        if (!firstRun) {
            if (part.getLogicOperator() != null) {
                query = part.getLogicOperator().getString() + " " + query;
            }
        }

        // add fuzziness if required
        if (part.isFuzziness()) {
            query += "~" + PluginProperties.getProperty(
                    SolrPropertyKeys.SOLR_DEFAULT_FUZZY.getKey(), "Solr");
        }

        // add the relevance if it was passed
        if (part.getRelevance() != 0) {
            query += "^" + part.getRelevance();
        }

        return query;
    }

    /**
     * This method checks if a string contains whitespaces. If so a the string
     * will be surrounded by quotation marks.
     *
     * @param str The input string that should be checked.
     * @return    The modified string.
     */
    private String maskString(String str) {
        if (str.contains(" ")) {
            // we must be sure that the client does not added quotation marks
            // as well
            if (!str.startsWith(("\"")) && !str.endsWith(("\""))) {
                str = "\"" + str +"\"";
            };
        }
        return str;
    }

    /**
     * This method extracts the name of every field from a raw Solr query and
     * return them in a list.
     *
     * @param query The raw Solr query.
     * @return      A list of fields extracted from the raw Solr query.
     */
    List<String> extractFields(String query) {
        List<String> lst = new ArrayList<String>();

        // pattern to capture the attribute type name in front of a colon
        Pattern pattern = Pattern.compile(
                "(\"{1}\\w+(\\s*|(\\s*\\w+\\s*)*)\\w+\"{1}$|\\w+$)");

        Matcher matcher = null;
        String attributeTypeName = "";

        // split the query on every colon to separate the attribute types
        String[] parts = query.split(":");

        for (int i = 0; i < parts.length; i++) {
            // the last entry is no attribute type
            if (i < parts.length - 1) {
                // retrieve the attribute type via regex
                matcher = pattern.matcher(parts[i]);
                if (matcher.find()) {
                    // save the attribute type name
                    attributeTypeName = matcher.group(0);

                    // remove chars that are not part of the attribute type name
                    attributeTypeName = attributeTypeName.replaceAll("\\+", "");
                    attributeTypeName = attributeTypeName.replaceAll("\\-", "");
                    attributeTypeName = attributeTypeName.replaceAll("\\(", "");
                    attributeTypeName = attributeTypeName.replaceAll("\\)", "");

                    // add the attribute type name to the result list
                    lst.add(attributeTypeName);
                }

            }

        }
        return lst;
    }
}
