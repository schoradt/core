package de.btu.openinfra.backend.db.pojos.meta;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class LogPojo extends OpenInfraPojo {

    private UUID userId;
    private String userName;
    private String createdOn;
    private LoggerPojo logger;
    private LevelPojo level;
    private String message;
    
    public UUID getUserId() {
        return userId;
    }
    
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getCreatedOn() {
        return createdOn;
    }
    
    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }
    
    public LoggerPojo getLogger() {
        return logger;
    }
    
    public void setLogger(LoggerPojo logger) {
        this.logger = logger;
    }
    
    public LevelPojo getLevel() {
        return level;
    }
    
    public void setLevel(LevelPojo level) {
        this.level = level;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
}
