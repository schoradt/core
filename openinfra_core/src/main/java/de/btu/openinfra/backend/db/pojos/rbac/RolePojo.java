package de.btu.openinfra.backend.db.pojos.rbac;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.jpa.model.PtLocale;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class RolePojo extends OpenInfraPojo {

	private String description;
	private String name;
	private List<PermissionPojo> permissions;
	
	public RolePojo() {}
	
	public RolePojo(OpenInfraModelObject modelObject) {
		super(modelObject);
	}

	public List<PermissionPojo> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<PermissionPojo> permissions) {
		this.permissions = permissions;
	}

	public String getDescription() {
		return this.description;
	}

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected void makePrimerHelper(PtLocale locale) {
        description = "";
        name = "";
        permissions = new ArrayList<PermissionPojo>();
        permissions.add(new PermissionPojo());
        permissions.get(0).makePrimer(locale);
    }

}