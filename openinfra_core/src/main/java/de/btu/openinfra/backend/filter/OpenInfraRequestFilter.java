package de.btu.openinfra.backend.filter;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpHeaders;

@PreMatching
public class OpenInfraRequestFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext crc) throws IOException {
		String url = crc.getUriInfo().getAbsolutePath().toString();
		try {
			if(url.endsWith(".json")) {
				crc.getHeaders().remove(HttpHeaders.ACCEPT);
				crc.getHeaders().add(
						HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);
				crc.setRequestUri(
						new URI(url.substring(0, url.lastIndexOf(".json"))));
			} else if(url.endsWith(".xml")) {
				crc.getHeaders().remove(HttpHeaders.ACCEPT);
				crc.getHeaders().add(
						HttpHeaders.ACCEPT, MediaType.APPLICATION_XML);
				crc.setRequestUri(
						new URI(url.substring(0, url.lastIndexOf(".xml"))));
			}			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
