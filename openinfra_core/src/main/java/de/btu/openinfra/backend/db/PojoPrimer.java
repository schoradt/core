package de.btu.openinfra.backend.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.jpa.model.PtLocale;
import de.btu.openinfra.backend.db.pojos.AttributeTypeAssociationPojo;
import de.btu.openinfra.backend.db.pojos.AttributeTypeGroupPojo;
import de.btu.openinfra.backend.db.pojos.AttributeTypeGroupToAttributeTypePojo;
import de.btu.openinfra.backend.db.pojos.AttributeTypeGroupToTopicCharacteristicPojo;
import de.btu.openinfra.backend.db.pojos.AttributeTypePojo;
import de.btu.openinfra.backend.db.pojos.AttributeTypeToAttributeTypeGroupPojo;
import de.btu.openinfra.backend.db.pojos.AttributeValueDomainPojo;
import de.btu.openinfra.backend.db.pojos.AttributeValueGeomPojo;
import de.btu.openinfra.backend.db.pojos.AttributeValueGeomzPojo;
import de.btu.openinfra.backend.db.pojos.AttributeValuePojo;
import de.btu.openinfra.backend.db.pojos.AttributeValueValuePojo;
import de.btu.openinfra.backend.db.pojos.CharacterCodePojo;
import de.btu.openinfra.backend.db.pojos.CountryCodePojo;
import de.btu.openinfra.backend.db.pojos.LanguageCodePojo;
import de.btu.openinfra.backend.db.pojos.MetaDataPojo;
import de.btu.openinfra.backend.db.pojos.MultiplicityPojo;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;
import de.btu.openinfra.backend.db.pojos.ProjectPojo;
import de.btu.openinfra.backend.db.pojos.PtFreeTextPojo;
import de.btu.openinfra.backend.db.pojos.PtLocalePojo;
import de.btu.openinfra.backend.db.pojos.RelationshipTypePojo;
import de.btu.openinfra.backend.db.pojos.RelationshipTypeToTopicCharacteristicPojo;
import de.btu.openinfra.backend.db.pojos.TopicCharacteristicPojo;
import de.btu.openinfra.backend.db.pojos.TopicCharacteristicToAttributeTypeGroupPojo;
import de.btu.openinfra.backend.db.pojos.TopicCharacteristicToRelationshipTypePojo;
import de.btu.openinfra.backend.db.pojos.TopicInstanceAssociationPojo;
import de.btu.openinfra.backend.db.pojos.TopicInstancePojo;
import de.btu.openinfra.backend.db.pojos.ValueListAssociationPojo;
import de.btu.openinfra.backend.db.pojos.ValueListPojo;
import de.btu.openinfra.backend.db.pojos.ValueListValueAssociationPojo;
import de.btu.openinfra.backend.db.pojos.ValueListValuePojo;
import de.btu.openinfra.backend.db.pojos.meta.CredentialsPojo;
import de.btu.openinfra.backend.db.pojos.meta.DatabaseConnectionPojo;
import de.btu.openinfra.backend.db.pojos.meta.DatabasesPojo;
import de.btu.openinfra.backend.db.pojos.meta.LevelPojo;
import de.btu.openinfra.backend.db.pojos.meta.LogPojo;
import de.btu.openinfra.backend.db.pojos.meta.LoggerPojo;
import de.btu.openinfra.backend.db.pojos.meta.PortsPojo;
import de.btu.openinfra.backend.db.pojos.meta.ProjectsPojo;
import de.btu.openinfra.backend.db.pojos.meta.SchemasPojo;
import de.btu.openinfra.backend.db.pojos.meta.ServersPojo;
import de.btu.openinfra.backend.db.pojos.meta.SettingKeysPojo;
import de.btu.openinfra.backend.db.pojos.meta.SettingsPojo;
import de.btu.openinfra.backend.db.pojos.rbac.PasswordBlacklistPojo;
import de.btu.openinfra.backend.db.pojos.rbac.PermissionPojo;
import de.btu.openinfra.backend.db.pojos.rbac.ProjectRelatedRolePojo;
import de.btu.openinfra.backend.db.pojos.rbac.RolePermissionPojo;
import de.btu.openinfra.backend.db.pojos.rbac.RolePojo;
import de.btu.openinfra.backend.db.pojos.rbac.SubjectObjectPojo;
import de.btu.openinfra.backend.db.pojos.rbac.SubjectPojo;
import de.btu.openinfra.backend.db.pojos.rbac.SubjectProjectPojo;
import de.btu.openinfra.backend.db.pojos.rbac.SubjectRolePojo;
import de.btu.openinfra.backend.exception.OpenInfraWebException;

