package de.btu.openinfra.backend.db.rbac.meta;

import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.meta.SchemasDao;
import de.btu.openinfra.backend.db.jpa.model.meta.Schemas;
import de.btu.openinfra.backend.db.pojos.meta.SchemasPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraRbac;

public class SchemasRbac extends
    OpenInfraRbac<SchemasPojo, Schemas, SchemasDao> {

    /**
     * Default constructor.
     */
    public SchemasRbac() {
        this(null, null);
    }

    public SchemasRbac(
            UUID currentProjectId,
            OpenInfraSchemas schema) {
        super(null, OpenInfraSchemas.META_DATA, SchemasDao.class);
    }
}
