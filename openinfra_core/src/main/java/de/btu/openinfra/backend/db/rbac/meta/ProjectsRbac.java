package de.btu.openinfra.backend.db.rbac.meta;

import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.meta.ProjectsDao;
import de.btu.openinfra.backend.db.jpa.model.meta.Projects;
import de.btu.openinfra.backend.db.pojos.meta.ProjectsPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraRbac;

public class ProjectsRbac extends
    OpenInfraRbac<ProjectsPojo, Projects, ProjectsDao> {

    /**
     * Default constructor.
     */
    public ProjectsRbac() {
        this(null, null);
    }

    public ProjectsRbac(
            UUID currentProjectId,
            OpenInfraSchemas schema) {
        super(null, OpenInfraSchemas.META_DATA, ProjectsDao.class);
    }
}
