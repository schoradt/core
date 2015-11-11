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
        return mapToPojoStatically(sk);
    }

    /**
     * This method implements the method mapToPojo in a static way.
     *
     * @param sk     the model object
     * @return       the POJO object when the model object is not null else null
     */
    public static SettingKeysPojo mapToPojoStatically(SettingKeys sk) {
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
            mapToModelStatically(pojo, sk);
            return new MappingResult<SettingKeys>(sk.getId(), sk);
        }
        else {
            return null;
        }
    }

    /**
     * This method implements the method mapToModel in a static way.
     * @param pojo the POJO object
     * @param sk the pre initialized model object
     * @return return a corresponding JPA model object
     * @throws OpenInfraEntityException
     */
    public static SettingKeys mapToModelStatically(
            SettingKeysPojo pojo,
            SettingKeys sk) {
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
        return resultSettingKeys;
    }

}
