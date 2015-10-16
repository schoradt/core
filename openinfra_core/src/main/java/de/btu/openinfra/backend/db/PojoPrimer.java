package de.btu.openinfra.backend.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<String, Class<? extends OpenInfraPojo>> pojoClasses;

    /**
     * Default non-argument constructor. The constructor initiates
     * the hash map.
     */
    public PojoPrimer() {
        initiatePojoClasses();
    }

    /**
     * Creates a primer object for the given pojo class.
     * @param pojoClass the pojo class
     * @return primer object if pojoClass exists in the pojo hash map otherwise
     * null
     */
    public static OpenInfraPojo primePojoStatically(String pojoClass) {
        return pojoPrimer.primePojo(pojoClass);
    }
    
    /**
     * Returns all primer class names.
     * @return a list of all primer class names
     */
    public static List<String> getPrimerNamesStatically() {
        return pojoPrimer.getPrimerNames();
    }

    /**
     * This methods initiates the hash map of OpenInfraPojo subclasses.
     */
    private void initiatePojoClasses() {
        // add meta pojos
        pojoClasses = new HashMap<String, Class<? extends OpenInfraPojo>>();
        pojoClasses.put("meta_credential", CredentialsPojo.class);
        pojoClasses.put("meta_databaseconnection", DatabaseConnectionPojo.class);
        pojoClasses.put("meta_database", DatabasesPojo.class);
        pojoClasses.put("meta_level", LevelPojo.class);
        pojoClasses.put("meta_logger", LoggerPojo.class);
        pojoClasses.put("meta_log", LogPojo.class);
        pojoClasses.put("meta_port", PortsPojo.class);
        pojoClasses.put("meta_project", ProjectsPojo.class);
        pojoClasses.put("meta_schema", SchemasPojo.class);
        pojoClasses.put("meta_server", ServersPojo.class);
        pojoClasses.put("meta_settingkey", SettingKeysPojo.class);
        pojoClasses.put("meta_setting", SettingsPojo.class);

        // add rbac pojos
        pojoClasses.put("rbac_passwordblacklist", PasswordBlacklistPojo.class);
        pojoClasses.put("rbac_permission", PermissionPojo.class);
        pojoClasses.put("rbac_rolepermission", RolePermissionPojo.class);
        pojoClasses.put("rbac_projectrelatedrole", ProjectRelatedRolePojo.class);
        pojoClasses.put("rbac_role", RolePojo.class);
        pojoClasses.put("rbac_subjectobject", SubjectObjectPojo.class);
        pojoClasses.put("rbac_subject", SubjectPojo.class);
        pojoClasses.put("rbac_subjectproject", SubjectProjectPojo.class);
        pojoClasses.put("rbac_subjectrole", SubjectRolePojo.class);

        // add normal pojos
        pojoClasses.put("attributetypeassociation", AttributeTypeAssociationPojo.class);
        pojoClasses.put("attributetypegroup", AttributeTypeGroupPojo.class);
        pojoClasses.put("attributetypegrouptoattributetype", AttributeTypeGroupToAttributeTypePojo.class);
        pojoClasses.put("attributetypegrouptotopiccharacteristic", AttributeTypeGroupToTopicCharacteristicPojo.class);
        pojoClasses.put("attributetype", AttributeTypePojo.class);
        pojoClasses.put("attributetypetoattributetypegroup", AttributeTypeToAttributeTypeGroupPojo.class);
        pojoClasses.put("attributevaluedomain", AttributeValueDomainPojo.class);
        pojoClasses.put("attributevaluegeom", AttributeValueGeomPojo.class);
        pojoClasses.put("attributevaluegeomz", AttributeValueGeomzPojo.class);
        pojoClasses.put("attributevalue", AttributeValuePojo.class);
        pojoClasses.put("attributevaluevalue", AttributeValueValuePojo.class);
        pojoClasses.put("charactercode", CharacterCodePojo.class);
        pojoClasses.put("countrycode", CountryCodePojo.class);
        pojoClasses.put("languagecode", LanguageCodePojo.class);
        pojoClasses.put("metadata", MetaDataPojo.class);
        pojoClasses.put("multiplicity", MultiplicityPojo.class);
        pojoClasses.put("project", ProjectPojo.class);
        pojoClasses.put("ptfreetext", PtFreeTextPojo.class);
        pojoClasses.put("ptlocale", PtLocalePojo.class);
        pojoClasses.put("relationshiptype", RelationshipTypePojo.class);
        pojoClasses.put("relationshiptypetotopiccharacteristic", RelationshipTypeToTopicCharacteristicPojo.class);
        pojoClasses.put("topiccharacteristic", TopicCharacteristicPojo.class);
        pojoClasses.put("topiccharacteristictoattributetypegroup", TopicCharacteristicToAttributeTypeGroupPojo.class);
        pojoClasses.put("topiccharacteristictorelationshiptype", TopicCharacteristicToRelationshipTypePojo.class);
        pojoClasses.put("topicinstanceassociation", TopicInstanceAssociationPojo.class);
        pojoClasses.put("topicinstance", TopicInstancePojo.class);
        pojoClasses.put("valuelistassociation", ValueListAssociationPojo.class);
        pojoClasses.put("valuelist", ValueListPojo.class);
        pojoClasses.put("valuelistvalueassociation", ValueListValueAssociationPojo.class);
        pojoClasses.put("valuelistvalue", ValueListValuePojo.class);
    }

    /**
     * Creates a primer object for the given pojo class.
     * @param pojoClass the pojo class
     * @return primer object if pojoClass exists in the pojo hash map otherwise
     * null
     */
    private OpenInfraPojo primePojo(String pojoClass) {
        OpenInfraPojo pojo = null;
        try {
            pojo = pojoClasses.get(pojoClass).newInstance();
            pojo.makePrimer();
        } catch (InstantiationException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return pojo;
    }
    
    /**
     * Returns all primer class names.
     * @return a list of all primer class names
     */
    private List<String> getPrimerNames() {
        return new ArrayList<String>(pojoClasses.keySet());
    }
}
