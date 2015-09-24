<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
response.setStatus(401);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>

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
<table>
<tr><th>Nutzername</th><th>Passwort</th><th>Beschreibung</th></tr>
<tr><td>root</td><td>root</td><td>Vollzugriff (ändern + lesen)</td></tr>
<tr><td>max</td><td>max</td><td>Vollzugriff (nur lesen)</td></tr>
<tr><td>lieschen</td><td>lieschen</td><td>nur Zugriff auf das Baalbek-Projekt (ändern + lesen)</td></tr>
<tr><td>anonymous</td><td>anonymous</td><td>nur Zugriff auf das Testproject (nur lesen)</td></tr>
</table>
<td>
</tr>
</table>


</body>
</html>