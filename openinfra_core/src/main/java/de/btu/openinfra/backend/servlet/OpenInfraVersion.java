package de.btu.openinfra.backend.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
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
	public static final String R = "d[-_-]b";

	@Override
	public void init() throws ServletException { }

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType(MediaType.TEXT_PLAIN);
		String t =
				OpenInfraApplication.getOpenInfraVersion(getServletContext());
		String v = getOpi() ? R : t;
		resp.getWriter().write(v);
	}

	public static boolean getOpi() {
		Calendar cal = Calendar.getInstance();
		int y = cal.get(Calendar.YEAR);
        int a = y % 19,
            b = y / 100,
            c = y % 100,
            d = b / 4,
            e = b % 4,
            g = (8 * b + 13) / 25,
            h = (19 * a + b - d - g + 15) % 30,
            j = c / 4,
            k = c % 4,
            m = (a + 11 * h) / 319,
            r = (2 * e + 2 * j - k - h + m + 32) % 7,
            n = (h - m + r + 90) / 25,
            p = (h - m + r + n + 19) % 32;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nn = (n < 10) ? "0" + n : "" + n;
        String pp = (p < 10) ? "0" + p : "" + p;
        Date d1 = null,
        	 d2 = null,
        	 d3 = null,
        	 d4 = null;
        try {
			d1 = sdf.parse(y + "-" + nn + "-" + pp);
			d2 = sdf.parse(sdf.format(new Date()));
	        cal.setTime(d1);
		    cal.add(Calendar.DAY_OF_MONTH, -3);
		    d3 = sdf.parse(sdf.format(cal.getTime()));
	        cal.setTime(d1);
		    cal.add(Calendar.DAY_OF_MONTH, +2);
		    d4 = sdf.parse(sdf.format(cal.getTime()));
		} catch (ParseException e1) { }
        return d2.after(d3) && d2.before(d4);
	}
}
