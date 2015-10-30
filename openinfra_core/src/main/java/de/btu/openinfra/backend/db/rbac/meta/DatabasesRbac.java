package de.btu.openinfra.backend.db.rbac.meta;

import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.meta.DatabasesDao;
import de.btu.openinfra.backend.db.jpa.model.meta.Databases;
import de.btu.openinfra.backend.db.pojos.meta.DatabasesPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraRbac;

public class DatabasesRbac extends
    OpenInfraRbac<DatabasesPojo, Databases, DatabasesDao> {

    /**
     * Default constructor.
     */
    public DatabasesRbac() {
        this(null, null);
    }

    public DatabasesRbac(
            UUID currentProjectId,
            OpenInfraSchemas schema) {
        super(null, OpenInfraSchemas.META_DATA, DatabasesDao.class);
    }

}
