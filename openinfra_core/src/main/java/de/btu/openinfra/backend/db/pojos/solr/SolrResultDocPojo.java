package de.btu.openinfra.backend.db.pojos.solr;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

/**
 * This POJO is a container for a document result of a Solr query.
 * <br/><br/>
 * Note: The POJOs must extend OpenInfraPojo to be accessible for the primer
 *       class. The UUID and TRID that is provided by the OpenInfraPojo will
 *       never be used.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 */
public class SolrResultDocPojo extends OpenInfraPojo {

    private UUID id;
    private String hashCode;
    private Map<String, List<String>> highlight;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public Map<String, List<String>> getHighlight() {
        return highlight;
    }

    public void setHighlight(Map<String, List<String>> highlight) {
        this.highlight = highlight;
    }
}
