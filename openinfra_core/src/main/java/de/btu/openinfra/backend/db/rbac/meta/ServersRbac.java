package de.btu.openinfra.backend.db.rbac.meta;

import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.meta.ServersDao;
import de.btu.openinfra.backend.db.jpa.model.meta.Servers;
import de.btu.openinfra.backend.db.pojos.meta.ServersPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraRbac;

public class ServersRbac extends
    OpenInfraRbac<ServersPojo, Servers, ServersDao> {

    /**
     * Default constructor.
     */
    public ServersRbac() {
        this(null, null);
    }

    public ServersRbac(
            UUID currentProjectId,
            OpenInfraSchemas schema) {
        super(null, OpenInfraSchemas.META_DATA, ServersDao.class);
    }
}
