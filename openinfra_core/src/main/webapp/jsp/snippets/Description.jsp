<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<ul>
    	<li>Sprachauspr&auml;gungen Beschreibung:</li>
	    <table>
	    	<tr><th class="border">Beschreibung</th>
	    	    <th class="border">LanguageCode</th>
	    	    <th class="border">countryCode</th>
	    	    <th class="border">characterCode</th>
	    	</tr>
	    	<c:choose>
	    		<c:when test="${it.descriptions.localizedStrings.size() == 0}">
		    		<tr>
			    		<td class="border">-</td>
			    		<td class="border">-</td>
			    		<td class="border">-</td>
			    		<td class="border">-</td>
		    		</tr>
	    		</c:when>
	    		<c:otherwise>
	    			<c:forEach items="${it.descriptions.localizedStrings}" var="item">
		    			<tr>
			    			<td class="border">${item.characterString}</td>
			    			<td class="border">${item.locale.languageCode}</td>
			    			<td class="border">${item.locale.countryCode}</td>
			    			<td class="border">${item.locale.characterCode}</td>
		    			</tr>
		    		</c:forEach>
	    		</c:otherwise>
	    	</c:choose>
	    </table>
    </ul>
    