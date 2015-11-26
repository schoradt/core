package de.btu.openinfra.backend.servlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import de.btu.openinfra.backend.OpenInfraApplication;

/**
 * A very simple servlet which is only used to transport the OpenInfRA version
 * to the client application.
 *
 * This dedicated servlet is necessary since the Jersey servlet is registered at
 * the path '/openinfra_core/rest'. There exists an agreement that the version
 * relates to the software itself and not only to the rest application. Thus,
 * the new servlet registers (see web.xml) the software version number at the
 * path '/openinfra_core/version'.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class OpenInfraVersion extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException { }

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		for(Cookie c : req.getCookies()) {
			System.out.println(c.getName() + " " + c.getValue() + " " + c.getPath() );
		}
		Enumeration<String> hn = req.getHeaderNames();
		while(hn.hasMoreElements()) {
			String name = hn.nextElement();
			Enumeration<String> contents = req.getHeaders(name);
			System.out.println("--> " + name);
			while(contents.hasMoreElements()) {
				System.out.println("   --> " + contents.nextElement());
			}
		}

		resp.setContentType(MediaType.TEXT_PLAIN);
		resp.getWriter().write(
				OpenInfraApplication.getOpenInfraVersion(getServletContext()));
	}
}
