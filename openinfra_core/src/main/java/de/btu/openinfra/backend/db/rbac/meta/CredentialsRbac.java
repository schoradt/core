package de.btu.openinfra.backend.db.rbac.meta;

import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.meta.CredentialsDao;
import de.btu.openinfra.backend.db.jpa.model.meta.Credentials;
import de.btu.openinfra.backend.db.pojos.meta.CredentialsPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraRbac;

public class CredentialsRbac extends
    OpenInfraRbac<CredentialsPojo, Credentials, CredentialsDao> {

    /**
     * Default constructor.
     */
    public CredentialsRbac() {
        this(null, null);
    }


    public CredentialsRbac(UUID currentProjectId, OpenInfraSchemas schema) {
        super(null, OpenInfraSchemas.META_DATA, CredentialsDao.class);
    }

}
