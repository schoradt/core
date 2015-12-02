<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
String status = request.getParameter("status");
if(status != null && status.equalsIgnoreCase("logout")) {
	response.setStatus(200);
} else {
	response.setStatus(401);
}
%>

<html>
<head>
<style type="text/css">
	td {padding: 3px 3px 3px 3px;}
	tr.top td { border-top: thin solid black; }
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>

<c:if test="${param.status == 'logout'}">
	<p style="color: red;">Logout successful!</p>
</c:if>

<table>
<tr>
<td>
<form name="loginform" action="" method="post">
    <table align="left" border="0" cellspacing="0" cellpadding="3">
        <tr>
            <td>Username:</td>
            <td><input type="text" name="username" maxlength="30"></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="password" name="password" maxlength="30"></td>
        </tr>
        <!-- tr>
            <td colspan="2" align="left"><input type="checkbox" name="rememberMe"><font size="2">Remember Me</font></td>
        </tr-->
        <tr>
            <td colspan="2" align="right"><input type="submit" name="submit" value="Login"></td>
        </tr>
    </table>
</form>
</td>
</tr>
<tr>
<td>
<table style="border-collapse: collapse;">
<tr><th>Rollen</th><th>Nutzername</th><th>Passwort</th><th>Beschreibung</th></tr>

<tr class="top"><td rowspan=4>allgemein</td><td>root</td><td>root</td><td>Vollzugriff Projektdaten + Systemdaten (ändern + lesen)</td></tr>
<tr><td>sysadmin</td><td>sysadmin</td><td>Systemadministrator (Nutzerverwaltung)</td></tr>
<tr><td>syseditor</td><td>syseditor</td><td>Systembearbeiter (Ändern von Inhalten im Systembereich)</td></tr>
<tr><td>sysguest</td><td>sysguest</td><td>Systemgast (Lesenden Zugriff auf Inhalte im Systembereich)</td></tr>
<tr class="top"><td rowspan=3>projektbezogen</td><td>projectadmin_baal</td><td>projectadmin_baal</td><td>Projektadministrator Baalbek (Nutzerverwaltung + Projektname-/beschreibung)</td></tr>
<tr><td>projectguest_baal</td><td>projectguest_baal</td><td>Projektgast Baalbek (Lesender Zugriff auf Inhalte im Projekt Baalbek)</td></tr>
<tr><td>projecteditor_baal</td><td>projecteditor_baal</td><td>Projektarbeiter Baalbek (Ändern von Inhalten im Projekt Baalbek)</td></tr>
<tr class="top"><td rowspan=4>gemischt</td><td>max</td><td>max</td><td>Projektgast (lesender Zugriff) für das Projekt Palatin und das Testprojekt</td></tr>
<tr><td>lieschen</td><td>lieschen</td><td>Projektbearbeiter (lesen + schreiben) auf das Baalbek-Projekt (Zugriff nur auf Foto, Areal und &Ouml;ffnung)</td></tr>
<tr><td>anonymous</td><td>anonymous</td><td>Zugriff auf Baalbek und Palatin (nur lesen) jeweils nur Foto</td></tr>
<tr><td>siehstnix</td><td>siehstnix</td><td>Nutzer ohne rechte (Verhalten aus Verison 1.2.0)</td></tr>
<tr class="top"><td rowspan=8>OpenInfRA</td><td>katja</td><td>katja</td><td>root-Recht</td></tr>
<tr><td>hanna</td><td>hanna</td><td>root-Recht</td></tr>
<tr><td>dörthe</td><td>dörthe</td><td>root-Recht</td></tr>
<tr><td>franks</td><td>franks</td><td>root-Recht</td></tr>
<tr><td>frankh</td><td>frankh</td><td>root-Recht</td></tr>
<tr><td>cornell</td><td>cornell</td><td>root-Recht</td></tr>
<tr><td>alex</td><td>alex</td><td>root-Recht</td></tr>
<tr><td>philipp</td><td>philipp</td><td>root-Recht</td></tr>
<tr><td>tino</td><td>tino</td><td>root-Recht</td></tr>
</table>
<td>
</tr>
</table>


</body>
</html>