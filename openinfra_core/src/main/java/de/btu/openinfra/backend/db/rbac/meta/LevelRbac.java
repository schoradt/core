package de.btu.openinfra.backend.db.rbac.meta;

import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.meta.LevelDao;
import de.btu.openinfra.backend.db.jpa.model.meta.Level;
import de.btu.openinfra.backend.db.pojos.meta.LevelPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraRbac;

public class LevelRbac extends OpenInfraRbac<LevelPojo, Level, LevelDao> {

    /**
     * Default constructor.
     */
    public LevelRbac() {
        this(null, null);
    }

    public LevelRbac(
            UUID currentProjectId,
            OpenInfraSchemas schema) {
        super(null, OpenInfraSchemas.META_DATA, LevelDao.class);
    }
}