/**
 * This class supplies methods to create primer objects and to request
 * primer classes using a singleton implementation.
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class PojoPrimer {

    /**
     * Pojo primer used to create primer objects and to request primer classes.
     */
    private static final PojoPrimer pojoPrimer = new PojoPrimer();

    /**
     * Hash map of OpenInfraPojo subclasses.
     */
    //private Map<String, Class<? extends OpenInfraPojo>> pojoClasses;
    private Map<OpenInfraSchemas,
        Map<String, Class<? extends OpenInfraPojo>>> pojoClasses;

    /**
     * Default non-argument constructor. The constructor initiates
     * the hash map.
     */
    public PojoPrimer() {
        initiatePojoClasses();
    }

    /**
     * Creates a primer object for the given schema and pojo class.
     * @param schema the schema
     * @param projectId the project id
     * @param locale A Java.util locale objects.
     * @param pojoClass the pojo class
     * @return primer object if pojoClass and the schema exist in the pojo
     * hash map otherwise throws OpenInfraWebException
     * @throws OpenInfraWebException
     */
    public static OpenInfraPojo primePojoStatically(
            OpenInfraSchemas schema,
            UUID projectId,
            Locale locale,
            String pojoClass) {
        switch(schema) {
            case SYSTEM:
            case PROJECTS:
                return pojoPrimer.primePojo(
                        schema,
                        new PtLocaleDao(projectId, schema).read(locale),
                        pojoClass);
            default:
                return pojoPrimer.primePojo(schema, null, pojoClass);
        }
        
    }
    
    /**
     * Returns all primer class names for the given schema.
     * @param schema the schema
     * @return a list of all primer class names if schema exists in the pojo
     * hash map otherwise throws OpenInfraWebException
     * @throws OpenInfraWebException
     */
    public static List<String> getPrimerNamesStatically(
            OpenInfraSchemas schema) {
        return pojoPrimer.getPrimerNames(schema);
    }

    /**
     * This methods initiates the hash map of OpenInfraPojo subclasses.
     */
    private void initiatePojoClasses() {
        pojoClasses = new HashMap<OpenInfraSchemas,
                Map<String, Class<? extends OpenInfraPojo>>>();
        
        // add meta pojos
        pojoClasses.put(
                OpenInfraSchemas.META_DATA,
                new HashMap<String, Class<? extends OpenInfraPojo>>());
        pojoClasses.get(OpenInfraSchemas.META_DATA).put(
                "credential",
                CredentialsPojo.class);
        pojoClasses.get(OpenInfraSchemas.META_DATA).put(
                "databaseconnection",
                DatabaseConnectionPojo.class);
        pojoClasses.get(OpenInfraSchemas.META_DATA).put(
                "database",
                DatabasesPojo.class);
        pojoClasses.get(OpenInfraSchemas.META_DATA).put(
                "level",
                LevelPojo.class);
        pojoClasses.get(OpenInfraSchemas.META_DATA).put(
                "logger",
                LoggerPojo.class);
        pojoClasses.get(OpenInfraSchemas.META_DATA).put(
                "log",
                LogPojo.class);
        pojoClasses.get(OpenInfraSchemas.META_DATA).put(
                "port",
                PortsPojo.class);
        pojoClasses.get(OpenInfraSchemas.META_DATA).put(
                "project",
                ProjectsPojo.class);
        pojoClasses.get(OpenInfraSchemas.META_DATA).put(
                "schema",
                SchemasPojo.class);
        pojoClasses.get(OpenInfraSchemas.META_DATA).put(
                "server",
                ServersPojo.class);
        pojoClasses.get(OpenInfraSchemas.META_DATA).put(
                "settingkey",
                SettingKeysPojo.class);
        pojoClasses.get(OpenInfraSchemas.META_DATA).put(
                "setting",
                SettingsPojo.class);

        // add rbac pojos
        pojoClasses.put(
                OpenInfraSchemas.RBAC,
                new HashMap<String, Class<? extends OpenInfraPojo>>());
        pojoClasses.get(OpenInfraSchemas.RBAC).put(
                "passwordblacklist",
                PasswordBlacklistPojo.class);
        pojoClasses.get(OpenInfraSchemas.RBAC).put(
                "permission",
                PermissionPojo.class);
        pojoClasses.get(OpenInfraSchemas.RBAC).put(
                "rolepermission",
                RolePermissionPojo.class);
        pojoClasses.get(OpenInfraSchemas.RBAC).put(
                "projectrelatedrole",
                ProjectRelatedRolePojo.class);
        pojoClasses.get(OpenInfraSchemas.RBAC).put(
                "role",
                RolePojo.class);
        pojoClasses.get(OpenInfraSchemas.RBAC).put(
                "subjectobject",
                SubjectObjectPojo.class);
        pojoClasses.get(OpenInfraSchemas.RBAC).put(
                "subject",
                SubjectPojo.class);
        pojoClasses.get(OpenInfraSchemas.RBAC).put(
                "subjectproject",
                SubjectProjectPojo.class);
        pojoClasses.get(OpenInfraSchemas.RBAC).put(
                "subjectrole",
                SubjectRolePojo.class);

        // add SYSTEM pojos
        pojoClasses.put(
                OpenInfraSchemas.SYSTEM,
                new HashMap<String, Class<? extends OpenInfraPojo>>());
        pojoClasses.get(OpenInfraSchemas.SYSTEM).put(
                "attributetypeassociation",
                AttributeTypeAssociationPojo.class);
        pojoClasses.get(OpenInfraSchemas.SYSTEM).put(
                "attributetypegroup",
                AttributeTypeGroupPojo.class);
        pojoClasses.get(OpenInfraSchemas.SYSTEM).put(
                "attributetypegrouptoattributetype",
                AttributeTypeGroupToAttributeTypePojo.class);
        pojoClasses.get(OpenInfraSchemas.SYSTEM).put(
                "attributetypegrouptotopiccharacteristic",
                AttributeTypeGroupToTopicCharacteristicPojo.class);
        pojoClasses.get(OpenInfraSchemas.SYSTEM).put(
                "attributetype",
                AttributeTypePojo.class);
        pojoClasses.get(OpenInfraSchemas.SYSTEM).put(
                "attributetypetoattributetypegroup",
                AttributeTypeToAttributeTypeGroupPojo.class);
        pojoClasses.get(OpenInfraSchemas.SYSTEM).put(
                "attributevaluedomain",
                AttributeValueDomainPojo.class);
        pojoClasses.get(OpenInfraSchemas.SYSTEM).put(
                "attributevaluegeomz",
                AttributeValueGeomzPojo.class);
        pojoClasses.get(OpenInfraSchemas.SYSTEM).put(
                "attributevaluevalue",
                AttributeValueValuePojo.class);
        pojoClasses.get(OpenInfraSchemas.SYSTEM).put(
                "charactercode",
                CharacterCodePojo.class);
        pojoClasses.get(OpenInfraSchemas.SYSTEM).put(
                "countrycode",
                CountryCodePojo.class);
        pojoClasses.get(OpenInfraSchemas.SYSTEM).put(
                "languagecode",
                LanguageCodePojo.class);
        pojoClasses.get(OpenInfraSchemas.SYSTEM).put(
                "metadata",
                MetaDataPojo.class);
        pojoClasses.get(OpenInfraSchemas.SYSTEM).put(
                "multiplicity",
                MultiplicityPojo.class);
        pojoClasses.get(OpenInfraSchemas.SYSTEM).put(
                "ptfreetext",
                PtFreeTextPojo.class);
        pojoClasses.get(OpenInfraSchemas.SYSTEM).put(
                "ptlocale",
                PtLocalePojo.class);
        pojoClasses.get(OpenInfraSchemas.SYSTEM).put(
                "relationshiptype",
                RelationshipTypePojo.class);
        pojoClasses.get(OpenInfraSchemas.SYSTEM).put(
                "relationshiptypetotopiccharacteristic",
                RelationshipTypeToTopicCharacteristicPojo.class);
        pojoClasses.get(OpenInfraSchemas.SYSTEM).put(
                "topiccharacteristic",
                TopicCharacteristicPojo.class);
        pojoClasses.get(OpenInfraSchemas.SYSTEM).put(
                "topiccharacteristictoattributetypegroup",
                TopicCharacteristicToAttributeTypeGroupPojo.class);
        pojoClasses.get(OpenInfraSchemas.SYSTEM).put(
                "topiccharacteristictorelationshiptype",
                TopicCharacteristicToRelationshipTypePojo.class);
        pojoClasses.get(OpenInfraSchemas.SYSTEM).put(
                "valuelistassociation",
                ValueListAssociationPojo.class);
        pojoClasses.get(OpenInfraSchemas.SYSTEM).put(
                "valuelist",
                ValueListPojo.class);
        pojoClasses.get(OpenInfraSchemas.SYSTEM).put(
                "valuelistvalueassociation",
                ValueListValueAssociationPojo.class);
        pojoClasses.get(OpenInfraSchemas.SYSTEM).put(
                "valuelistvalue",
                ValueListValuePojo.class);
        
        // add projects pojos
        pojoClasses.put(
                OpenInfraSchemas.PROJECTS,
                new HashMap<String, Class<? extends OpenInfraPojo>>());
        pojoClasses.get(OpenInfraSchemas.PROJECTS).putAll(
                pojoClasses.get(OpenInfraSchemas.SYSTEM));
        pojoClasses.get(OpenInfraSchemas.PROJECTS).put(
                "attributevalue",
                AttributeValuePojo.class);
        pojoClasses.get(OpenInfraSchemas.PROJECTS).put(
                "attributevaluegeom",
                AttributeValueGeomPojo.class);
        pojoClasses.get(OpenInfraSchemas.PROJECTS).put(
                "project",
                ProjectPojo.class);
        pojoClasses.get(OpenInfraSchemas.PROJECTS).put(
                "topicinstance",
                TopicInstancePojo.class);
        pojoClasses.get(OpenInfraSchemas.PROJECTS).put(
                "topicinstanceassociation",
                TopicInstanceAssociationPojo.class);
    }

    /**
     * Creates a primer object for the given schema and pojo class.
     * @param schema the schema
     * @param locale a PtLocale object
     * @param pojoClass the pojo class
     * @return primer object if pojoClass and the schema exist in the pojo
     * hash map otherwise throws OpenInfraWebException
     * @throws OpenInfraWebException
     */
    private OpenInfraPojo primePojo(
            OpenInfraSchemas schema,
            PtLocale locale,
            String pojoClass) {
        try {
            OpenInfraPojo pojo =
                    pojoClasses.get(schema).get(pojoClass).newInstance();
            pojo.makePrimer(locale);
            return pojo;
        } catch (InstantiationException | IllegalAccessException |
                NullPointerException e) {
            throw new OpenInfraWebException(e);
        }
    }
    
    /**
     * Returns all primer class names for the given schema.
     * @param schema the schema
     * @return a list of all primer class names if schema exists in the pojo
     * hash map otherwise throws OpenInfraWebException
     * @throws OpenInfraWebException
     */
    private List<String> getPrimerNames(OpenInfraSchemas schema) {
        try {
            return new ArrayList<String>(pojoClasses.get(schema).keySet());
        }
        catch(NullPointerException e) {
            throw new OpenInfraWebException(e);
        }
    }
}
