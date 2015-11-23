/**
 * This package contains the REST resources of the web-application schema.
 *
 * In order to test the RBAC API you could use curl like so:
 *
 * -- Login
 * curl -i -c cookie.txt -d "username=root" -d "password=root" -X POST http://localhost:8080/openinfra_core/login.jsp
 *
 * -- WebappSystem
 * curl -i -b cookie.txt -X PUT -H "Content-Type: application/xml" -d @WebappSystem.xml http://localhost:8080/openinfra_core/rest/v1/webapp/911fee71-efb8-4194-b383-a1e54b02e806/system/807c6539-1a61-477c-bafb-138397352f30
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
package de.btu.openinfra.backend.rest.webapp;