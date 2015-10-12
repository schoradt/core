/**
 * This folder contains files in XML format for testing purposes.
 * 
 * In order to test the RBAC API you could use curl like so:
 * 
 * curl -i -c cookie.txt -d "username=root" -d "password=root" -X POST http://localhost:8080/openinfra_core/login.jsp
 * curl -i -b cookie.txt -X POST -H "Content-Type: application/xml" -d @C:\Users\tino\git\core\openinfra_core\src\test\java\de\btu\openinfra\backend\rest\project\test\xml\Subject.xml http://localhost:8080/openinfra_core/rest/v1/rbac/subjects
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
package de.btu.openinfra.backend.rest.project.test.xml;