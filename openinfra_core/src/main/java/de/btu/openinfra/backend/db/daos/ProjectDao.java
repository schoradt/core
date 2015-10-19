package de.btu.openinfra.backend.db.daos;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import jersey.repackaged.com.google.common.collect.Lists;
import de.btu.openinfra.backend.OpenInfraProperties;
import de.btu.openinfra.backend.OpenInfraPropertyKeys;
import de.btu.openinfra.backend.db.MappingResult;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.meta.CredentialsDao;
import de.btu.openinfra.backend.db.daos.meta.DatabaseConnectionDao;
import de.btu.openinfra.backend.db.daos.meta.DatabasesDao;
import de.btu.openinfra.backend.db.daos.meta.PortsDao;
import de.btu.openinfra.backend.db.daos.meta.ProjectsDao;
import de.btu.openinfra.backend.db.daos.meta.SchemasDao;
import de.btu.openinfra.backend.db.daos.meta.ServersDao;
import de.btu.openinfra.backend.db.jpa.model.Project;
import de.btu.openinfra.backend.db.jpa.model.meta.Credentials;
import de.btu.openinfra.backend.db.jpa.model.meta.DatabaseConnection;
import de.btu.openinfra.backend.db.jpa.model.meta.Databases;
import de.btu.openinfra.backend.db.jpa.model.meta.Ports;
import de.btu.openinfra.backend.db.jpa.model.meta.Projects;
import de.btu.openinfra.backend.db.jpa.model.meta.Servers;
import de.btu.openinfra.backend.db.pojos.LocalizedString;
import de.btu.openinfra.backend.db.pojos.ProjectPojo;
import de.btu.openinfra.backend.db.pojos.PtFreeTextPojo;
import de.btu.openinfra.backend.db.pojos.meta.CredentialsPojo;
import de.btu.openinfra.backend.db.pojos.meta.DatabaseConnectionPojo;
import de.btu.openinfra.backend.db.pojos.meta.DatabasesPojo;
import de.btu.openinfra.backend.db.pojos.meta.PortsPojo;
import de.btu.openinfra.backend.db.pojos.meta.ProjectsPojo;
import de.btu.openinfra.backend.db.pojos.meta.SchemasPojo;
import de.btu.openinfra.backend.db.pojos.meta.ServersPojo;
import de.btu.openinfra.backend.exception.OpenInfraDatabaseException;
import de.btu.openinfra.backend.exception.OpenInfraEntityException;
import de.btu.openinfra.backend.exception.OpenInfraExceptionTypes;
import de.btu.openinfra.backend.exception.OpenInfraWebException;

