package de.btu.openinfra.backend.db;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import de.btu.openinfra.backend.OpenInfraApplication;
import de.btu.openinfra.backend.OpenInfraProperties;
import de.btu.openinfra.backend.OpenInfraPropertyKeys;
import de.btu.openinfra.backend.db.daos.meta.ProjectsDao;
import de.btu.openinfra.backend.db.jpa.model.meta.Projects;
import de.btu.openinfra.backend.db.pojos.meta.ProjectsPojo;

/**
 * This class implements access to the meta database using a singleton 
 * implementation.  
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class MetaDataManager {
	
	/**
	 * The entity manager used for accessing the meta database.
	 */
	private static EntityManager emMeta;
	
	/**
	 * This method retrieves the connection string and credentials required for
	 * accessing the current project. The current project id is set by the
	 * constructor method.
	 * 
	 * @return The connection string and credentials required to access the
	 *         current project.
	 */
	public static ProjectsPojo getProjects(UUID projectId) {	
		if(emMeta == null) {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put(
					OpenInfraPropertyKeys.JDBC_DRIVER.toString(), 
					OpenInfraPropertyValues.JDBC_DRIVER.toString());
			properties.put(
					OpenInfraPropertyKeys.USER.toString(), 
					OpenInfraProperties.getProperty(
							OpenInfraPropertyKeys.USER.toString()));
			properties.put(
					OpenInfraPropertyKeys.PASSWORD.toString(), 
					OpenInfraProperties.getProperty(
							OpenInfraPropertyKeys.PASSWORD.toString()));
			String url = String.format(
					OpenInfraPropertyValues.URL.toString(), 
					OpenInfraProperties.getProperty(
							OpenInfraPropertyKeys.SERVER.toString()), 
					OpenInfraProperties.getProperty(
							OpenInfraPropertyKeys.PORT.toString()), 
					OpenInfraProperties.getProperty(
							OpenInfraPropertyKeys.DB_NAME.toString()));
			properties.put(
					OpenInfraPropertyKeys.URL.toString(), 
					url + "currentSchema=" + 
					OpenInfraPropertyValues.META_DATA_SEARCH_PATH + "," + 
					OpenInfraPropertyValues.SEARCH_PATH);
			emMeta = Persistence.createEntityManagerFactory(
					OpenInfraApplication.PERSISTENCE_CONTEXT, 
					properties).createEntityManager();			
		}
		
		return ProjectsDao.mapPojoStatically(
				emMeta.find(Projects.class, projectId));
	}

}
