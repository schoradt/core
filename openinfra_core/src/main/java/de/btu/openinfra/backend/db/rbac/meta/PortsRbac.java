package de.btu.openinfra.backend.db.rbac.meta;

import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.meta.PortsDao;
import de.btu.openinfra.backend.db.jpa.model.meta.Ports;
import de.btu.openinfra.backend.db.pojos.meta.PortsPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraRbac;

public class PortsRbac extends OpenInfraRbac<PortsPojo, Ports, PortsDao> {

    /**
     * Default constructor.
     */
    public PortsRbac() {
        this(null, null);
    }

    public PortsRbac(
            UUID currentProjectId,
            OpenInfraSchemas schema) {
        super(null, OpenInfraSchemas.META_DATA, PortsDao.class);
    }
}
