package de.btu.openinfra.backend.db.jpa.model.meta;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;


/**
 * The persistent class for the level database table.
 *
 */
@Entity
@Table(schema="meta_data")
@NamedQueries({
    @NamedQuery(name="Level.findAll", query="SELECT l FROM Level l"),
    @NamedQuery(name="Level.count", query="SELECT COUNT(l) FROM Level l")
})
@NamedNativeQueries({
    @NamedNativeQuery(name="Level.findAllByLocaleAndOrder",
            query="SELECT *, xmin "
                    + "FROM level "
                    + "ORDER BY %s ",
                resultClass=Level.class)
})
public class Level extends OpenInfraModelObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private String level;

	//bi-directional many-to-one association to Log
	@OneToMany(mappedBy="levelBean")
	private List<Log> logs;

	public Level() {
	}

	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public List<Log> getLogs() {
		return this.logs;
	}

	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}

	public Log addLog(Log log) {
		getLogs().add(log);
		log.setLevelBean(this);

		return log;
	}

	public Log removeLog(Log log) {
		getLogs().remove(log);
		log.setLevelBean(null);

		return log;
	}

}