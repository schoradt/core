package de.btu.openinfra.backend.db.rbac.meta;

import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.meta.SettingKeysDao;
import de.btu.openinfra.backend.db.jpa.model.meta.SettingKeys;
import de.btu.openinfra.backend.db.pojos.meta.SettingKeysPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraRbac;

public class SettingKeysRbac extends
    OpenInfraRbac<SettingKeysPojo, SettingKeys, SettingKeysDao> {

    /**
     * Default constructor.
     */
    public SettingKeysRbac() {
        this(null, null);
    }

    public SettingKeysRbac(
            UUID currentProjectId,
            OpenInfraSchemas schema) {
        super(null, OpenInfraSchemas.META_DATA, SettingKeysDao.class);
    }
}
