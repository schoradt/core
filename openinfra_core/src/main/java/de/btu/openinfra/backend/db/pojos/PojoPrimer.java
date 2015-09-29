package de.btu.openinfra.backend.db.pojos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import de.btu.openinfra.backend.db.pojos.rbac.RolePermissionPojo;
import de.btu.openinfra.backend.db.pojos.rbac.RolePojo;
import de.btu.openinfra.backend.db.pojos.rbac.SubjectPojo;
import de.btu.openinfra.backend.db.pojos.rbac.SubjectRolePojo;

public class PojoPrimer {

    private static final PojoPrimer pojoPrimer = new PojoPrimer();

    private Map<String, Class<? extends OpenInfraPojo>> pojoClasses;

    public PojoPrimer() {
        initiatePojoClasses();
    }

    public static OpenInfraPojo primePojoStatically(String pojoName) {
        return pojoPrimer.primePojo(pojoName);
    }
    
    public static List<String> getPrimerNamesStatically() {
        return pojoPrimer.getPrimerNames();
    }

    private void initiatePojoClasses() {
        // add meta pojos
        pojoClasses = new HashMap<String, Class<? extends OpenInfraPojo>>();
        pojoClasses.put("credential", CredentialsPojo.class);
        pojoClasses.put("databaseconnection", DatabaseConnectionPojo.class);
        pojoClasses.put("database", DatabasesPojo.class);
        pojoClasses.put("level", LevelPojo.class);
        pojoClasses.put("logger", LoggerPojo.class);
        pojoClasses.put("log", LogPojo.class);
        pojoClasses.put("port", PortsPojo.class);
        pojoClasses.put("meta_project", ProjectsPojo.class);
        pojoClasses.put("schema", SchemasPojo.class);
        pojoClasses.put("server", ServersPojo.class);
        pojoClasses.put("settingkey", SettingKeysPojo.class);
        pojoClasses.put("setting", SettingsPojo.class);

        // add rbac pojos
        pojoClasses.put("passwordblacklist", PasswordBlacklistPojo.class);
        pojoClasses.put("permission", PermissionPojo.class);
        pojoClasses.put("rolepermission", RolePermissionPojo.class);
        pojoClasses.put("role", RolePojo.class);
        pojoClasses.put("subject", SubjectPojo.class);
        pojoClasses.put("subjectrole", SubjectRolePojo.class);

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

    private OpenInfraPojo primePojo(String pojoName) {
        OpenInfraPojo pojo = null;
        try {
            pojo = pojoClasses.get(pojoName).newInstance();
            pojo.makePrimer();
        } catch (InstantiationException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return pojo;
    }
    
    private List<String> getPrimerNames() {
        return new ArrayList<String>(pojoClasses.keySet());
    }
}
