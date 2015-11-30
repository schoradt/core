package de.btu.openinfra.backend.db.daos.meta;

import java.util.Locale;
import java.util.UUID;

import de.btu.openinfra.backend.db.MappingResult;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraDao;
import de.btu.openinfra.backend.db.jpa.model.meta.SettingKeys;
import de.btu.openinfra.backend.db.pojos.meta.SettingKeysPojo;
import de.btu.openinfra.backend.exception.OpenInfraEntityException;
import de.btu.openinfra.backend.exception.OpenInfraExceptionTypes;

public class SettingKeysDao extends OpenInfraDao<SettingKeysPojo, SettingKeys> {

    /**
     * This is the required constructor which calls the super constructor and in
     * turn creates the corresponding entity manager.
     * @param currentProjectId The identifier of the current project.
     * @param schema           the required schema
     */
    public SettingKeysDao(UUID currentProjectId, OpenInfraSchemas schema) {
        super(null, OpenInfraSchemas.META_DATA, SettingKeys.class);
    }

    @Override
    public SettingKeysPojo mapToPojo(Locale locale, SettingKeys sk) {
        if (sk != null) {
            SettingKeysPojo pojo = new SettingKeysPojo(sk);
            pojo.setKey(sk.getKey());
            return pojo;
        } else {
            return null;
        }
    }

    @Override
    public MappingResult<SettingKeys> mapToModel(
            SettingKeysPojo pojo,
            SettingKeys sk) {
        if(pojo != null) {
            SettingKeys resultSettingKeys = null;
            try {
                resultSettingKeys = sk;
                if(resultSettingKeys == null) {
                    resultSettingKeys = new SettingKeys();
                    resultSettingKeys.setId(pojo.getUuid());
                }
                resultSettingKeys.setKey(pojo.getKey());
            } catch (NullPointerException npe) {
                throw new OpenInfraEntityException(
                        OpenInfraExceptionTypes.MISSING_DATA_IN_POJO);
            }
            return new MappingResult<SettingKeys>(
                    resultSettingKeys.getId(),
                    resultSettingKeys);
        }
        else {
            return null;
        }
    }

}
