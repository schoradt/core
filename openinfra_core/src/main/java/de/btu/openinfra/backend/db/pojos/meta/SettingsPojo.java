package de.btu.openinfra.backend.db.pojos.meta;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class SettingsPojo extends OpenInfraPojo {

    private String key;
    private String value;
    private Date updatedOn;
    private ProjectsPojo project;
    
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public Date getUpdatedOn() {
        return updatedOn;
    }
    
    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public ProjectsPojo getProject() {
        return project;
    }

    public void setProject(ProjectsPojo project) {
        this.project = project;
    }
    
}
