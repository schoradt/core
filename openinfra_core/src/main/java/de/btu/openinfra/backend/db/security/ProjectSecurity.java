package de.btu.openinfra.backend.db.security;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;

import de.btu.openinfra.backend.db.daos.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.ProjectDao;
import de.btu.openinfra.backend.db.jpa.model.Project;
import de.btu.openinfra.backend.db.pojos.ProjectPojo;

public class ProjectSecurity extends OpenInfraSecurity<ProjectPojo, Project> {

	public ProjectSecurity(
			UUID currentProjectId, 
			OpenInfraSchemas schema) {
		super(currentProjectId, schema, Project.class);
	}
	
	public List<ProjectPojo> readMainProjects(Locale locale) 
			throws UnauthorizedException {
		Subject user = SecurityUtils.getSubject();
		if(user.isPermitted("/projects:get")) {
			List<ProjectPojo> list = new ProjectDao(
					null,
					OpenInfraSchemas.META_DATA).readMainProjects(locale);
			
			Iterator<ProjectPojo> it = list.iterator();
			while(it.hasNext()) {
				if(!user.isPermitted(
						"/projects/{id}:get:" + it.next().getUuid())) {
					it.remove();
				}
			}
			return list;		
		} else {
			throw new UnauthorizedException();
		}
	}

}
