package de.btu.openinfra.backend.db.rbac.meta;

import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.meta.DatabaseConnectionDao;
import de.btu.openinfra.backend.db.jpa.model.meta.DatabaseConnection;
import de.btu.openinfra.backend.db.pojos.meta.DatabaseConnectionPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraRbac;

public class DatabaseConnectionRbac extends OpenInfraRbac<
    DatabaseConnectionPojo, DatabaseConnection, DatabaseConnectionDao> {

    /**
     * Default constructor.
     */
    public DatabaseConnectionRbac() {
        this(null, null);
    }

    public DatabaseConnectionRbac(
            UUID currentProjectId,
            OpenInfraSchemas schema) {
        super(null, OpenInfraSchemas.META_DATA, DatabaseConnectionDao.class);
    }

}
