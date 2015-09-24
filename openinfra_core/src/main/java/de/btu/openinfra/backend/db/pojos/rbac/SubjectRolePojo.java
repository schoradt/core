package de.btu.openinfra.backend.db.pojos.rbac;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class SubjectRolePojo extends OpenInfraPojo {

    private RolePojo role;

    private SubjectPojo subject;

    public SubjectRolePojo() {
    }

    public SubjectRolePojo(OpenInfraModelObject modelObject) {
        super(modelObject);
    }

    public RolePojo getRole() {
        return role;
    }

    public void setRole(RolePojo role) {
        this.role = role;
    }

    public SubjectPojo getSubject() {
        return subject;
    }

    public void setSubject(SubjectPojo subject) {
        this.subject = subject;
    }

    @Override
    public void makePrimer() {
        role = new RolePojo();
        role.makePrimer();
        subject = new SubjectPojo();
        subject.makePrimer();
    }

}