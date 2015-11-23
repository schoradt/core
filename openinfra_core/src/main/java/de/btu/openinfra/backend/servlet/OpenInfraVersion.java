package de.btu.openinfra.backend.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import de.btu.openinfra.backend.OpenInfraApplication;

public class OpenInfraVersion extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException { }

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType(MediaType.TEXT_PLAIN);
		resp.getWriter().write(
				OpenInfraApplication.getOpenInfraVersion(getServletContext()));
	}

}
