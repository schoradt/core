package de.btu.openinfra.backend.db.pojos.solr;

import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

/**
 * This POJO is a container for a index request from the client. It contains
 * a list of project UUIDs that should be indexed.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@XmlRootElement
public class SolrIndexPojo extends OpenInfraPojo {

    private List<UUID> projects;

    public List<UUID> getProjects() {
        return projects;
    }

    public void setProjects(List<UUID> projects) {
        this.projects = projects;
    }
}
