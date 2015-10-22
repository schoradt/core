package de.btu.openinfra.backend.db.jpa.model.meta;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;


/**
 * The persistent class for the logger database table.
 *
 */
@Entity
@Table(schema="meta_data")
@NamedQuery(name="Logger.findAll", query="SELECT l FROM Logger l")
@NamedNativeQueries({
    @NamedNativeQuery(name="Logger.findAllByLocaleAndOrder",
            query="SELECT *, xmin "
                    + "FROM logger "
                    + "ORDER BY %s ",
                resultClass=Logger.class)
})
public class Logger extends OpenInfraModelObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private String logger;

	//bi-directional many-to-one association to Log
	@OneToMany(mappedBy="loggerBean")
	private List<Log> logs;

	public Logger() {
	}

	public String getLogger() {
		return this.logger;
	}

	public void setLogger(String logger) {
		this.logger = logger;
	}

	public List<Log> getLogs() {
		return this.logs;
	}

	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}

	public Log addLog(Log log) {
		getLogs().add(log);
		log.setLoggerBean(this);

		return log;
	}

	public Log removeLog(Log log) {
		getLogs().remove(log);
		log.setLoggerBean(null);

		return log;
	}

}