/**
 * This class represents the Project and is used to access the underlying layer
 * generated by JPA.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class ProjectDao extends OpenInfraDao<ProjectPojo, Project> {

	/**
	 * This is the required constructor which calls the super constructor and in
	 * turn creates the corresponding entity manager.
	 *
	 * @param currentProjectId the current project id (this should be null when
	 *                         the system schema is selected)
	 * @param schema           the required schema
	 */
	public ProjectDao(UUID currentProjectId, OpenInfraSchemas schema) {
		super(currentProjectId, schema, Project.class);
	}

	/**
	 * This method delivers a list of sub projects specified by the current
	 * project id {@see OpenInfraDao} in only one hierarchy level.
	 *
     * @param locales    A list of Java.util locale objects. All locals which
     *                   this list contains will be returned. As defined by
     *         <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html">
     *                   rfc2616</a>, the asterisk is used to retrieve all
     *                   available languages.
	 * @param offset     the number where to start
	 * @param size       the size of items to provide
	 * @return           a list of sub projects referring to the specified
	 *                   project in only one hierarchy level
	 */
	public List<ProjectPojo> readSubProjects(
			Locale locale,
			int offset,
			int size) {
		Project p = em.find(Project.class, currentProjectId);
		List<ProjectPojo> pojos = new LinkedList<ProjectPojo>();
		for(Project project : p.getProjects()) {
			pojos.add(mapToPojo(locale, project));
		} // end for
		return pojos;
	}

	/**
	 * This method returns the count of sub projects.
	 *
	 * @return count of sub projects
	 */
	public long getSubProjectsCount() {
		return readSubProjects(null, 0, Integer.MAX_VALUE).size();
	}

	@Override
	public long getCount() {
		return readMainProjects(null).size();
	}

	/**
	 * This method delivers a list of main projects retrieved from metadata
	 * database. This method also retrieves the name and description of
	 * all main projects specified by the locale parameter.
	 *
	 * @param locale the required language
	 * @return       a list of main projects
	 */
	public List<ProjectPojo> readMainProjects(Locale locale) {
		// 1. We need to deliver each main Project from meta data database
		List<ProjectsPojo> metaProjects = new ProjectsDao(
				OpenInfraSchemas.META_DATA).read(
						locale,
						0,
						Integer.MAX_VALUE);
		Iterator<ProjectsPojo> it = metaProjects.iterator();
		// 2. Only keep main projects in the list
		while (it.hasNext()) {
			if(it.next().getIsSubproject()) {
				it.remove();
			} // end if
		} // end while
		// 3. Create a list of main projects and add a corresponding main
		//    project to the list found in the metadata database
		List<ProjectPojo> mainProjects = new LinkedList<ProjectPojo>();
		for(ProjectsPojo item : metaProjects) {
			ProjectPojo pp = new ProjectDao(
					 item.getProjectId(),
					 OpenInfraSchemas.PROJECTS).read(
					         locale, item.getProjectId());
			if(pp != null) {
				mainProjects.add(pp);
			}
		} // end for
		return mainProjects;
	}

	/**
	 * This method returns the count of main projects.
	 *
	 * @return the count of main projects
	 */
	public long getMainProjectsCount() {
		return readMainProjects(null).size();
	}

	@Override
	public ProjectPojo mapToPojo(Locale locale, Project p) {
		return mapToPojoStatically(locale, p,
		        new MetaDataDao(currentProjectId, schema));
	}

	/**
     * This method implements the method mapToPojo in a static way.
     *
     * @param locale the requested language as Java.util locale
     * @param p      the model object
     * @param mdDao  the meta data DAO
     * @return       the POJO object when the model object is not null else null
     */
	public static ProjectPojo mapToPojoStatically(Locale locale, Project p,
	        MetaDataDao mdDao) {
		if(p != null) {
			ProjectPojo pojo = new ProjectPojo(p, mdDao);

			Project parent = p.getProject();

			pojo.setNames(PtFreeTextDao.mapToPojoStatically(
					locale,
					p.getPtFreeText2()));
			pojo.setDescriptions(PtFreeTextDao.mapToPojoStatically(
					locale,
					p.getPtFreeText1()));
			if(parent != null) {
				pojo.setSubprojectOf(parent.getId());
			} // end if

			return pojo;
		} else {
			return null;
		} // end if else
	}

	@Override
	public MappingResult<Project> mapToModel(ProjectPojo pojo, Project p) {

	    PtFreeTextDao ptfDao =
                new PtFreeTextDao(currentProjectId, schema);

	    try {
	        // set the name
            p.setPtFreeText2(ptfDao.getPtFreeTextModel(pojo.getNames()));
	    } catch (NullPointerException npe) {
            throw new OpenInfraEntityException(
                    OpenInfraExceptionTypes.MISSING_NAME_IN_POJO);
        }

        // set the description (optional)
        if (pojo.getDescriptions() != null) {
            p.setPtFreeText1(
                    ptfDao.getPtFreeTextModel(pojo.getDescriptions()));
        } else {
            // reset the description
            p.setPtFreeText1(null);
        }

		// set the sub project (optional)
		if(pojo.getSubprojectOf() != null) {
			p.setProject(em.find(Project.class, pojo.getSubprojectOf()));
		} else {
		    // reset the subProject id
		    p.setProject(null);
		}

		return new MappingResult<Project>(p.getId(), p);
	}

	/**
	 * This method delivers a list of parents in reverse order.
	 *
	 * @param locale the required locale
	 * @return       a list of parents in reverse order
	 */
	public List<ProjectPojo> readParents(Locale locale) {
		Project self = em.find(Project.class, currentProjectId);
		List<ProjectPojo> parents = new LinkedList<ProjectPojo>();
		MetaDataDao mdDao = new MetaDataDao(currentProjectId, schema);
		parents.add(mapToPojoStatically(locale, self, mdDao));

		while(self.getProject() != null) {
			self = self.getProject();
			parents.add(mapToPojoStatically(locale, self, mdDao));
		} // end while
		return Lists.reverse(parents);
	}

	/**
	 * This is a special method which returns a hierarchical list of parent
	 * projects plus the current project.
	 *
	 * @param locale the locale of the request (shouldn't be null)
	 * @param url    the current URL
	 * @return       list of parents or a list containing a null value
	 */
	public static List<ProjectPojo> getParents(Locale locale, String url) {
		String currentProject = getCurrentProject(url);
		if(currentProject != null) {
			return new ProjectDao(
					UUID.fromString(currentProject),
					OpenInfraSchemas.PROJECTS).readParents(locale);
		} else {
			return new LinkedList<ProjectPojo>();
		} // end if else
	}

	/**
	 * This is a static method which returns the current project id by the
	 * given URL string. It'll return an empty String object when the current
	 * URL doesn't contain a specific project id.
	 *
	 * @param url the URL to parse
	 * @return    the current project id or null when not specified
	 */
	public static String getCurrentProject(String url) {
		String[] split = url.split("/");
		if(split.length >= 6 && split[5] != null) {
			return split[5];
		} else {
			return "";
		} // end if else
	}

	/**
	 * This method decides if the project that should be created is a new sub
	 * project or a new main project. If it is a new main project it is
	 * necessary to create a new database schema and write some data into the
	 * meta data schema.
	 *
	 * @param project the project pojo
	 * @return        the UUID of the new created project or NULL if something
	 *                went wrong
	 */
	public UUID createProject(ProjectPojo pojo) {

		// the UUID that will be returned
	    UUID id = null;

	    // generate a UUID for the new project
        UUID newProjectId = UUID.randomUUID();

	    // determine if we want to create a sub or a main project
	    if (pojo.getSubprojectOf() != null) {
	        ProjectDao pDao = new ProjectDao(pojo.getSubprojectOf(),
	                OpenInfraSchemas.PROJECTS);
	        id = pDao.createOrUpdate(pojo, null);
	        if (id != null) {
	            try {
	                // insert the necessary data into the meta data schema
	                writeMetaData(id, pojo.getSubprojectOf());
                } catch (OpenInfraDatabaseException e) {
                    // if it fails to generate the meta data the project must be
                    // removed
                    pDao.deleteProject();
                }

	        }
	    } else {
	        try {
    	        // create the database schema
                createSchema(pojo, newProjectId);

                // insert the necessary data into the meta data schema
                writeMetaData(newProjectId, null);

                // insert the basic project data into the project table in the
                // new project schema
                writeBasicProjectData(pojo, newProjectId);

                // copy the initial data from the system schema into the new
                // project schema
                mergeSystemData(newProjectId);

                // save the new id for returning it to the client
                id = newProjectId;
	        } catch (OpenInfraDatabaseException e) {
	            // execute different roll backs depending on the thrown error
	            switch (e.getType()) {
	            case MERGE_SYSTEM:
	                /* run through */
	            case INSERT_BASIC_PROJECT_DATA:
	                /* run through */
                case INSERT_META_DATA:
                    deleteMetaData(newProjectId);
                    /* run through */
                case RENAME_SCHEMA:
                    deleteSchema(newProjectId);
                    break;
                default:
                    /* no roll back necessary for e.g. CREATE_SCHEMA */
                    break;
                }
	            // TODO: throw error to resource
	            System.out.println("THROW ERROR TO REST API: " + e.toString());
	            return null;
            }
	    }
	    return id;
	}

	/**
	 * This method decides if the project that should be deleted is a sub
     * project or a main project. If it is a main project it is necessary to
     * delete the database schema and remove the project entry in the meta data
     * schema.
     *
	 * @return true if the project was deleted otherwise false
	 */
	public boolean deleteProject() {

	    boolean result = false;

	    try {
	        Project p = em.find(Project.class, currentProjectId);

	        // first delete the meta data
            deleteMetaData(currentProjectId);

	        // determine if we want to delete a sub or a main project
	        if (p.getProject() != null) {
	            // delete a sub project
	            result = delete(currentProjectId);
	        } else {
                // second remove the schema
                deleteSchema(currentProjectId);
                result = true;
	        }
	    } catch (Exception e) {
            return false;
	    }
	    return result;
	}

    /**
     * This method creates a ProjectPojo shell that contains informations about
     * the name, the description and the parent project.
     *
     * @param locale the locale the informations should be saved at
     * @return       the ProjectPojo
     */
    public ProjectPojo newSubProject(Locale locale) {
        // create the return pojo
        ProjectPojo pojo = new ProjectPojo();

        PtLocaleDao ptl = new PtLocaleDao(currentProjectId, schema);
        List<LocalizedString> lcs = new LinkedList<LocalizedString>();
        LocalizedString ls = new LocalizedString();

        // set an empty character string
        ls.setCharacterString("");

        // set the locale of the character string
        ls.setLocale(PtLocaleDao.mapToPojoStatically(
                locale,
                ptl.read(locale)));
        lcs.add(ls);

        // add the localized string for the name
        pojo.setNames(new PtFreeTextPojo(lcs, null));

        // add the localized string for the description
        pojo.setDescriptions(new PtFreeTextPojo(lcs, null));

        // set the id of the main project to the current project
        pojo.setSubprojectOf(currentProjectId);

        return pojo;
    }

    /**
     * This method creates a new project schema with the given project id. The
     * schema creation will be handled by JPA using a special persistence
     * context. After schema creation a stored procedure is called that will
     * rename the schema.
     *
     * @param pojo
     * @param newProjectId
     * @throws OpenInfraDatabaseException if the creation or renaming of the
     *         schema failed.
     * @return
     */
    private void createSchema(ProjectPojo pojo, UUID newProjectId) {
        try {
            // set the default database connection properties
            Map<String, String> properties =
                    OpenInfraProperties.getConnectionProperties();

            // create the new project schema with trigger and initial project
            // data
            Persistence.generateSchema("openinfra_schema_creation", properties);

            // rename the project schema
            if (!em.createStoredProcedureQuery(
                "rename_project_schema", Boolean.class)
                    .registerStoredProcedureParameter(
                        "name",
                        String.class,
                        ParameterMode.IN)
                    .registerStoredProcedureParameter(
                        "uuid",
                        UUID.class,
                        ParameterMode.IN)
                    .setParameter(
                        "name",
                        pojo.getNames().getLocalizedStrings().get(0)
                            .getCharacterString())
                    .setParameter("uuid", newProjectId)
                                .execute()) {
                throw new OpenInfraDatabaseException(
                        OpenInfraExceptionTypes.RENAME_SCHEMA);
            }
        } catch (PersistenceException pe) {
            throw new OpenInfraDatabaseException(
                    OpenInfraExceptionTypes.CREATE_SCHEMA);
        }
    }

    /**
     * This method delete a project schema for the given project id.
     *
     * @param projectId
     */
    private void deleteSchema(UUID projectId) {

        try {
            // delete the project schema
            if(em.createStoredProcedureQuery(
                    "delete_project_schema", Boolean.class)
                    .registerStoredProcedureParameter(
                            "project_id",
                            UUID.class,
                            ParameterMode.IN)
                    .setParameter("project_id", projectId)
                    .execute()) {
            } else {
                // TODO: throw something if the delete process fails
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method writes all necessary meta data for the new project into the
     * meta data schema.
     *
     * @param newProjectId the project id for the meta data
     * @param mainProjectId the id of the main project
     * @throws SchemaCreationException
     * @return
     */
    private void writeMetaData(UUID newProjectId, UUID mainProjectId)
            throws OpenInfraDatabaseException {
        try {

            // the id of the database connection
            UUID dbCId = null;
            // create the DAO for the database connection
            DatabaseConnectionDao dbCDao =
                    new DatabaseConnectionDao(OpenInfraSchemas.META_DATA);

            boolean isSubProject = false;

            if (mainProjectId != null) {
                // retrieve the database connection from the main project
                // find the id of the projects table in the meta data schema by
                // the given project id
                Projects pMeta = em.createNamedQuery(
                      "Projects.findByProject",
                      Projects.class)
                      .setParameter("value", mainProjectId)
                      .getSingleResult();

                // save the database connection Id for the next steps
                dbCId = pMeta.getDatabaseConnection().getId();
                isSubProject = true;
            } else {
                // create a new database connection for main projects
                // create a POJO for the schema in the meta data schema
                SchemasPojo metaSchemasPojo = new SchemasPojo();
                // set all necessary data for the schema
                metaSchemasPojo.setSchema("project_" + newProjectId);
                // create the DAO for the schema
                SchemasDao schemaDao = new SchemasDao(
                        OpenInfraSchemas.META_DATA);
                // insert the data
                // TODO: createOrUpdate can throw an exception!
                UUID schemaId = schemaDao.createOrUpdate(metaSchemasPojo, null);

                // create a POJO for the database connection in the meta data
                // schema
                DatabaseConnectionPojo dbCPojo = new DatabaseConnectionPojo();

                // set all necessary data for the database connection
                dbCPojo.setSchema(schemaDao.read(null, schemaId));
                // create necessary DAOs for the credentials, ports, databases and
                // servers
                CredentialsDao credentialsDao =
                        new CredentialsDao(OpenInfraSchemas.META_DATA);
                PortsDao portsDao = new PortsDao(OpenInfraSchemas.META_DATA);
                DatabasesDao dbDao = new DatabasesDao(
                        OpenInfraSchemas.META_DATA);
                ServersDao serversDao = new ServersDao(
                        OpenInfraSchemas.META_DATA);

                // Use the default values from the properties file for the new
                // connection.
                // TODO: Find a better way?

                // retrieve the default credentials
                CredentialsPojo credentialsPojo = new CredentialsPojo();
                credentialsPojo.setPassword(OpenInfraProperties.getProperty(
                        OpenInfraPropertyKeys.PASSWORD.toString()));
                credentialsPojo.setUsername(OpenInfraProperties.getProperty(
                        OpenInfraPropertyKeys.USER.toString()));
                // check if credentials for the default user and password exists
                // and save the id into the credentials POJO
                try {
                    credentialsPojo.setUuid(
                        credentialsDao.mapToPojo(
                                null,
                                em.createNamedQuery(
                                        "Credentials.findByUsernameAndPassword",
                                        Credentials.class)
                                  .setParameter(
                                          "username",
                                          OpenInfraProperties.getProperty(
                                                  OpenInfraPropertyKeys.USER
                                                  .toString()))
                                  .setParameter(
                                          "password",
                                          OpenInfraProperties.getProperty(
                                                  OpenInfraPropertyKeys.PASSWORD
                                                  .toString()))
                                  .getSingleResult()).getUuid());
                } catch(NoResultException nre){
                    // their is no entry in the database, create a new one
                    credentialsPojo.setUuid(
                            credentialsDao.createOrUpdate(
                                    credentialsPojo, null));
                }

                // retrieve the default port
                PortsPojo portsPojo = new PortsPojo();
                portsPojo.setPort(new Integer(OpenInfraProperties.getProperty(
                        OpenInfraPropertyKeys.PORT.toString())));
                // check if ports for the default port exists and save the id
                // into the port POJO
                try {
                    portsPojo.setUuid(
                        portsDao.mapToPojo(
                                null,
                                em.createNamedQuery(
                                        "Ports.findByPort",
                                        Ports.class)
                                  .setParameter(
                                          "port",
                                          Integer.parseInt(
                                                  OpenInfraProperties
                                                  .getProperty(
                                                          OpenInfraPropertyKeys
                                                          .PORT
                                                          .toString())))
                                  .getSingleResult()).getUuid());
                } catch (NoResultException nre) {
                    // their is no entry in the database, create a new one
                    portsPojo.setUuid(
                            portsDao.createOrUpdate(portsPojo, null));
                }

                // retrieve the default database
                DatabasesPojo databasesPojo = new DatabasesPojo();
                databasesPojo.setDatabase(OpenInfraProperties.getProperty(
                        OpenInfraPropertyKeys.DB_NAME.toString()));
                // check if databases for the default database exists and save
                // the id into the database POJO
                try {
                    databasesPojo.setUuid(
                        dbDao.mapToPojo(
                                null,
                                em.createNamedQuery(
                                        "Databases.findByDatabase",
                                        Databases.class)
                                  .setParameter(
                                          "database",
                                          OpenInfraProperties.getProperty(
                                                  OpenInfraPropertyKeys.DB_NAME
                                                  .toString()))
                                  .getSingleResult()).getUuid());
                } catch (NoResultException nre) {
                    // their is no entry in the database, create a new one
                    databasesPojo.setUuid(
                            dbDao.createOrUpdate(databasesPojo, null));
                }

                // retrieve the default server
                ServersPojo serversPojo = new ServersPojo();
                serversPojo.setServer(OpenInfraProperties.getProperty(
                        OpenInfraPropertyKeys.SERVER.toString()));
                // check if servers for the default server exists and save the
                // id into the server POJO
                try {
                    serversPojo.setUuid(
                        serversDao.mapToPojo(
                                null,
                                em.createNamedQuery(
                                        "Servers.findByServer",
                                        Servers.class)
                                  .setParameter(
                                          "server",
                                          OpenInfraProperties.getProperty(
                                                  OpenInfraPropertyKeys.SERVER
                                                  .toString()))
                                  .getSingleResult()).getUuid());
                } catch (NoResultException nre) {
                    // their is no entry in the database, create a new one
                    serversPojo.setUuid(
                            serversDao.createOrUpdate(serversPojo, null));
                }

                // add the POJOs to the database connection POJO
                dbCPojo.setCredentials(credentialsPojo);
                dbCPojo.setPort(portsPojo);
                dbCPojo.setDatabase(databasesPojo);
                dbCPojo.setServer(serversPojo);

                // insert the database connection information
                dbCId = dbCDao.createOrUpdate(dbCPojo, null);
                if (dbCId == null) {
                    throw new OpenInfraDatabaseException(
                            OpenInfraExceptionTypes.INSERT_META_DATA);
                }
            }

            // create a POJO for the project in the meta data schema
            ProjectsPojo metaProjectsPojo = new ProjectsPojo();

            // set the project id
            metaProjectsPojo.setProjectId(newProjectId);
            // set the subproject flag to false
            metaProjectsPojo.setIsSubproject(isSubProject);
            // set the database connection information
            metaProjectsPojo.setDatabaseConnection(dbCDao.read(null, dbCId));
            // insert the informations into the meta_data schema
            new ProjectsDao(OpenInfraSchemas.META_DATA)
                .createOrUpdate(metaProjectsPojo, null);

        } catch (RuntimeException re) {
            // thrown by createOrUpdate
            throw new OpenInfraDatabaseException(
                    OpenInfraExceptionTypes.INSERT_META_DATA);
        }
    }

    /**
     * This method delete all entries in the meta data schema for the passed
     * project id that are necessary to access the corresponding project schema.
     *
     * @throws RuntimeException if it fails to delete the meta data correctly
     * @param projectId
     */
    private void deleteMetaData(UUID projectId) {

        try {
            // find the id of the projects table in the meta data schema by the
            // given project id
            Projects pMeta = em.createNamedQuery(
                  "Projects.findByProject",
                  Projects.class)
                  .setParameter("value", projectId)
                  .getSingleResult();

            // save the database connection Id for the next steps
            UUID dbConnId = pMeta.getDatabaseConnection().getId();

            try {
                Projects subP = null;
                ProjectsDao pDao = new ProjectsDao(OpenInfraSchemas.META_DATA);
                // if we want to delete a main project we must also delete the
                // subprojects from the meta data
                if (!pMeta.getIsSubproject()) {
                    // find all sub projects
                    List<Project> pList = new ProjectDao(
                            projectId, OpenInfraSchemas.PROJECTS).read();
                    for (Project project : pList) {
                        // for every project that is a sub project
                        if (project.getProject() != null) {
                            // retrieve the model object
                            subP = em.createNamedQuery(
                                    "Projects.findByProject",
                                    Projects.class)
                                    .setParameter("value", project.getId())
                                    .getSingleResult();
                            // deleted from the projects table in the meta data
                            // schema
                            pDao.delete(subP.getId());
                        }
                    }
                }

                // delete the entry from projects in the meta data schema
                pDao.delete(pMeta.getId());
            } catch (RuntimeException ex) {
                throw new OpenInfraWebException(ex);
            }

            // read the database connection to retrieve the id's for the next
            // step
            DatabaseConnection dc = em.find(DatabaseConnection.class, dbConnId);

            // save necessary UUIDs
            UUID serverId = dc.getServerBean().getId();
            UUID portId = dc.getPortBean().getId();
            UUID databaseId = dc.getDatabaseBean().getId();
            UUID schemaId = dc.getSchemaBean().getId();
            UUID credentialsId = dc.getCredential().getId();

            try {
                // delete the entry from database connection
                new DatabaseConnectionDao(OpenInfraSchemas.META_DATA).delete(
                        dbConnId);
            } catch (RuntimeException ex) { /* do nothing */ }

            try {
                // delete the entry from server if possible
                new ServersDao(OpenInfraSchemas.META_DATA).delete(serverId);
            } catch (RuntimeException ex) { /* do nothing */ }

            try {
                // delete the entry from port if possible
                new PortsDao(OpenInfraSchemas.META_DATA).delete(portId);
            } catch (RuntimeException ex) { /* do nothing */ }

            try {
                // delete the entry from database if possible
                new DatabasesDao(OpenInfraSchemas.META_DATA).delete(databaseId);
            } catch (RuntimeException ex) { /* do nothing */ }

            try {
                // delete the entry from schema if possible
                new SchemasDao(OpenInfraSchemas.META_DATA).delete(schemaId);
            } catch (RuntimeException ex) { /* do nothing */ }

            try {
                // delete the entry from credentials if possible
                new CredentialsDao(OpenInfraSchemas.META_DATA).delete(
                    credentialsId);
            } catch (RuntimeException ex) { /* do nothing */ }

        } catch (NoResultException nre) {
            throw new OpenInfraEntityException(
            		OpenInfraExceptionTypes.ENTITY_NOT_FOUND);
        }
    }

    /**
     * This method writes the basic project data in the new created project
     * schema. This covers only an initial entry in the table project.
     *
     * @param pojo
     * @param newProjectId
     * @throws SchemaCreationException
     * @return
     */
    private void writeBasicProjectData(ProjectPojo pojo, UUID newProjectId) {
        try {
            // create a POJO for the project in the recent created project
            // schema
            ProjectPojo newProjectPojo = new ProjectPojo();
            // set the recently generated UUID as id of the project
            newProjectPojo.setUuid(newProjectId);
            // take on the parameters of the passed POJO
            newProjectPojo.setNames(pojo.getNames());
            newProjectPojo.setDescriptions(pojo.getDescriptions());
            // set the sub project to null because it is a main project
            newProjectPojo.setSubprojectOf(null);

            // write the data of the project
            new ProjectDao(newProjectId, OpenInfraSchemas.PROJECTS)
                    .createOrUpdate(newProjectPojo, newProjectId);

        } catch (RuntimeException re) {
            throw new OpenInfraDatabaseException(
                    OpenInfraExceptionTypes.INSERT_BASIC_PROJECT_DATA);
        }
    }

    /**
     * This method merges the initial topic framework from the system schema
     * into the project schema with the passed project id.
     *
     * @param newProjectId
     * @throws SchemaCreationException
     * @return
     */
    private void mergeSystemData(UUID newProjectId) {
        try {
            // merge the content of the system schema into the new project
            // schema
            if(em.createStoredProcedureQuery(
                    "merge_system_schema", Boolean.class)
                    .registerStoredProcedureParameter(
                            "project_id",
                            UUID.class,
                            ParameterMode.IN)
                    .setParameter("project_id", newProjectId)
                    .execute()) {

            } else {
                throw new OpenInfraDatabaseException(
                        OpenInfraExceptionTypes.MERGE_SYSTEM);
            }
        } catch (PersistenceException | IllegalArgumentException pe) {
            // something went wrong while merging the data
            throw new OpenInfraDatabaseException(
                    OpenInfraExceptionTypes.MERGE_SYSTEM);
        }
    }
}
