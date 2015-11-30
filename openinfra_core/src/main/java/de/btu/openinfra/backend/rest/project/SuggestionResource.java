package de.btu.openinfra.backend.rest.project;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;
import de.btu.openinfra.backend.db.rbac.TopicCharacteristicRbac;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path(OpenInfraResponseBuilder.REST_URI_PROJECTS + "/topiccharacteristics/"
		+ "{topicCharacteristicId}/attributetypes/{attributeTypeId}/suggest")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
    MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET})
public class SuggestionResource {
    /**
     * This resource will return a list of attribute values for a given query
     * string. The list will only contain values that belong to a specified
     * topic characteristic and a specified attribute type. The localization of
     * the string will depend on the requested language.
     *
     * @param uriInfo
     * @param request
     * @param language              The language of the query string.
     * @param projectId             The project id ...
     * @param topicCharacteristicId The topic characteristic id the values
     *                              should be part of.
     * @param attributeTypeId       The attribute type id the values should be
     *                              part of. The attribute type must be used in
     *                              the topic characteristic.
     * @param qString               The query string that should be searched
     *                              for.
     * @return                      A list of strings that match to the
     *                              specified parameter or null if the locale
     *                              and / or the qString is not specified.
     */
    @GET
    public List<String> getSuggestion(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
            @PathParam("topicCharacteristicId") UUID topicCharacteristicId,
            @PathParam("attributeTypeId") UUID attributeTypeId,
            @QueryParam("q") String qString) {
        return new TopicCharacteristicRbac(
                projectId,
                OpenInfraSchemas.PROJECTS).getSuggestion(
                        uriInfo,
                        OpenInfraHttpMethod.valueOf(request.getMethod()),
                        PtLocaleDao.forLanguageTag(language),
                        topicCharacteristicId,
                        attributeTypeId,
                        qString);
    }
}
