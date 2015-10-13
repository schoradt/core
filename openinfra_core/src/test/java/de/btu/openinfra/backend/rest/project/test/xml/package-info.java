/**
 * This folder contains files in XML format for testing purposes.
 * 
 * In order to test the RBAC API you could use curl like so:
 * 
 * -- Login
 * curl -i -c cookie.txt -d "username=root" -d "password=root" -X POST http://localhost:8080/openinfra_core/login.jsp
 * 
 * -- Subjects
 * curl -i -b cookie.txt -X POST -H "Content-Type: application/xml" -d @C:\Users\tino\git\core\openinfra_core\src\test\java\de\btu\openinfra\backend\rest\project\test\xml\Subject.xml http://localhost:8080/openinfra_core/rest/v1/rbac/subjects
 * curl -i -b cookie.txt -X PUT -H "Content-Type: application/xml" -d @C:\Users\tino\git\core\openinfra_core\src\test\java\de\btu\openinfra\backend\rest\project\test\xml\Subject.xml http://localhost:8080/openinfra_core/rest/v1/rbac/subjects/d2d0c86d-750e-438f-8f54-60699d30a29c
 * curl -i -b cookie.txt -X DELETE http://localhost:8080/openinfra_core/rest/v1/rbac/subjects/8d5860f3-0a06-4e71-9bfe-b8e3e7eb1cba
 * 
 * -- Passwordblacklist
 * curl -i -b cookie.txt -X POST -H "Content-Type: application/xml" -d @C:\Users\tino\git\core\openinfra_core\src\test\java\de\btu\openinfra\backend\rest\project\test\xml\Passwordblacklist.xml http://localhost:8080/openinfra_core/rest/v1/rbac/passwordblacklist
 * curl -i -b cookie.txt -X PUT -H "Content-Type: application/xml" -d @C:\Users\tino\git\core\openinfra_core\src\test\java\de\btu\openinfra\backend\rest\project\test\xml\Passwordblacklist.xml http://localhost:8080/openinfra_core/rest/v1/rbac/passwordblacklist/704de597-6688-4d94-89fc-b2ba71114cd8
 * curl -i -b cookie.txt -X DELETE http://localhost:8080/openinfra_core/rest/v1/rbac/passwordblacklist/8d5860f3-0a06-4e71-9bfe-b8e3e7eb1cba
 * 
 * -- Permission
 * curl -i -b cookie.txt -X POST -H "Content-Type: application/xml" -d @C:\Users\tino\git\core\openinfra_core\src\test\java\de\btu\openinfra\backend\rest\project\test\xml\Permission.xml http://localhost:8080/openinfra_core/rest/v1/rbac/permissions
 * curl -i -b cookie.txt -X PUT -H "Content-Type: application/xml" -d @C:\Users\tino\git\core\openinfra_core\src\test\java\de\btu\openinfra\backend\rest\project\test\xml\Permission.xml http://localhost:8080/openinfra_core/rest/v1/rbac/permissions/fc41683a-ac78-46ce-8c2f-d54778093091
 * curl -i -b cookie.txt -X DELETE http://localhost:8080/openinfra_core/rest/v1/rbac/permissions/fc41683a-ac78-46ce-8c2f-d54778093091
 * 
 * -- 
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
package de.btu.openinfra.backend.rest.project.test.xml;