package de.btu.openinfra.backend.rest;

import java.util.UUID;

/**
 * This is a simple container that is used to return a JSON object to the client.
 * This is strictly required by jQuery.
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class OpenInfraResultMessage {
	
	private String message;
	private UUID uuid;
	
	public OpenInfraResultMessage() {};
	
	public OpenInfraResultMessage(String message, UUID id) {
		this.message = message;
		this.uuid = id;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

}
