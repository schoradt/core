package de.btu.openinfra.backend.filter;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpHeaders;

/**
 * This class represents the request filter which is configured to be a
 * pre-matching filter. This is required since the filter must be accessed
 * before the available REST URLs are parsed.
 *
 * Currently, this filter is used to replace the accept header and forward the
 * the request to the origin URI when the requested URI ends with '.xml' or
 * '.json'. This helps to keep the REST API straight and consistent while the
 * URI extension can be additionally provided.
 *
 * There still exists another filter which is used to remove trailing slashes
 * from the URI. This filter is still necessary since it is executed before this
 * filter and directly redirects (including URL rewriting) to the origin URI.
 * This behavior cannot be achieved here.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
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
						new URI(crc.getUriInfo().getRequestUri().toString()
								.replace(".json", "")));
			} else if(url.endsWith(".xml")) {
				crc.getHeaders().remove(HttpHeaders.ACCEPT);
				crc.getHeaders().add(
						HttpHeaders.ACCEPT, MediaType.APPLICATION_XML);
				URI uri = new URI(crc.getUriInfo().getRequestUri().toString()
						.replace(".xml", ""));
				crc.setRequestUri(uri);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
