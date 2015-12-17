package de.btu.openinfra.backend.db.jpa.model.meta;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;


/**
 * The persistent class for the log database table.
 *
 */
@Entity
@Table(schema="meta_data")
@NamedQueries({
	@NamedQuery(name="Log.findAll",
	        query="SELECT l FROM Log l ORDER BY l.id"),
    @NamedQuery(name="Log.count", query="SELECT COUNT(l) FROM Log l")
})
@NamedNativeQueries({
    @NamedNativeQuery(name="Log.findAllByLocaleAndOrder",
            query="SELECT l.*, l.xmin "
                    + "FROM log AS l "
                    + "LEFT OUTER JOIN ("
                        + "SELECT * FROM level) AS sq1 "
                    + "ON (l.level_id = sq1.id) "
                    + "LEFT OUTER JOIN ("
                        + "SELECT * FROM logger) AS sq2 "
                    + "ON (l.logger_id = sq2.id) "
                    + "ORDER BY %s ",
                resultClass=Log.class)
})
public class Log extends OpenInfraModelObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="created_on")
	private Timestamp createdOn;

	private String message;

	@Column(name="user_id")
	private UUID userId;

	@Column(name="username")
	private String userName;

	//bi-directional many-to-one association to Level
	@ManyToOne
	@JoinColumn(name="level_id")
	private Level levelBean;

	//bi-directional many-to-one association to Logger
	@ManyToOne
	@JoinColumn(name="logger_id")
	private Logger loggerBean;

	public Log() {
	}

	public Timestamp getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public UUID getUserId() {
		return this.userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Level getLevelBean() {
		return this.levelBean;
	}

	public void setLevelBean(Level levelBean) {
		this.levelBean = levelBean;
	}

	public Logger getLoggerBean() {
		return this.loggerBean;
	}

	public void setLoggerBean(Logger loggerBean) {
		this.loggerBean = loggerBean;
	}

}