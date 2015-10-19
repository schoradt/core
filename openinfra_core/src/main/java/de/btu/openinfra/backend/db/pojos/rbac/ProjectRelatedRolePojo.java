package de.btu.openinfra.backend.db.pojos.rbac;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.PtLocale;
import de.btu.openinfra.backend.db.jpa.model.rbac.ProjectRelatedRole;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class ProjectRelatedRolePojo extends OpenInfraPojo {
	
	private String name;
	private String description;

	public ProjectRelatedRolePojo() {}
	
	public ProjectRelatedRolePojo(ProjectRelatedRole model) {
		super(model);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    @Override
    protected void makePrimerHelper(PtLocale locale) {
        name = "";
        description = "";
    }

}
