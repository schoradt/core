package de.btu.openinfra.backend.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.HttpHeaders;

import org.apache.shiro.SecurityUtils;

/**
 * This class represents a response filter and is used to add the session
 * time out for every subject (user) to a specific header field.
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class OpenInfraResponseFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext arg0,
			ContainerResponseContext crc) throws IOException {
		crc.getHeaders().add(HttpHeaders.EXPIRES, 
				SecurityUtils.getSubject().getSession().getTimeout());
	}

}
