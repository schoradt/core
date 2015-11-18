package de.btu.openinfra.plugins.solr.db.pojos;

import java.util.List;


/**
 * This POJO is a container for the result of a Solr query. It contains all Solr
 * result pojos and some meta data for the request.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 */
public class SolrResultPojo {

    private long elapsedTime;
    private long resultCount;
    private List<SolrResultDbPojo> databaseResult;

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public long getResultCount() {
        return resultCount;
    }

    public void setResultCount(long resultCount) {
        this.resultCount = resultCount;
    }

    public List<SolrResultDbPojo> getDatabaseResult() {
        return databaseResult;
    }

    public void setDatabaseResult(List<SolrResultDbPojo> databaseResult) {
        this.databaseResult = databaseResult;
    }
}
