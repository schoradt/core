package de.btu.openinfra.backend.db.rbac.meta;

import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.meta.LoggerDao;
import de.btu.openinfra.backend.db.jpa.model.meta.Logger;
import de.btu.openinfra.backend.db.pojos.meta.LoggerPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraRbac;

public class LoggerRbac extends OpenInfraRbac<LoggerPojo, Logger, LoggerDao> {

    /**
     * Default constructor.
     */
    public LoggerRbac() {
        this(null, null);
    }

    public LoggerRbac(
            UUID currentProjectId,
            OpenInfraSchemas schema) {
        super(null, OpenInfraSchemas.META_DATA, LoggerDao.class);
    }
}
