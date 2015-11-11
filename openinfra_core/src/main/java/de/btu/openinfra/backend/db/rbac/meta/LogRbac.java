package de.btu.openinfra.backend.db.rbac.meta;

import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.meta.LogDao;
import de.btu.openinfra.backend.db.jpa.model.meta.Log;
import de.btu.openinfra.backend.db.pojos.meta.LogPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraRbac;

public class LogRbac extends OpenInfraRbac<LogPojo, Log, LogDao> {

    /**
     * Default constructor.
     */
    public LogRbac() {
        this(null, null);
    }

    public LogRbac(
            UUID currentProjectId,
            OpenInfraSchemas schema) {
        super(null, OpenInfraSchemas.META_DATA, LogDao.class);
    }
}
