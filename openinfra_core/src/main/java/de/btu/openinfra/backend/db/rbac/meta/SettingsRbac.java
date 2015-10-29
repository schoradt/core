package de.btu.openinfra.backend.db.rbac.meta;

import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.meta.SettingsDao;
import de.btu.openinfra.backend.db.jpa.model.meta.Settings;
import de.btu.openinfra.backend.db.pojos.meta.SettingsPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraRbac;

public class SettingsRbac extends
    OpenInfraRbac<SettingsPojo, Settings, SettingsDao> {

    /**
     * Default constructor.
     */
    public SettingsRbac() {
        this(null, null);
    }

    public SettingsRbac(
            UUID currentProjectId,
            OpenInfraSchemas schema) {
        super(null, OpenInfraSchemas.META_DATA, SettingsDao.class);
    }
}
